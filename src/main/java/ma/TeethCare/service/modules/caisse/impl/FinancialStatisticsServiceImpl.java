package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.repository.api.ChargesRepository;
import ma.TeethCare.repository.api.RevenuesRepository;
import ma.TeethCare.service.modules.caisse.api.FinancialStatisticsService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FinancialStatisticsServiceImpl implements FinancialStatisticsService {

    private final RevenuesRepository revenuesRepository;
    private final ChargesRepository chargesRepository;

    public FinancialStatisticsServiceImpl(RevenuesRepository revenuesRepository, ChargesRepository chargesRepository) {
        this.revenuesRepository = revenuesRepository;
        this.chargesRepository = chargesRepository;
    }

    @Override
    public FinancialSummary getFinancialSummary(LocalDateTime startDate, LocalDateTime endDate) {
        Double recettes = revenuesRepository.calculateTotalAmount(startDate, endDate);
        Double depenses = chargesRepository.calculateTotalAmount(startDate, endDate);
        return new FinancialSummary(recettes, depenses, recettes - depenses);
    }

    @Override
    public Map<String, Object> getChartData(int year) {
        Map<Integer, Double> revenuesMap = revenuesRepository.groupTotalByMonth(year);
        Map<Integer, Double> chargesMap = chargesRepository.groupTotalByMonth(year);
        
        // Prepare arrays for chart (Index 0 = Jan, 11 = Dec)
        double[] revenueData = new double[12];
        double[] expenseData = new double[12];
        
        for (int i = 0; i < 12; i++) {
            revenueData[i] = revenuesMap.getOrDefault(i + 1, 0.0);
            expenseData[i] = chargesMap.getOrDefault(i + 1, 0.0);
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("revenues", revenueData);
        data.put("expenses", expenseData);
        return data;
    }
    @Override
    public java.util.List<FinancialStatisticsService.TransactionDTO> getRecentTransactions(int limit) {
        java.util.List<FinancialStatisticsService.TransactionDTO> transactions = new java.util.ArrayList<>();
        
        try {
            // Fetch Revenues
            for (ma.TeethCare.entities.revenues.revenues r : revenuesRepository.findAll()) {
                transactions.add(new FinancialStatisticsService.TransactionDTO(
                    "Recette", 
                    r.getTitre() != null ? r.getTitre() : "Recette diverse", 
                    r.getDate() != null ? r.getDate() : LocalDateTime.now(), 
                    r.getMontant(), 
                    "Confirmé"
                ));
            }
            
            // Fetch Charges
            for (ma.TeethCare.entities.charges.charges c : chargesRepository.findAll()) {
                transactions.add(new FinancialStatisticsService.TransactionDTO(
                    "Dépense", 
                    c.getTitre() != null ? c.getTitre() : "Dépense diverse", 
                    c.getDate() != null ? c.getDate() : LocalDateTime.now(), 
                    c.getMontant(), 
                    "Payé"
                ));
            }
            
            // Sort by Date DESC
            transactions.sort(java.util.Comparator.comparing(FinancialStatisticsService.TransactionDTO::date).reversed());
            
            // Limit
            return transactions.stream().limit(limit).collect(java.util.stream.Collectors.toList());
            
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
}
