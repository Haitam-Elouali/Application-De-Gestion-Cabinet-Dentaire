package ma.TeethCare.service.test;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.api.LogRepository;
import ma.TeethCare.service.modules.api.logService;
import ma.TeethCare.service.modules.impl.LogServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.SQLException;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-14
 */

public class LogServiceTest {

    static class LogRepositoryStub implements LogRepository {
        private Map<Long, log> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<log> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public log findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(log entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(log entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(log entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public List<log> findByUtilisateur(String utilisateur) {
            // Mock implementation: filter by utilisateur entity username if available
            return data.values().stream()
                .filter(l -> l.getUtilisateurEntity() != null && utilisateur.equals(l.getUtilisateurEntity().getUsername()))
                .collect(Collectors.toList());
        }

        @Override
        public List<log> findByAction(String action) {
             // Mock implementation: map action to typeSupp
            return data.values().stream().filter(l -> l.getTypeSupp() != null && l.getTypeSupp().equals(action)).collect(Collectors.toList());
        }

        @Override
        public List<log> findByDateRange(LocalDateTime debut, LocalDateTime fin) {
            return data.values().stream()
                .filter(l -> l.getDateAction() != null && !l.getDateAction().isBefore(debut) && !l.getDateAction().isAfter(fin))
                .collect(Collectors.toList());
        }
    }

    public static void main(String[] args) {
        try {
            LogRepositoryStub repo = new LogRepositoryStub();
            logService service = new LogServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            testCustomQueries((LogServiceImpl)service);
            
            System.out.println("All LogService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(logService service) throws Exception {
        System.out.println("Testing Create...");
        log l = log.builder()
            .typeSupp("LOGIN")
            .dateAction(LocalDateTime.now())
            .message("User logged in")
            .build();
        log created = service.create(l);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(logService service) throws Exception {
        System.out.println("Testing FindById...");
        log l = log.builder().typeSupp("TEST_ID").dateAction(LocalDateTime.now()).build();
        l = service.create(l);
        Optional<log> found = service.findById(l.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(logService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(log.builder().typeSupp("ALL").dateAction(LocalDateTime.now()).build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(logService service) throws Exception {
        System.out.println("Testing Update...");
        log l = log.builder().typeSupp("OLD").dateAction(LocalDateTime.now()).build();
        l = service.create(l);
        l.setTypeSupp("NEW");
        log updated = service.update(l);
        if (!updated.getTypeSupp().equals("NEW")) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(logService service) throws Exception {
        System.out.println("Testing Delete...");
        log l = log.builder().typeSupp("DELETE").dateAction(LocalDateTime.now()).build();
        l = service.create(l);
        Long id = l.getId();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(logService service) throws Exception {
        System.out.println("Testing Exists...");
        log l = log.builder().typeSupp("EXISTS").dateAction(LocalDateTime.now()).build();
        l = service.create(l);
        if (!service.exists(l.getId())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(logService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
    
    public static void testCustomQueries(LogServiceImpl service) throws Exception {
        System.out.println("Testing Custom Queries...");
        LocalDateTime now = LocalDateTime.now();
        // Create dummy user for query
        ma.TeethCare.entities.utilisateur.utilisateur u1 = ma.TeethCare.entities.utilisateur.utilisateur.builder().username("u1").build();
        ma.TeethCare.entities.utilisateur.utilisateur u2 = ma.TeethCare.entities.utilisateur.utilisateur.builder().username("u2").build();
        
        log l1 = log.builder().typeSupp("QUERY").utilisateurEntity(u1).dateAction(now).build();
        log l2 = log.builder().typeSupp("QUERY").utilisateurEntity(u2).dateAction(now.minusHours(1)).build();
        service.create(l1);
        service.create(l2);
        
        if (service.findByUtilisateur("u1").size() != 1) throw new RuntimeException("findByUtilisateur failed");
        if (service.findByAction("QUERY").size() < 2) throw new RuntimeException("findByAction failed"); 
        
        List<log> range = service.findByDateRange(now.minusMinutes(30), now.plusMinutes(30));
        // Should find l1 but not l2
        boolean hasL1 = range.stream().anyMatch(l -> l.getId().equals(l1.getId()));
        boolean hasL2 = range.stream().anyMatch(l -> l.getId().equals(l2.getId()));
        
        if (!hasL1 || hasL2) throw new RuntimeException("findByDateRange failed");
        
        System.out.println("Custom Queries passed.");
    }
}

