package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.api.OrdonnanceRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdonnanceRepositoryImpl implements OrdonnanceRepository {

    @Override
    public List<ordonnance> findAll() throws SQLException {
        List<ordonnance> ordonnanceList = new ArrayList<>();
        // Table: ordonnance
        // Columns: id, dateOrdonnance, consultation_id
        // Entity: consultationId, dateOrdonnance (medecinId, patientId, duree, frequence ignored)
        String sql = "SELECT t.id as idEntite, t.id as idOrd, t.dateOrdonnance, t.consultation_id as consultationId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM ordonnance t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ordonnanceList.add(RowMappers.mapOrdonnance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return ordonnanceList;
    }

    @Override
    public ordonnance findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idOrd, t.dateOrdonnance, t.consultation_id as consultationId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM ordonnance t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapOrdonnance(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(ordonnance o) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtOrd = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, o.getDateCreation() != null ? o.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, o.getCreePar() != null ? o.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, o.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                o.setIdEntite(id);
                o.setId(id);
            } else {
                throw new SQLException("Creating Entite for Ordonnance failed, no ID obtained.");
            }

            // 2. Insert into Ordonnance
            // Schema: id, dateOrdonnance, consultation_id
            String sqlOrd = "INSERT INTO ordonnance (id, dateOrdonnance, consultation_id) VALUES (?, ?, ?)";
            
            stmtOrd = conn.prepareStatement(sqlOrd);
            stmtOrd.setLong(1, id);
            stmtOrd.setDate(2, o.getDateOrdonnance() != null ? Date.valueOf(o.getDateOrdonnance()) : null);
            // Note: entity has 'date' AND 'dateOrdonnance'? method create used 'getDate()' but findById selected 'dateOrdonnance'. 
            // I'll prefer dateOrdonnance, fallback to date.
            stmtOrd.setLong(3, o.getConsultationId() != null ? o.getConsultationId() : 0);

            stmtOrd.executeUpdate();

            conn.commit();
            System.out.println("✓ Ordonnance créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Ordonnance: " + e.getMessage());
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
                if (stmtOrd != null) stmtOrd.close();
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
    public void update(ordonnance o) {
        o.setDateDerniereModification(LocalDateTime.now());
        if (o.getModifierPar() == null)
            o.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtOrd = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(o.getDateDerniereModification()));
            stmtEntite.setString(2, o.getModifierPar());
            stmtEntite.setLong(3, o.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Ordonnance
            String sqlOrd = "UPDATE ordonnance SET dateOrdonnance = ?, consultation_id = ? WHERE id = ?";
            stmtOrd = conn.prepareStatement(sqlOrd);
            stmtOrd.setDate(1, o.getDateOrdonnance() != null ? Date.valueOf(o.getDateOrdonnance()) : null);
            stmtOrd.setLong(2, o.getConsultationId());
            stmtOrd.setLong(3, o.getId());

            stmtOrd.executeUpdate();

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
                if (stmtOrd != null) stmtOrd.close();
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
    public void delete(ordonnance o) {
        if (o != null && o.getIdEntite() != null) {
            deleteById(o.getIdEntite());
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
