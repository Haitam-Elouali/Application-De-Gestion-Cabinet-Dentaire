package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.api.RevenuesRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RevenuesRepositoryImpl implements RevenuesRepository {

    @Override
    public List<revenues> findAll() throws SQLException {
        List<revenues> revenuesList = new ArrayList<>();
        String sql = "SELECT * FROM Revenues";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                revenuesList.add(RowMappers.mapRevenues(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return revenuesList;
    }

    @Override
    public revenues findById(Long id) {
        String sql = "SELECT * FROM Revenues WHERE idRevenue = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapRevenues(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(revenues r) {
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null)
            r.setCreePar("SYSTEM");

        String sql = "INSERT INTO Revenues (dateCreation, creePar, idRevenue, factureId, titre, description, montant, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(r.getDateCreation()));
            ps.setString(2, r.getCreePar());

            ps.setLong(3, r.getIdRevenue());
            ps.setLong(4, r.getFactureId());
            ps.setString(5, r.getTitre());
            ps.setString(6, r.getDescription());
            ps.setDouble(7, r.getMontant());
            ps.setTimestamp(8, r.getDate() != null ? Timestamp.valueOf(r.getDate()) : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    r.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(revenues r) {
        r.setDateDerniereModification(LocalDateTime.now());
        if (r.getModifierPar() == null)
            r.setModifierPar("SYSTEM");

        String sql = "UPDATE Revenues SET idRevenue = ?, factureId = ?, titre = ?, description = ?, montant = ?, date = ?, dateDerniereModification = ?, modifierPar = ? WHERE idRevenue = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, r.getIdRevenue());
            ps.setLong(2, r.getFactureId());
            ps.setString(3, r.getTitre());
            ps.setString(4, r.getDescription());
            ps.setDouble(5, r.getMontant());
            ps.setTimestamp(6, r.getDate() != null ? Timestamp.valueOf(r.getDate()) : null);

            ps.setTimestamp(7, Timestamp.valueOf(r.getDateDerniereModification()));
            ps.setString(8, r.getModifierPar());

            ps.setLong(9, r.getIdRevenue());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(revenues r) {
        if (r != null && r.getIdRevenue() != null) {
            deleteById(r.getIdRevenue());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Revenues WHERE idRevenue = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<revenues> findByTitre(String titre) {
        String sql = "SELECT * FROM Revenues WHERE titre = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapRevenues(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<revenues> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<revenues> revenuesList = new ArrayList<>();
        String sql = "SELECT * FROM Revenues WHERE date BETWEEN ? AND ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    revenuesList.add(RowMappers.mapRevenues(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenuesList;
    }
}
