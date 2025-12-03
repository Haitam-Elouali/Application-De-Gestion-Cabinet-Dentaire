package ma.TeethCare.repository.charges;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.modules.charges.MySQLImplementation.ChargesRepositoryImpl;
import ma.TeethCare.repository.modules.charges.api.ChargesRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class ChargesRepositoryImplTest {

    private ChargesRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new ChargesRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 charges")
    void testFindAll() {
        List<charges> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Charge id=1")
    void testFindById() {
        charges c = repo.findById(1L);
        assertThat(c).isNotNull();
        assertThat(c.getTitre()).isEqualTo("Loyer");
    }

    @Test
    @DisplayName("3) create : ajoute une charge")
    void testCreate() {
        charges c = charges.builder()
                .titre("Assurance")
                .description("Assurance cabinet")
                .montant(500.00)
                .categorie("Assurance")
                .date(LocalDateTime.now())
                .build();

        repo.create(c);
        assertThat(c.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie charge")
    void testUpdate() {
        charges c = repo.findById(1L);
        c.setMontant(3200.00);
        repo.update(c);

        charges updated = repo.findById(1L);
        assertThat(updated.getMontant()).isEqualTo(3200.00);
    }

    @Test
    @DisplayName("5) deleteById : supprime une charge")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
