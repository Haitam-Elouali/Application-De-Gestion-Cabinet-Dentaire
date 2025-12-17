package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.api.FactureRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactureRepositoryImpl implements FactureRepository {

    @Override
    public List<facture> findAll() throws SQLException {
        List<facture> factureList = new ArrayList<>();
        // Table: facture (lower case)
        // Join with entite
        // Aliases: id -> idEntite, id -> idFacture
        // Reste -> reste (Map column Reste to Entity field reste usually handled by mapper if column name matches, 
        // but if mapper uses 'reste', and DB has 'Reste', usually Case Insensitive in MySQL unless quoted. 
        // I will select t.Reste as reste just in case mapper expects 'reste').
        // consultationId not in schema, ignoring.
        
        String sql = "SELECT t.id as idEntite, t.id as idFacture, t.totaleFacture, t.totalePaye, t.totalePaye as totalPaye, t.Reste as reste, t.statut, t.dateFacture, " + 
                     "t.patient_id as patientId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM facture t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                factureList.add(RowMappers.mapFacture(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return factureList;
    }

    @Override
    public facture findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idFacture, t.totaleFacture, t.totalePaye, t.totalePaye as totalPaye, t.Reste as reste, t.statut, t.dateFacture, " + 
                     "t.patient_id as patientId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM facture t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapFacture(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(facture f) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtFacture = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, f.getDateCreation() != null ? f.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, f.getCreePar() != null ? f.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, f.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                f.setIdEntite(id);
                f.setId(id);
            } else {
                throw new SQLException("Creating Entite for Facture failed, no ID obtained.");
            }

            // 2. Insert into Facture
            // Schema: id, totaleFacture, totalePaye, Reste, statut, modePaiement, dateFacture, patient_id, secretaire_id
            // Entity: totaleFacture, totalPaye, reste, statut, dateFacture, patientId, consultationId (ignored)
            // Need to insert into 'Reste'.
            String sqlFacture = "INSERT INTO facture (id, totaleFacture, totalePaye, Reste, statut, dateFacture, patient_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            stmtFacture = conn.prepareStatement(sqlFacture);
            stmtFacture.setLong(1, id);
            stmtFacture.setDouble(2, f.getTotaleFacture());
            stmtFacture.setDouble(3, f.getTotalePaye());
            stmtFacture.setDouble(4, f.getReste());
            stmtFacture.setString(5, f.getStatut() != null ? f.getStatut().name() : null);
            stmtFacture.setTimestamp(6, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : null);
            stmtFacture.setLong(7, f.getPatientId());

            stmtFacture.executeUpdate();

            conn.commit();
            System.out.println("✓ Facture créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Facture: " + e.getMessage());
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
                if (stmtFacture != null) stmtFacture.close();
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
    public void update(facture f) {
        f.setDateDerniereModification(LocalDateTime.now());
        if (f.getModifierPar() == null)
            f.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtFacture = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(f.getDateDerniereModification()));
            stmtEntite.setString(2, f.getModifierPar());
            stmtEntite.setLong(3, f.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Facture
            String sqlFacture = "UPDATE facture SET totaleFacture = ?, totalePaye = ?, Reste = ?, statut = ?, dateFacture = ?, patient_id = ? WHERE id = ?";
            stmtFacture = conn.prepareStatement(sqlFacture);
            stmtFacture.setDouble(1, f.getTotaleFacture());
            stmtFacture.setDouble(2, f.getTotalePaye());
            stmtFacture.setDouble(3, f.getReste());
            stmtFacture.setString(4, f.getStatut() != null ? f.getStatut().name() : null);
            stmtFacture.setTimestamp(5, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : null);
            stmtFacture.setLong(6, f.getPatientId());
            stmtFacture.setLong(7, f.getId());

            stmtFacture.executeUpdate();
            
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
                if (stmtFacture != null) stmtFacture.close();
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
    public void delete(facture f) {
        if (f != null && f.getIdEntite() != null) {
            deleteById(f.getIdEntite());
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
