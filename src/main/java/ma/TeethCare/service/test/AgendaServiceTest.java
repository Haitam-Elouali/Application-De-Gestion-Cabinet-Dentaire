package ma.TeethCare.service.test;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.repository.api.AgendaRepository;
import ma.TeethCare.repository.mySQLImpl.AgendaRepositoryImpl;
import ma.TeethCare.service.modules.api.agendaService;
import ma.TeethCare.service.modules.impl.agendaServiceImpl;
import ma.TeethCare.common.enums.Mois;

import java.util.Arrays;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-10
 */

public class AgendaServiceTest {

    private static agendaService service;
    private static AgendaRepository repository;

    static {
        // Initialisation de base (structure uniquement)
        repository = new AgendaRepositoryImpl();
        service = new agendaServiceImpl(repository);
    }

    public static void main(String[] args) {
        testCreate();
        testFindById();
        testFindAll();
        testUpdate();
        testDelete();
        testExists();
        testCount();
    }

    public static void testCreate() {
        System.out.println("=== Test Create Agenda ===");

        // Structure d’un agenda pour le test
        agenda a = new agenda();
        a.setMedecinId(1L);
        a.setMois(Mois.Janvier);
        a.setJoursDisponible(Arrays.asList());

        // Appel structurel
        try {
            agenda result = service.create(a);
            System.out.println("Agenda créé : " + result);
        } catch (Exception e) {
            System.out.println("Erreur create : " + e.getMessage());
        }
    }

    public static void testFindById() {
        System.out.println("=== Test Find By Id ===");

        Long id = 1L;

        try {
            var result = service.findById(id);
            System.out.println("Agenda trouvé : " + result);
        } catch (Exception e) {
            System.out.println("Erreur findById : " + e.getMessage());
        }
    }

    public static void testFindAll() {
        System.out.println("=== Test Find All ===");

        try {
            var list = service.findAll();
            System.out.println("Liste des agendas : " + list);
        } catch (Exception e) {
            System.out.println("Erreur findAll : " + e.getMessage());
        }
    }

    public static void testUpdate() {
        System.out.println("=== Test Update Agenda ===");

        agenda a = new agenda();
        a.setIdAgenda(1L);
        a.setMedecinId(2L);
        a.setMois(Mois.Fevrier);

        try {
            agenda updated = service.update(a);
            System.out.println("Agenda mis à jour : " + updated);
        } catch (Exception e) {
            System.out.println("Erreur update : " + e.getMessage());
        }
    }

    public static void testDelete() {
        System.out.println("=== Test Delete Agenda ===");

        Long id = 1L;

        try {
            boolean deleted = service.delete(id);
            System.out.println("Suppression réussie : " + deleted);
        } catch (Exception e) {
            System.out.println("Erreur delete : " + e.getMessage());
        }
    }

    public static void testExists() {
        System.out.println("=== Test Exists ===");

        Long id = 1L;

        try {
            boolean exists = service.exists(id);
            System.out.println("Agenda existe ? " + exists);
        } catch (Exception e) {
            System.out.println("Erreur exists : " + e.getMessage());
        }
    }

    public static void testCount() {
        System.out.println("=== Test Count ===");

        try {
            long count = service.count();
            System.out.println("Nombre d'agendas : " + count);
        } catch (Exception e) {
            System.out.println("Erreur count : " + e.getMessage());
        }
    }
}