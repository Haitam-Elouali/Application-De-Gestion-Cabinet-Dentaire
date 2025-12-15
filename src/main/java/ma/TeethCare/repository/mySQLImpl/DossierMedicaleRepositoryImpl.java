package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.api.DossierMedicaleRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DossierMedicaleRepositoryImpl implements DossierMedicaleRepository {

    @Override
    public List<dossierMedicale> findAll() throws SQLException {
        List<dossierMedicale> dmList = new ArrayList<>();
        // Table: dossiermedicale (id, dateDeCreation, patient_id)
        String sql = "SELECT t.id as idEntite, t.id as idDM, t.dateDeCreation, t.patient_id as patientId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM dossiermedicale t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                dmList.add(RowMappers.mapDossierMedicale(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return dmList;
    }

    @Override
    public dossierMedicale findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idDM, t.dateDeCreation, t.patient_id as patientId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM dossiermedicale t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapDossierMedicale(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(dossierMedicale d) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtDM = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, d.getDateCreation() != null ? d.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, d.getCreePar() != null ? d.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, d.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                d.setIdEntite(id);
                d.setId(id);
            } else {
                throw new SQLException("Creating Entite for DossierMedicale failed, no ID obtained.");
            }

            // 2. Insert into DossierMedicale
            // Table: dossiermedicale (id, dateDeCreation, patient_id)
            String sqlDM = "INSERT INTO dossiermedicale (id, dateDeCreation, patient_id) VALUES (?, ?, ?)";
            stmtDM = conn.prepareStatement(sqlDM);
            stmtDM.setLong(1, id);
            stmtDM.setTimestamp(2, d.getDateDeCreation() != null ? Timestamp.valueOf(d.getDateDeCreation()) : null);
            stmtDM.setLong(3, d.getPatientId());

            stmtDM.executeUpdate();

            conn.commit();
            System.out.println("✓ DossierMedicale créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour DossierMedicale: " + e.getMessage());
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
                if (stmtDM != null) stmtDM.close();
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
    public void update(dossierMedicale d) {
        d.setDateDerniereModification(LocalDateTime.now());
        if (d.getModifierPar() == null)
            d.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtDM = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(d.getDateDerniereModification()));
            stmtEntite.setString(2, d.getModifierPar());
            stmtEntite.setLong(3, d.getIdEntite());
            stmtEntite.executeUpdate();

            // Update DossierMedicale
            String sqlDM = "UPDATE dossiermedicale SET dateDeCreation = ?, patient_id = ? WHERE id = ?";
            stmtDM = conn.prepareStatement(sqlDM);
            stmtDM.setTimestamp(1, d.getDateDeCreation() != null ? Timestamp.valueOf(d.getDateDeCreation()) : null);
            stmtDM.setLong(2, d.getPatientId());
            stmtDM.setLong(3, d.getId());

            stmtDM.executeUpdate();

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
                if (stmtDM != null) stmtDM.close();
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
    public void delete(dossierMedicale d) {
        if (d != null && d.getIdEntite() != null) {
            deleteById(d.getIdEntite());
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
