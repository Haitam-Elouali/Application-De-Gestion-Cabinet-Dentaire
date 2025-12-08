package ma.TeethCare.repository.test;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.mySQLImpl.RevenuesRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class RevenuesRepositoryTest {

    private static RevenuesRepositoryImpl repository = new RevenuesRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: RevenuesRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: RevenuesRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        revenues r = new revenues();
        r.setFactureId(1L); // FK
        r.setTitre("Consultation Mr X");
        r.setDescription("Paiement consultation");
        r.setMontant(300.0);
        r.setDate(LocalDateTime.now());
        repository.create(r);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<revenues> list = repository.findAll();
        for (revenues r : list) {
            System.out.println("ID: " + r.getIdRevenue() + ", Montant: " + r.getMontant());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<revenues> list = repository.findAll();
        if (!list.isEmpty()) {
            revenues last = list.get(list.size() - 1);
            last.setMontant(350.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<revenues> list = repository.findAll();
        if (!list.isEmpty()) {
            revenues last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
