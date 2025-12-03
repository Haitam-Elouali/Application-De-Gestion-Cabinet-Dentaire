package ma.TeethCare.repository.modules.log.MySQLImplementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.common.GenericJdbcRepository;
import ma.TeethCare.repository.modules.log.LogRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class LogRepositoryImpl extends GenericJdbcRepository<log> 
        implements LogRepository {

            "useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String TABLE_NAME = "LOG";

    public LogRepositoryImpl() {
        super(log.class, TABLE_NAME);
        log.info("✓ LogRepositoryImpl initialisé avec table: {}", TABLE_NAME);
    }

    @Override
    public List<log> findAll() throws Exception {
        try {
            String sql = buildSelectQuery(null);
            log.info("Récupération de tous les logs");
            List<log> result = executeQuery(sql);
            log.info("✓ {} log(s) récupéré(s)", result.size());
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findAll() pour Log", e);
            throw e;
        }
    }

    @Override
    public log findById(Long id) throws Exception {
        try {
            String sql = buildSelectQuery("id = ?");
            log.info("Recherche du log avec id: {}", id);
            log result = executeSingleQuery(sql, id);
            if (result != null) {
                log.info("✓ Log trouvé avec id: {}", id);
            } else {
                log.warn("⚠ Log non trouvé avec id: {}", id);
            }
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findById({}) pour Log", id, e);
            throw e;
        }
    }

    @Override
    public void create(log entity) throws Exception {
        try {
            log.info("Création d'un nouveau log: action={}", entity.getAction());
            String sql = buildInsertQuery(List.of(
                    "idLog", "action", "utilisateur", "dateAction", "description", "adresseIP"
            ));
            Long id = executeInsertAndGetId(sql,
                    entity.getIdLog(),
                    entity.getAction(),
                    entity.getUtilisateur(),
                    entity.getDateAction(),
                    entity.getDescription(),
                    entity.getAdresseIP()
            );
            entity.setId(id);
            log.info("✓ Log créé avec id: {}", id);
        } catch (Exception e) {
            log.error("✗ Erreur lors de create() pour Log", e);
            throw e;
        }
    }

    @Override
    public void update(log entity) throws Exception {
        try {
            log.info("Mise à jour du log avec id: {}", entity.getId());
            String sql = buildUpdateQuery(
                    List.of("action", "utilisateur", "dateAction", "description", "adresseIP"),
                    "id = ?"
            );
            executeUpdate(sql,
                    entity.getAction(),
                    entity.getUtilisateur(),
                    entity.getDateAction(),
                    entity.getDescription(),
                    entity.getAdresseIP(),
                    entity.getId()
            );
            log.info("✓ Log mis à jour avec id: {}", entity.getId());
        } catch (Exception e) {
            log.error("✗ Erreur lors de update() pour Log", e);
            throw e;
        }
    }

    @Override
    public void delete(log entity) throws Exception {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) throws Exception {
        try {
            log.info("Suppression du log avec id: {}", id);
            String sql = buildDeleteQuery("id = ?");
            executeUpdate(sql, id);
            log.info("✓ Log supprimé avec id: {}", id);
        } catch (Exception e) {
            log.error("✗ Erreur lors de deleteById({}) pour Log", id, e);
            throw e;
        }
    }

    @Override
    public List<log> findByUtilisateur(String utilisateur) throws Exception {
        try {
            log.info("Recherche des logs pour l'utilisateur: {}", utilisateur);
            String sql = buildSelectQuery("utilisateur = ?");
            List<log> result = executeQuery(sql, utilisateur);
            log.info("✓ {} log(s) trouvé(s) pour l'utilisateur: {}", result.size(), utilisateur);
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findByUtilisateur({}) pour Log", utilisateur, e);
            throw e;
        }
    }

    @Override
    public List<log> findByAction(String action) throws Exception {
        try {
            log.info("Recherche des logs pour l'action: {}", action);
            String sql = buildSelectQuery("action = ?");
            List<log> result = executeQuery(sql, action);
            log.info("✓ {} log(s) trouvé(s) pour l'action: {}", result.size(), action);
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findByAction({}) pour Log", action, e);
            throw e;
        }
    }

    @Override
    public List<log> findByDateRange(LocalDateTime debut, LocalDateTime fin) throws Exception {
        try {
            log.info("Recherche des logs entre {} et {}", debut, fin);
            String sql = buildSelectQuery("dateAction BETWEEN ? AND ?");
            List<log> result = executeQuery(sql, debut, fin);
            log.info("✓ {} log(s) trouvé(s) dans la plage de dates", result.size());
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findByDateRange({}, {}) pour Log", debut, fin, e);
            throw e;
        }
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}

