package ma.TeethCare.repository.test;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.repository.mySQLImpl.AdminRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdminRepositoryTest {

    private static AdminRepositoryImpl repository = new AdminRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: AdminRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: AdminRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        admin a = new admin();
        a.setPermissionAdmin("FULL");
        a.setDomaine("IT");
        a.setNom("AdminTest");
        a.setEmail("admin.test@example.com");
        a.setTel("0600000000");
        a.setDateCreation(LocalDate.now());
        repository.create(a);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<admin> list = repository.findAll();
        for (admin a : list) {
            System.out.println("ID: " + a.getIdUser() + ", Nom: " + a.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<admin> list = repository.findAll();
        if (!list.isEmpty()) {
            admin last = list.get(list.size() - 1);
            last.setNom("Admin Modifié");
            last.setDateDerniereModification(LocalDateTime.now());
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<admin> list = repository.findAll();
        if (!list.isEmpty()) {
            admin last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
