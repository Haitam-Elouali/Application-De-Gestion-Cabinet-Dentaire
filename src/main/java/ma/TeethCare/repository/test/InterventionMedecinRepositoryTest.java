package ma.TeethCare.repository.test;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.mySQLImpl.InterventionMedecinRepositoryImpl;
import ma.TeethCare.common.enums.Assurance;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.common.enums.Statut;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InterventionMedecinRepositoryTest {

    private static InterventionMedecinRepositoryImpl repository = new InterventionMedecinRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl patientRepo = new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl medecinRepo = new ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl consultationRepo = new ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl();

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
        
        // 1. Create Patient
        ma.TeethCare.entities.patient.Patient p = new ma.TeethCare.entities.patient.Patient();
        p.setNom("PatientIntervention");
        p.setPrenom("Test");
        p.setTelephone("0600000000");
        p.setSexe(Sexe.Homme);
        p.setAssurance(Assurance.CNOPS);
        patientRepo.create(p);
        if (p.getIdEntite() == null) throw new SQLException("Failed to create Patient for Intervention");

        // 2. Create Medecin
        ma.TeethCare.entities.medecin.medecin m = new ma.TeethCare.entities.medecin.medecin();
        m.setNom("MedecinIntervention");
        m.setEmail("dr.intervention" + System.currentTimeMillis() + "@test.com");
        m.setSpecialite("Chirurgien");
        m.setSexe(Sexe.Homme);
        medecinRepo.create(m);
        if (m.getIdEntite() == null) throw new SQLException("Failed to create Medecin for Intervention");

        // 3. Create Consultation
        ma.TeethCare.entities.consultation.consultation c = new ma.TeethCare.entities.consultation.consultation();
        c.setPatientId(p.getIdEntite());
        c.setMedecinId(m.getIdEntite());
        c.setDate(LocalDate.now());
        c.setStatut(Statut.Terminee);
        consultationRepo.create(c);
        if (c.getIdEntite() == null) throw new SQLException("Failed to create Consultation for Intervention");

        // 4. Create Intervention
        interventionMedecin i = new interventionMedecin();
        i.setMedecinId(m.getIdEntite());
        i.setConsultationId(c.getIdEntite()); // FK
        
        // i.setPrixDePatient(300.0); // Not in schema
        // i.setNumDent(12); // Not in schema
        
        repository.create(i);
        
        System.out.println("InterventionMedecin created with ID: " + i.getIdEntite());
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<interventionMedecin> list = repository.findAll();
        for (interventionMedecin i : list) {
            System.out.println("ID: " + i.getIdIM());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<interventionMedecin> list = repository.findAll();
        if (!list.isEmpty()) {
            interventionMedecin last = list.get(list.size() - 1);
            // Update logic if needed, but fields removed. Just testing update call works.
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
