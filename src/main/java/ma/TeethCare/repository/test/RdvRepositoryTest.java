package ma.TeethCare.repository.test;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.mySQLImpl.RdvRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RdvRepositoryTest {

    private static RdvRepositoryImpl repository = new RdvRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: RdvRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: RdvRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        rdv r = new rdv();
        r.setPatientId(1L); // FK
        r.setMedecinId(1L); // FK
        r.setDate(LocalDate.now().plusDays(1));
        r.setHeure(LocalTime.of(10, 0));
        r.setMotif("Consultation standard");
        r.setStatut(Statut.En_attente);
        r.setNoteMedecin("Premier RDV");
        repository.create(r);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<rdv> list = repository.findAll();
        for (rdv r : list) {
            System.out.println("ID: " + r.getIdRDV() + ", Motif: " + r.getMotif());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<rdv> list = repository.findAll();
        if (!list.isEmpty()) {
            rdv last = list.get(list.size() - 1);
            last.setStatut(Statut.Planifiee);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<rdv> list = repository.findAll();
        if (!list.isEmpty()) {
            rdv last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
