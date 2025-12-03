package ma.TeethCare.repository.agenda;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.repository.modules.agenda.MySQLImplementation.AgendaRepositoryImpl;
import ma.TeethCare.repository.modules.agenda.api.AgendaRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class AgendaRepositoryImplTest {

    private AgendaRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new AgendaRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 3 agendas")
    void testFindAll() {
        List<agenda> list = repo.findAll();
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("2) findById : Agenda id=1")
    void testFindById() {
        agenda a = repo.findById(1L);
        assertThat(a).isNotNull();
        assertThat(a.getMois()).isEqualTo("JANVIER");
    }

    @Test
    @DisplayName("3) create : ajoute un agenda")
    void testCreate() {
        agenda a = agenda.builder()
                .mois("AVRIL")
                .annee(2025)
                .joursNonDisponibles("8,14,21")
                .medecin_id(1L)
                .build();

        repo.create(a);
        assertThat(a.getId()).isNotNull();
    }

    @Test
    @DisplayName("4) update : modifie agenda")
    void testUpdate() {
        agenda a = repo.findById(1L);
        a.setJoursNonDisponibles("2,4,6");
        repo.update(a);

        agenda updated = repo.findById(1L);
        assertThat(updated.getJoursNonDisponibles()).isEqualTo("2,4,6");
    }

    @Test
    @DisplayName("5) deleteById : supprime un agenda")
    void testDeleteById() {
        repo.deleteById(3L);
        assertThat(repo.findById(3L)).isNull();
    }
}
