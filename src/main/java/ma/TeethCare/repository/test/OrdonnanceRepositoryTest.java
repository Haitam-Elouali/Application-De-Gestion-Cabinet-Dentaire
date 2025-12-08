package ma.TeethCare.repository.test;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.mySQLImpl.OrdonnanceRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrdonnanceRepositoryTest {

    private static OrdonnanceRepositoryImpl repository = new OrdonnanceRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: OrdonnanceRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: OrdonnanceRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        ordonnance o = new ordonnance();
        o.setConsultationId(1L); // FK
        o.setMedecinId(1L); // FK
        o.setPatientId(1L); // FK
        o.setDate(LocalDate.now());
        o.setDuree("7 jours");
        o.setFrequence("3 fois par jour");
        repository.create(o);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<ordonnance> list = repository.findAll();
        for (ordonnance o : list) {
            System.out.println("ID: " + o.getIdOrd() + ", Durée: " + o.getDuree());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<ordonnance> list = repository.findAll();
        if (!list.isEmpty()) {
            ordonnance last = list.get(list.size() - 1);
            last.setDuree("10 jours");
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<ordonnance> list = repository.findAll();
        if (!list.isEmpty()) {
            ordonnance last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
