package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.api.ConsultationRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    @Override
    public List<consultation> findAll() throws SQLException {
        List<consultation> consultationList = new ArrayList<>();
        String sql = "SELECT * FROM Consultation";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                consultationList.add(RowMappers.mapConsultation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return consultationList;
    }

    @Override
    public consultation findById(Long id) {
        String sql = "SELECT * FROM Consultation WHERE idConsultation = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapConsultation(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(consultation c) {
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null)
            c.setCreePar("SYSTEM");

        String sql = "INSERT INTO Consultation (dateCreation, creePar, idConsultation, rdvId, patientId, medecinId, Date, statut, observationMedecin, diagnostique) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setLong(3, c.getIdConsultation());
            ps.setLong(4, c.getRdvId());
            ps.setLong(5, c.getPatientId());
            ps.setLong(6, c.getMedecinId());
            ps.setDate(7, c.getDate() != null ? Date.valueOf(c.getDate()) : null);
            ps.setString(8, c.getStatut() != null ? c.getStatut().name() : null);
            ps.setString(9, c.getObservationMedecin());
            ps.setString(10, c.getDiagnostique());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    c.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(consultation c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");

        String sql = "UPDATE Consultation SET idConsultation = ?, rdvId = ?, patientId = ?, medecinId = ?, Date = ?, statut = ?, observationMedecin = ?, diagnostique = ?, dateDerniereModification = ?, modifierPar = ? WHERE idConsultation = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, c.getIdConsultation());
            ps.setLong(2, c.getRdvId());
            ps.setLong(3, c.getPatientId());
            ps.setLong(4, c.getMedecinId());
            ps.setDate(5, c.getDate() != null ? Date.valueOf(c.getDate()) : null);
            ps.setString(6, c.getStatut() != null ? c.getStatut().name() : null);
            ps.setString(7, c.getObservationMedecin());
            ps.setString(8, c.getDiagnostique());

            ps.setTimestamp(9, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(10, c.getModifierPar());

            ps.setLong(11, c.getIdConsultation());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(consultation c) {
        if (c != null && c.getIdConsultation() != null) {
            deleteById(c.getIdConsultation());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Consultation WHERE idConsultation = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
