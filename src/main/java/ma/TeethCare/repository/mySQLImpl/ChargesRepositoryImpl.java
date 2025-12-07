package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.api.ChargesRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChargesRepositoryImpl implements ChargesRepository {

    @Override
    public List<charges> findAll() throws SQLException {
        List<charges> chargesList = new ArrayList<>();
        String sql = "SELECT * FROM Charges";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                chargesList.add(RowMappers.mapCharges(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return chargesList;
    }

    @Override
    public charges findById(Long id) {
        String sql = "SELECT * FROM Charges WHERE idEntite = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapCharges(rs);
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
        if (c.getCreePar() == null)
            c.setCreePar("SYSTEM");

        String sql = "INSERT INTO Charges (dateCreation, creePar, titre, description, montant, categorie, date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setString(3, c.getTitre());
            ps.setString(4, c.getDescription());
            ps.setDouble(5, c.getMontant());
            ps.setString(6, c.getCategorie());
            ps.setTimestamp(7, c.getDate() != null ? Timestamp.valueOf(c.getDate()) : null);

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
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");

        String sql = "UPDATE Charges SET titre = ?, description = ?, montant = ?, categorie = ?, date = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getTitre());
            ps.setString(2, c.getDescription());
            ps.setDouble(3, c.getMontant());
            ps.setString(4, c.getCategorie());
            ps.setTimestamp(5, c.getDate() != null ? Timestamp.valueOf(c.getDate()) : null);

            ps.setTimestamp(6, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(7, c.getModifierPar());

            ps.setLong(8, c.getIdEntite());

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
        try (Connection conn = SessionFactory.getInstance().getConnection();
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

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapCharges(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
