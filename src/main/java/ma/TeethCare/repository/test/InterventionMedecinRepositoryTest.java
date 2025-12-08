package ma.TeethCare.repository.test;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.mySQLImpl.InterventionMedecinRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class InterventionMedecinRepositoryTest {

    private static InterventionMedecinRepositoryImpl repository = new InterventionMedecinRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: InterventionMedecinRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: InterventionMedecinRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        interventionMedecin i = new interventionMedecin();
        i.setMedecinId(1L); // FK
        i.setActeId(1L); // FK
        i.setConsultationId(1L); // FK
        i.setPrixDePatient(300.0);
        i.setNumDent(12);
        repository.create(i);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<interventionMedecin> list = repository.findAll();
        for (interventionMedecin i : list) {
            System.out.println("ID: " + i.getIdIM() + ", Dent: " + i.getNumDent());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<interventionMedecin> list = repository.findAll();
        if (!list.isEmpty()) {
            interventionMedecin last = list.get(list.size() - 1);
            last.setPrixDePatient(350.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<interventionMedecin> list = repository.findAll();
        if (!list.isEmpty()) {
            interventionMedecin last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
