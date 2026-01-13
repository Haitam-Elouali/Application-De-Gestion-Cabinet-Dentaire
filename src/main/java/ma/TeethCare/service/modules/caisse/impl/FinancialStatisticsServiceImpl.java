package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.repository.api.ChargesRepository;
import ma.TeethCare.repository.api.FactureRepository;
import ma.TeethCare.repository.api.RevenuesRepository;
import ma.TeethCare.service.modules.caisse.api.FinancialStatisticsService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FinancialStatisticsServiceImpl implements FinancialStatisticsService {

    private final RevenuesRepository revenuesRepository;
    private final ChargesRepository chargesRepository;
    private final FactureRepository factureRepository;

    public FinancialStatisticsServiceImpl(RevenuesRepository revenuesRepository, ChargesRepository chargesRepository, FactureRepository factureRepository) {
        this.revenuesRepository = revenuesRepository;
        this.chargesRepository = chargesRepository;
        this.factureRepository = factureRepository;
    }

    @Override
    public FinancialSummary getFinancialSummary(LocalDateTime startDate, LocalDateTime endDate) {
        Double recettes = revenuesRepository.calculateTotalAmount(startDate, endDate);
        Double depenses = chargesRepository.calculateTotalAmount(startDate, endDate);
        return new FinancialSummary(recettes, depenses, recettes - depenses);
    }

    @Override
    public Map<String, Object> getChartData(int year) {
        // Keeping this method signature but logic is less relevant if charts are removed from UI. 
        // Can be kept for administrative dashboards if needed.
        Map<Integer, Double> revenuesMap = revenuesRepository.groupTotalByMonth(year);
        Map<Integer, Double> chargesMap = chargesRepository.groupTotalByMonth(year);
        
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
            // Fetch Only Paid Transactions from Factures
            java.util.List<ma.TeethCare.entities.facture.facture> factures = factureRepository.findRecentPaidWithPatient(limit);
            
            for (ma.TeethCare.entities.facture.facture f : factures) {
                String patientName = "Inconnu";
                if (f.getPatient() != null) {
                    patientName = f.getPatient().getNom() + " " + f.getPatient().getPrenom();
                } else if (f.getPatientId() != null) {
                    patientName = "Patient #" + f.getPatientId();
                }
                
                transactions.add(new FinancialStatisticsService.TransactionDTO(
                    "Paiement", 
                    patientName, 
                    f.getDateFacture() != null ? f.getDateFacture() : LocalDateTime.now(), 
                    f.getTotalePaye(), // Use amount paid
                    f.getStatut() != null ? f.getStatut().toString() : "PAYEE"
                ));
            }
            
            return transactions;
            
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
}
