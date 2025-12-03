package ma.TeethCare.repository.modules.consultation.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.modules.consultation.api.ConsultationRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    private consultation mapResultSetToEntity(ResultSet rs) throws SQLException {
        consultation c = new consultation();

        c.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            c.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            c.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        c.setCreePar(rs.getString("creePar"));
        c.setModifierPar(rs.getString("modifierPar"));

        c.setIdConsultation(rs.getLong("idConsultation"));

        Date dateSql = rs.getDate("Date");
        if (dateSql != null) {
            c.setDate(dateSql.toLocalDate());
        }

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            c.setStatut(Statut.valueOf(statutStr));
        }

        c.setObservationMedecin(rs.getString("observationMedecin"));

        return c;
    }

    @Override
    public List<consultation> findAll() {
        List<consultation> consultationList = new ArrayList<>();
        String sql = "SELECT * FROM Consultation";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                consultationList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultationList;
    }

    @Override
    public consultation findById(Long id) {
        String sql = "SELECT * FROM Consultation WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
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
        if (c.getCreePar() == null) c.setCreePar("SYSTEM");

        String sql = "INSERT INTO Consultation (dateCreation, creePar, idConsultation, Date, statut, observationMedecin) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setLong(3, c.getIdConsultation());
            ps.setDate(4, c.getDate() != null ? Date.valueOf(c.getDate()) : null);
            ps.setString(5, c.getStatut() != null ? c.getStatut().name() : null);
            ps.setString(6, c.getObservationMedecin());

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
        if (c.getModifierPar() == null) c.setModifierPar("SYSTEM");

        String sql = "UPDATE Consultation SET idConsultation = ?, Date = ?, statut = ?, observationMedecin = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, c.getIdConsultation());
            ps.setDate(2, c.getDate() != null ? Date.valueOf(c.getDate()) : null);
            ps.setString(3, c.getStatut() != null ? c.getStatut().name() : null);
            ps.setString(4, c.getObservationMedecin());

            ps.setTimestamp(5, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(6, c.getModifierPar());

            ps.setLong(7, c.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(consultation c) {
        if (c != null && c.getIdEntite() != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Consultation WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<consultation> findByIdConsultation(Long idConsultation) {
        String sql = "SELECT * FROM Consultation WHERE idConsultation = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idConsultation);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}

