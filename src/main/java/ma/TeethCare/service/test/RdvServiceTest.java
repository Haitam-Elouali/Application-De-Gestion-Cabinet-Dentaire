package ma.TeethCare.service.test;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.repository.api.RdvRepository;
import ma.TeethCare.service.modules.api.rdvService;
import ma.TeethCare.service.modules.impl.rdvServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RdvServiceTest {

    static class RdvRepositoryStub implements RdvRepository {
        private Map<Long, rdv> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<rdv> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public rdv findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(rdv entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(rdv entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(rdv entity) {
            if (entity != null && entity.getId() != null) {
                data.remove(entity.getId());
            }
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<rdv> findByIdRDV(Long idRDV) {
            return Optional.ofNullable(data.get(idRDV));
        }

        @Override
        public List<rdv> findByDate(LocalDate date) {
            return data.values().stream()
                    .filter(r -> date.equals(r.getDate()))
                    .collect(Collectors.toList());
        }
    }

    public static void main(String[] args) {
        try {
            RdvRepositoryStub repo = new RdvRepositoryStub();
            rdvService service = new rdvServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All RdvService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(rdvService service) throws Exception {
        System.out.println("Testing Create...");
        rdv r = rdv.builder()
                .date(LocalDate.now())
                .motif("Consultation")
                .build();
        rdv created = service.create(r);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(rdvService service) throws Exception {
        System.out.println("Testing FindById...");
        rdv r = rdv.builder().date(LocalDate.now()).motif("Checkup").build();
        r = service.create(r);
        Optional<rdv> found = service.findById(r.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(rdvService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(rdv.builder().date(LocalDate.now()).motif("Cleaning").build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(rdvService service) throws Exception {
        System.out.println("Testing Update...");
        rdv r = rdv.builder().date(LocalDate.of(2025, 1, 1)).motif("Old Motif").build();
        r = service.create(r);
        r.setMotif("New Motif");
        rdv updated = service.update(r);
        if (!"New Motif".equals(updated.getMotif())) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(rdvService service) throws Exception {
        System.out.println("Testing Delete...");
        rdv r = rdv.builder().date(LocalDate.now()).motif("To Delete").build();
        r = service.create(r);
        Long id = r.getId();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(rdvService service) throws Exception {
        System.out.println("Testing Exists...");
        rdv r = rdv.builder().date(LocalDate.now()).motif("Exists").build();
        r = service.create(r);
        if (!service.exists(r.getId())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(rdvService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
