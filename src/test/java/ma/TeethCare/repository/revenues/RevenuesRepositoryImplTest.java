package ma.TeethCare.repository.revenues;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.modules.revenues.MySQLImplementation.RevenuesRepositoryImpl;
import ma.TeethCare.repository.modules.revenues.api.RevenuesRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class RevenuesRepositoryImplTest {

    private RevenuesRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new RevenuesRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 revenues")
    void testFindAll() {
        List<revenues> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Revenue id=1")
    void testFindById() {
        revenues r = repo.findById(1L);
        assertThat(r).isNotNull();
        assertThat(r.getTitre()).isEqualTo("Consultations");
    }

    @Test
    @DisplayName("3) create : ajoute une revenue")
    void testCreate() {
        revenues r = revenues.builder()
                .titre("Nettoyage")
                .description("Nettoyage spécialisé")
                .montant(2000.00)
                .categorie("Nettoyage")
                .date(LocalDateTime.now())
                .build();

        repo.create(r);
        assertThat(r.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie revenue")
    void testUpdate() {
        revenues r = repo.findById(1L);
        r.setMontant(8500.00);
        repo.update(r);

        revenues updated = repo.findById(1L);
        assertThat(updated.getMontant()).isEqualTo(8500.00);
    }

    @Test
    @DisplayName("5) deleteById : supprime une revenue")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
