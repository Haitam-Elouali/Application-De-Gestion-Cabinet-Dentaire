package ma.TeethCare.repository.patient;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.entities.enums.Assurance;
import ma.TeethCare.repository.modules.patient.MySQLImplementation.PatientRepositoryImpl;
import ma.TeethCare.repository.modules.patient.api.PatientRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class PatientRepositoryImplTest {

    private PatientRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new PatientRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 6 patients")
    void testFindAll() {
        List<Patient> list = repo.findAll();
        assertThat(list).hasSize(6);
        assertThat(list).extracting(Patient::getEmail)
                .contains("amal@example.com", "omar@example.com");
    }

    @Test
    @DisplayName("2) findById : Omar id=2")
    void testFindById() {
        Patient p = repo.findById(2L);
        assertThat(p).isNotNull();
        assertThat(p.getNom()).isEqualTo("Omar");
    }

    @Test
    @DisplayName("3) create : ajoute un nouveau patient")
    void testCreate() {
        Patient p = Patient.builder()
                .nom("Dina")
                .prenom("Saidi")
                .telephone("0707070707")
                .dateNaissance(LocalDate.of(2002, 1, 15))
                .sexe(Sexe.Femme)
                .assurance(Assurance.Autre)
                .build();

        repo.create(p);
        assertThat(p.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie patient")
    void testUpdate() {
        Patient p = repo.findById(1L);
        p.setTelephone("0700000000");
        repo.update(p);

        Patient updated = repo.findById(1L);
        assertThat(updated.getTelephone()).isEqualTo("0700000000");
    }

    @Test
    @DisplayName("5) deleteById : supprime un patient")
    void testDeleteById() {
        repo.deleteById(6L);
        assertThat(repo.findById(6L)).isNull();
    }
}
