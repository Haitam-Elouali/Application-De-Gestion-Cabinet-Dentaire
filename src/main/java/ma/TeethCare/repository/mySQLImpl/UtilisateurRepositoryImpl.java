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
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM utilisateur t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                utilisateurList.add(RowMappers.mapUtilisateur(rs));
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
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM utilisateur t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapUtilisateur(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
                u.setIdUser(id);
            } else {
                throw new SQLException("Creating Entite for Utilisateur failed, no ID obtained.");
            }

            // 2. Insert into Utilisateur
            // Table: utilisateur (id, nom, prenom, email, tele, username, password, sexe, dateNaissance, role_id)
            // Note: login -> username, motDePasse -> password, tel -> tele. cin, adresse not in schema?
            // Assuming simplified schema based on previous interactions (role_id nullable or not handled?)
            String sqlUser = "INSERT INTO utilisateur (id, nom, email, tele, username, password, sexe, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setLong(1, id);
            stmtUser.setString(2, u.getNom());
            stmtUser.setString(3, u.getEmail());
            stmtUser.setString(4, u.getTel());
            stmtUser.setString(5, u.getLogin());
            stmtUser.setString(6, u.getMotDePasse());
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
            stmtUser.setString(3, u.getTel());
            stmtUser.setString(4, u.getLogin());
            stmtUser.setString(5, u.getMotDePasse());
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
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM utilisateur t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.email = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapUtilisateur(rs));
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
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM utilisateur t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.username = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapUtilisateur(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
