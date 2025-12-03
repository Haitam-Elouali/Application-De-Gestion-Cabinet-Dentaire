package ma.TeethCare.repository.caisse;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.repository.modules.caisse.MySQLImplementation.CaisseRepositoryImpl;
import ma.TeethCare.repository.modules.caisse.api.CaisseRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class CaisseRepositoryImplTest {

    private CaisseRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new CaisseRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 entrées caisse")
    void testFindAll() {
        List<caisse> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Caisse id=1")
    void testFindById() {
        caisse c = repo.findById(1L);
        assertThat(c).isNotNull();
        assertThat(c.getMontantEntree()).isEqualTo(5000.00);
    }

    @Test
    @DisplayName("3) create : ajoute une entrée caisse")
    void testCreate() {
        caisse c = caisse.builder()
                .montantEntree(4000.00)
                .montantSortie(1000.00)
                .solde(3000.00)
                .build();

        repo.create(c);
        assertThat(c.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie caisse")
    void testUpdate() {
        caisse c = repo.findById(1L);
        c.setMontantSortie(2500.00);
        c.setSolde(2500.00);
        repo.update(c);

        caisse updated = repo.findById(1L);
        assertThat(updated.getMontantSortie()).isEqualTo(2500.00);
    }

    @Test
    @DisplayName("5) deleteById : supprime une entrée caisse")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
