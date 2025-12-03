package ma.TeethCare.repository.interventionMedecin;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.modules.interventionMedecin.MySQLImplementation.InterventionMedecinRepositoryImpl;
import ma.TeethCare.repository.modules.interventionMedecin.api.InterventionMedecinRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class InterventionMedecinRepositoryImplTest {

    private InterventionMedecinRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new InterventionMedecinRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 interventions")
    void testFindAll() {
        List<interventionMedecin> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Intervention id=1")
    void testFindById() {
        interventionMedecin i = repo.findById(1L);
        assertThat(i).isNotNull();
        assertThat(i.getDuree()).isEqualTo(30);
    }

    @Test
    @DisplayName("3) create : ajoute une intervention")
    void testCreate() {
        interventionMedecin i = interventionMedecin.builder()
                .duree(50)
                .note("Traitement de canal")
                .resultatImagerie("Infection confirmée")
                .consultation_id(1L)
                .build();

        repo.create(i);
        assertThat(i.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie intervention")
    void testUpdate() {
        interventionMedecin i = repo.findById(1L);
        i.setDuree(35);
        repo.update(i);

        interventionMedecin updated = repo.findById(1L);
        assertThat(updated.getDuree()).isEqualTo(35);
    }

    @Test
    @DisplayName("5) deleteById : supprime une intervention")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
