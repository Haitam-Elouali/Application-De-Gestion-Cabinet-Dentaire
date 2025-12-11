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
    private static ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl patientRepo = new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl();

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

        // 1. Create Patient
        ma.TeethCare.entities.patient.Patient p = new ma.TeethCare.entities.patient.Patient();
        p.setNom("PatientRdv");
        p.setPrenom("Jean");
        p.setTelephone("0655555555");
        p.setSexe(ma.TeethCare.common.enums.Sexe.Homme);
        p.setAssurance(ma.TeethCare.common.enums.Assurance.CNOPS);
        patientRepo.create(p);
        if (p.getIdEntite() == null) throw new SQLException("Failed to create Patient for Rdv");

        rdv r = new rdv();
        r.setPatientId(p.getIdEntite()); // Valid FK
        // r.setMedecinId(1L); // FK not in schema for RDV table in text.txt, but maybe in Entity? 
        // Logic in Repo create() doesn't insert medecinId into RDV table, so it shouldn't cause SQL error unless table structure is different from text.txt
        r.setDate(LocalDate.now().plusDays(1));
        r.setHeure(LocalTime.of(10, 0));
        r.setMotif("Consultation standard");
        r.setStatut(Statut.En_attente);
        r.setNoteMedecin("Premier RDV");
        repository.create(r);
        
        System.out.println("RDV created with ID: " + r.getIdRDV());
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
