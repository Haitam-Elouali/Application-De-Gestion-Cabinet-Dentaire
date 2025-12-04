package ma.TeethCare.repository.modules.role.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.entities.enums.Libeller;
import ma.TeethCare.repository.api.RoleRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRepositoryImpl implements RoleRepository {

    private role mapResultSetToEntity(ResultSet rs) throws SQLException {
        role r = new role();

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

        r.setIdRole(rs.getLong("idRole"));

        String libellerStr = rs.getString("libeller");
        if (libellerStr != null) {
            r.setLibeller(Libeller.valueOf(libellerStr));
        }

        return r;
    }

    @Override
    public List<role> findAll() {
        List<role> roleList = new ArrayList<>();
        String sql = "SELECT * FROM Role";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roleList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }

    @Override
    public role findById(Long id) {
        String sql = "SELECT * FROM Role WHERE idEntite = ?";

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
    public void create(role r) {
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null) r.setCreePar("SYSTEM");

        String sql = "INSERT INTO Role (dateCreation, creePar, idRole, libeller) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(r.getDateCreation()));
            ps.setString(2, r.getCreePar());

            ps.setLong(3, r.getIdRole());
            ps.setString(4, r.getLibeller() != null ? r.getLibeller().name() : null);

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
    public void update(role r) {
        r.setDateDerniereModification(LocalDateTime.now());
        if (r.getModifierPar() == null) r.setModifierPar("SYSTEM");

        String sql = "UPDATE Role SET idRole = ?, libeller = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, r.getIdRole());
            ps.setString(2, r.getLibeller() != null ? r.getLibeller().name() : null);

            ps.setTimestamp(3, Timestamp.valueOf(r.getDateDerniereModification()));
            ps.setString(4, r.getModifierPar());

            ps.setLong(5, r.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(role r) {
        if (r != null && r.getIdEntite() != null) {
            deleteById(r.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Role WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<role> findByIdRole(Long idRole) {
        String sql = "SELECT * FROM Role WHERE idRole = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idRole);
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
    public Optional<role> findByLibeller(Libeller libeller) {
        String sql = "SELECT * FROM Role WHERE libeller = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libeller.name());
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


