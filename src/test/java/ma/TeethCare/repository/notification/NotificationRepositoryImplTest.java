package ma.TeethCare.repository.notification;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.repository.modules.notification.MySQLImplementation.NotificationRepositoryImpl;
import ma.TeethCare.repository.modules.notification.NotificationRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class NotificationRepositoryImplTest {

    private NotificationRepository repo;

    @BeforeEach
    void setup() {
        DbTestUtils.cleanAll();
        DbTestUtils.seedFullDataset();
        repo = new NotificationRepositoryImpl();
    }

    @Test
    @DisplayName("1) findAll : retourne 5 notifications")
    void testFindAll() {
        List<notification> list = repo.findAll();
        assertThat(list).hasSize(5);
    }

    @Test
    @DisplayName("2) findById : Notification id=1")
    void testFindById() {
        notification n = repo.findById(1L);
        assertThat(n).isNotNull();
        assertThat(n.getTitre()).isEqualTo("Nouveau patient");
    }

    @Test
    @DisplayName("3) findByNonLues : notifications non lues")
    void testFindByNonLues() {
        List<notification> list = repo.findByNonLues();
        assertThat(list).hasSize(3);
        assertThat(list).extracting(notification::isLue)
                .containsOnly(false);
    }

    @Test
    @DisplayName("4) findByType : INSCRIPTION")
    void testFindByType() {
        List<notification> list = repo.findByType("INSCRIPTION");
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getTitre()).isEqualTo("Nouveau patient");
    }

    @Test
    @DisplayName("5) create : ajoute une notification")
    void testCreate() {
        notification n = notification.builder()
                .idNotif(6L)
                .titre("Test notification")
                .message("Ceci est un test")
                .dateEnvoi(LocalDateTime.now())
                .type("TEST")
                .lue(false)
                .build();

        repo.create(n);
        assertThat(n.getId()).isNotNull();
    }

    @Test
    @DisplayName("6) update : marque comme lue")
    void testUpdate() {
        notification n = repo.findById(2L);
        n.setLue(true);
        repo.update(n);

        notification updated = repo.findById(2L);
        assertThat(updated.isLue()).isTrue();
    }
}
