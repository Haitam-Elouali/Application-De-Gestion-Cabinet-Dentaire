package ma.TeethCare.repository.modules.notification.MySQLImplementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.repository.common.GenericJdbcRepository;
import ma.TeethCare.repository.modules.notification.NotificationRepository;

import java.util.List;

@Slf4j
public class NotificationRepositoryImpl extends GenericJdbcRepository<notification> 
        implements NotificationRepository {

            "useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String TABLE_NAME = "NOTIFICATION";

    public NotificationRepositoryImpl() {
        super(notification.class, TABLE_NAME);
        log.info("✓ NotificationRepositoryImpl initialisé avec table: {}", TABLE_NAME);
    }

    @Override
    public List<notification> findAll() throws Exception {
        try {
            String sql = buildSelectQuery(null);
            log.info("Récupération de toutes les notifications");
            List<notification> result = executeQuery(sql);
            log.info("✓ {} notification(s) récupérée(s)", result.size());
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findAll() pour Notification", e);
            throw e;
        }
    }

    @Override
    public notification findById(Long id) throws Exception {
        try {
            String sql = buildSelectQuery("id = ?");
            log.info("Recherche de la notification avec id: {}", id);
            notification result = executeSingleQuery(sql, id);
            if (result != null) {
                log.info("✓ Notification trouvée avec id: {}", id);
            } else {
                log.warn("⚠ Notification non trouvée avec id: {}", id);
            }
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findById({}) pour Notification", id, e);
            throw e;
        }
    }

    @Override
    public void create(notification entity) throws Exception {
        try {
            log.info("Création d'une nouvelle notification: titre={}", entity.getTitre());
            String sql = buildInsertQuery(List.of(
                    "idNotif", "titre", "message", "dateEnvoi", "type", "lue"
            ));
            Long id = executeInsertAndGetId(sql,
                    entity.getIdNotif(),
                    entity.getTitre(),
                    entity.getMessage(),
                    entity.getDateEnvoi(),
                    entity.getType(),
                    entity.isLue() ? 1 : 0
            );
            entity.setId(id);
            log.info("✓ Notification créée avec id: {}", id);
        } catch (Exception e) {
            log.error("✗ Erreur lors de create() pour Notification", e);
            throw e;
        }
    }

    @Override
    public void update(notification entity) throws Exception {
        try {
            log.info("Mise à jour de la notification avec id: {}", entity.getId());
            String sql = buildUpdateQuery(
                    List.of("titre", "message", "dateEnvoi", "type", "lue"),
                    "id = ?"
            );
            executeUpdate(sql,
                    entity.getTitre(),
                    entity.getMessage(),
                    entity.getDateEnvoi(),
                    entity.getType(),
                    entity.isLue() ? 1 : 0,
                    entity.getId()
            );
            log.info("✓ Notification mise à jour avec id: {}", entity.getId());
        } catch (Exception e) {
            log.error("✗ Erreur lors de update() pour Notification", e);
            throw e;
        }
    }

    @Override
    public void delete(notification entity) throws Exception {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) throws Exception {
        try {
            log.info("Suppression de la notification avec id: {}", id);
            String sql = buildDeleteQuery("id = ?");
            executeUpdate(sql, id);
            log.info("✓ Notification supprimée avec id: {}", id);
        } catch (Exception e) {
            log.error("✗ Erreur lors de deleteById({}) pour Notification", id, e);
            throw e;
        }
    }

    @Override
    public List<notification> findByNonLues() throws Exception {
        try {
            log.info("Récupération des notifications non lues");
            String sql = buildSelectQuery("lue = false");
            List<notification> result = executeQuery(sql);
            log.info("✓ {} notification(s) non lue(s) trouvée(s)", result.size());
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findByNonLues() pour Notification", e);
            throw e;
        }
    }

    @Override
    public List<notification> findByType(String type) throws Exception {
        try {
            log.info("Recherche des notifications avec type: {}", type);
            String sql = buildSelectQuery("type = ?");
            List<notification> result = executeQuery(sql, type);
            log.info("✓ {} notification(s) trouvée(s) pour le type: {}", result.size(), type);
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findByType({}) pour Notification", type, e);
            throw e;
        }
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}

