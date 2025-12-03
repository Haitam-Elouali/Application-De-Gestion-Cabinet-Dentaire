package ma.TeethCare.repository.prescription;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.modules.prescription.MySQLImplementation.PrescriptionRepositoryImpl;
import ma.TeethCare.repository.modules.prescription.api.PrescriptionRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class PrescriptionRepositoryImplTest {

    private PrescriptionRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new PrescriptionRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 prescriptions")
    void testFindAll() {
        List<prescription> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Prescription id=1")
    void testFindById() {
        prescription p = repo.findById(1L);
        assertThat(p).isNotNull();
        assertThat(p.getQuantite()).isEqualTo(2);
    }

    @Test
    @DisplayName("3) create : ajoute une prescription")
    void testCreate() {
        prescription p = prescription.builder()
                .quantite(2)
                .posologie("2 fois par jour")
                .dureeEnJours(7)
                .ordonnance_id(1L)
                .medicament_id(2L)
                .build();

        repo.create(p);
        assertThat(p.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie prescription")
    void testUpdate() {
        prescription p = repo.findById(1L);
        p.setQuantite(3);
        repo.update(p);

        prescription updated = repo.findById(1L);
        assertThat(updated.getQuantite()).isEqualTo(3);
    }

    @Test
    @DisplayName("5) deleteById : supprime une prescription")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
