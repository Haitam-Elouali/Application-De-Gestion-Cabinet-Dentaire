package ma.TeethCare.repository.medicament;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.repository.modules.medicament.MySQLImplementation.MedicamentRepositoryImpl;
import ma.TeethCare.repository.modules.medicament.api.MedicamentRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class MedicamentRepositoryImplTest {

    private MedicamentRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new MedicamentRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 médicaments")
    void testFindAll() {
        List<medicaments> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Médicament id=1")
    void testFindById() {
        medicaments m = repo.findById(1L);
        assertThat(m).isNotNull();
        assertThat(m.getNomCommercial()).isEqualTo("Paracetamol 500mg");
    }

    @Test
    @DisplayName("3) create : ajoute un médicament")
    void testCreate() {
        medicaments m = medicaments.builder()
                .nomCommercial("Aspirine 500mg")
                .principeActif("Acide acétylsalicylique")
                .forme("Comprimé")
                .dosage("500mg")
                .type("Analgésique")
                .remboursable(true)
                .prixUnitaire(10.00)
                .description("Analgésique antipyrétique")
                .build();

        repo.create(m);
        assertThat(m.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie médicament")
    void testUpdate() {
        medicaments m = repo.findById(1L);
        m.setPrixUnitaire(16.50);
        repo.update(m);

        medicaments updated = repo.findById(1L);
        assertThat(updated.getPrixUnitaire()).isEqualTo(16.50);
    }

    @Test
    @DisplayName("5) deleteById : supprime un médicament")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
