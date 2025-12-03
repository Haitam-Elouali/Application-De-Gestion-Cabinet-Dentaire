package ma.TeethCare.repository.facture;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.modules.facture.MySQLImplementation.FactureRepositoryImpl;
import ma.TeethCare.repository.modules.facture.api.FactureRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class FactureRepositoryImplTest {

    private FactureRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new FactureRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 factures")
    void testFindAll() {
        List<facture> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Facture id=1")
    void testFindById() {
        facture f = repo.findById(1L);
        assertThat(f).isNotNull();
        assertThat(f.getTotaleFacture()).isEqualTo(1200.00);
    }

    @Test
    @DisplayName("3) create : ajoute une facture")
    void testCreate() {
        facture f = facture.builder()
                .totaleFacture(3000.00)
                .totalePaye(1500.00)
                .Reste(1500.00)
                .statut(Statut.EnCours)
                .modePaiement("Espèces")
                .dateFacture(LocalDateTime.now())
                .patient_id(1L)
                .secretaire_id(1L)
                .build();

        repo.create(f);
        assertThat(f.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie facture")
    void testUpdate() {
        facture f = repo.findById(1L);
        f.setTotalePaye(1200.00);
        f.setReste(0.00);
        f.setStatut(Statut.Paye);
        repo.update(f);

        facture updated = repo.findById(1L);
        assertThat(updated.getTotalePaye()).isEqualTo(1200.00);
        assertThat(updated.getStatut()).isEqualTo(Statut.Paye);
    }

    @Test
    @DisplayName("5) deleteById : supprime une facture")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }

    @Test
    @DisplayName("6) findUnpaid : retourne les factures impayées")
    void testFindUnpaid() {
        List<facture> list = repo.findByStatut(Statut.Impaye);
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getTotalePaye()).isEqualTo(0.00);
    }
}
