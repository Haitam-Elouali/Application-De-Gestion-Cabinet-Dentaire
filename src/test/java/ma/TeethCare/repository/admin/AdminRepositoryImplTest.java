package ma.TeethCare.repository.admin;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.modules.admin.MySQLImplementation.AdminRepositoryImpl;
import ma.TeethCare.repository.modules.admin.AdminRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class AdminRepositoryImplTest {

    private AdminRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new AdminRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 2 admins")
    void testFindAll() {
        List<admin> list = repo.findAll();
        assertThat(list).hasSize(2);
    }

    @Test
    @DisplayName("2) findById : Admin id=1")
    void testFindById() {
        admin a = repo.findById(1L);
        assertThat(a).isNotNull();
        assertThat(a.getNom()).isEqualTo("Dupont");
    }

    @Test
    @DisplayName("3) findByDomaine : Gestion globale")
    void testFindByDomaine() {
        List<admin> list = repo.findByDomaine("Gestion globale");
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getPermissionAdmin()).isEqualTo("FULL_ACCESS");
    }

    @Test
    @DisplayName("4) create : ajoute un admin")
    void testCreate() {
        admin a = admin.builder()
                .nom("Leclerc")
                .prenom("Jean")
                .email("jean.leclerc@example.com")
                .tele("0699999999")
                .permissionAdmin("WRITE_ACCESS")
                .domaine("Gestion utilisateurs")
                .sexe(Sexe.Homme)
                .dateNaissance(LocalDate.of(1988, 5, 10))
                .salaire(5200.00)
                .dateRecrutement(LocalDate.now())
                .build();

        repo.create(a);
        assertThat(a.getId()).isNotNull();
    }

    @Test
    @DisplayName("5) update : modifie admin")
    void testUpdate() {
        admin a = repo.findById(1L);
        a.setPermissionAdmin("WRITE_ACCESS");
        repo.update(a);

        admin updated = repo.findById(1L);
        assertThat(updated.getPermissionAdmin()).isEqualTo("WRITE_ACCESS");
    }

    @Test
    @DisplayName("6) deleteById : supprime un admin")
    void testDeleteById() {
        repo.deleteById(2L);
        assertThat(repo.findById(2L)).isNull();
    }
}
