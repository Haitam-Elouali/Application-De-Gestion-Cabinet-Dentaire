package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.api.CabinetMedicaleRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CabinetMedicaleRepositoryImpl implements CabinetMedicaleRepository {

    @Override
    public List<cabinetMedicale> findAll() throws SQLException {
        List<cabinetMedicale> cabinetList = new ArrayList<>();
        String sql = "SELECT * FROM CabinetMedicale";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cabinetList.add(RowMappers.mapCabinetMedicale(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return cabinetList;
    }

    @Override
    public cabinetMedicale findById(Long id) {
        String sql = "SELECT * FROM CabinetMedicale WHERE idEntite = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapCabinetMedicale(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(cabinetMedicale c) {
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null)
            c.setCreePar("SYSTEM");

        String sql = "INSERT INTO CabinetMedicale (dateCreation, creePar, nom, email, logo, cin, tel1, tel2, siteWeb, instagram, facebook, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setString(3, c.getNom());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getLogo());
            ps.setString(6, c.getCin());
            ps.setString(7, c.getTel1());
            ps.setString(8, c.getTel2());
            ps.setString(9, c.getSiteWeb());
            ps.setString(10, c.getInstagram());
            ps.setString(11, c.getFacebook());
            ps.setString(12, c.getDescription());

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
    public void update(cabinetMedicale c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");

        String sql = "UPDATE CabinetMedicale SET nom = ?, email = ?, logo = ?, cin = ?, tel1 = ?, tel2 = ?, siteWeb = ?, instagram = ?, facebook = ?, description = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNom());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getLogo());
            ps.setString(4, c.getCin());
            ps.setString(5, c.getTel1());
            ps.setString(6, c.getTel2());
            ps.setString(7, c.getSiteWeb());
            ps.setString(8, c.getInstagram());
            ps.setString(9, c.getFacebook());
            ps.setString(10, c.getDescription());

            ps.setTimestamp(11, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(12, c.getModifierPar());

            ps.setLong(13, c.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(cabinetMedicale c) {
        if (c != null && c.getIdEntite() != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM CabinetMedicale WHERE idEntite = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<cabinetMedicale> findByEmail(String email) {
        String sql = "SELECT * FROM CabinetMedicale WHERE email = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapCabinetMedicale(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
