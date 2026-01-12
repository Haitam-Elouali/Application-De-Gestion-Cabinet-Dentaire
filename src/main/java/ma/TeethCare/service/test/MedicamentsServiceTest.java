package ma.TeethCare.service.test;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.mvc.dto.medicament.MedicamentDTO;
import ma.TeethCare.repository.api.MedicamentRepository;
import ma.TeethCare.service.modules.dossierMedical.api.medicamentsService;
import ma.TeethCare.service.modules.dossierMedical.impl.medicamentsServiceImpl;

import java.sql.SQLException;
import java.util.*;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-14
 */

public class MedicamentsServiceTest {

    static class MedicamentRepositoryStub implements MedicamentRepository {
        private Map<Long, medicaments> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<medicaments> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public medicaments findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(medicaments entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(medicaments entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(medicaments entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<medicaments> findByNom(String nom) {
            return data.values().stream().filter(m -> m.getNomCommercial().equals(nom)).findFirst();
        }
    }

    public static void main(String[] args) {
        try {
            MedicamentRepositoryStub repo = new MedicamentRepositoryStub();
            medicamentsService service = new medicamentsServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All MedicamentsService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(medicamentsService service) throws Exception {
        System.out.println("Testing Create...");
        MedicamentDTO m = MedicamentDTO.builder()
            .nom("Doliprane")
            .composition("Paracetamol")
            .prix(15.0)
            .build();
        MedicamentDTO created = service.create(m);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(medicamentsService service) throws Exception {
        System.out.println("Testing FindById...");
        MedicamentDTO m = MedicamentDTO.builder().nom("Advil").build();
        m = service.create(m);
        Optional<MedicamentDTO> found = service.findById(m.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(medicamentsService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(MedicamentDTO.builder().nom("Smecta").build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(medicamentsService service) throws Exception {
        System.out.println("Testing Update...");
        MedicamentDTO m = MedicamentDTO.builder().nom("Old Name").build();
        m = service.create(m);
        m.setNom("New Name");
        MedicamentDTO updated = service.update(m);
        if (!updated.getNom().equals("New Name")) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(medicamentsService service) throws Exception {
        System.out.println("Testing Delete...");
        MedicamentDTO m = MedicamentDTO.builder().nom("Delete Me").build();
        m = service.create(m);
        Long id = m.getId();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(medicamentsService service) throws Exception {
        System.out.println("Testing Exists...");
        MedicamentDTO m = MedicamentDTO.builder().nom("Exists").build();
        m = service.create(m);
        if (!service.exists(m.getId())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(medicamentsService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
