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
        String sql = "SELECT * FROM DossierMedicale";

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
        String sql = "SELECT * FROM DossierMedicale WHERE idDM = ?";

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
                d.setIdDM(id);
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

        String sql = "UPDATE DossierMedicale SET idDM = ?, patientId = ?, dateDeCreation = ?, dateDerniereModification = ?, modifierPar = ? WHERE idDM = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, d.getIdDM());
            ps.setLong(2, d.getPatientId());
            ps.setTimestamp(3, d.getDateDeCreation() != null ? Timestamp.valueOf(d.getDateDeCreation()) : null);

            ps.setTimestamp(4, Timestamp.valueOf(d.getDateDerniereModification()));
            ps.setString(5, d.getModifierPar());

            ps.setLong(6, d.getIdDM());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(dossierMedicale d) {
        if (d != null && d.getIdDM() != null) {
            deleteById(d.getIdDM());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM DossierMedicale WHERE idDM = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
