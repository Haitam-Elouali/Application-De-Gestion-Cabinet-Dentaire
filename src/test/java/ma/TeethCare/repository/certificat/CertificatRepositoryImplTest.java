package ma.TeethCare.repository.certificat;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.modules.certificat.MySQLImplementation.CertificatRepositoryImpl;
import ma.TeethCare.repository.modules.certificat.api.CertificatRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class CertificatRepositoryImplTest {

    private CertificatRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new CertificatRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 certificats")
    void testFindAll() {
        List<certificat> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Certificat id=1")
    void testFindById() {
        certificat c = repo.findById(1L);
        assertThat(c).isNotNull();
        assertThat(c.getType()).isEqualTo("Arrêt de travail");
    }

    @Test
    @DisplayName("3) create : ajoute un certificat")
    void testCreate() {
        certificat c = certificat.builder()
                .type("Certificat médical")
                .dateDebut(LocalDate.of(2025, 2, 10))
                .dateFin(LocalDate.of(2025, 2, 15))
                .duree(5)
                .note("Suivi recommandé")
                .consultation_id(1L)
                .build();

        repo.create(c);
        assertThat(c.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie certificat")
    void testUpdate() {
        certificat c = repo.findById(1L);
        c.setNote("Arrêt prolongé");
        repo.update(c);

        certificat updated = repo.findById(1L);
        assertThat(updated.getNote()).isEqualTo("Arrêt prolongé");
    }

    @Test
    @DisplayName("5) deleteById : supprime un certificat")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
