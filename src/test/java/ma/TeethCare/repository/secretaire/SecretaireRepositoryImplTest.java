package ma.TeethCare.repository.secretaire;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.modules.secretaire.MySQLImplementation.SecretaireRepositoryImpl;
import ma.TeethCare.repository.modules.secretaire.api.SecretaireRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class SecretaireRepositoryImplTest {

    private SecretaireRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new SecretaireRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 2 secrétaires")
    void testFindAll() {
        List<secretaire> list = repo.findAll();
        assertThat(list).hasSize(2);
    }

    @Test
    @DisplayName("2) findById : Secrétaire id=1")
    void testFindById() {
        secretaire s = repo.findById(1L);
        assertThat(s).isNotNull();
        assertThat(s.getNom()).isEqualTo("Lemoine");
    }

    @Test
    @DisplayName("3) create : ajoute une secrétaire")
    void testCreate() {
        secretaire s = secretaire.builder()
                .nom("Dubois")
                .prenom("Marie")
                .email("marie@example.com")
                .tele("0699999999")
                .commission(170.00)
                .sexe(Sexe.Femme)
                .dateNaissance(LocalDate.of(1996, 3, 20))
                .salaire(3300.00)
                .dateRecrutement(LocalDate.now())
                .build();

        repo.create(s);
        assertThat(s.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie secrétaire")
    void testUpdate() {
        secretaire s = repo.findById(1L);
        s.setCommission(175.00);
        repo.update(s);

        secretaire updated = repo.findById(1L);
        assertThat(updated.getCommission()).isEqualTo(175.00);
    }

    @Test
    @DisplayName("5) deleteById : supprime une secrétaire")
    void testDeleteById() {
        repo.deleteById(2L);
        assertThat(repo.findById(2L)).isNull();
    }
}
