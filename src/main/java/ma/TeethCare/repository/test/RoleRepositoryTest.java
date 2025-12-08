package ma.TeethCare.repository.test;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.common.enums.Libeller;
import ma.TeethCare.repository.mySQLImpl.RoleRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class RoleRepositoryTest {

    private static RoleRepositoryImpl repository = new RoleRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: RoleRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: RoleRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        role r = new role();
        r.setLibeller(Libeller.Admin);
        r.setDescription("Administrateur du système");
        repository.create(r);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<role> list = repository.findAll();
        for (role r : list) {
            System.out.println("ID: " + r.getIdRole() + ", Role: " + r.getLibeller());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<role> list = repository.findAll();
        if (!list.isEmpty()) {
            role last = list.get(list.size() - 1);
            last.setDescription("Description modifiée");
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<role> list = repository.findAll();
        if (!list.isEmpty()) {
            role last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
