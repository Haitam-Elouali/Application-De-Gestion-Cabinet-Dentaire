package ma.TeethCare.repository.test;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.repository.mySQLImpl.NotificationRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationRepositoryTest {

    private static NotificationRepositoryImpl repository = new NotificationRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: NotificationRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: NotificationRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        notification n = new notification();
        n.setTitre("Rappel RDV");
        n.setMessage("Vous avez un RDV demain.");
        n.setDateEnvoi(LocalDateTime.now());
        n.setType("SMS");
        n.setLue(false);
        repository.create(n);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<notification> list = repository.findAll();
        for (notification n : list) {
            System.out.println("ID: " + n.getIdNotif() + ", Titre: " + n.getTitre());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<notification> list = repository.findAll();
        if (!list.isEmpty()) {
            notification last = list.get(list.size() - 1);
            last.setLue(true);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<notification> list = repository.findAll();
        if (!list.isEmpty()) {
            notification last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
