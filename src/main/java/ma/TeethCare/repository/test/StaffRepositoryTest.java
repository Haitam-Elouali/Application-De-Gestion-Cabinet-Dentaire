package ma.TeethCare.repository.test;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.repository.mySQLImpl.StaffRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StaffRepositoryTest {

    private static StaffRepositoryImpl repository = new StaffRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: StaffRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: StaffRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        staff s = new staff();
        s.setIdUser(1L); // Placeholder
        s.setNom("Staff Test");
        s.setEmail("staff@test.com");
        s.setAdresse("Adresse Staff");
        s.setCin("ST123456");
        s.setTel("0600000002");
        s.setSexe(Sexe.Homme);
        s.setLogin("staff");
        s.setMotDePasse("pass");
        s.setDateCreation(LocalDate.now());
        s.setSalaire(4000.0);
        s.setPrime(200.0);
        s.setSoldeConge(20);
        repository.create(s);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<staff> list = repository.findAll();
        for (staff s : list) {
            System.out.println("ID: " + s.getIdEntite() + ", Nom: " + s.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<staff> list = repository.findAll();
        if (!list.isEmpty()) {
            staff last = list.get(list.size() - 1);
            last.setSalaire(4500.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<staff> list = repository.findAll();
        if (!list.isEmpty()) {
            staff last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
