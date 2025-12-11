package ma.TeethCare.repository.test;

import ma.TeethCare.common.enums.Assurance;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConsultationRepositoryTest {

    private static ConsultationRepositoryImpl repository = new ConsultationRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl patientRepo = new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl medecinRepo = new ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl();
    
    // Track created IDs for cleanup or reuse
    private static Long createdPatientId;
    private static Long createdMedecinId;

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
        
        // 1. Create or Find Patient
        ma.TeethCare.entities.patient.Patient p = new ma.TeethCare.entities.patient.Patient();
        p.setNom("TestPatient");
        p.setPrenom("PourConsultation");
        p.setTelephone("0600000000");
        p.setSexe(ma.TeethCare.common.enums.Sexe.Homme);
        p.setAssurance(Assurance.CNSS);
        patientRepo.create(p);
        if (p.getIdEntite() == null) throw new SQLException("Failed to create Patient dependency");
        createdPatientId = p.getIdEntite();
        System.out.println("Created Patient Dependency ID: " + createdPatientId);

        // 2. Create or Find Medecin
        ma.TeethCare.entities.medecin.medecin m = new ma.TeethCare.entities.medecin.medecin();
        m.setNom("TestMedecin");
        m.setEmail("medecin" + System.currentTimeMillis() + "@test.com"); // Unique email
        m.setSpecialite("Dentiste");
        m.setSexe(ma.TeethCare.common.enums.Sexe.Femme);
        // m.setPrenom("Dr"); // Omitted as removed from schema/SQL
        medecinRepo.create(m);
        if (m.getIdEntite() == null) throw new SQLException("Failed to create Medecin dependency");
        createdMedecinId = m.getIdEntite();
        System.out.println("Created Medecin Dependency ID: " + createdMedecinId);
        
        // 3. Create Consultation
        consultation c = new consultation();
        c.setPatientId(createdPatientId);
        c.setMedecinId(createdMedecinId);
        // c.setRdvId(1L); // Omitted as removed from SQL
        c.setDate(LocalDate.now());
        c.setStatut(Statut.Planifiee);
        c.setObservationMedecin("Rien à signaler");
        c.setDiagnostique("Carie simple");
        repository.create(c);
        
        if (c.getIdEntite() == null) System.err.println("Failed to create Consultation");
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
        
        // Optional: clean up dependencies (not strict for local test but good practice)
        /*
        if (createdPatientId != null) patientRepo.deleteById(createdPatientId);
        if (createdMedecinId != null) medecinRepo.deleteById(createdMedecinId);
        */
    }
}
