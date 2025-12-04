package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.repository.common.GenericJdbcRepository;
import ma.TeethCare.repository.api.FactureRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Implémentation JDBC MySQL du repository Facture avec réflexion Java.
 * 
 * Cas d'usage avancés:
 * - Filtrage par plages de dates
 * - Agrégations (SUM, COUNT)
 * - Calculs de totaux
 * - Statuts avec énumérations
 */
@Slf4j
public class FactureRepositoryImpl extends GenericJdbcRepository<facture> implements FactureRepository {

    private static final String TABLE_NAME = "FACTURE";

    public FactureRepositoryImpl() {
        super(facture.class, TABLE_NAME);
        log.info("✓ FactureRepositoryImpl initialisé (MySQL JDBC avec réflexion)");
    }

    @Override
    public List<facture> findAll() {
        log.info("Recherche de toutes les factures");
        String sql = buildSelectQuery(null) + " ORDER BY dateFacture DESC";
        return executeQuery(sql);
    }

    @Override
    public facture findById(Long id) {
        log.info("Recherche de la facture ID: {}", id);
        String sql = buildSelectQuery("idEntite = ?");
        return executeSingleQuery(sql, id);
    }

    @Override
    public void create(facture facture) {
        log.info("Création d'une nouvelle facture");
        String sql = buildInsertQuery(List.of(
                "idFacture", "totaleFacture", "totalPaye", "reste", "statut",
                "dateFacture", "dateCreation"
        ));

        Long generatedId = executeInsertAndGetId(sql,
                facture.getIdFacture(),
                facture.getTotaleFacture(),
                facture.getTotalPaye(),
                facture.getReste(),
                facture.getStatut() != null ? facture.getStatut().name() : null,
                facture.getDateFacture(),
                facture.getDateCreation()
        );

        if (generatedId != null) {
            facture.setIdEntite(generatedId);
            log.info("✓ Facture créée avec l'ID: {}", generatedId);
        }
    }

    @Override
    public void update(facture facture) {
        log.info("Mise à jour de la facture ID: {}", facture.getIdEntite());
        String sql = buildUpdateQuery(
                List.of("totaleFacture", "totalPaye", "reste", "statut", "dateDerniereModification"),
                "idEntite = ?"
        );

        executeUpdate(sql,
                facture.getTotaleFacture(),
                facture.getTotalPaye(),
                facture.getReste(),
                facture.getStatut() != null ? facture.getStatut().name() : null,
                facture.getDateDerniereModification(),
                facture.getIdEntite()
        );
        log.info("✓ Facture ID {} mise à jour", facture.getIdEntite());
    }

    @Override
    public void delete(facture facture) {
        if (facture != null && facture.getIdEntite() != null) {
            deleteById(facture.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Suppression de la facture ID: {}", id);
        String sql = buildDeleteQuery("idEntite = ?");
        executeUpdate(sql, id);
    }

    @Override
    public List<facture> findByStatut(Statut statut) {
        log.info("Recherche des factures par statut: {}", statut);
        String sql = buildSelectQuery("statut = ?");
        return executeQuery(sql, statut != null ? statut.name() : null);
    }

    @Override
    public List<facture> findByDateRange(LocalDate dateDebut, LocalDate dateFin) {
        log.info("Recherche des factures entre {} et {}", dateDebut, dateFin);
        String sql = buildSelectQuery("DATE(dateFacture) BETWEEN ? AND ?");
        return executeQuery(sql, dateDebut, dateFin);
    }

    @Override
    public List<facture> findUnpaid() {
        log.info("Recherche des factures non payées (reste > 0)");
        String sql = buildSelectQuery("reste > 0") + " ORDER BY dateFacture ASC";
        return executeQuery(sql);
    }

    @Override
    public List<facture> findPending() {
        log.info("Recherche des factures en attente");
        String sql = buildSelectQuery("statut = ?");
        return executeQuery(sql, Statut.Attente.name());
    }

    @Override
    public double getTotalPending() {
        log.info("Calcul du total des factures en attente");
        String sql = "SELECT SUM(totaleFacture) as total FROM " + tableName + " WHERE statut = ?";

        try (var conn = getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Statut.Attente.name());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double total = rs.getDouble("total");
                    log.info("✓ Total factures en attente: {}", total);
                    return total;
                }
            }
        } catch (Exception e) {
            log.error("✗ Erreur lors du calcul du total: {}", e.getMessage());
        }
        return 0.0;
    }

    @Override
    public double getAverageAmount() {
        log.info("Calcul du montant moyen des factures");
        String sql = "SELECT AVG(totaleFacture) as avg FROM " + tableName;

        try (var conn = getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            if (rs.next()) {
                double average = rs.getDouble("avg");
                log.info("✓ Montant moyen des factures: {}", average);
                return average;
            }
        } catch (Exception e) {
            log.error("✗ Erreur lors du calcul de la moyenne: {}", e.getMessage());
        }
        return 0.0;
    }

    @Override
    public long countByStatut(Statut statut) {
        log.info("Comptage des factures par statut: {}", statut);
        String sql = "SELECT COUNT(*) as count FROM " + tableName + " WHERE statut = ?";

        try (var conn = getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statut != null ? statut.name() : null);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    long count = rs.getLong("count");
                    log.info("✓ {} facture(s) avec le statut: {}", count, statut);
                    return count;
                }
            }
        } catch (Exception e) {
            log.error("✗ Erreur lors du comptage: {}", e.getMessage());
        }
        return 0L;
    }
}
    public List<facture> findAll() {
        List<facture> factureList = new ArrayList<>();
        String sql = "SELECT * FROM Facture";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                factureList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factureList;
    }

    @Override
    public facture findById(Long id) {
        String sql = "SELECT * FROM Facture WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(facture f) {
        f.setDateCreation(LocalDate.now());
        if (f.getCreePar() == null) f.setCreePar("SYSTEM");

        String sql = "INSERT INTO Facture (dateCreation, creePar, idFacture, totaleFacture, totalPaye, reste, statut, dateFacture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(f.getDateCreation()));
            ps.setString(2, f.getCreePar());

            ps.setLong(3, f.getIdFacture());
            ps.setDouble(4, f.getTotaleFacture());
            ps.setDouble(5, f.getTotalPaye());
            ps.setDouble(6, f.getReste());
            ps.setString(7, f.getStatut() != null ? f.getStatut().name() : null);
            ps.setTimestamp(8, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    f.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(facture f) {
        f.setDateDerniereModification(LocalDateTime.now());
        if (f.getModifierPar() == null) f.setModifierPar("SYSTEM");

        String sql = "UPDATE Facture SET idFacture = ?, totaleFacture = ?, totalPaye = ?, reste = ?, statut = ?, dateFacture = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, f.getIdFacture());
            ps.setDouble(2, f.getTotaleFacture());
            ps.setDouble(3, f.getTotalPaye());
            ps.setDouble(4, f.getReste());
            ps.setString(5, f.getStatut() != null ? f.getStatut().name() : null);
            ps.setTimestamp(6, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : null);

            ps.setTimestamp(7, Timestamp.valueOf(f.getDateDerniereModification()));
            ps.setString(8, f.getModifierPar());

            ps.setLong(9, f.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(facture f) {
        if (f != null && f.getIdEntite() != null) {
            deleteById(f.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Facture WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<facture> findByIdFacture(Long idFacture) {
        String sql = "SELECT * FROM Facture WHERE idFacture = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idFacture);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}


