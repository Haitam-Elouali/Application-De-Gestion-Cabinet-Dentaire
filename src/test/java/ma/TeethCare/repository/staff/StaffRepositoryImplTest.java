package ma.TeethCare.repository.staff;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.modules.staff.MySQLImplementation.StaffRepositoryImpl;
import ma.TeethCare.repository.modules.staff.api.StaffRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class StaffRepositoryImplTest {

    private StaffRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new StaffRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 2 staff")
    void testFindAll() {
        List<staff> list = repo.findAll();
        assertThat(list).hasSize(2);
    }

    @Test
    @DisplayName("2) findById : Staff id=1")
    void testFindById() {
        staff s = repo.findById(1L);
        assertThat(s).isNotNull();
        assertThat(s.getSalaire()).isEqualTo(8000.00);
    }

    @Test
    @DisplayName("3) create : ajoute un staff")
    void testCreate() {
        staff s = staff.builder()
                .nom("Moreau")
                .prenom("Pierre")
                .email("pierre@example.com")
                .tele("0688888888")
                .salaire(5000.00)
                .dateRecrutement(LocalDate.now())
                .sexe(Sexe.Homme)
                .dateNaissance(LocalDate.of(1990, 6, 15))
                .build();

        repo.create(s);
        assertThat(s.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie staff")
    void testUpdate() {
        staff s = repo.findById(1L);
        s.setSalaire(8500.00);
        repo.update(s);

        staff updated = repo.findById(1L);
        assertThat(updated.getSalaire()).isEqualTo(8500.00);
    }

    @Test
    @DisplayName("5) deleteById : supprime un staff")
    void testDeleteById() {
        repo.deleteById(2L);
        assertThat(repo.findById(2L)).isNull();
    }
}
