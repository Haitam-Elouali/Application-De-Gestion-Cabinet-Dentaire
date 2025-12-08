package ma.TeethCare.repository.test;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.mySQLImpl.DossierMedicaleRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DossierMedicaleRepositoryTest {

    private static DossierMedicaleRepositoryImpl repository = new DossierMedicaleRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: DossierMedicaleRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: DossierMedicaleRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        dossierMedicale d = new dossierMedicale();
        d.setPatientId(1L); // FK placeholder
        d.setDateDeCreation(LocalDateTime.now());
        repository.create(d);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<dossierMedicale> list = repository.findAll();
        for (dossierMedicale d : list) {
            System.out.println("ID: " + d.getIdDM());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<dossierMedicale> list = repository.findAll();
        if (!list.isEmpty()) {
            dossierMedicale last = list.get(list.size() - 1);
            last.setDateDeCreation(LocalDateTime.now().minusDays(1));
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<dossierMedicale> list = repository.findAll();
        if (!list.isEmpty()) {
            dossierMedicale last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
