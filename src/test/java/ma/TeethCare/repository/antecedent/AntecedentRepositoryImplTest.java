package ma.TeethCare.repository.antecedent;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.repository.modules.antecedent.MySQLImplementation.AntecedentRepositoryImpl;
import ma.TeethCare.repository.modules.antecedent.api.AntecedentRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class AntecedentRepositoryImplTest {

    private AntecedentRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new AntecedentRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 5 antécédents")
    void testFindAll() {
        List<antecedent> list = repo.findAll();
        assertThat(list).hasSize(5);
    }

    @Test
    @DisplayName("2) findById : Antécédent id=1")
    void testFindById() {
        antecedent a = repo.findById(1L);
        assertThat(a).isNotNull();
        assertThat(a.getNom()).isEqualTo("Allergie pénicilline");
    }

    @Test
    @DisplayName("3) create : ajoute un antécédent")
    void testCreate() {
        antecedent a = antecedent.builder()
                .nom("Infarctus antérieur")
                .categorie("ANTECEDENT")
                .niveauDeRisque("CRITIQUE")
                .build();

        repo.create(a);
        assertThat(a.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie antécédent")
    void testUpdate() {
        antecedent a = repo.findById(1L);
        a.setNiveauDeRisque("EXTREME");
        repo.update(a);

        antecedent updated = repo.findById(1L);
        assertThat(updated.getNiveauDeRisque()).isEqualTo("EXTREME");
    }

    @Test
    @DisplayName("5) deleteById : supprime un antécédent")
    void testDeleteById() {
        repo.deleteById(5L);
        assertThat(repo.findById(5L)).isNull();
    }
}
