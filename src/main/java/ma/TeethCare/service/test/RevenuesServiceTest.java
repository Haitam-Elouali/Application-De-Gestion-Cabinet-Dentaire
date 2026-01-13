package ma.TeethCare.service.test;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.service.modules.caisse.impl.revenuesServiceImpl;
import ma.TeethCare.service.modules.caisse.api.revenuesService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author ELOUALI Haitam
 * @date 2025-12-09
 */

import ma.TeethCare.mvc.dto.revenues.RevenuesDTO;

public class RevenuesServiceTest {

    // Stub implementation for RevenuesRepository
    static class RevenuesRepositoryStub implements ma.TeethCare.repository.api.RevenuesRepository {
        private java.util.Map<Long, revenues> data = new java.util.HashMap<>();
        private long idCounter = 1;

        @Override
        public void create(revenues entity) {
            if (entity.getId() == null)
                entity.setId(idCounter++);
            data.put(entity.getId(), entity);
        }

        @Override
        public revenues findById(Long id) {
            return data.get(id);
        }

        @Override
        public java.util.List<revenues> findAll() {
            return new java.util.ArrayList<>(data.values());
        }

        @Override
        public void update(revenues entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(revenues entity) {
            if (entity != null)
                data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public java.util.Optional<revenues> findByTitre(String titre) {
            return data.values().stream()
                    .filter(r -> r.getTitre() != null && r.getTitre().equals(titre))
                    .findFirst();
        }

        @Override
        public java.util.List<revenues> findByDateBetween(java.time.LocalDateTime startDate,
                java.time.LocalDateTime endDate) {
            return data.values().stream()
                    .filter(r -> r.getDate() != null
                            && (r.getDate().isEqual(startDate) || r.getDate().isAfter(startDate))
                            && (r.getDate().isEqual(endDate) || r.getDate().isBefore(endDate)))
                    .collect(java.util.stream.Collectors.toList());
        }
        @Override
        public Double calculateTotalAmount(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate) {
            return 0.0;
        }

        @Override
        public java.util.Map<Integer, Double> groupTotalByMonth(int year) {
            return new java.util.HashMap<>();
        }
    }

    private static revenuesService service;
    private static Long createdId;

    public static void main(String[] args) {
        service = new revenuesServiceImpl(new RevenuesRepositoryStub());
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
        RevenuesDTO r = RevenuesDTO.builder()
                .cabinetId(1L)
                .titre("Consultation Test")
                .description("Test Description")
                .montant(300.0)
                .categorie("CONSULT")
                .date(LocalDateTime.now())
                .build();

        RevenuesDTO created = service.create(r);
        if (created != null && created.getId() != null) {
            createdId = created.getId();
            System.out.println("SUCCESS: Created Revenue with ID: " + createdId);
        } else {
            System.err.println("FAILURE: Create returned null or no ID");
        }
    }

    public static void testFindById() throws Exception {
        System.out.println("\n[Test FindById]");
        if (createdId == null) {
            System.out.println("Skipping: No ID");
            return;
        }

        Optional<RevenuesDTO> found = service.findById(createdId);
        if (found.isPresent()) {
            System.out.println("SUCCESS: Found Revenue: " + found.get().getTitre());
        } else {
            System.err.println("FAILURE: Revenue not found with ID: " + createdId);
        }
    }

    public static void testFindAll() throws Exception {
        System.out.println("\n[Test FindAll]");
        List<RevenuesDTO> list = service.findAll();
        System.out.println("SUCCESS: Retrieved " + list.size() + " revenues.");
        list.forEach(r -> System.out.println(" - " + r.getId() + ": " + r.getTitre()));
    }

    public static void testUpdate() throws Exception {
        System.out.println("\n[Test Update]");
        if (createdId == null) {
            System.out.println("Skipping: No ID");
            return;
        }

        Optional<RevenuesDTO> rOpt = service.findById(createdId);
        if (rOpt.isPresent()) {
            RevenuesDTO r = rOpt.get();
            r.setTitre("Consultation Updated");
            r.setMontant(450.0);

            service.update(r);

            Optional<RevenuesDTO> updatedOpt = service.findById(createdId);
            if (updatedOpt.isPresent() && "Consultation Updated".equals(updatedOpt.get().getTitre())) {
                System.out.println("SUCCESS: Updated Revenue Titre: " + updatedOpt.get().getTitre());
            } else {
                System.err.println("FAILURE: Update failed verification");
            }
        }
    }

    public static void testDelete() throws Exception {
        System.out.println("\n[Test Delete]");
        if (createdId == null) {
            System.out.println("Skipping: No ID");
            return;
        }

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
        if (createdId == null) {
            System.out.println("Skipping: No ID");
            return;
        }

        boolean exists = service.exists(createdId);
        System.out.println(exists ? "SUCCESS: Revenue exists" : "FAILURE: Revenue should exist");
    }

    public static void testCount() throws Exception {
        System.out.println("\n[Test Count]");
        long count = service.count();
        System.out.println("SUCCESS: Count is " + count);
    }
}
