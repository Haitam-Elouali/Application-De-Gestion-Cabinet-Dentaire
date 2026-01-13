package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.repository.api.UtilisateurRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    @Override
    public List<utilisateur> findAll() throws SQLException {
        List<utilisateur> utilisateurList = new ArrayList<>();
        String sql = "SELECT t.id as idUser, t.id as idEntite, " +
                     "t.nom, t.prenom, t.email, t.tele as tel, t.username as login, t.password as motDePasse, t.sexe, t.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar, " + 
                     "r.libelle as roleName, r.id as roleId " +
                     "FROM utilisateur t " + 
                     "JOIN entite e ON t.id = e.id " +
                     "LEFT JOIN role r ON t.role_id = r.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                utilisateur u = RowMappers.mapUtilisateur(rs);
                populateRole(u, rs);
                utilisateurList.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return utilisateurList;
    }

    @Override
    public utilisateur findById(Long id) {
        String sql = "SELECT t.id as idUser, t.id as idEntite, " +
                     "t.nom, t.prenom, t.email, t.tele as tel, t.username as login, t.password as motDePasse, t.sexe, t.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar, " +
                     "r.libelle as roleName, r.id as roleId " +
                     "FROM utilisateur t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "LEFT JOIN role r ON t.role_id = r.id " +
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    utilisateur u = RowMappers.mapUtilisateur(rs);
                    populateRole(u, rs);
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ... create/update methods ... (skipping modification for brevity if not strictly needed for login, but good for consistency)
    // For now only read methods are critical for login. create/update already fixed for seed/data.

    @Override
    public void create(utilisateur u) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, u.getDateCreation() != null ? u.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, u.getCreePar() != null ? u.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, u.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                u.setIdEntite(id);
                u.setId(id);
            } else {
                throw new SQLException("Creating Entite for Utilisateur failed, no ID obtained.");
            }

            // 2. Insert into Utilisateur
            String sqlUser = "INSERT INTO utilisateur (id, nom, email, tele, username, password, sexe, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setLong(1, id);
            stmtUser.setString(2, u.getNom());
            stmtUser.setString(3, u.getEmail());
            stmtUser.setString(4, u.getTelephone());
            stmtUser.setString(5, u.getUsername());
            stmtUser.setString(6, u.getPassword());
            stmtUser.setString(7, u.getSexe() != null ? u.getSexe().name() : null);
            stmtUser.setObject(8, u.getDateNaissance());

            stmtUser.executeUpdate();

            conn.commit();
            System.out.println("✓ Utilisateur créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Utilisateur: " + e.getMessage());
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
                if (stmtUser != null) stmtUser.close();
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
    public void update(utilisateur u) {
        u.setDateDerniereModification(LocalDateTime.now());
        if (u.getModifierPar() == null)
            u.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(u.getDateDerniereModification()));
            stmtEntite.setString(2, u.getModifierPar());
            stmtEntite.setLong(3, u.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Utilisateur
            String sqlUser = "UPDATE utilisateur SET nom = ?, email = ?, tele = ?, username = ?, password = ?, sexe = ?, dateNaissance = ? WHERE id = ?";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setString(1, u.getNom());
            stmtUser.setString(2, u.getEmail());
            stmtUser.setString(3, u.getTelephone());
            stmtUser.setString(4, u.getUsername());
            stmtUser.setString(5, u.getPassword());
            stmtUser.setString(6, u.getSexe() != null ? u.getSexe().name() : null);
            stmtUser.setObject(7, u.getDateNaissance());
            stmtUser.setLong(8, u.getIdEntite());

            stmtUser.executeUpdate();

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
                if (stmtUser != null) stmtUser.close();
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
    public void delete(utilisateur u) {
        if (u != null && u.getIdEntite() != null) {
            deleteById(u.getIdEntite());
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
    public Optional<utilisateur> findByEmail(String email) {
        String sql = "SELECT t.id as idUser, t.id as idEntite, " +
                     "t.nom, t.prenom, t.email, t.tele as tel, t.username as login, t.password as motDePasse, t.sexe, t.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar, " +
                     "r.libelle as roleName, r.id as roleId " +
                     "FROM utilisateur t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "LEFT JOIN role r ON t.role_id = r.id " +
                     "WHERE t.email = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    utilisateur u = RowMappers.mapUtilisateur(rs);
                    populateRole(u, rs);
                    return Optional.of(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<utilisateur> findByLogin(String login) {
        String sql = "SELECT t.id as idUser, t.id as idEntite, " +
                     "t.nom, t.prenom, t.email, t.tele as tel, t.username as login, t.password as motDePasse, t.sexe, t.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar, " +
                     "r.libelle as roleName, r.id as roleId " +
                     "FROM utilisateur t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "LEFT JOIN role r ON t.role_id = r.id " +
                     "WHERE t.username = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    utilisateur u = RowMappers.mapUtilisateur(rs);
                    populateRole(u, rs);
                    return Optional.of(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void populateRole(utilisateur u, ResultSet rs) throws SQLException {
        String roleName = rs.getString("roleName");
        Long roleId = rs.getLong("roleId");
        if (roleName != null) {
            ma.TeethCare.entities.role.role r = new ma.TeethCare.entities.role.role();
            r.setId(roleId);
            r.setLibelle(roleName);
            // Initialize list if null (should be initialized in constructor generally but safe here)
            if (u.getRoles() == null) {
                u.setRoles(new ArrayList<>());
            }
            u.getRoles().add(r);
        }
    }
}
