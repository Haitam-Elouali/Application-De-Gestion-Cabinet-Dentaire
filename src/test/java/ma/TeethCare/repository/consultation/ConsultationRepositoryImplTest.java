package ma.TeethCare.repository.consultation;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.repository.modules.consultation.MySQLImplementation.ConsultationRepositoryImpl;
import ma.TeethCare.repository.modules.consultation.api.ConsultationRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class ConsultationRepositoryImplTest {

    private ConsultationRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new ConsultationRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 consultations")
    void testFindAll() {
        List<consultation> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Consultation id=1")
    void testFindById() {
        consultation c = repo.findById(1L);
        assertThat(c).isNotNull();
        assertThat(c.getMotif()).isEqualTo("Douleur dent");
    }

    @Test
    @DisplayName("3) create : ajoute une consultation")
    void testCreate() {
        consultation c = consultation.builder()
                .date(LocalDate.of(2025, 2, 4))
                .motif("Contrôle")
                .diagnostic("Visite de contrôle")
                .statut("Programmée")
                .patient_id(1L)
                .medecin_id(1L)
                .build();

        repo.create(c);
        assertThat(c.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie consultation")
    void testUpdate() {
        consultation c = repo.findById(1L);
        c.setStatut("Résumée");
        repo.update(c);

        consultation updated = repo.findById(1L);
        assertThat(updated.getStatut()).isEqualTo("Résumée");
    }

    @Test
    @DisplayName("5) deleteById : supprime une consultation")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
