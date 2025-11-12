package ma.TeethCare.repository.modules.revenues.inMemDB_implementation;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.modules.revenues.api.RevenuesRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RevenuesRepositoryImpl implements RevenuesRepository {

    private revenues mapResultSetToEntity(ResultSet rs) throws SQLException {
        revenues r = new revenues();

        r.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            r.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            r.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        r.setCreePar(rs.getString("creePar"));
        r.setModifierPar(rs.getString("modifierPar"));

        r.setTitre(rs.getString("titre"));
        r.setDescription(rs.getString("description"));
        r.setMontant(rs.getDouble("montant"));

        Timestamp dateSql = rs.getTimestamp("date");
        if (dateSql != null) {
            r.setDate(dateSql.toLocalDateTime());
        }

        return r;
    }

    @Override
    public List<revenues> findAll() {
        List<revenues> revenuesList = new ArrayList<>();
        String sql = "SELECT * FROM Revenues";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                revenuesList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenuesList;
    }

    @Override
    public revenues findById(Long id) {
        String sql = "SELECT * FROM Revenues WHERE idEntite = ?";

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
    public void create(revenues r) {
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null) r.setCreePar("SYSTEM");

        String sql = "INSERT INTO Revenues (dateCreation, creePar, titre, description, montant, date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(r.getDateCreation()));
            ps.setString(2, r.getCreePar());

            ps.setString(3, r.getTitre());
            ps.setString(4, r.getDescription());
            ps.setDouble(5, r.getMontant());
            ps.setTimestamp(6, r.getDate() != null ? Timestamp.valueOf(r.getDate()) : null);

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
        if (r.getModifierPar() == null) r.setModifierPar("SYSTEM");

        String sql = "UPDATE Revenues SET titre = ?, description = ?, montant = ?, date = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getTitre());
            ps.setString(2, r.getDescription());
            ps.setDouble(3, r.getMontant());
            ps.setTimestamp(4, r.getDate() != null ? Timestamp.valueOf(r.getDate()) : null);

            ps.setTimestamp(5, Timestamp.valueOf(r.getDateDerniereModification()));
            ps.setString(6, r.getModifierPar());

            ps.setLong(7, r.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(revenues r) {
        if (r != null && r.getIdEntite() != null) {
            deleteById(r.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Revenues WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
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

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titre);
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
    public List<revenues> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<revenues> revenuesList = new ArrayList<>();
        String sql = "SELECT * FROM Revenues WHERE date BETWEEN ? AND ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    revenuesList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenuesList;
    }
}
