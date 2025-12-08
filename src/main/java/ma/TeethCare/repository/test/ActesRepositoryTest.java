package ma.TeethCare.repository.test;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.repository.mySQLImpl.ActesRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class ActesRepositoryTest {

    private static ActesRepositoryImpl repository = new ActesRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: ActesRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: ActesRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        actes acte = new actes();
        acte.setLibeller("Détartrage Test");
        acte.setCategorie("Soins");
        acte.setPrixDeBase(350.0);
        repository.create(acte);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<actes> list = repository.findAll();
        for (actes a : list) {
            System.out.println("ID: " + a.getIdEntite() + ", Libelle: " + a.getLibeller());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<actes> list = repository.findAll();
        if (!list.isEmpty()) {
            actes last = list.get(list.size() - 1);
            last.setLibeller("Détartrage Modifié");
            repository.update(last);
        } else {
            System.out.println("Aucun acte à mettre à jour.");
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<actes> list = repository.findAll();
        if (!list.isEmpty()) {
            actes last = list.get(list.size() - 1);
            repository.delete(last);
        } else {
            System.out.println("Aucun acte à supprimer.");
        }
    }
}
