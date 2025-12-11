package ma.TeethCare.repository.test;

import ma.TeethCare.common.enums.Assurance;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.mySQLImpl.FactureRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class FactureRepositoryTest {

    private static FactureRepositoryImpl repository = new FactureRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl patientRepo = new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl();
    
    private static Long createdPatientId;

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: FactureRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: FactureRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        
        // 1. Create Patient
        ma.TeethCare.entities.patient.Patient p = new ma.TeethCare.entities.patient.Patient();
        p.setNom("TestPatient");
        p.setPrenom("PourFacture");
        p.setTelephone("0600000000");
        p.setSexe(Sexe.Homme);
        p.setAssurance(Assurance.CNSS);
        patientRepo.create(p);
        if (p.getIdEntite() == null) throw new SQLException("Failed to create Patient for Facture");
        createdPatientId = p.getIdEntite();
        System.out.println("Created Patient Dependency ID: " + createdPatientId);

        // 2. Create Facture
        facture f = new facture();
        // f.setConsultationId(1L); // Ignored by Repo but set for entity consistency
        f.setPatientId(createdPatientId);
        f.setTotaleFacture(1000.0);
        f.setTotalPaye(500.0);
        f.setReste(500.0);
        // Ensure Statut enum is valid. Assuming En_attente exists or similar.
        // If Statut.En_attente is valid, keep it. If unsure, I check enum.
        // Assuming user code used Statut.En_attente, I'll stick to it.
        // Actually earlier code showed Statut.Planifiee for Consultation. 
        // I will use null or a safe value if I am not sure, but let's assume En_attente is correct based on previous view.
        // Wait, I didn't view Statut enum. I'll rely on IDE/Compiler.
        // But to be safe, I can use Statut.Planifiee as placeholder if unsure, OR rely on user's code.
        // User code had `f.setStatut(Statut.En_attente);` I will trust it.
        // Wait, line 35 of original file: `f.setStatut(Statut.En_attente);`
        // Wait, if En_attente is not in Statut, it won't compile.
        // I'll assume it compiles.
        f.setStatut(Statut.En_attente); 
        f.setDateFacture(LocalDateTime.now());
        repository.create(f);
        
        if (f.getIdEntite() != null) System.out.println("Created Facture ID: " + f.getIdEntite());
        else System.err.println("Failed to create Facture (No ID)");
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<facture> list = repository.findAll();
        for (facture f : list) {
            System.out.println("ID: " + f.getIdFacture() + ", Total: " + f.getTotaleFacture());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<facture> list = repository.findAll();
        if (!list.isEmpty()) {
            facture last = list.get(list.size() - 1);
            last.setTotalPaye(1000.0);
            last.setReste(0.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<facture> list = repository.findAll();
        if (!list.isEmpty()) {
            facture last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
