package ma.TeethCare.repository.modules.charges.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.modules.charges.api.ChargesRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChargesRepositoryImpl implements ChargesRepository {

    private charges mapResultSetToEntity(ResultSet rs) throws SQLException {
        charges c = new charges();

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

        c.setTitre(rs.getString("titre"));
        c.setDescription(rs.getString("description"));
        c.setMontant(rs.getDouble("montant"));

        Timestamp dateSql = rs.getTimestamp("date");
        if (dateSql != null) {
            c.setDate(dateSql.toLocalDateTime());
        }

        return c;
    }

    @Override
    public List<charges> findAll() {
        List<charges> chargesList = new ArrayList<>();
        String sql = "SELECT * FROM Charges";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                chargesList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chargesList;
    }

    @Override
    public charges findById(Long id) {
        String sql = "SELECT * FROM Charges WHERE idEntite = ?";

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
    public void create(charges c) {
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null) c.setCreePar("SYSTEM");

        String sql = "INSERT INTO Charges (dateCreation, creePar, titre, description, montant, date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setString(3, c.getTitre());
            ps.setString(4, c.getDescription());
            ps.setDouble(5, c.getMontant());
            ps.setTimestamp(6, c.getDate() != null ? Timestamp.valueOf(c.getDate()) : null);

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
    public void update(charges c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null) c.setModifierPar("SYSTEM");

        String sql = "UPDATE Charges SET titre = ?, description = ?, montant = ?, date = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getTitre());
            ps.setString(2, c.getDescription());
            ps.setDouble(3, c.getMontant());
            ps.setTimestamp(4, c.getDate() != null ? Timestamp.valueOf(c.getDate()) : null);

            ps.setTimestamp(5, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(6, c.getModifierPar());

            ps.setLong(7, c.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(charges c) {
        if (c != null && c.getIdEntite() != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Charges WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<charges> findByTitre(String titre) {
        String sql = "SELECT * FROM Charges WHERE titre = ?";

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
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}

