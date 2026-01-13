package ma.TeethCare.service.modules.caisse.api;

import java.time.LocalDateTime;
import java.util.Map;

public interface FinancialStatisticsService {
    
    record FinancialSummary(Double totalRecettes, Double totalDepenses, Double benefice) {}
    
    // Simple DTO for unified view
    record TransactionDTO(String type, String label, LocalDateTime date, Double amount, String status) {}
    
    FinancialSummary getFinancialSummary(LocalDateTime startDate, LocalDateTime endDate);
    
    Map<String, Object> getChartData(int year);
    
    java.util.List<TransactionDTO> getRecentTransactions(int limit);
}
