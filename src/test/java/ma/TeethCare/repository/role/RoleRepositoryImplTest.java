package ma.TeethCare.repository.role;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.repository.modules.role.MySQLImplementation.RoleRepositoryImpl;
import ma.TeethCare.repository.modules.role.api.RoleRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class RoleRepositoryImplTest {

    private RoleRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new RoleRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 4 roles")
    void testFindAll() {
        List<role> list = repo.findAll();
        assertThat(list).hasSize(4);
        assertThat(list).extracting(role::getLibelle)
                .contains("ADMIN", "MEDECIN", "SECRETAIRE", "PATIENT");
    }

    @Test
    @DisplayName("2) findById : Role id=1")
    void testFindById() {
        role r = repo.findById(1L);
        assertThat(r).isNotNull();
        assertThat(r.getLibelle()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("3) create : ajoute un role")
    void testCreate() {
        role r = role.builder()
                .libelle("CONSULTANT")
                .build();

        repo.create(r);
        assertThat(r.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie role")
    void testUpdate() {
        role r = repo.findById(1L);
        r.setLibelle("ADMINISTRATEUR");
        repo.update(r);

        role updated = repo.findById(1L);
        assertThat(updated.getLibelle()).isEqualTo("ADMINISTRATEUR");
    }

    @Test
    @DisplayName("5) deleteById : supprime un role")
    void testDeleteById() {
        repo.deleteById(4L);
        assertThat(repo.findById(4L)).isNull();
    }
}
