package ma.TeethCare.service.test;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.mvc.dto.notification.NotificationDTO;
import ma.TeethCare.repository.api.NotificationRepository;
import ma.TeethCare.service.modules.notifications.api.notificationService;
import ma.TeethCare.service.modules.notifications.impl.notificationServiceImpl;
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

public class NotificationServiceTest {

    static class NotificationRepositoryStub implements NotificationRepository {
        private Map<Long, notification> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<notification> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public notification findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(notification entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(notification entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(notification entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public List<notification> findByNonLues() {
            return data.values().stream().filter(n -> "NON_LUE".equalsIgnoreCase(n.getStatut())).collect(Collectors.toList());
        }

        @Override
        public List<notification> findByType(String type) {
            return data.values().stream().filter(n -> n.getType() != null && n.getType().equals(type)).collect(Collectors.toList());
        }
    }

    public static void main(String[] args) {
        try {
            NotificationRepositoryStub repo = new NotificationRepositoryStub();
            notificationService service = new notificationServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            testFindByNonLues(service);
            testFindByType(service);
            
            System.out.println("All NotificationService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(notificationService service) throws Exception {
        System.out.println("Testing Create...");
        NotificationDTO n = NotificationDTO.builder()
            .titre("Rappel")
            .message("RDV demain")
            .dateNotification(LocalDateTime.now())
            .lue(false)
            .type("rappel")
            .build();
        NotificationDTO created = service.create(n);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(notificationService service) throws Exception {
        System.out.println("Testing FindById...");
        NotificationDTO n = NotificationDTO.builder().titre("Test ID").build();
        n = service.create(n);
        Optional<NotificationDTO> found = service.findById(n.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(notificationService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(NotificationDTO.builder().titre("All 1").build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(notificationService service) throws Exception {
        System.out.println("Testing Update...");
        NotificationDTO n = NotificationDTO.builder().titre("Old Title").build();
        n = service.create(n);
        n.setTitre("New Title");
        NotificationDTO updated = service.update(n);
        if (!updated.getTitre().equals("New Title")) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(notificationService service) throws Exception {
        System.out.println("Testing Delete...");
        NotificationDTO n = NotificationDTO.builder().titre("Delete Me").build();
        n = service.create(n);
        Long id = n.getId();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(notificationService service) throws Exception {
        System.out.println("Testing Exists...");
        NotificationDTO n = NotificationDTO.builder().titre("Exists").build();
        n = service.create(n);
        if (!service.exists(n.getId())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(notificationService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }

    public static void testFindByNonLues(notificationService service) throws Exception {
         System.out.println("Testing FindByNonLues...");
         NotificationDTO n1 = NotificationDTO.builder().lue(false).titre("Unread").build();
         NotificationDTO n2 = NotificationDTO.builder().lue(true).titre("Read").build();
         service.create(n1);
         service.create(n2);
         List<NotificationDTO> unread = ((notificationServiceImpl)service).findByNonLues(); 
         
         // Note: If Stub uses generic logic, it might not filter correctly unless 'lue' maps to 'statut=NON_LUE' which Stub checks.
         // Assuming Mapper handles this translation.
         // If RepositoryStub 'findByNonLues' relies on hardcoded 'statut=NON_LUE', Mapper MUST set statut="NON_LUE" when lue=false.
         
         if (unread.stream().anyMatch(n -> n.getId().equals(n2.getId()))) throw new RuntimeException("FindByNonLues failed: returned read notification");
         if (unread.stream().noneMatch(n -> n.getId().equals(n1.getId()))) throw new RuntimeException("FindByNonLues failed: missing unread notification");
         System.out.println("FindByNonLues passed.");
    }

    public static void testFindByType(notificationService service) throws Exception {
         System.out.println("Testing FindByType...");
         NotificationDTO n = NotificationDTO.builder().type("alert").titre("Alert").build();
         service.create(n);
         List<NotificationDTO> found = ((notificationServiceImpl)service).findByType("alert");
         if (found.stream().noneMatch(x -> x.getId().equals(n.getId()))) throw new RuntimeException("FindByType failed");
         System.out.println("FindByType passed.");
    }
}

