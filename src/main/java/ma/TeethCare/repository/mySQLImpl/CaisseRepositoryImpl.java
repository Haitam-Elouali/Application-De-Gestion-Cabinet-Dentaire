package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.repository.api.CaisseRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaisseRepositoryImpl implements CaisseRepository {

    @Override
    public List<caisse> findAll() throws SQLException {
        List<caisse> caisseList = new ArrayList<>();
        String sql = "SELECT * FROM Caisse";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                caisseList.add(RowMappers.mapCaisse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return caisseList;
    }

    @Override
    public caisse findById(Long id) {
        String sql = "SELECT * FROM Caisse WHERE idCaisse = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapCaisse(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(caisse c) {
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null)
            c.setCreePar("SYSTEM");

        String sql = "INSERT INTO Caisse (dateCreation, creePar, idCaisse, factureId, montant, dateEncaissement, modeEncaissement, reference) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setLong(3, c.getIdCaisse());
            ps.setLong(4, c.getFactureId());
            ps.setDouble(5, c.getMontant());
            ps.setDate(6, c.getDateEncaissement() != null ? Date.valueOf(c.getDateEncaissement()) : null);
            ps.setString(7, c.getModeEncaissement());
            ps.setString(8, c.getReference());

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
    public void update(caisse c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");

        String sql = "UPDATE Caisse SET idCaisse = ?, factureId = ?, montant = ?, dateEncaissement = ?, modeEncaissement = ?, reference = ?, dateDerniereModification = ?, modifierPar = ? WHERE idCaisse = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, c.getIdCaisse());
            ps.setLong(2, c.getFactureId());
            ps.setDouble(3, c.getMontant());
            ps.setDate(4, c.getDateEncaissement() != null ? Date.valueOf(c.getDateEncaissement()) : null);
            ps.setString(5, c.getModeEncaissement());
            ps.setString(6, c.getReference());

            ps.setTimestamp(7, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(8, c.getModifierPar());

            ps.setLong(9, c.getIdCaisse());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(caisse c) {
        if (c != null && c.getIdCaisse() != null) {
            deleteById(c.getIdCaisse());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Caisse WHERE idCaisse = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
