package ma.TeethCare.repository.test;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.mySQLImpl.PrescriptionRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class PrescriptionRepositoryTest {

    private static PrescriptionRepositoryImpl repository = new PrescriptionRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: PrescriptionRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: PrescriptionRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        prescription p = new prescription();
        p.setOrdonnanceId(1L); // FK
        p.setMedicamentId(1L); // FK
        p.setQuantite(2);
        p.setFrequence("Matin et Soir");
        p.setDureeEnjours(5);
        repository.create(p);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<prescription> list = repository.findAll();
        for (prescription p : list) {
            System.out.println("ID: " + p.getIdPr() + ", Quantité: " + p.getQuantite());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<prescription> list = repository.findAll();
        if (!list.isEmpty()) {
            prescription last = list.get(list.size() - 1);
            last.setQuantite(3);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<prescription> list = repository.findAll();
        if (!list.isEmpty()) {
            prescription last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
