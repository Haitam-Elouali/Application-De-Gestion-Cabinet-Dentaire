package ma.TeethCare.repository.situationFinanciere;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.modules.situationFinanciere.MySQLImplementation.SituationFinanciereRepositoryImpl;
import ma.TeethCare.repository.modules.situationFinanciere.api.SituationFinanciereRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class SituationFinanciereRepositoryImplTest {

    private SituationFinanciereRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new SituationFinanciereRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 situations")
    void testFindAll() {
        List<situationFinanciere> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Situation id=1")
    void testFindById() {
        situationFinanciere s = repo.findById(1L);
        assertThat(s).isNotNull();
        assertThat(s.getTotalDesActes()).isEqualTo(1500.00);
    }

    @Test
    @DisplayName("3) create : ajoute une situation")
    void testCreate() {
        situationFinanciere s = situationFinanciere.builder()
                .totalDesActes(2500.00)
                .totalPaye(1250.00)
                .credit(1250.00)
                .statut("En cours")
                .enPromo("Non")
                .build();

        repo.create(s);
        assertThat(s.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie situation")
    void testUpdate() {
        situationFinanciere s = repo.findById(1L);
        s.setTotalPaye(1200.00);
        s.setCredit(300.00);
        repo.update(s);

        situationFinanciere updated = repo.findById(1L);
        assertThat(updated.getTotalPaye()).isEqualTo(1200.00);
    }

    @Test
    @DisplayName("5) deleteById : supprime une situation")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
