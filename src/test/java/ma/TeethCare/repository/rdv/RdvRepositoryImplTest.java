package ma.TeethCare.repository.rdv;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.repository.modules.rdv.MySQLImplementation.RdvRepositoryImpl;
import ma.TeethCare.repository.modules.rdv.api.RdvRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class RdvRepositoryImplTest {

    private RdvRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new RdvRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 4 RDV")
    void testFindAll() {
        List<rdv> list = repo.findAll();
        assertThat(list).hasSize(4);
    }

    @Test
    @DisplayName("2) findById : RDV id=1")
    void testFindById() {
        rdv r = repo.findById(1L);
        assertThat(r).isNotNull();
        assertThat(r.getNumero()).isEqualTo("RDV001");
    }

    @Test
    @DisplayName("3) create : ajoute un RDV")
    void testCreate() {
        rdv r = rdv.builder()
                .numero("RDV005")
                .date(LocalDate.of(2025, 2, 15))
                .heure(LocalTime.of(11, 0))
                .statut("Confirmé")
                .patient_id(1L)
                .build();

        repo.create(r);
        assertThat(r.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie RDV")
    void testUpdate() {
        rdv r = repo.findById(2L);
        r.setStatut("Confirmé");
        repo.update(r);

        rdv updated = repo.findById(2L);
        assertThat(updated.getStatut()).isEqualTo("Confirmé");
    }

    @Test
    @DisplayName("5) deleteById : supprime un RDV")
    void testDeleteById() {
        repo.deleteById(4L);
        assertThat(repo.findById(4L)).isNull();
    }
}
