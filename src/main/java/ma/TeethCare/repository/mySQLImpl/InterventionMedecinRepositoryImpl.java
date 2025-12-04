package ma.TeethCare.repository.modules.interventionMedecin.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.api.InterventionMedecinRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InterventionMedecinRepositoryImpl implements InterventionMedecinRepository {

    private interventionMedecin mapResultSetToEntity(ResultSet rs) throws SQLException {
        interventionMedecin i = new interventionMedecin();

        i.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            i.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            i.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        i.setCreePar(rs.getString("creePar"));
        i.setModifierPar(rs.getString("modifierPar"));

        i.setIdIM(rs.getLong("idIM"));
        i.setPrixDePatient(rs.getDouble("prixDePatient"));
        i.setNumDent(rs.getInt("numDent"));

        return i;
    }

    @Override
    public List<interventionMedecin> findAll() {
        List<interventionMedecin> interventionList = new ArrayList<>();
        String sql = "SELECT * FROM InterventionMedecin";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                interventionList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return interventionList;
    }

    @Override
    public interventionMedecin findById(Long id) {
        String sql = "SELECT * FROM InterventionMedecin WHERE idEntite = ?";

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
    public void create(interventionMedecin i) {
        i.setDateCreation(LocalDate.now());
        if (i.getCreePar() == null) i.setCreePar("SYSTEM");

        String sql = "INSERT INTO InterventionMedecin (dateCreation, creePar, idIM, prixDePatient, numDent) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(i.getDateCreation()));
            ps.setString(2, i.getCreePar());

            ps.setLong(3, i.getIdIM());
            ps.setDouble(4, i.getPrixDePatient());
            ps.setInt(5, i.getNumDent());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    i.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(interventionMedecin i) {
        i.setDateDerniereModification(LocalDateTime.now());
        if (i.getModifierPar() == null) i.setModifierPar("SYSTEM");

        String sql = "UPDATE InterventionMedecin SET idIM = ?, prixDePatient = ?, numDent = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, i.getIdIM());
            ps.setDouble(2, i.getPrixDePatient());
            ps.setInt(3, i.getNumDent());

            ps.setTimestamp(4, Timestamp.valueOf(i.getDateDerniereModification()));
            ps.setString(5, i.getModifierPar());

            ps.setLong(6, i.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(interventionMedecin i) {
        if (i != null && i.getIdEntite() != null) {
            deleteById(i.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM InterventionMedecin WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<interventionMedecin> findByIdIM(Long idIM) {
        String sql = "SELECT * FROM InterventionMedecin WHERE idIM = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idIM);
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


