package ma.TeethCare.repository.test;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.repository.mySQLImpl.SecretaireRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SecretaireRepositoryTest {

    private static SecretaireRepositoryImpl repository = new SecretaireRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: SecretaireRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: SecretaireRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        secretaire s = new secretaire();
        s.setIdUser(1L); // Placeholder
        s.setNom("Secretaire Test");
        long timestamp = System.currentTimeMillis();
        s.setEmail("sec" + timestamp + "@test.com");
        s.setAdresse("Adresse Sec");
        s.setCin("S123456");
        s.setTel("0600000001");
        s.setSexe(Sexe.Femme);
        s.setLogin("sec" + timestamp);
        s.setMotDePasse("pass");
        s.setDateCreation(LocalDate.now());
        s.setSalaire(5000.0);
        s.setNumCNSS("123456789");
        s.setCommission(2.5);
        repository.create(s);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<secretaire> list = repository.findAll();
        for (secretaire s : list) {
            System.out.println("ID: " + s.getIdEntite() + ", Nom: " + s.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<secretaire> list = repository.findAll();
        if (!list.isEmpty()) {
            secretaire last = list.get(list.size() - 1);
            last.setSalaire(5500.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<secretaire> list = repository.findAll();
        if (!list.isEmpty()) {
            secretaire last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
