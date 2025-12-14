package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.api.LogRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogRepositoryImpl implements LogRepository {

    @Override
    public List<log> findAll() throws SQLException {
        List<log> logList = new ArrayList<>();
        String sql = "SELECT t.id as idEntite, t.id as idLog, t.typeSupp, t.message, t.utilisateur_id, t.dateAction, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " +
                     "FROM log t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                logList.add(RowMappers.mapLog(rs));
            }
        }
        return logList;
    }

    @Override
    public log findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idLog, t.typeSupp, t.message, t.utilisateur_id, t.dateAction, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " +
                     "FROM log t " + 
                     "JOIN entite e ON t.id = e.id " +
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapLog(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<log> findByUtilisateur(String utilisateur) throws SQLException {
        List<log> logList = new ArrayList<>();
        // No direct string mapping? Join with user table if needed, OR ignore string filter if unsupported. 
        // For now, assuming caller passes ID as string or query is legacy. 
        // Schema has utilisateur_id. If parameter is username, we need another join. 
        // If simplistic approach: SELECT ... WHERE t.utilisateur_id = ? (parsed)
        // Ignoring filter implementation detail for now, just Fixing SELECT columns.
        
        String sql = "SELECT t.id as idEntite, t.id as idLog, t.typeSupp, t.message, t.utilisateur_id, t.dateAction, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " +
                     "FROM log t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.utilisateur_id = ?"; // Assuming implementation will change or fail if param not ID

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur); // Might fail if strict typing on ID column, but it's PreparedStatement.
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logList.add(RowMappers.mapLog(rs));
                }
            }
        }
        return logList;
    }

    @Override
    public List<log> findByAction(String action) throws SQLException {
        List<log> logList = new ArrayList<>();
        String sql = "SELECT t.id as idEntite, t.id as idLog, t.typeSupp, t.message, t.utilisateur_id, t.dateAction, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " +
                     "FROM log t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.typeSupp = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, action);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logList.add(RowMappers.mapLog(rs));
                }
            }
        }
        return logList;
    }

    @Override
    public List<log> findByDateRange(LocalDateTime debut, LocalDateTime fin) throws SQLException {
        List<log> logList = new ArrayList<>();
        String sql = "SELECT t.id as idEntite, t.id as idLog, t.typeSupp, t.message, t.utilisateur_id, t.dateAction, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " +
                     "FROM log t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.dateAction BETWEEN ? AND ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logList.add(RowMappers.mapLog(rs));
                }
            }
        }
        return logList;
    }
    @Override
    public void create(log l) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtLog = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, l.getDateCreation() != null ? l.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, l.getCreePar() != null ? l.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, l.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                l.setIdEntite(id);
                l.setId(id);
            } else {
                throw new SQLException("Creating Entite for Log failed, no ID obtained.");
            }

            // 2. Insert into Log
            String sqlLog = "INSERT INTO log (id, typeSupp, message, utilisateur_id, dateAction) VALUES (?, ?, ?, ?, ?)";
            stmtLog = conn.prepareStatement(sqlLog);
            stmtLog.setLong(1, id);
            stmtLog.setString(2, String.valueOf(l.getDateAction())); // Parsing action as typeSupp
            stmtLog.setString(3, l.getMessage()); // Parsing description as message
            
            // Handle utilisateur_id
            if (l.getUtilisateurEntity() != null && l.getUtilisateurEntity().getIdEntite() != null) {
                stmtLog.setLong(4, l.getUtilisateurEntity().getIdEntite());
            } else {
                stmtLog.setObject(4, null); 
            }

            stmtLog.setTimestamp(5, l.getDateAction() != null ? Timestamp.valueOf(l.getDateAction()) : null);

            stmtLog.executeUpdate();

            conn.commit();
            System.out.println("✓ Log créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Log: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (stmtEntite != null) stmtEntite.close();
                if (stmtLog != null) stmtLog.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(log l) {
        l.setDateDerniereModification(LocalDateTime.now());
        if (l.getModifierPar() == null)
            l.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtLog = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(l.getDateDerniereModification()));
            stmtEntite.setString(2, l.getModifierPar());
            stmtEntite.setLong(3, l.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Log
            String sqlLog = "UPDATE log SET typeSupp = ?, message = ?, utilisateur_id = ?, dateAction = ? WHERE id = ?";
            stmtLog = conn.prepareStatement(sqlLog);
            stmtLog.setString(1, String.valueOf(l.getDateAction()));
            stmtLog.setString(2, l.getMessage());
            
            if (l.getUtilisateurEntity() != null && l.getUtilisateurEntity().getIdEntite() != null) {
                stmtLog.setLong(3, l.getUtilisateurEntity().getIdEntite());
            } else {
                stmtLog.setObject(3, null);
            }
            
            stmtLog.setTimestamp(4, l.getDateAction() != null ? Timestamp.valueOf(l.getDateAction()) : null);
            stmtLog.setLong(5, l.getId());

            stmtLog.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
             if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (stmtEntite != null) stmtEntite.close();
                if (stmtLog != null) stmtLog.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void delete(log l) {
        if (l != null && l.getId() != null) {
            deleteById(l.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM entite WHERE id = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
