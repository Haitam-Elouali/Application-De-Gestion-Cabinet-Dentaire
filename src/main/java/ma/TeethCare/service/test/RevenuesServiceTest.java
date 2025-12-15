package ma.TeethCare.service.test;
import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.service.modules.impl.revenuesServiceImpl;
import ma.TeethCare.service.modules.api.revenuesService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author ELOUALI Haitam
 * @date 2025-12-09
 */

public class RevenuesServiceTest {

    private static final revenuesService service = new revenuesServiceImpl();
    private static Long createdId;

    public static void main(String[] args) {
        try {
            System.out.println("--- Starting RevenuesServiceTest ---");
            testCreate();
            testFindById();
            testFindAll();
            testUpdate();
            testExists();
            testCount();
            testDelete();
            System.out.println("--- RevenuesServiceTest Completed ---");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate() throws Exception {
        System.out.println("\n[Test Create]");
        revenues r = revenues.builder()
                .titre("Consultation Test")
                .description("Test Description")
                .montant(300.0)
                .date(LocalDateTime.now())
                .build();
        
        revenues created = service.create(r);
        if (created != null && created.getIdEntite() != null) {
            createdId = created.getIdEntite();
            System.out.println("SUCCESS: Created Revenue with ID: " + createdId);
        } else {
            System.err.println("FAILURE: Create returned null or no ID");
        }
    }

    public static void testFindById() throws Exception {
        System.out.println("\n[Test FindById]");
        if (createdId == null) { System.out.println("Skipping: No ID"); return; }
        
        Optional<revenues> opt = service.findById(createdId);
        if (opt.isPresent()) {
            System.out.println("SUCCESS: Found Revenue: " + opt.get().getTitre());
        } else {
            System.err.println("FAILURE: Revenue not found with ID: " + createdId);
        }
    }

    public static void testFindAll() throws Exception {
        System.out.println("\n[Test FindAll]");
        List<revenues> list = service.findAll();
        System.out.println("SUCCESS: Retrieved " + list.size() + " revenues.");
        list.forEach(r -> System.out.println(" - " + r.getIdEntite() + ": " + r.getTitre()));
    }

    public static void testUpdate() throws Exception {
        System.out.println("\n[Test Update]");
        if (createdId == null) { System.out.println("Skipping: No ID"); return; }

        Optional<revenues> opt = service.findById(createdId);
        if (opt.isPresent()) {
            revenues r = opt.get();
            r.setTitre("Consultation Updated");
            r.setMontant(450.0);
            
            service.update(r);
            
            Optional<revenues> updatedOpt = service.findById(createdId);
            if (updatedOpt.isPresent() && "Consultation Updated".equals(updatedOpt.get().getTitre())) {
                System.out.println("SUCCESS: Updated Revenue Titre: " + updatedOpt.get().getTitre());
            } else {
                System.err.println("FAILURE: Update failed verification");
            }
        }
    }

    public static void testDelete() throws Exception {
        System.out.println("\n[Test Delete]");
        if (createdId == null) { System.out.println("Skipping: No ID"); return; }
        
        boolean deleted = service.delete(createdId);
        if (deleted) {
            System.out.println("SUCCESS: Deleted Revenue with ID: " + createdId);
            if (!service.exists(createdId)) {
                System.out.println("VERIFIED: Revenue no longer exists.");
            } else {
                System.err.println("FAILURE: Revenue still exists after delete");
            }
        } else {
            System.err.println("FAILURE: Delete returned false");
        }
    }

    public static void testExists() throws Exception {
        System.out.println("\n[Test Exists]");
        if (createdId == null) { System.out.println("Skipping: No ID"); return; }
        
        boolean exists = service.exists(createdId);
        System.out.println(exists ? "SUCCESS: Revenue exists" : "FAILURE: Revenue should exist");
    }

    public static void testCount() throws Exception {
        System.out.println("\n[Test Count]");
        long count = service.count();
        System.out.println("SUCCESS: Count is " + count);
    }
}
