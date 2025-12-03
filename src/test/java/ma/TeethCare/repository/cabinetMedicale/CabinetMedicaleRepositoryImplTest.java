package ma.TeethCare.repository.cabinetMedicale;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.modules.cabinetMedicale.MySQLImplementation.CabinetMedicaleRepositoryImpl;
import ma.TeethCare.repository.modules.cabinetMedicale.api.CabinetMedicaleRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class CabinetMedicaleRepositoryImplTest {

    private CabinetMedicaleRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new CabinetMedicaleRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 cabinets")
    void testFindAll() {
        List<cabinetMedicale> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Cabinet id=1")
    void testFindById() {
        cabinetMedicale c = repo.findById(1L);
        assertThat(c).isNotNull();
        assertThat(c.getNomCabinet()).isEqualTo("Cabinet Dental Central");
    }

    @Test
    @DisplayName("3) create : ajoute un cabinet")
    void testCreate() {
        cabinetMedicale c = cabinetMedicale.builder()
                .nomCabinet("New Clinic")
                .adresse("99 Rue Nouvelle, Marrakech")
                .tele("0524456789")
                .email("new@clinic.ma")
                .siteWeb("newclinic.ma")
                .description("Nouvelle clinique dentaire")
                .build();

        repo.create(c);
        assertThat(c.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie cabinet")
    void testUpdate() {
        cabinetMedicale c = repo.findById(1L);
        c.setTele("0522999999");
        repo.update(c);

        cabinetMedicale updated = repo.findById(1L);
        assertThat(updated.getTele()).isEqualTo("0522999999");
    }

    @Test
    @DisplayName("5) deleteById : supprime un cabinet")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
