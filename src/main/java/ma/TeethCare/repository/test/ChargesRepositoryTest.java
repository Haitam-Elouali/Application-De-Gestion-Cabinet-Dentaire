package ma.TeethCare.repository.test;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.mySQLImpl.ChargesRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ChargesRepositoryTest {

    private static ChargesRepositoryImpl repository = new ChargesRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: ChargesRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: ChargesRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        charges c = new charges();
        c.setTitre("Facture Eau");
        c.setDescription("Paiement Lydec");
        c.setMontant(150.0);
        c.setCategorie("FIXE");
        c.setDate(LocalDateTime.now());
        repository.create(c);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<charges> list = repository.findAll();
        for (charges c : list) {
            System.out.println("ID: " + c.getIdEntite() + ", Titre: " + c.getTitre());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<charges> list = repository.findAll();
        if (!list.isEmpty()) {
            charges last = list.get(list.size() - 1);
            last.setMontant(160.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<charges> list = repository.findAll();
        if (!list.isEmpty()) {
            charges last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
