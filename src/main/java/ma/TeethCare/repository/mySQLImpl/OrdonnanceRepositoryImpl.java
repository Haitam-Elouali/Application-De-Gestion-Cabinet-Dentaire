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
        String sql = "SELECT * FROM Ordonnance";

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
        String sql = "SELECT * FROM Ordonnance WHERE idOrd = ?";

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
        o.setDateCreation(LocalDate.now());
        if (o.getCreePar() == null)
            o.setCreePar("SYSTEM");

        String sql = "INSERT INTO Ordonnance (dateCreation, creePar, idOrd, consultationId, medecinId, patientId, date, duree, frequence) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(o.getDateCreation()));
            ps.setString(2, o.getCreePar());

            ps.setLong(3, o.getIdOrd());
            ps.setLong(4, o.getConsultationId());
            ps.setLong(5, o.getMedecinId());
            ps.setLong(6, o.getPatientId());
            ps.setDate(7, o.getDate() != null ? Date.valueOf(o.getDate()) : null);
            ps.setString(8, o.getDuree());
            ps.setString(9, o.getFrequence());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    o.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ordonnance o) {
        o.setDateDerniereModification(LocalDateTime.now());
        if (o.getModifierPar() == null)
            o.setModifierPar("SYSTEM");

        String sql = "UPDATE Ordonnance SET idOrd = ?, consultationId = ?, medecinId = ?, patientId = ?, date = ?, duree = ?, frequence = ?, dateDerniereModification = ?, modifierPar = ? WHERE idOrd = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, o.getIdOrd());
            ps.setLong(2, o.getConsultationId());
            ps.setLong(3, o.getMedecinId());
            ps.setLong(4, o.getPatientId());
            ps.setDate(5, o.getDate() != null ? Date.valueOf(o.getDate()) : null);
            ps.setString(6, o.getDuree());
            ps.setString(7, o.getFrequence());

            ps.setTimestamp(8, Timestamp.valueOf(o.getDateDerniereModification()));
            ps.setString(9, o.getModifierPar());

            ps.setLong(10, o.getIdOrd());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ordonnance o) {
        if (o != null && o.getIdOrd() != null) {
            deleteById(o.getIdOrd());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Ordonnance WHERE idOrd = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
