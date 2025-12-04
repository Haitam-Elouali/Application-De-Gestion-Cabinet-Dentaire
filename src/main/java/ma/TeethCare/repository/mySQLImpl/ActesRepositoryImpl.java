package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.repository.common.GenericJdbcRepository;
import ma.TeethCare.repository.api.ActesRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation JDBC MySQL du repository Actes avec réflexion Java.
 * Utilise GenericJdbcRepository pour automatiser les opérations CRUD.
 * 
 * Features:
 * - Chargement du driver MySQL via Class.forName
 * - Mapping automatique par réflexion des champs de classe aux colonnes SQL
 * - Gestion des PreparedStatements et des résultats
 */
@Slf4j
public class ActesRepositoryImpl extends GenericJdbcRepository<actes> implements ActesRepository {

    private static final String TABLE_NAME = "ACTES";

    public ActesRepositoryImpl() {
        super(actes.class, TABLE_NAME);
        log.info("✓ ActesRepositoryImpl initialisé (MySQL JDBC avec réflexion)");
    }

    @Override
    public List<actes> findAll() {
        log.info("Recherche de tous les actes");
        String sql = buildSelectQuery(null);
        return executeQuery(sql);
    }

    @Override
    public actes findById(Long id) {
        log.info("Recherche de l'acte avec l'ID: {}", id);
        String sql = buildSelectQuery("idEntite = ?");
        return executeSingleQuery(sql, id);
    }

    @Override
    public void create(actes acte) {
        log.info("Création d'un nouvel acte: {}", acte.getLibeller());
        String sql = buildInsertQuery(List.of("libeller", "categorie", "prixDeBase"));
        
        Long generatedId = executeInsertAndGetId(sql,
                acte.getLibeller(),
                acte.getCategorie(),
                acte.getPrixDeBase()
        );
        
        if (generatedId != null) {
            acte.setIdEntite(generatedId);
            log.info("✓ Acte créé avec l'ID: {}", generatedId);
        }
    }

    @Override
    public void update(actes acte) {
        log.info("Mise à jour de l'acte ID: {}", acte.getIdEntite());
        String sql = buildUpdateQuery(
                List.of("libeller", "categorie", "prixDeBase"),
                "idEntite = ?"
        );
        
        executeUpdate(sql,
                acte.getLibeller(),
                acte.getCategorie(),
                acte.getPrixDeBase(),
                acte.getIdEntite()
        );
        log.info("✓ Acte ID {} mis à jour", acte.getIdEntite());
    }

    @Override
    public void delete(actes acte) {
        if (acte != null && acte.getIdEntite() != null) {
            deleteById(acte.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Suppression de l'acte ID: {}", id);
        String sql = buildDeleteQuery("idEntite = ?");
        executeUpdate(sql, id);
    }

    @Override
    public List<actes> findByCategorie(String categorie) {
        log.info("Recherche des actes par catégorie: {}", categorie);
        String sql = buildSelectQuery("categorie = ?");
        List<actes> results = executeQuery(sql, categorie);
        return results.stream()
                .filter(a -> a.getCategorie().equalsIgnoreCase(categorie))
                .collect(Collectors.toList());
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}


