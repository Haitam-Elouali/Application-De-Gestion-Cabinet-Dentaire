package ma.TeethCare.repository.test;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.repository.mySQLImpl.UtilisateurRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UtilisateurRepositoryTest {

    private static UtilisateurRepositoryImpl repository = new UtilisateurRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: UtilisateurRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: UtilisateurRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        utilisateur u = new utilisateur();
        u.setNom("User Test");
        u.setEmail("user@test.com");
        u.setAdresse("Adresse User");
        u.setCin("U123456");
        u.setTel("0600000003");
        u.setSexe(Sexe.Homme);
        u.setLogin("user");
        u.setMotDePasse("pass");
        u.setDateCreation(LocalDate.now());
        repository.create(u);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<utilisateur> list = repository.findAll();
        for (utilisateur u : list) {
            System.out.println("ID: " + u.getIdUser() + ", Nom: " + u.getNom());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<utilisateur> list = repository.findAll();
        if (!list.isEmpty()) {
            utilisateur last = list.get(list.size() - 1);
            last.setNom("User Modified");
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<utilisateur> list = repository.findAll();
        if (!list.isEmpty()) {
            utilisateur last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
