package ma.TeethCare.repository.test;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.repository.mySQLImpl.CaisseRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CaisseRepositoryTest {

    private static CaisseRepositoryImpl repository = new CaisseRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: CaisseRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: CaisseRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        caisse c = new caisse();
        c.setFactureId(1L); // Placeholder
        c.setMontant(500.0);
        c.setDateEncaissement(LocalDate.now());
        c.setModeEncaissement("Espece");
        c.setReference("REF1234");
        repository.create(c);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<caisse> list = repository.findAll();
        for (caisse c : list) {
            System.out.println("ID: " + c.getIdCaisse() + ", Montant: " + c.getMontant());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<caisse> list = repository.findAll();
        if (!list.isEmpty()) {
            caisse last = list.get(list.size() - 1);
            last.setMontant(600.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<caisse> list = repository.findAll();
        if (!list.isEmpty()) {
            caisse last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
