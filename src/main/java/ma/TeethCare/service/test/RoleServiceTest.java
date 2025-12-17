package ma.TeethCare.service.test;

import ma.TeethCare.common.exceptions.ServiceException;
import ma.TeethCare.entities.role.role;
import ma.TeethCare.repository.api.RoleRepository;

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
            RoleServiceTest service = new RoleServiceTest(repo);

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

    private final RoleRepository roleRepository;

    public RoleServiceTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public role create(role entity) throws Exception {
        try {
            if (entity == null) {
                throw new ServiceException("Role ne peut pas être null");
            }
            roleRepository.create(entity);
            return entity;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du role", e);
        }
    }

    public Optional<role> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ServiceException("ID role ne peut pas être null");
            }
            return Optional.ofNullable(roleRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du role", e);
        }
    }

    public List<role> findAll() throws Exception {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la liste des roles", e);
        }
    }

    public role update(role entity) throws Exception {
        try {
            if (entity == null) {
                throw new ServiceException("Role ne peut pas être null");
            }
            roleRepository.update(entity);
            return entity;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du role", e);
        }
    }

    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) {
                return false;
            }
            roleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du role", e);
        }
    }

    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) {
                return false;
            }
            return roleRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification d'existence du role", e);
        }
    }

    public long count() throws Exception {
        try {
            return roleRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des roles", e);
        }
    }

    public static void testCreate(RoleServiceTest service) throws Exception {
        System.out.println("Testing Create...");
        role r = role.builder()
                .libelle("ADMIN")
                .utilisateurId(1L)
                .build();
        role created = service.create(r);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(RoleServiceTest service) throws Exception {
        System.out.println("Testing FindById...");
        role r = role.builder()
                .libelle("USER")
                .utilisateurId(2L)
                .build();
        r = service.create(r);
        Optional<role> found = service.findById(r.getId());
        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(RoleServiceTest service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(role.builder()
                .libelle("MODERATOR")
                .utilisateurId(3L)
                .build());
        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(RoleServiceTest service) throws Exception {
        System.out.println("Testing Update...");
        role r = role.builder()
                .libelle("GUEST")
                .utilisateurId(4L)
                .build();
        r = service.create(r);
        r.setLibelle("SUPER_ADMIN");
        role updated = service.update(r);
        if (!"SUPER_ADMIN".equals(updated.getLibelle()))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(RoleServiceTest service) throws Exception {
        System.out.println("Testing Delete...");
        role r = role.builder()
                .libelle("TEMP")
                .utilisateurId(5L)
                .build();
        r = service.create(r);
        Long id = r.getId();
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(RoleServiceTest service) throws Exception {
        System.out.println("Testing Exists...");
        role r = role.builder()
                .libelle("EDITOR")
                .utilisateurId(6L)
                .build();
        r = service.create(r);
        if (!service.exists(r.getId()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(RoleServiceTest service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0)
            throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
