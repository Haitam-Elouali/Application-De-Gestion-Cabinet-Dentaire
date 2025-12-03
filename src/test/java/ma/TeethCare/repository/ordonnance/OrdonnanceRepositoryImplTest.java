package ma.TeethCare.repository.ordonnance;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.modules.ordonnance.MySQLImplementation.OrdonnanceRepositoryImpl;
import ma.TeethCare.repository.modules.ordonnance.api.OrdonnanceRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class OrdonnanceRepositoryImplTest {

    private OrdonnanceRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new OrdonnanceRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 ordonnances")
    void testFindAll() {
        List<ordonnance> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Ordonnance id=1")
    void testFindById() {
        ordonnance o = repo.findById(1L);
        assertThat(o).isNotNull();
        assertThat(o.getDateOrdonnance()).isEqualTo(LocalDate.of(2025, 2, 1));
    }

    @Test
    @DisplayName("3) create : ajoute une ordonnance")
    void testCreate() {
        ordonnance o = ordonnance.builder()
                .dateOrdonnance(LocalDate.of(2025, 2, 4))
                .consultation_id(1L)
                .build();

        repo.create(o);
        assertThat(o.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie ordonnance")
    void testUpdate() {
        ordonnance o = repo.findById(1L);
        o.setDateOrdonnance(LocalDate.of(2025, 2, 10));
        repo.update(o);

        ordonnance updated = repo.findById(1L);
        assertThat(updated.getDateOrdonnance()).isEqualTo(LocalDate.of(2025, 2, 10));
    }

    @Test
    @DisplayName("5) deleteById : supprime une ordonnance")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
