package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.api.InterventionMedecinRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InterventionMedecinRepositoryImpl implements InterventionMedecinRepository {

    @Override
    public List<interventionMedecin> findAll() throws SQLException {
        List<interventionMedecin> interventionList = new ArrayList<>();
        String sql = "SELECT * FROM InterventionMedecin";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                interventionList.add(RowMappers.mapInterventionMedecin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return interventionList;
    }

    @Override
    public interventionMedecin findById(Long id) {
        String sql = "SELECT * FROM InterventionMedecin WHERE idIM = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapInterventionMedecin(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(interventionMedecin i) {
        i.setDateCreation(LocalDate.now());
        if (i.getCreePar() == null)
            i.setCreePar("SYSTEM");

        String sql = "INSERT INTO InterventionMedecin (dateCreation, creePar, idIM, medecinId, acteId, consultationId, prixDePatient, numDent) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(i.getDateCreation()));
            ps.setString(2, i.getCreePar());

            ps.setLong(3, i.getIdIM());
            ps.setLong(4, i.getMedecinId());
            ps.setLong(5, i.getActeId());
            ps.setLong(6, i.getConsultationId());
            ps.setDouble(7, i.getPrixDePatient());
            ps.setInt(8, i.getNumDent());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    i.setIdIM(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(interventionMedecin i) {
        i.setDateDerniereModification(LocalDateTime.now());
        if (i.getModifierPar() == null)
            i.setModifierPar("SYSTEM");

        String sql = "UPDATE InterventionMedecin SET idIM = ?, medecinId = ?, acteId = ?, consultationId = ?, prixDePatient = ?, numDent = ?, dateDerniereModification = ?, modifierPar = ? WHERE idIM = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, i.getIdIM());
            ps.setLong(2, i.getMedecinId());
            ps.setLong(3, i.getActeId());
            ps.setLong(4, i.getConsultationId());
            ps.setDouble(5, i.getPrixDePatient());
            ps.setInt(6, i.getNumDent());

            ps.setTimestamp(7, Timestamp.valueOf(i.getDateDerniereModification()));
            ps.setString(8, i.getModifierPar());

            ps.setLong(9, i.getIdIM());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(interventionMedecin i) {
        if (i != null && i.getIdIM() != null) {
            deleteById(i.getIdIM());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM InterventionMedecin WHERE idIM = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
