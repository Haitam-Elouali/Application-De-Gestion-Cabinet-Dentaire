package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.api.PrescriptionRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    @Override
    public List<prescription> findAll() throws SQLException {
        List<prescription> prescriptionList = new ArrayList<>();
        // Table: prescription
        // Columns: id, quantite, posologie, dureeEnJours, ordonnance_id, medicament_id
        // Entity: quantite, frequence (-> posologie), dureeEnjours, ordonnanceId, medicamentId
        String sql = "SELECT t.id as idEntite, t.id as idPr, t.quantite, t.posologie as frequence, t.dureeEnJours as dureeEnjours, t.ordonnance_id as ordonnanceId, t.medicament_id as medicamentId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM prescription t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                prescriptionList.add(RowMappers.mapPrescription(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return prescriptionList;
    }

    @Override
    public prescription findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idPr, t.quantite, t.posologie as frequence, t.dureeEnJours as dureeEnjours, t.ordonnance_id as ordonnanceId, t.medicament_id as medicamentId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM prescription t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapPrescription(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(prescription p) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtPresc = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, p.getDateCreation() != null ? p.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, p.getCreePar() != null ? p.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, p.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                p.setIdEntite(id);
                p.setIdPr(id);
            } else {
                throw new SQLException("Creating Entite for Prescription failed, no ID obtained.");
            }

            // 2. Insert into Prescription
            // Table: prescription (id, quantite, posologie, dureeEnJours, ordonnance_id, medicament_id)
            // Entity: quantite, frequence -> posologie, dureeEnjours, ordonnanceId, medicamentId
            String sqlPresc = "INSERT INTO prescription (id, quantite, posologie, dureeEnJours, ordonnance_id, medicament_id) VALUES (?, ?, ?, ?, ?, ?)";
            
            stmtPresc = conn.prepareStatement(sqlPresc);
            stmtPresc.setLong(1, id);
            stmtPresc.setInt(2, p.getQuantite());
            stmtPresc.setString(3, p.getFrequence());
            stmtPresc.setInt(4, p.getDureeEnjours());
            stmtPresc.setLong(5, p.getOrdonnanceId());
            stmtPresc.setLong(6, p.getMedicamentId());

            stmtPresc.executeUpdate();

            conn.commit();
            System.out.println("✓ Prescription créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Prescription: " + e.getMessage());
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
                if (stmtPresc != null) stmtPresc.close();
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
    public void update(prescription p) {
        p.setDateDerniereModification(LocalDateTime.now());
        if (p.getModifierPar() == null)
            p.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtPresc = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(p.getDateDerniereModification()));
            stmtEntite.setString(2, p.getModifierPar());
            stmtEntite.setLong(3, p.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Prescription
            String sqlPresc = "UPDATE prescription SET quantite = ?, posologie = ?, dureeEnJours = ?, ordonnance_id = ?, medicament_id = ? WHERE id = ?";
            stmtPresc = conn.prepareStatement(sqlPresc);
            stmtPresc.setInt(1, p.getQuantite());
            stmtPresc.setString(2, p.getFrequence());
            stmtPresc.setInt(3, p.getDureeEnjours());
            stmtPresc.setLong(4, p.getOrdonnanceId());
            stmtPresc.setLong(5, p.getMedicamentId());
            stmtPresc.setLong(6, p.getIdPr());

            stmtPresc.executeUpdate();

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
                if (stmtPresc != null) stmtPresc.close();
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
    public void delete(prescription p) {
        if (p != null && p.getIdEntite() != null) {
            deleteById(p.getIdEntite());
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
