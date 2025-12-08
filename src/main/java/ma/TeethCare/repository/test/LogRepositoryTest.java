package ma.TeethCare.repository.test;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.mySQLImpl.LogRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class LogRepositoryTest {

    private static LogRepositoryImpl repository = new LogRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: LogRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: LogRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        log l = new log();
        l.setAction("CONNEXION");
        l.setUtilisateur("AdminUser");
        l.setDateAction(LocalDateTime.now());
        l.setDescription("Connexion réussie");
        l.setAdresseIP("192.168.1.1");
        repository.create(l);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<log> list = repository.findAll();
        for (log l : list) {
            System.out.println("ID: " + l.getIdLog() + ", Action: " + l.getAction());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<log> list = repository.findAll();
        if (!list.isEmpty()) {
            log last = list.get(list.size() - 1);
            last.setDescription("Modifié via test");
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<log> list = repository.findAll();
        if (!list.isEmpty()) {
            log last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
