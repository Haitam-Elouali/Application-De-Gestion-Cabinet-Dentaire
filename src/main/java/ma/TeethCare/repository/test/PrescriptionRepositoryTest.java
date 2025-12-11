package ma.TeethCare.repository.test;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.mySQLImpl.PrescriptionRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class PrescriptionRepositoryTest {

    private static PrescriptionRepositoryImpl repository = new PrescriptionRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl patientRepo = new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl medecinRepo = new ma.TeethCare.repository.mySQLImpl.MedecinRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl consultationRepo = new ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.OrdonnanceRepositoryImpl ordonnanceRepo = new ma.TeethCare.repository.mySQLImpl.OrdonnanceRepositoryImpl();
    private static ma.TeethCare.repository.mySQLImpl.MedicamentRepositoryImpl medicamentRepo = new ma.TeethCare.repository.mySQLImpl.MedicamentRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: PrescriptionRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: PrescriptionRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");

        // 1. Create Patient
        ma.TeethCare.entities.patient.Patient p = new ma.TeethCare.entities.patient.Patient();
        p.setNom("PatientPrescription");
        p.setPrenom("Paul");
        p.setTelephone("0600000000");
        p.setSexe(ma.TeethCare.common.enums.Sexe.Homme);
        p.setAssurance(ma.TeethCare.common.enums.Assurance.CNOPS);
        patientRepo.create(p);
        if (p.getIdEntite() == null) throw new SQLException("Failed to create Patient for Prescription");

        // 2. Create Medecin
        ma.TeethCare.entities.medecin.medecin m = new ma.TeethCare.entities.medecin.medecin();
        m.setNom("MedecinPrescription");
        m.setEmail("dr.prescription" + System.currentTimeMillis() + "@test.com");
        m.setSpecialite("Dentiste");
        m.setSexe(ma.TeethCare.common.enums.Sexe.Femme);
        medecinRepo.create(m);
        if (m.getIdEntite() == null) throw new SQLException("Failed to create Medecin for Prescription");

        // 3. Create Consultation
        ma.TeethCare.entities.consultation.consultation c = new ma.TeethCare.entities.consultation.consultation();
        c.setPatientId(p.getIdEntite());
        c.setMedecinId(m.getIdEntite());
        c.setDate(java.time.LocalDate.now());
        c.setStatut(ma.TeethCare.common.enums.Statut.En_attente);
        consultationRepo.create(c);
        if (c.getIdEntite() == null) throw new SQLException("Failed to create Consultation for Prescription");

        // 4. Create Ordonnance
        ma.TeethCare.entities.ordonnance.ordonnance ord = new ma.TeethCare.entities.ordonnance.ordonnance();
        ord.setConsultationId(c.getIdEntite());
        ord.setMedecinId(m.getIdEntite());
        ord.setPatientId(p.getIdEntite());
        ord.setDate(java.time.LocalDate.now());
        ord.setDuree("10 jours");
        ord.setFrequence("3/jour");
        ordonnanceRepo.create(ord);
        if (ord.getIdEntite() == null) throw new SQLException("Failed to create Ordonnance for Prescription");

        // 5. Create Medicament
        ma.TeethCare.entities.medicaments.medicaments med = new ma.TeethCare.entities.medicaments.medicaments();
        med.setNom("Doliprane" + System.currentTimeMillis());
        med.setType("Paracetamol");
        med.setPrixUnitaire(20.0);
        med.setRemboursable(true);
        medicamentRepo.create(med);
        if (med.getIdEntite() == null) throw new SQLException("Failed to create Medicament for Prescription");

        // 6. Create Prescription
        prescription presc = new prescription();
        presc.setOrdonnanceId(ord.getIdEntite()); // Valid FK
        presc.setMedicamentId(med.getIdEntite()); // Valid FK
        presc.setQuantite(2);
        presc.setFrequence("Matin et Soir");
        presc.setDureeEnjours(5);
        repository.create(presc);
        
        System.out.println("Prescription created with ID: " + presc.getIdEntite());
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<prescription> list = repository.findAll();
        for (prescription p : list) {
            System.out.println("ID: " + p.getIdPr() + ", Quantité: " + p.getQuantite());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<prescription> list = repository.findAll();
        if (!list.isEmpty()) {
            prescription last = list.get(list.size() - 1);
            last.setQuantite(3);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<prescription> list = repository.findAll();
        if (!list.isEmpty()) {
            prescription last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
