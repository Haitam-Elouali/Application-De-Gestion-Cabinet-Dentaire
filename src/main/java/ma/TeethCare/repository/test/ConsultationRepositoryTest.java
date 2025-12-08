package ma.TeethCare.repository.test;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConsultationRepositoryTest {

    private static ConsultationRepositoryImpl repository = new ConsultationRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: ConsultationRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: ConsultationRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        consultation c = new consultation();
        c.setRdvId(1L); // FK placeholder
        c.setPatientId(1L); // FK placeholder
        c.setMedecinId(1L); // FK placeholder
        c.setDate(LocalDate.now());
        c.setStatut(Statut.Confirmer);
        c.setObservationMedecin("Rien à signaler");
        c.setDiagnostique("Carie simple");
        repository.create(c);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<consultation> list = repository.findAll();
        for (consultation c : list) {
            System.out.println("ID: " + c.getIdConsultation() + ", Statut: " + c.getStatut());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<consultation> list = repository.findAll();
        if (!list.isEmpty()) {
            consultation last = list.get(list.size() - 1);
            last.setObservationMedecin("Observation mise à jour");
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<consultation> list = repository.findAll();
        if (!list.isEmpty()) {
            consultation last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
