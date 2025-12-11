package ma.TeethCare.repository.test;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.mySQLImpl.DossierMedicaleRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DossierMedicaleRepositoryTest {

    private static DossierMedicaleRepositoryImpl repository = new DossierMedicaleRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl patientRepo = new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: DossierMedicaleRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: DossierMedicaleRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        
        // 1. Create Patient
        ma.TeethCare.entities.patient.Patient p = new ma.TeethCare.entities.patient.Patient();
        p.setNom("PatientForDM");
        p.setPrenom("Test");
        p.setTelephone("0600000000");
        // p.setAdresse("Test Address"); // Not in schema
        p.setSexe(ma.TeethCare.common.enums.Sexe.Homme);
        p.setAssurance(ma.TeethCare.common.enums.Assurance.CNOPS);
        patientRepo.create(p);
        
        if (p.getIdEntite() == null) throw new SQLException("Failed to create Patient for DM");
        System.out.println("Patient created with ID: " + p.getIdEntite());

        dossierMedicale d = new dossierMedicale();
        d.setPatientId(p.getIdEntite()); 
        d.setDateDeCreation(LocalDateTime.now());
        repository.create(d);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<dossierMedicale> list = repository.findAll();
        for (dossierMedicale d : list) {
            System.out.println("ID: " + d.getIdDM() + ", PatientID: " + d.getPatientId());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<dossierMedicale> list = repository.findAll();
        if (!list.isEmpty()) {
            dossierMedicale last = list.get(list.size() - 1);
            last.setDateDeCreation(LocalDateTime.now().minusDays(1));
            // Ensure patientId is preserved or re-set if needed, though update usually touches specific fields
            // repository.update uses what's in 'last', which should be full from findAll/RowMapper
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<dossierMedicale> list = repository.findAll();
        if (!list.isEmpty()) {
            dossierMedicale last = list.get(list.size() - 1);
            repository.delete(last);
        }
        // Cleanup patient? (optional)
        // patientRepo.deleteById(patientId);
    }
}
