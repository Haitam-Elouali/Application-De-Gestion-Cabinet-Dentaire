package ma.TeethCare.service.test;

import ma.TeethCare.common.exceptions.ServiceException;
import ma.TeethCare.entities.role.role;
import ma.TeethCare.mvc.dto.role.RoleDTO;
import ma.TeethCare.repository.api.RoleRepository;
import ma.TeethCare.service.modules.users.api.roleService;
import ma.TeethCare.service.modules.users.impl.roleServiceImpl;

import java.sql.SQLException;
import java.util.*;

/**
 * @author MOKADAMI Zouhair
 * @date 2025-12-17
 */

public class RoleServiceTest {

    // Stub implementation for RoleRepository (In-Memory)
    static class RoleRepositoryStub implements RoleRepository {
        private Map<Long, role> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<role> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public role findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(role entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(role entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(role entity) {
            if (entity != null && entity.getId() != null) {
                data.remove(entity.getId());
            }
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<role> findByLibeller(ma.TeethCare.common.enums.Libeller libeller) {
            for (role r : data.values()) {
                if (r.getLibelle() != null && r.getLibelle().equals(libeller.name())) {
                    return Optional.of(r);
                }
            }
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        try {
            RoleRepositoryStub repo = new RoleRepositoryStub();
            roleService service = new roleServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All RoleService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(roleService service) throws Exception {
        System.out.println("Testing Create...");
        RoleDTO r = RoleDTO.builder()
                .nom("ADMIN")
                .build();
        RoleDTO created = service.create(r);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(roleService service) throws Exception {
        System.out.println("Testing FindById...");
        RoleDTO r = RoleDTO.builder()
                .nom("USER")
                .build();
        r = service.create(r);
        Optional<RoleDTO> found = service.findById(r.getId());
        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(roleService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(RoleDTO.builder()
                .nom("MODERATOR")
                .build());
        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(roleService service) throws Exception {
        System.out.println("Testing Update...");
        RoleDTO r = RoleDTO.builder()
                .nom("GUEST")
                .build();
        r = service.create(r);
        r.setNom("SUPER_ADMIN");
        RoleDTO updated = service.update(r);
        if (!"SUPER_ADMIN".equals(updated.getNom()))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(roleService service) throws Exception {
        System.out.println("Testing Delete...");
        RoleDTO r = RoleDTO.builder()
                .nom("TEMP")
                .build();
        r = service.create(r);
        Long id = r.getId();
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(roleService service) throws Exception {
        System.out.println("Testing Exists...");
        RoleDTO r = RoleDTO.builder()
                .nom("EDITOR")
                .build();
        r = service.create(r);
        if (!service.exists(r.getId()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(roleService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0)
            throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
