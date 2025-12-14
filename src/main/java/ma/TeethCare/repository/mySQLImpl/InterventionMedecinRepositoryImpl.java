package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.api.InterventionMedecinRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InterventionMedecinRepositoryImpl implements InterventionMedecinRepository {

    @Override
    public List<interventionMedecin> findAll() throws SQLException {
        List<interventionMedecin> interventionList = new ArrayList<>();
        // Table: interventionmedecin
        // Columns: id, duree, note, resultatImagerie, consultation_id
        // Entity: consultationId (medecinId, acteId, numDent, prixDePatient ignored)
        String sql = "SELECT t.id as idEntite, t.id as idIM, t.consultation_id as consultationId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM interventionmedecin t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                interventionList.add(RowMappers.mapInterventionMedecin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return interventionList;
    }

    @Override
    public interventionMedecin findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idIM, t.consultation_id as consultationId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM interventionmedecin t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapInterventionMedecin(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(interventionMedecin i) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtIM = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, i.getDateCreation() != null ? i.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, i.getCreePar() != null ? i.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, i.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                i.setIdEntite(id);
                i.setId(id);
            } else {
                throw new SQLException("Creating Entite for InterventionMedecin failed, no ID obtained.");
            }

            // 2. Insert into InterventionMedecin
            // Table: interventionmedecin (id, duree, note, resultatImagerie, consultation_id)
            // Entity: consultationId. Ignored: medecinId, acteId, prixDePatient, numDent.
            String sqlIM = "INSERT INTO interventionmedecin (id, consultation_id) VALUES (?, ?)";
            stmtIM = conn.prepareStatement(sqlIM);
            stmtIM.setLong(1, id);
            stmtIM.setLong(2, i.getConsultationId());

            stmtIM.executeUpdate();

            conn.commit();
            System.out.println("✓ InterventionMedecin créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour InterventionMedecin: " + e.getMessage());
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
                if (stmtIM != null) stmtIM.close();
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
    public void update(interventionMedecin i) {
        i.setDateDerniereModification(LocalDateTime.now());
        if (i.getModifierPar() == null)
            i.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtIM = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(i.getDateDerniereModification()));
            stmtEntite.setString(2, i.getModifierPar());
            stmtEntite.setLong(3, i.getIdEntite());
            stmtEntite.executeUpdate();

            // Update InterventionMedecin
            String sqlIM = "UPDATE interventionmedecin SET consultation_id = ? WHERE id = ?";
            stmtIM = conn.prepareStatement(sqlIM);
            stmtIM.setLong(1, i.getConsultationId());
            stmtIM.setLong(2, i.getId());

            stmtIM.executeUpdate();

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
                if (stmtIM != null) stmtIM.close();
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
    public void delete(interventionMedecin i) {
        if (i != null && i.getIdEntite() != null) {
            deleteById(i.getIdEntite());
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
