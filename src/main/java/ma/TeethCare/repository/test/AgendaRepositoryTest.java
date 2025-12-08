package ma.TeethCare.repository.test;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.common.enums.Mois;
import ma.TeethCare.common.enums.Jour;
import ma.TeethCare.repository.mySQLImpl.AgendaRepositoryImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendaRepositoryTest {

    private static AgendaRepositoryImpl repository = new AgendaRepositoryImpl();

    public static void main(String[] args) {
        try {
            System.out.println("========== TEST START: AgendaRepository ==========");
            createProcessTest();
            readProcessTest();
            updateProcessTest();
            deleteProcessTest();
            System.out.println("========== TEST END: AgendaRepository ==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProcessTest() throws SQLException {
        System.out.println("\n--- createProcessTest ---");
        agenda a = new agenda();
        // Assuming medecinId exists or is nullable/foreign key. 
        // Using 1L as placeholder, might fail if constraint exists and empty DB.
        a.setMedecinId(1L); 
        a.setMois(Mois.Janvier);
        List<Jour> jours = new ArrayList<>();
        jours.add(Jour.Lundi);
        a.setJoursDisponible(jours);
        a.setDateDebut(LocalDate.now());
        a.setDateFin(LocalDate.now().plusDays(30));
        repository.create(a);
    }

    static void readProcessTest() throws SQLException {
        System.out.println("\n--- readProcessTest ---");
        List<agenda> list = repository.findAll();
        for (agenda a : list) {
            System.out.println("ID: " + a.getIdAgenda() + ", Mois: " + a.getMois());
        }
    }

    static void updateProcessTest() throws SQLException {
        System.out.println("\n--- updateProcessTest ---");
        List<agenda> list = repository.findAll();
        if (!list.isEmpty()) {
            agenda last = list.get(list.size() - 1);
            last.setMois(Mois.Fevrier);
            repository.update(last);
        }
    }

    static void deleteProcessTest() throws SQLException {
        System.out.println("\n--- deleteProcessTest ---");
        List<agenda> list = repository.findAll();
        if (!list.isEmpty()) {
            agenda last = list.get(list.size() - 1);
            repository.delete(last);
        }
    }
}
