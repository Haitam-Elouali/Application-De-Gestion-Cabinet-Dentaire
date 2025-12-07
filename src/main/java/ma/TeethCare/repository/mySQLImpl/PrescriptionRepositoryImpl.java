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
        String sql = "SELECT * FROM Prescription";

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
        String sql = "SELECT * FROM Prescription WHERE idPr = ?";

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
        p.setDateCreation(LocalDate.now());
        if (p.getCreePar() == null)
            p.setCreePar("SYSTEM");

        String sql = "INSERT INTO Prescription (dateCreation, creePar, idPr, ordonnanceId, medicamentId, quantite, frequence, dureeEnjours) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(p.getDateCreation()));
            ps.setString(2, p.getCreePar());

            ps.setLong(3, p.getIdPr());
            ps.setLong(4, p.getOrdonnanceId());
            ps.setLong(5, p.getMedicamentId());
            ps.setInt(6, p.getQuantite());
            ps.setString(7, p.getFrequence());
            ps.setInt(8, p.getDureeEnjours());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(prescription p) {
        p.setDateDerniereModification(LocalDateTime.now());
        if (p.getModifierPar() == null)
            p.setModifierPar("SYSTEM");

        String sql = "UPDATE Prescription SET idPr = ?, ordonnanceId = ?, medicamentId = ?, quantite = ?, frequence = ?, dureeEnjours = ?, dateDerniereModification = ?, modifierPar = ? WHERE idPr = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, p.getIdPr());
            ps.setLong(2, p.getOrdonnanceId());
            ps.setLong(3, p.getMedicamentId());
            ps.setInt(4, p.getQuantite());
            ps.setString(5, p.getFrequence());
            ps.setInt(6, p.getDureeEnjours());

            ps.setTimestamp(7, Timestamp.valueOf(p.getDateDerniereModification()));
            ps.setString(8, p.getModifierPar());

            ps.setLong(9, p.getIdPr());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(prescription p) {
        if (p != null && p.getIdPr() != null) {
            deleteById(p.getIdPr());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Prescription WHERE idPr = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
