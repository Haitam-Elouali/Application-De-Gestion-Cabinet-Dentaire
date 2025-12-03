package ma.TeethCare.repository.log;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.modules.log.MySQLImplementation.LogRepositoryImpl;
import ma.TeethCare.repository.modules.log.LogRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class LogRepositoryImplTest {

    private LogRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new LogRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 5 logs")
    void testFindAll() {
        List<log> list = repo.findAll();
        assertThat(list).hasSize(5);
    }

    @Test
    @DisplayName("2) findById : Log id=1")
    void testFindById() {
        log l = repo.findById(1L);
        assertThat(l).isNotNull();
        assertThat(l.getAction()).isEqualTo("LOGIN");
    }

    @Test
    @DisplayName("3) findByUtilisateur : user1")
    void testFindByUtilisateur() {
        List<log> list = repo.findByUtilisateur("user1");
        assertThat(list).hasSize(3);
        assertThat(list).extracting(log::getAction)
                .contains("LOGIN", "UPDATE", "LOGOUT");
    }

    @Test
    @DisplayName("4) findByAction : CREATE")
    void testFindByAction() {
        List<log> list = repo.findByAction("CREATE");
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getUtilisateur()).isEqualTo("user2");
    }

    @Test
    @DisplayName("5) create : ajoute un log")
    void testCreate() {
        log l = log.builder()
                .idLog(6L)
                .action("EXPORT")
                .utilisateur("admin")
                .dateAction(LocalDateTime.now())
                .description("Export de rapport")
                .adresseIP("192.168.1.103")
                .build();

        repo.create(l);
        assertThat(l.getId()).isNotNull();
    }

    @Test
    @DisplayName("6) deleteById : supprime un log")
    void testDeleteById() {
        repo.deleteById(5L);
        assertThat(repo.findById(5L)).isNull();
    }
}
