package ma.TeethCare.repository.utilisateur;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.modules.utilisateur.MySQLImplementation.UtilisateurRepositoryImpl;
import ma.TeethCare.repository.modules.utilisateur.api.UtilisateurRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class UtilisateurRepositoryImplTest {

    private UtilisateurRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new UtilisateurRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 utilisateurs")
    void testFindAll() {
        List<utilisateur> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Utilisateur id=1")
    void testFindById() {
        utilisateur u = repo.findById(1L);
        assertThat(u).isNotNull();
        assertThat(u.getNom()).isEqualTo("Dupont");
    }

    @Test
    @DisplayName("3) create : ajoute un utilisateur")
    void testCreate() {
        utilisateur u = utilisateur.builder()
                .nom("Martin")
                .prenom("Sophie")
                .email("sophie@example.com")
                .tele("0699999999")
                .sexe(Sexe.Femme)
                .dateNaissance(LocalDate.of(1990, 5, 10))
                .build();

        repo.create(u);
        assertThat(u.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie utilisateur")
    void testUpdate() {
        utilisateur u = repo.findById(1L);
        u.setTele("0700000000");
        repo.update(u);

        utilisateur updated = repo.findById(1L);
        assertThat(updated.getTele()).isEqualTo("0700000000");
    }

    @Test
    @DisplayName("5) deleteById : supprime un utilisateur")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
