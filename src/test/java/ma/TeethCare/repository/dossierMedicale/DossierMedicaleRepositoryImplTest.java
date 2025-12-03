package ma.TeethCare.repository.dossierMedicale;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.modules.dossierMedicale.MySQLImplementation.DossierMedicaleRepositoryImpl;
import ma.TeethCare.repository.modules.dossierMedicale.api.DossierMedicaleRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class DossierMedicaleRepositoryImplTest {

    private DossierMedicaleRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new DossierMedicaleRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 dossiers")
    void testFindAll() {
        List<dossierMedicale> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Dossier id=1")
    void testFindById() {
        dossierMedicale d = repo.findById(1L);
        assertThat(d).isNotNull();
        assertThat(d.getPatient_id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("3) create : ajoute un dossier")
    void testCreate() {
        dossierMedicale d = dossierMedicale.builder()
                .dateDeCreation(LocalDate.now())
                .patient_id(1L)
                .build();

        repo.create(d);
        assertThat(d.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie dossier")
    void testUpdate() {
        dossierMedicale d = repo.findById(1L);
        d.setDateDeCreation(LocalDate.of(2025, 1, 15));
        repo.update(d);

        dossierMedicale updated = repo.findById(1L);
        assertThat(updated.getDateDeCreation()).isEqualTo(LocalDate.of(2025, 1, 15));
    }

    @Test
    @DisplayName("5) deleteById : supprime un dossier")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
