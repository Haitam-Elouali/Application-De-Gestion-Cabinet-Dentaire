package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.api.CertificatRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificatRepositoryImpl implements CertificatRepository {

    @Override
    public List<certificat> findAll() throws SQLException {
        List<certificat> certificatList = new ArrayList<>();
        // Alias database columns to match properties expected by RowMapper
        // id -> idCertif
        // duree -> dureer
        // note -> noteMedecin
        String sql = "SELECT t.id as idCertif, t.type, t.dateDebut, t.dateFin, t.duree as dureer, t.note as noteMedecin, t.consultation_id as consultationId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Certificat t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                certificatList.add(RowMappers.mapCertificat(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return certificatList;
    }

    @Override
    public certificat findById(Long id) {
        String sql = "SELECT t.id as idCertif, t.type, t.dateDebut, t.dateFin, t.duree as dureer, t.note as noteMedecin, t.consultation_id as consultationId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Certificat t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapCertificat(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(certificat c) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtCertif = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, c.getDateCreation() != null ? c.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, c.getCreePar() != null ? c.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, c.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                c.setIdEntite(id);
                c.setIdCertif(id);
            } else {
                throw new SQLException("Creating Entite for Certificat failed, no ID obtained.");
            }

            // 2. Insert into Certificat
            // Removed 'type' from SQL as it is not in the Entity
            String sqlCertif = "INSERT INTO certificat (id, dateDebut, dateFin, duree, note) VALUES (?, ?, ?, ?, ?)";
            
            stmtCertif = conn.prepareStatement(sqlCertif);
            stmtCertif.setLong(1, id);
            stmtCertif.setDate(2, c.getDateDebut() != null ? Date.valueOf(c.getDateDebut()) : null);
            stmtCertif.setDate(3, c.getDateFin() != null ? Date.valueOf(c.getDateFin()) : null);
            stmtCertif.setInt(4, c.getDuree());
            stmtCertif.setString(5, c.getNoteMedecin());

            stmtCertif.executeUpdate();

            conn.commit();
            System.out.println("✓ Certificat créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Certificat: " + e.getMessage());
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
                if (stmtCertif != null) stmtCertif.close();
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
    public void update(certificat c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtCertif = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(c.getDateDerniereModification()));
            stmtEntite.setString(2, c.getModifierPar());
            stmtEntite.setLong(3, c.getIdCertif()); 
            stmtEntite.executeUpdate();

            // Update Certificat
            // Removed 'type' from SQL
            String sqlCertif = "UPDATE Certificat SET dateDebut = ?, dateFin = ?, duree = ?, note = ? WHERE id = ?";
            stmtCertif = conn.prepareStatement(sqlCertif);
            stmtCertif.setDate(1, c.getDateDebut() != null ? Date.valueOf(c.getDateDebut()) : null);
            stmtCertif.setDate(2, c.getDateFin() != null ? Date.valueOf(c.getDateFin()) : null);
            stmtCertif.setInt(3, c.getDuree());
            stmtCertif.setString(4, c.getNoteMedecin());
            stmtCertif.setLong(5, c.getIdCertif());

            stmtCertif.executeUpdate();
            
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
                if (stmtCertif != null) stmtCertif.close();
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
    public void delete(certificat c) {
        if (c != null && c.getIdCertif() != null) {
            deleteById(c.getIdCertif());
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
