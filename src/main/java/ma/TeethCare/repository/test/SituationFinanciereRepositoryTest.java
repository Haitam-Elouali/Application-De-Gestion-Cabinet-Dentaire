package ma.TeethCare.repository.test;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.common.enums.EnPromo;
import ma.TeethCare.repository.mySQLImpl.SituationFinanciereRepositoryImpl;
import java.sql.SQLException;
import java.util.List;

public class SituationFinanciereRepositoryTest {

    private static SituationFinanciereRepositoryImpl repository = new SituationFinanciereRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: SituationFinanciereRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: SituationFinanciereRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        situationFinanciere sf = new situationFinanciere();
        sf.setPatientId(1L); // FK
        sf.setTotaleDesActes(1000.0);
        sf.setTotalPaye(500.0);
        sf.setCredit(500.0);
        sf.setReste(500.0);
        sf.setStatut(Statut.EnAttent);
        sf.setEnPromo(EnPromo.Non);
        repository.create(sf);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<situationFinanciere> list = repository.findAll();
        for (situationFinanciere sf : list) {
            System.out.println("ID: " + sf.getIdSF() + ", Reste: " + sf.getReste());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<situationFinanciere> list = repository.findAll();
        if (!list.isEmpty()) {
            situationFinanciere last = list.get(list.size() - 1);
            last.setTotalPaye(1000.0);
            last.setReste(0.0);
            last.setCredit(0.0);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<situationFinanciere> list = repository.findAll();
        if (!list.isEmpty()) {
            situationFinanciere last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
