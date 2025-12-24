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

import ma.TeethCare.service.modules.caisse.dto.RevenuesDto;

public class RevenuesServiceTest {

    // Stub implementation for RevenuesRepository
    static class RevenuesRepositoryStub implements ma.TeethCare.repository.api.RevenuesRepository {
        private java.util.Map<Long, revenues> data = new java.util.HashMap<>();
        private long idCounter = 1;

        @Override
        public void create(revenues entity) {
            if (entity.getIdEntite() == null)
                entity.setIdEntite(idCounter++);
            data.put(entity.getIdEntite(), entity);
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
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public void delete(revenues entity) {
            if (entity != null)
                data.remove(entity.getIdEntite());
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
        RevenuesDto r = new RevenuesDto(null, 1L, "Consultation Test", "Test Description", 300.0, "CONSULT",
                LocalDateTime.now());

        RevenuesDto created = service.create(r);
        if (created != null && created.id() != null) {
            createdId = created.id();
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

        RevenuesDto found = service.findById(createdId);
        if (found != null) {
            System.out.println("SUCCESS: Found Revenue: " + found.titre());
        } else {
            System.err.println("FAILURE: Revenue not found with ID: " + createdId);
        }
    }

    public static void testFindAll() throws Exception {
        System.out.println("\n[Test FindAll]");
        List<RevenuesDto> list = service.findAll();
        System.out.println("SUCCESS: Retrieved " + list.size() + " revenues.");
        list.forEach(r -> System.out.println(" - " + r.id() + ": " + r.titre()));
    }

    public static void testUpdate() throws Exception {
        System.out.println("\n[Test Update]");
        if (createdId == null) {
            System.out.println("Skipping: No ID");
            return;
        }

        RevenuesDto r = service.findById(createdId);
        if (r != null) {
            RevenuesDto toUpdate = new RevenuesDto(r.id(), r.cabinetId(), "Consultation Updated", r.description(),
                    450.0, r.categorie(), r.date());

            service.update(r.id(), toUpdate);

            RevenuesDto updated = service.findById(createdId);
            if (updated != null && "Consultation Updated".equals(updated.titre())) {
                System.out.println("SUCCESS: Updated Revenue Titre: " + updated.titre());
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
