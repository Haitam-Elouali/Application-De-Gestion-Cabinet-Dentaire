package ma.TeethCare.repository.test;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.repository.mySQLImpl.MedicamentRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class MedicamentRepositoryTest {

    private static MedicamentRepositoryImpl repository = new MedicamentRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: MedicamentRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: MedicamentRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        medicaments m = new medicaments();
        m.setNom("Paracétamol");
        m.setLaboratoire("PharmaLab");
        m.setType("Comprimé");
        m.setRemboursable(true);
        m.setPrixUnitaire(20.0);
        m.setDescription("Antidouleur");
        repository.create(m);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<medicaments> list = repository.findAll();
        for (medicaments m : list) {
            System.out.println("ID: " + m.getIdMed() + ", Nom: " + m.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<medicaments> list = repository.findAll();
        if (!list.isEmpty()) {
            medicaments last = list.get(list.size() - 1);
            last.setPrixUnitaire(25.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<medicaments> list = repository.findAll();
        if (!list.isEmpty()) {
            medicaments last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
