package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.common.enums.Libeller;
import ma.TeethCare.repository.api.RoleRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRepositoryImpl implements RoleRepository {

    @Override
    public List<role> findAll() throws SQLException {
        List<role> roleList = new ArrayList<>();
        String sql = "SELECT * FROM Role";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roleList.add(RowMappers.mapRole(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return roleList;
    }

    @Override
    public role findById(Long id) {
        String sql = "SELECT * FROM Role WHERE idRole = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapRole(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(role r) {
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null)
            r.setCreePar("SYSTEM");

        String sql = "INSERT INTO Role (dateCreation, creePar, idRole, libeller, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(r.getDateCreation()));
            ps.setString(2, r.getCreePar());

            ps.setLong(3, r.getIdRole());
            ps.setString(4, r.getLibeller() != null ? r.getLibeller().name() : null);
            ps.setString(5, r.getDescription());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    r.setIdRole(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(role r) {
        r.setDateDerniereModification(LocalDateTime.now());
        if (r.getModifierPar() == null)
            r.setModifierPar("SYSTEM");

        String sql = "UPDATE Role SET idRole = ?, libeller = ?, description = ?, dateDerniereModification = ?, modifierPar = ? WHERE idRole = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, r.getIdRole());
            ps.setString(2, r.getLibeller() != null ? r.getLibeller().name() : null);
            ps.setString(3, r.getDescription());

            ps.setTimestamp(4, Timestamp.valueOf(r.getDateDerniereModification()));
            ps.setString(5, r.getModifierPar());

            ps.setLong(6, r.getIdRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(role r) {
        if (r != null && r.getIdRole() != null) {
            deleteById(r.getIdRole());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Role WHERE idRole = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<role> findByLibeller(Libeller libeller) {
        String sql = "SELECT * FROM Role WHERE libeller = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libeller.name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapRole(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
