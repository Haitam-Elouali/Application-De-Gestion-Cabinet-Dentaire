package ma.TeethCare.service.test;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.api.ChargesRepository;
import ma.TeethCare.service.modules.caisse.api.chargesService;
import ma.TeethCare.service.modules.caisse.impl.chargesServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.SQLException;

/**
 * @author Haitam ELOUALI
 * @date 2025-12-14
 */

import ma.TeethCare.service.modules.caisse.dto.ChargesDto;

public class ChargesServiceTest {

    static class ChargesRepositoryStub implements ChargesRepository {
        private Map<Long, charges> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<charges> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public charges findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(charges entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(charges entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(charges entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<charges> findByTitre(String titre) {
            return data.values().stream().filter(c -> c.getTitre().equals(titre)).findFirst();
        }
    }

    public static void main(String[] args) {
        try {
            ChargesRepositoryStub repo = new ChargesRepositoryStub();
            chargesService service = new chargesServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All ChargesService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(chargesService service) throws Exception {
        System.out.println("Testing Create...");
        ChargesDto c = new ChargesDto(null, "Facture Electrique", "Desc", 500.0, "Utilities", LocalDateTime.now(), 1L);

        ChargesDto created = service.create(c);
        if (created.id() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(chargesService service) throws Exception {
        System.out.println("Testing FindById...");
        ChargesDto c = new ChargesDto(null, "Test ID", "Desc", 100.0, "Cat", LocalDateTime.now(), 1L);
        c = service.create(c);

        ChargesDto found = service.findById(c.id());
        if (found == null)
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(chargesService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(new ChargesDto(null, "All 1", "Desc", 100.0, "Cat", LocalDateTime.now(), 1L));

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(chargesService service) throws Exception {
        System.out.println("Testing Update...");
        ChargesDto c = new ChargesDto(null, "Old Title", "Desc", 100.0, "Cat", LocalDateTime.now(), 1L);
        c = service.create(c);

        ChargesDto toUpdate = new ChargesDto(c.id(), "New Title", c.description(), c.montant(), c.categorie(), c.date(),
                c.cabinetId());

        ChargesDto updated = service.update(c.id(), toUpdate);
        if (!updated.titre().equals("New Title"))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(chargesService service) throws Exception {
        System.out.println("Testing Delete...");
        ChargesDto c = new ChargesDto(null, "Delete Me", "Desc", 100.0, "Cat", LocalDateTime.now(), 1L);
        c = service.create(c);
        Long id = c.id();
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(chargesService service) throws Exception {
        System.out.println("Testing Exists...");
        ChargesDto c = new ChargesDto(null, "Exists", "Desc", 100.0, "Cat", LocalDateTime.now(), 1L);
        c = service.create(c);
        if (!service.exists(c.id()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(chargesService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0)
            throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
