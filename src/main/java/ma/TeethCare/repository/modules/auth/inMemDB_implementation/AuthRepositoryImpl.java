package ma.TeethCare.repository.modules.auth.inMemDB_implementation;

import ma.TeethCare.entities.auth.auth;
import ma.TeethCare.repository.modules.auth.api.AuthRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthRepositoryImpl implements AuthRepository {

    private auth mapResultSetToEntity(ResultSet rs) throws SQLException {
        auth u = new auth();

        u.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            u.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            u.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        u.setCreePar(rs.getString("creePar"));
        u.setModifierPar(rs.getString("modifierPar"));

        u.setEmail(rs.getString("email"));
        u.setMotDePasse(rs.getString("motDePasse"));

        return u;
    }

    @Override
    public List<auth> findAll() {
        List<auth> authList = new ArrayList<>();
        String sql = "SELECT * FROM Auth";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                authList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authList;
    }

    @Override
    public auth findById(Long id) {
        String sql = "SELECT * FROM Auth WHERE idEntite = ?";

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
    public void create(auth u) {
        u.setDateCreation(LocalDate.now());
        if (u.getCreePar() == null) u.setCreePar("SYSTEM");

        String sql = "INSERT INTO Auth (dateCreation, creePar, email, motDePasse) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(u.getDateCreation()));
            ps.setString(2, u.getCreePar());

            ps.setString(3, u.getEmail());
            ps.setString(4, u.getMotDePasse());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    u.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(auth u) {
        u.setDateDerniereModification(LocalDateTime.now());
        if (u.getModifierPar() == null) u.setModifierPar("SYSTEM");

        String sql = "UPDATE Auth SET email = ?, motDePasse = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getEmail());
            ps.setString(2, u.getMotDePasse());

            ps.setTimestamp(3, Timestamp.valueOf(u.getDateDerniereModification()));
            ps.setString(4, u.getModifierPar());

            ps.setLong(5, u.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(auth u) {
        if (u != null && u.getIdEntite() != null) {
            deleteById(u.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Auth WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<auth> findByEmail(String email) {
        String sql = "SELECT * FROM Auth WHERE email = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
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
}