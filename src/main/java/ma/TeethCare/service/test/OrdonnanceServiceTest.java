package ma.TeethCare.service.test;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.mvc.dto.ordonnance.OrdonnanceDTO;
import ma.TeethCare.repository.api.OrdonnanceRepository;
import ma.TeethCare.service.modules.dossierMedical.api.ordonnanceService;
import ma.TeethCare.service.modules.dossierMedical.impl.ordonnanceServiceImpl;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.sql.SQLException;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-09
 */

public class OrdonnanceServiceTest {

    static class OrdonnanceRepositoryStub implements OrdonnanceRepository {
        private Map<Long, ordonnance> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<ordonnance> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public ordonnance findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(ordonnance entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(ordonnance entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(ordonnance entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            OrdonnanceRepositoryStub repo = new OrdonnanceRepositoryStub();
            ordonnanceService service = new ordonnanceServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All OrdonnanceService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(ordonnanceService service) throws Exception {
        System.out.println("Testing Create...");
        OrdonnanceDTO o = OrdonnanceDTO.builder()
            .date(java.time.LocalDate.now())
            .build();
        OrdonnanceDTO created = service.create(o);
        if (created.getIdOrd() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(ordonnanceService service) throws Exception {
        System.out.println("Testing FindById...");
        OrdonnanceDTO o = OrdonnanceDTO.builder().date(java.time.LocalDate.now()).build();
        o = service.create(o);
        Optional<OrdonnanceDTO> found = service.findById(o.getIdOrd());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(ordonnanceService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(OrdonnanceDTO.builder().date(java.time.LocalDate.now()).build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(ordonnanceService service) throws Exception {
        System.out.println("Testing Update...");
        OrdonnanceDTO o = OrdonnanceDTO.builder().date(java.time.LocalDate.now()).build();
        o = service.create(o);
        o.setDate(java.time.LocalDate.of(2025, 12, 12));
        OrdonnanceDTO updated = service.update(o);
        if (!updated.getDate().equals(java.time.LocalDate.of(2025, 12, 12))) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(ordonnanceService service) throws Exception {
        System.out.println("Testing Delete...");
        OrdonnanceDTO o = OrdonnanceDTO.builder().date(java.time.LocalDate.now()).build();
        o = service.create(o);
        Long id = o.getIdOrd();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(ordonnanceService service) throws Exception {
        System.out.println("Testing Exists...");
        OrdonnanceDTO o = OrdonnanceDTO.builder().date(java.time.LocalDate.now()).build();
        o = service.create(o);
        if (!service.exists(o.getIdOrd())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(ordonnanceService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}

