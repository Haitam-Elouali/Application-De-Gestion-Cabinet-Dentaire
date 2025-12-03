package ma.TeethCare.repository.actes;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.repository.modules.actes.MySQLImplementation.ActesRepositoryImpl;
import ma.TeethCare.repository.modules.actes.api.ActesRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class ActesRepositoryImplTest {

    private ActesRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new ActesRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 5 actes")
    void testFindAll() {
        List<actes> list = repo.findAll();
        assertThat(list).hasSize(5);
    }

    @Test
    @DisplayName("2) findById : Acte id=1")
    void testFindById() {
        actes a = repo.findById(1L);
        assertThat(a).isNotNull();
        assertThat(a.getNom()).isEqualTo("Consultation Générale");
        assertThat(a.getPrix()).isEqualTo(300.00);
    }

    @Test
    @DisplayName("3) findByCategorie : Détartrage")
    void testFindByCategorie() {
        List<actes> list = repo.findByCategorie("Détartrage");
        assertThat(list).hasSize(2);
        assertThat(list).extracting(actes::getNom)
                .contains("Détartrage simple", "Détartrage avec fluor");
    }

    @Test
    @DisplayName("4) create : ajoute un acte")
    void testCreate() {
        actes a = actes.builder()
                .categorie("Traitement")
                .nom("Dévitalisation")
                .description("Traitement de la pulpe dentaire")
                .prix(800.00)
                .code("ACT006")
                .build();

        repo.create(a);
        assertThat(a.getId()).isNotNull();
    }

    @Test
    @DisplayName("5) update : modifie acte")
    void testUpdate() {
        actes a = repo.findById(1L);
        a.setPrix(350.00);
        repo.update(a);

        actes updated = repo.findById(1L);
        assertThat(updated.getPrix()).isEqualTo(350.00);
    }

    @Test
    @DisplayName("6) deleteById : supprime un acte")
    void testDeleteById() {
        repo.deleteById(5L);
        assertThat(repo.findById(5L)).isNull();
    }
}
