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
        String sql = "SELECT t.id as idRole, t.id as idEntite, t.libelle as libeller, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Role t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roleList.add(RowMappers.mapRole(rs));
            }
        }
        return roleList;
    }

    @Override
    public role findById(Long id) {
        String sql = "SELECT t.id as idRole, t.id as idEntite, t.libelle as libeller, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Role t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

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
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtRole = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, r.getDateCreation() != null ? r.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, r.getCreePar() != null ? r.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, r.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                r.setIdEntite(id);
                r.setId(id);
            } else {
                throw new SQLException("Creating Entite for Role failed, no ID obtained.");
            }

            // 2. Insert into Role
            // Schema: id, libelle
            String sqlRole = "INSERT INTO Role (id, libelle) VALUES (?, ?)";
            
            stmtRole = conn.prepareStatement(sqlRole);
            stmtRole.setLong(1, id);
            stmtRole.setString(2, r.getLibelle() != null ? r.getLibelle() : null);

            stmtRole.executeUpdate();

            conn.commit();
            System.out.println("✓ Role créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Role: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (stmtEntite != null) stmtEntite.close();
                if (stmtRole != null) stmtRole.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(role r) {
        r.setDateDerniereModification(LocalDateTime.now());
        if (r.getModifierPar() == null)
            r.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtRole = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(r.getDateDerniereModification()));
            stmtEntite.setString(2, r.getModifierPar());
            stmtEntite.setLong(3, r.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Role
            String sqlRole = "UPDATE Role SET libelle = ? WHERE id = ?";
            stmtRole = conn.prepareStatement(sqlRole);
            stmtRole.setString(1, r.getLibelle() != null ? r.getLibelle() : null);
            stmtRole.setLong(2, r.getId());

            stmtRole.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
             if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (stmtEntite != null) stmtEntite.close();
                if (stmtRole != null) stmtRole.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(role r) {
        if (r != null && r.getId() != null) {
            deleteById(r.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM entite WHERE id = ?";
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
        String sql = "SELECT t.id as idRole, t.id as idEntite, t.libelle as libeller, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Role t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.libelle = ?";

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
