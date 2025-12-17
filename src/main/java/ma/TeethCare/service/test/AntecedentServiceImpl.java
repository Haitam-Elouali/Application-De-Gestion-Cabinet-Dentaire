package ma.TeethCare.service.test;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.repository.api.AntecedentRepository;
import ma.TeethCare.common.enums.niveauDeRisque;

import java.sql.SQLException;
import java.util.*;

/**
 * @author MOKADAMI Zouhair
 * @date 2025-12-17
 */

public class AntecedentServiceImpl {

    // Stub implementation for AntecedentRepository (In-Memory)
    static class AntecedentRepositoryStub implements AntecedentRepository {
        private Map<Long, antecedent> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<antecedent> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public antecedent findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(antecedent entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(antecedent entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(antecedent entity) {
            if (entity != null && entity.getId() != null) {
                data.remove(entity.getId());
            }
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public List<antecedent> findByCategorie(String categorie) {
            List<antecedent> result = new ArrayList<>();
            for (antecedent a : data.values()) {
                if (a.getCategorie() != null && a.getCategorie().equals(categorie)) {
                    result.add(a);
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        try {
            AntecedentRepositoryStub repo = new AntecedentRepositoryStub();
            AntecedentServiceImpl service = new AntecedentServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All AntecedentService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final AntecedentRepository antecedentRepository;

    public AntecedentServiceImpl(AntecedentRepository antecedentRepository) {
        this.antecedentRepository = antecedentRepository;
    }

    public antecedent create(antecedent entity) throws Exception {
        if (entity == null) {
            throw new IllegalArgumentException("Antecedent ne peut pas être null");
        }
        antecedentRepository.create(entity);
        return entity;
    }

    public Optional<antecedent> findById(Long id) throws Exception {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(antecedentRepository.findById(id));
    }

    public List<antecedent> findAll() throws Exception {
        return antecedentRepository.findAll();
    }

    public antecedent update(antecedent entity) throws Exception {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("Antecedent ou ID invalide");
        }
        antecedentRepository.update(entity);
        return entity;
    }

    public boolean delete(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        antecedentRepository.deleteById(id);
        return true;
    }

    public boolean exists(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        return antecedentRepository.findById(id) != null;
    }

    public long count() throws Exception {
        return antecedentRepository.findAll().size();
    }

    public static void testCreate(AntecedentServiceImpl service) throws Exception {
        System.out.println("Testing Create...");
        antecedent a = antecedent.builder()
                .nom("Diabetes")
                .categorie("Maladie Chronique")
                .niveauDeRisque(niveauDeRisque.Moyen)
                .dossierMedicaleId(1L)
                .build();
        antecedent created = service.create(a);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(AntecedentServiceImpl service) throws Exception {
        System.out.println("Testing FindById...");
        antecedent a = antecedent.builder()
                .nom("Hypertension")
                .categorie("Cardiovasculaire")
                .niveauDeRisque(niveauDeRisque.Eleve)
                .dossierMedicaleId(2L)
                .build();
        a = service.create(a);
        Optional<antecedent> found = service.findById(a.getId());
        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(AntecedentServiceImpl service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(antecedent.builder()
                .nom("Asthma")
                .categorie("Respiratoire")
                .niveauDeRisque(niveauDeRisque.Faible)
                .dossierMedicaleId(3L)
                .build());
        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(AntecedentServiceImpl service) throws Exception {
        System.out.println("Testing Update...");
        antecedent a = antecedent.builder()
                .nom("Allergie")
                .categorie("Allergie")
                .niveauDeRisque(niveauDeRisque.Faible)
                .dossierMedicaleId(4L)
                .build();
        a = service.create(a);
        a.setNom("Allergie Penicilline");
        antecedent updated = service.update(a);
        if (!"Allergie Penicilline".equals(updated.getNom()))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(AntecedentServiceImpl service) throws Exception {
        System.out.println("Testing Delete...");
        antecedent a = antecedent.builder()
                .nom("Cancer")
                .categorie("Oncologie")
                .niveauDeRisque(niveauDeRisque.Eleve)
                .dossierMedicaleId(5L)
                .build();
        a = service.create(a);
        Long id = a.getId();
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(AntecedentServiceImpl service) throws Exception {
        System.out.println("Testing Exists...");
        antecedent a = antecedent.builder()
                .nom("Epilepsie")
                .categorie("Neurologique")
                .niveauDeRisque(niveauDeRisque.Moyen)
                .dossierMedicaleId(6L)
                .build();
        a = service.create(a);
        if (!service.exists(a.getId()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(AntecedentServiceImpl service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0)
            throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
