package ma.TeethCare.repository.test;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.mySQLImpl.OrdonnanceRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrdonnanceRepositoryTest {

    private static OrdonnanceRepositoryImpl repository = new OrdonnanceRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl patientRepo = new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl medecinRepo = new ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl consultationRepo = new ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl();

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

        // 1. Create Patient
        ma.TeethCare.entities.patient.Patient p = new ma.TeethCare.entities.patient.Patient();
        p.setNom("PatientOrdonnance");
        p.setPrenom("Pierre");
        p.setTelephone("0612345678");
        p.setSexe(ma.TeethCare.common.enums.Sexe.Homme);
        p.setAssurance(ma.TeethCare.common.enums.Assurance.CNOPS);
        patientRepo.create(p);
        if (p.getIdEntite() == null) throw new SQLException("Failed to create Patient for Ordonnance");

        // 2. Create Medecin
        ma.TeethCare.entities.medecin.medecin m = new ma.TeethCare.entities.medecin.medecin();
        m.setNom("MedecinOrdonnance");
        m.setEmail("dr.ordonnance" + System.currentTimeMillis() + "@test.com");
        m.setSpecialite("Generaliste");
        m.setSexe(ma.TeethCare.common.enums.Sexe.Femme);
        medecinRepo.create(m);
        if (m.getIdEntite() == null) throw new SQLException("Failed to create Medecin for Ordonnance");

        // 3. Create Consultation
        ma.TeethCare.entities.consultation.consultation c = new ma.TeethCare.entities.consultation.consultation();
        c.setPatientId(p.getIdEntite());
        c.setMedecinId(m.getIdEntite());
        c.setDate(LocalDate.now());
        c.setStatut(ma.TeethCare.common.enums.Statut.Terminee);
        consultationRepo.create(c);
        if (c.getIdEntite() == null) throw new SQLException("Failed to create Consultation for Ordonnance");

        // 4. Create Ordonnance
        ordonnance o = new ordonnance();
        o.setConsultationId(c.getIdEntite()); // Valid FK
        o.setMedecinId(m.getIdEntite()); 
        o.setPatientId(p.getIdEntite());
        o.setDate(LocalDate.now());
        o.setDuree("7 jours");
        o.setFrequence("3 fois par jour");
        repository.create(o);
        
        System.out.println("Ordonnance created with ID: " + o.getIdEntite());
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<ordonnance> list = repository.findAll();
        for (ordonnance o : list) {
            System.out.println("ID: " + o.getIdOrd() + ", Date: " + o.getDate());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<ordonnance> list = repository.findAll();
        if (!list.isEmpty()) {
            ordonnance last = list.get(list.size() - 1);
            last.setDuree("Updated Duree"); // Schema doesn't have duree, but preserving object state if repo was updated to handle it?
            // Actually repo create() ignores duree/frequence as they aren't in schema, but we can test updating date or consultationId
            last.setDate(LocalDate.now().plusDays(1));
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
