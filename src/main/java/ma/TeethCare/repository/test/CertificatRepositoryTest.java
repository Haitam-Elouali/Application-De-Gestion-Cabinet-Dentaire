package ma.TeethCare.repository.test;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.mySQLImpl.CertificatRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CertificatRepositoryTest {

    private static CertificatRepositoryImpl repository = new CertificatRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: CertificatRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: CertificatRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        certificat c = new certificat();
        c.setDateDebut(LocalDate.now());
        c.setDateFin(LocalDate.now().plusDays(5));
        c.setDuree(5);
        c.setNoteMedecin("Repos recommandé");
        repository.create(c);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<certificat> list = repository.findAll();
        for (certificat c : list) {
            System.out.println("ID: " + c.getIdCertif() + ", Duree: " + c.getDuree());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<certificat> list = repository.findAll();
        if (!list.isEmpty()) {
            certificat last = list.get(list.size() - 1);
            last.setDuree(7);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<certificat> list = repository.findAll();
        if (!list.isEmpty()) {
            certificat last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
