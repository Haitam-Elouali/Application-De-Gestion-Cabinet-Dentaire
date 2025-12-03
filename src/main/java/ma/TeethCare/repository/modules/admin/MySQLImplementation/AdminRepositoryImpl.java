package ma.TeethCare.repository.modules.admin.MySQLImplementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.repository.common.GenericJdbcRepository;
import ma.TeethCare.repository.modules.admin.AdminRepository;

import java.util.List;

@Slf4j
public class AdminRepositoryImpl extends GenericJdbcRepository<admin> 
        implements AdminRepository {

            "useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String TABLE_NAME = "ADMIN";

    public AdminRepositoryImpl() {
        super(admin.class, TABLE_NAME);
        log.info("✓ AdminRepositoryImpl initialisé avec table: {}", TABLE_NAME);
    }

    @Override
    public List<admin> findAll() throws Exception {
        try {
            String sql = buildSelectQuery(null);
            log.info("Récupération de tous les admins");
            List<admin> result = executeQuery(sql);
            log.info("✓ {} admins récupérés", result.size());
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findAll() pour Admin", e);
            throw e;
        }
    }

    @Override
    public admin findById(Long id) throws Exception {
        try {
            String sql = buildSelectQuery("id = ?");
            log.info("Recherche de l'admin avec id: {}", id);
            admin result = executeSingleQuery(sql, id);
            if (result != null) {
                log.info("✓ Admin trouvé avec id: {}", id);
            } else {
                log.warn("⚠ Admin non trouvé avec id: {}", id);
            }
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findById({}) pour Admin", id, e);
            throw e;
        }
    }

    @Override
    public void create(admin entity) throws Exception {
        try {
            log.info("Création d'un nouveau admin: domaine={}", entity.getDomaine());
            String sql = buildInsertQuery(List.of(
                    "permissionAdmin", "domaine", "nom", "prenom", "email", "telephone", 
                    "dateCreation", "dateModification"
            ));
            Long id = executeInsertAndGetId(sql,
                    entity.getPermissionAdmin(),
                    entity.getDomaine(),
                    entity.getNom(),
                    entity.getPrenom(),
                    entity.getEmail(),
                    entity.getTelephone(),
                    entity.getDateCreation(),
                    entity.getDateModification()
            );
            entity.setId(id);
            log.info("✓ Admin créé avec id: {}", id);
        } catch (Exception e) {
            log.error("✗ Erreur lors de create() pour Admin", e);
            throw e;
        }
    }

    @Override
    public void update(admin entity) throws Exception {
        try {
            log.info("Mise à jour de l'admin avec id: {}", entity.getId());
            String sql = buildUpdateQuery(
                    List.of("permissionAdmin", "domaine", "nom", "prenom", "email", 
                            "telephone", "dateModification"),
                    "id = ?"
            );
            executeUpdate(sql,
                    entity.getPermissionAdmin(),
                    entity.getDomaine(),
                    entity.getNom(),
                    entity.getPrenom(),
                    entity.getEmail(),
                    entity.getTelephone(),
                    entity.getDateModification(),
                    entity.getId()
            );
            log.info("✓ Admin mis à jour avec id: {}", entity.getId());
        } catch (Exception e) {
            log.error("✗ Erreur lors de update() pour Admin", e);
            throw e;
        }
    }

    @Override
    public void delete(admin entity) throws Exception {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) throws Exception {
        try {
            log.info("Suppression de l'admin avec id: {}", id);
            String sql = buildDeleteQuery("id = ?");
            executeUpdate(sql, id);
            log.info("✓ Admin supprimé avec id: {}", id);
        } catch (Exception e) {
            log.error("✗ Erreur lors de deleteById({}) pour Admin", id, e);
            throw e;
        }
    }

    @Override
    public List<admin> findByDomaine(String domaine) throws Exception {
        try {
            log.info("Recherche des admins pour le domaine: {}", domaine);
            String sql = buildSelectQuery("domaine = ?");
            List<admin> result = executeQuery(sql, domaine);
            log.info("✓ {} admin(s) trouvé(s) pour le domaine: {}", result.size(), domaine);
            return result;
        } catch (Exception e) {
            log.error("✗ Erreur lors de findByDomaine({}) pour Admin", domaine, e);
            throw e;
        }
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}

