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
        d.setDateCreation(LocalDate.now());
        if (d.getCreePar() == null)
            d.setCreePar("SYSTEM");

        String sql = "INSERT INTO DossierMedicale (dateCreation, creePar, idDM, patientId, dateDeCreation) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(d.getDateCreation()));
            ps.setString(2, d.getCreePar());

            ps.setLong(3, d.getIdDM());
            ps.setLong(4, d.getPatientId());
            ps.setTimestamp(5, d.getDateDeCreation() != null ? Timestamp.valueOf(d.getDateDeCreation()) : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    d.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
