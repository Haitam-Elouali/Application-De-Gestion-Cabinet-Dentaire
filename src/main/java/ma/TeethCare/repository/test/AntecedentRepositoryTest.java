package ma.TeethCare.repository.test;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.common.enums.niveauDeRisque;
import ma.TeethCare.repository.mySQLImpl.AntecedentRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class AntecedentRepositoryTest {

    private static AntecedentRepositoryImpl repository = new AntecedentRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: AntecedentRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: AntecedentRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        
        // 1. Create Patient
        ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl patientRepo = new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl();
        ma.TeethCare.entities.patient.Patient p = new ma.TeethCare.entities.patient.Patient();
        p.setNom("Doe");
        p.setPrenom("John");
        p.setTelephone("0612345678");
        patientRepo.create(p);
        System.out.println("Created test Patient with ID: " + p.getIdEntite());
        
        // 2. Create DossierMedicale
        ma.TeethCare.repository.mySQLImpl.DossierMedicaleRepositoryImpl dossierRepo = new ma.TeethCare.repository.mySQLImpl.DossierMedicaleRepositoryImpl();
        ma.TeethCare.entities.dossierMedicale.dossierMedicale dm = new ma.TeethCare.entities.dossierMedicale.dossierMedicale();
        dm.setPatientId(p.getIdEntite());
        dossierRepo.create(dm);
        System.out.println("Created test DossierMedicale with ID: " + dm.getIdEntite());

        antecedent a = new antecedent();
        a.setDossierMedicaleId(dm.getIdEntite()); 
        a.setNom("Diabète");
        a.setCategorie("Chronique");
        a.setNiveauRisque(niveauDeRisque.Eleve);
        repository.create(a);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<antecedent> list = repository.findAll();
        for (antecedent a : list) {
            System.out.println("ID: " + a.getIdAntecedent() + ", Nom: " + a.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<antecedent> list = repository.findAll();
        if (!list.isEmpty()) {
            antecedent last = list.get(list.size() - 1);
            last.setNom("Diabète Type 2");
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<antecedent> list = repository.findAll();
        if (!list.isEmpty()) {
            antecedent last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
