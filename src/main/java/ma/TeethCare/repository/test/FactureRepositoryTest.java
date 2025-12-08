package ma.TeethCare.repository.test;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.mySQLImpl.FactureRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class FactureRepositoryTest {

    private static FactureRepositoryImpl repository = new FactureRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: FactureRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: FactureRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        facture f = new facture();
        f.setConsultationId(1L); // FK
        f.setPatientId(1L); // FK
        f.setTotaleFacture(1000.0);
        f.setTotalPaye(500.0);
        f.setReste(500.0);
        f.setStatut(Statut.EnAttent); // Might be typo in enum "EnAttent" vs "EnAttente"
        f.setDateFacture(LocalDateTime.now());
        repository.create(f);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<facture> list = repository.findAll();
        for (facture f : list) {
            System.out.println("ID: " + f.getIdFacture() + ", Total: " + f.getTotaleFacture());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<facture> list = repository.findAll();
        if (!list.isEmpty()) {
            facture last = list.get(list.size() - 1);
            last.setTotalPaye(1000.0);
            last.setReste(0.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<facture> list = repository.findAll();
        if (!list.isEmpty()) {
            facture last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
