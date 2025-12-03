package ma.TeethCare.repository.medecin;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.modules.medecin.MySQLImplementation.MedecinRepositoryImpl;
import ma.TeethCare.repository.modules.medecin.api.MedecinRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class MedecinRepositoryImplTest {

    private MedecinRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new MedecinRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 médecins")
    void testFindAll() {
        List<medecin> list = repo.findAll();
        assertThat(list).hasSize(3);
        assertThat(list).extracting(medecin::getSpecialite)
                .contains("Dentiste", "Orthodontiste", "Implantologue");
    }

    @Test
    @DisplayName("2) findById : Luc Bernard id=1")
    void testFindById() {
        medecin m = repo.findById(1L);
        assertThat(m).isNotNull();
        assertThat(m.getNom()).isEqualTo("Bernard");
        assertThat(m.getSpecialite()).isEqualTo("Dentiste");
    }

    @Test
    @DisplayName("3) findBySpecialite : Dentiste")
    void testFindBySpecialite() {
        List<medecin> list = repo.findBySpecialite("Dentiste");
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getNom()).isEqualTo("Bernard");
    }

    @Test
    @DisplayName("4) create : ajoute un médecin")
    void testCreate() {
        medecin m = medecin.builder()
                .nom("Leclerc")
                .prenom("Sophie")
                .email("sophie.leclerc@example.com")
                .tele("0699999999")
                .specialite("Parodontiste")
                .sexe(Sexe.Femme)
                .dateNaissance(LocalDate.of(1988, 5, 10))
                .salaire(8500.00)
                .dateRecrutement(LocalDate.now())
                .build();

        repo.create(m);
        assertThat(m.getId()).isNotNull();
    }

    @Test
    @DisplayName("5) update : modifie médecin")
    void testUpdate() {
        medecin m = repo.findById(1L);
        m.setSpecialite("Chirurgien-Dentiste");
        repo.update(m);

        medecin updated = repo.findById(1L);
        assertThat(updated.getSpecialite()).isEqualTo("Chirurgien-Dentiste");
    }

    @Test
    @DisplayName("6) deleteById : supprime un médecin")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
