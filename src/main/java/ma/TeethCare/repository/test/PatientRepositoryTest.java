package ma.TeethCare.repository.test;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.common.enums.Assurance;
import ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PatientRepositoryTest {

    private static PatientRepositoryImpl repository = new PatientRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: PatientRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: PatientRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        Patient p = new Patient();
        p.setNom("PatientTest");
        p.setPrenom("Test");
        p.setAdresse("Rue de test");
        p.setTelephone("0612345678");
        p.setEmail("patient@test.com");
        p.setDateNaissance(LocalDate.of(1990, 1, 1));
        p.setSexe(Sexe.Homme);
        p.setAssurance(Assurance.CNOPS);
        repository.create(p);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<Patient> list = repository.findAll();
        for (Patient p : list) {
            System.out.println("ID: " + p.getIdEntite() + ", Nom: " + p.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<Patient> list = repository.findAll();
        if (!list.isEmpty()) {
            Patient last = list.get(list.size() - 1);
            last.setAdresse("Nouvelle adresse");
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<Patient> list = repository.findAll();
        if (!list.isEmpty()) {
            Patient last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
