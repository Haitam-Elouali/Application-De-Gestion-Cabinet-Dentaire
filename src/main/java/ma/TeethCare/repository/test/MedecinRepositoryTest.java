package ma.TeethCare.repository.test;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MedecinRepositoryTest {

    private static MedecinRepositoryImpl repository = new MedecinRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: MedecinRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: MedecinRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        medecin m = new medecin();
        m.setIdUser(2L); // ID is ignored in create
        m.setNom("Dr House");
        long timestamp = System.currentTimeMillis();
        m.setEmail("house" + timestamp + "@hospital.com");
        // m.setAdresse("Princeton"); // Not in schema
        // m.setCin("TEST12345"); // Not in schema
        m.setTel("0600112233");
        m.setSexe(Sexe.Homme);
        m.setLogin("house" + timestamp);
        m.setMotDePasse("vicodin");
        m.setDateCreation(LocalDate.now());
        m.setSpecialite("Diagnostic");
        // m.setNumeroOrdre("ORD123"); // Not in schema
        // m.setDiplome("MD"); // Not in schema
        m.setSalaire(10000.0);
        // m.setPrime(0.0); // Not in schema (Staff table only has salaire, recrutement)
        // m.setSoldeConge(30); // Not in schema
        repository.create(m);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<medecin> list = repository.findAll();
        for (medecin m : list) {
            System.out.println("ID: " + m.getIdMedecin() + ", Nom: " + m.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<medecin> list = repository.findAll();
        if (!list.isEmpty()) {
            medecin last = list.get(list.size() - 1);
            last.setSalaire(12000.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<medecin> list = repository.findAll();
        if (!list.isEmpty()) {
            medecin last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
