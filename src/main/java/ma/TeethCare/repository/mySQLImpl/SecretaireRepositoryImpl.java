package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.repository.api.SecretaireRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecretaireRepositoryImpl implements SecretaireRepository {

    @Override
    public List<secretaire> findAll() throws SQLException {
        List<secretaire> secretaireList = new ArrayList<>();
        // generic select * might fail if tables are split.
        // Joining secretaire -> staff -> utilisateur -> entite
        String sql = "SELECT t.id as idEntite, t.id as idSecretaire, t.id as idUser, t.commission, " +
                     "s.salaire, s.dateRecrutement, s.dateDepart, " +
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM secretaire t " +
                     "JOIN staff s ON t.id = s.id " +
                     "JOIN utilisateur u ON t.id = u.id " +
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                secretaireList.add(RowMappers.mapSecretaire(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return secretaireList;
    }

    @Override
    public secretaire findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idSecretaire, t.id as idUser, t.commission, " +
                     "s.salaire, s.dateRecrutement, s.dateDepart, " +
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM secretaire t " +
                     "JOIN staff s ON t.id = s.id " +
                     "JOIN utilisateur u ON t.id = u.id " +
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapSecretaire(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(secretaire s) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtStaff = null;
        PreparedStatement stmtSec = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, s.getDateCreation() != null ? s.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, s.getCreePar() != null ? s.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, s.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                s.setIdEntite(id);
                s.setIdUser(id);
            } else {
                throw new SQLException("Creating Entite for Secretaire failed, no ID obtained.");
            }

            // 2. Insert into Utilisateur
            String sqlUser = "INSERT INTO utilisateur (id, nom, email, tele, username, password, sexe, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setLong(1, id);
            stmtUser.setString(2, s.getNom());
            stmtUser.setString(3, s.getEmail());
            stmtUser.setString(4, s.getTel());
            stmtUser.setString(5, s.getLogin());
            stmtUser.setString(6, s.getMotDePasse());
            stmtUser.setString(7, s.getSexe() != null ? s.getSexe().name() : null);
            stmtUser.setObject(8, s.getDateNaissance());
            stmtUser.executeUpdate();

            // 3. Insert into Staff
            String sqlStaff = "INSERT INTO staff (id, salaire, dateRecrutement) VALUES (?, ?, ?)";
            stmtStaff = conn.prepareStatement(sqlStaff);
            stmtStaff.setLong(1, id);
            stmtStaff.setDouble(2, s.getSalaire() != null ? s.getSalaire() : 0.0);
            stmtStaff.setObject(3, s.getDateRecrutement());
            stmtStaff.executeUpdate();

            // 4. Insert into Secretaire
            // Secretaire table: id, commission. 
            // (numCNSS not in schema? let's check text.txt line 195. 'secretaire' -> id, commission. NO numCNSS).
            // Ignoring numCNSS.
            String sqlSec = "INSERT INTO secretaire (id, commission) VALUES (?, ?)";
            stmtSec = conn.prepareStatement(sqlSec);
            stmtSec.setLong(1, id);
            stmtSec.setDouble(2, s.getCommission());
            stmtSec.executeUpdate();

            conn.commit();
            System.out.println("✓ Secretaire créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Secretaire: " + e.getMessage());
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
                if (stmtStaff != null) stmtStaff.close();
                if (stmtSec != null) stmtSec.close();
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
    public void update(secretaire s) {
        s.setDateDerniereModification(LocalDateTime.now());
        if (s.getModifierPar() == null)
            s.setModifierPar("SYSTEM");

        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtStaff = null;
        PreparedStatement stmtSec = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(s.getDateDerniereModification()));
            stmtEntite.setString(2, s.getModifierPar());
            stmtEntite.setLong(3, s.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Utilisateur
            String sqlUser = "UPDATE utilisateur SET nom = ?, email = ?, tele = ?, username = ?, password = ?, sexe = ?, dateNaissance = ? WHERE id = ?";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setString(1, s.getNom());
            stmtUser.setString(2, s.getEmail());
            stmtUser.setString(3, s.getTel());
            stmtUser.setString(4, s.getLogin());
            stmtUser.setString(5, s.getMotDePasse());
            stmtUser.setString(6, s.getSexe() != null ? s.getSexe().name() : null);
            stmtUser.setObject(7, s.getDateNaissance());
            stmtUser.setLong(8, s.getIdEntite());
            stmtUser.executeUpdate();

            // Update Staff
            String sqlStaff = "UPDATE staff SET salaire = ?, dateRecrutement = ? WHERE id = ?";
            stmtStaff = conn.prepareStatement(sqlStaff);
            stmtStaff.setDouble(1, s.getSalaire() != null ? s.getSalaire() : 0.0);
            stmtStaff.setObject(2, s.getDateRecrutement());
            stmtStaff.setLong(3, s.getIdEntite());
            stmtStaff.executeUpdate();

            // Update Secretaire (commission)
            String sqlSec = "UPDATE secretaire SET commission = ? WHERE id = ?";
            stmtSec = conn.prepareStatement(sqlSec);
            stmtSec.setDouble(1, s.getCommission());
            stmtSec.setLong(2, s.getIdEntite());
            stmtSec.executeUpdate();

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
                if (stmtStaff != null) stmtStaff.close();
                if (stmtSec != null) stmtSec.close();
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
    public void delete(secretaire s) {
        if (s != null && s.getIdEntite() != null) {
            deleteById(s.getIdEntite());
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
    public Optional<secretaire> findByNumCNSS(String numCNSS) {
        // numCNSS not in schema. Returning empty.
        return Optional.empty();
    }

    @Override
    public Optional<secretaire> findByCin(String cin) {
        // CIN not in schema.
        return Optional.empty();
    }
}
