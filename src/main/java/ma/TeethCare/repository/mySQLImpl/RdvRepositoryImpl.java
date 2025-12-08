package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.api.RdvRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RdvRepositoryImpl implements RdvRepository {

    @Override
    public List<rdv> findAll() throws SQLException {
        List<rdv> rdvList = new ArrayList<>();
        String sql = "SELECT * FROM Rdv";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rdvList.add(RowMappers.mapRdv(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rdvList;
    }

    @Override
    public rdv findById(Long id) {
        String sql = "SELECT * FROM Rdv WHERE idRDV = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapRdv(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(rdv r) {
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null)
            r.setCreePar("SYSTEM");

        String sql = "INSERT INTO Rdv (dateCreation, creePar, idRDV, patientId, medecinId, date, heure, motif, statut, noteMedecin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(r.getDateCreation()));
            ps.setString(2, r.getCreePar());

            ps.setLong(3, r.getIdRDV());
            ps.setLong(4, r.getPatientId());
            ps.setLong(5, r.getMedecinId());
            ps.setDate(6, r.getDate() != null ? Date.valueOf(r.getDate()) : null);
            ps.setTime(7, r.getHeure() != null ? Time.valueOf(r.getHeure()) : null);
            ps.setString(8, r.getMotif());
            ps.setString(9, r.getStatut() != null ? r.getStatut().name() : null);
            ps.setString(10, r.getNoteMedecin());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    r.setIdRDV(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(rdv r) {
        r.setDateDerniereModification(LocalDateTime.now());
        if (r.getModifierPar() == null)
            r.setModifierPar("SYSTEM");

        String sql = "UPDATE Rdv SET idRDV = ?, patientId = ?, medecinId = ?, date = ?, heure = ?, motif = ?, statut = ?, noteMedecin = ?, dateDerniereModification = ?, modifierPar = ? WHERE idRDV = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, r.getIdRDV());
            ps.setLong(2, r.getPatientId());
            ps.setLong(3, r.getMedecinId());
            ps.setDate(4, r.getDate() != null ? Date.valueOf(r.getDate()) : null);
            ps.setTime(5, r.getHeure() != null ? Time.valueOf(r.getHeure()) : null);
            ps.setString(6, r.getMotif());
            ps.setString(7, r.getStatut() != null ? r.getStatut().name() : null);
            ps.setString(8, r.getNoteMedecin());

            ps.setTimestamp(9, Timestamp.valueOf(r.getDateDerniereModification()));
            ps.setString(10, r.getModifierPar());

            ps.setLong(11, r.getIdRDV());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(rdv r) {
        if (r != null && r.getIdRDV() != null) {
            deleteById(r.getIdRDV());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Rdv WHERE idRDV = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<rdv> findByIdRDV(Long idRDV) {
        String sql = "SELECT * FROM Rdv WHERE idRDV = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idRDV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapRdv(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<rdv> findByDate(LocalDate date) {
        List<rdv> rdvList = new ArrayList<>();
        String sql = "SELECT * FROM Rdv WHERE date = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rdvList.add(RowMappers.mapRdv(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rdvList;
    }
}
