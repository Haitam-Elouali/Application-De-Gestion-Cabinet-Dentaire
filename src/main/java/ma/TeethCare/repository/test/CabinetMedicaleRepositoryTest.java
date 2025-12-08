package ma.TeethCare.repository.test;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.mySQLImpl.CabinetMedicaleRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class CabinetMedicaleRepositoryTest {

    private static CabinetMedicaleRepositoryImpl repository = new CabinetMedicaleRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: CabinetMedicaleRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: CabinetMedicaleRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        cabinetMedicale c = new cabinetMedicale();
        c.setNom("Cabinet Dr Test");
        c.setEmail("contact@cabinet-test.ma");
        c.setLogo("logo.png");
        c.setCin("A12345678");
        c.setTel1("0522000000");
        c.setTel2("0661000000");
        c.setSiteWeb("www.cabinet-test.ma");
        c.setInstagram("@cabinet_test");
        c.setFacebook("Cabinet Test");
        c.setDescription("Cabinet de test");
        repository.create(c);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<cabinetMedicale> list = repository.findAll();
        for (cabinetMedicale c : list) {
            System.out.println("ID: " + c.getIdEntite() + ", Nom: " + c.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<cabinetMedicale> list = repository.findAll();
        if (!list.isEmpty()) {
            cabinetMedicale last = list.get(list.size() - 1);
            last.setNom("Cabinet Modifié");
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<cabinetMedicale> list = repository.findAll();
        if (!list.isEmpty()) {
            cabinetMedicale last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
