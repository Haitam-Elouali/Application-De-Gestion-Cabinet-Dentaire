package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.repository.api.StaffRepository;

import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffRepositoryImpl implements StaffRepository {

    @Override
    public List<staff> findAll() {
        List<staff> staffList = new ArrayList<>();
        String sql = "SELECT t.id as idStaff, t.id as idUser, t.id as idEntite, " +
                     "t.salaire, t.dateRecrutement, " + 
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM staff t " + 
                     "JOIN utilisateur u ON t.id = u.id " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                staffList.add(RowMappers.mapStaff(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    @Override
    public staff findById(Long id) {
        String sql = "SELECT t.id as idStaff, t.id as idUser, t.id as idEntite, " +
                     "t.salaire, t.dateRecrutement, t.dateRecrutement as dateEmbauche, " + 
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM staff t " + 
                     "JOIN utilisateur u ON t.id = u.id " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapStaff(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(staff s) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtStaff = null;
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
                s.setId(id);
            } else {
                throw new SQLException("Creating Entite for Staff failed, no ID obtained.");
            }

            // 2. Insert into Utilisateur
            String sqlUser = "INSERT INTO utilisateur (id, nom, email, tele, username, password, sexe, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setLong(1, id);
            stmtUser.setString(2, s.getNom());
            stmtUser.setString(3, s.getEmail());
            stmtUser.setString(4, s.getTelephone());
            stmtUser.setString(5, s.getUsername());
            stmtUser.setString(6, s.getPassword());
            stmtUser.setString(7, s.getSexe() != null ? s.getSexe().name() : null);
            stmtUser.setObject(8, s.getDateNaissance());
            stmtUser.executeUpdate();

            // 3. Insert into Staff
            // Staff table: id, salaire, dateRecrutement, dateDepart. (prime, soldeConge not in schema)
            String sqlStaff = "INSERT INTO staff (id, salaire, dateRecrutement) VALUES (?, ?, ?)";
            stmtStaff = conn.prepareStatement(sqlStaff);
            stmtStaff.setLong(1, id);
            stmtStaff.setDouble(2, s.getSalaire() != null ? s.getSalaire() : 0.0);
            stmtStaff.setObject(3, s.getDateEmbauche());
            
            stmtStaff.executeUpdate();

            conn.commit();
            System.out.println("✓ Staff créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Staff: " + e.getMessage());
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
    public void update(staff s) {
        s.setDateDerniereModification(LocalDateTime.now());
        if (s.getModifierPar() == null)
            s.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtStaff = null;

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
            stmtUser.setString(3, s.getTelephone());
            stmtUser.setString(4, s.getUsername());
            stmtUser.setString(5, s.getPassword());
            stmtUser.setString(6, s.getSexe() != null ? s.getSexe().name() : null);
            stmtUser.setObject(7, s.getDateNaissance());
            stmtUser.setLong(8, s.getIdEntite());
            stmtUser.executeUpdate();

            // Update Staff
            String sqlStaff = "UPDATE staff SET salaire = ?, dateRecrutement = ? WHERE id = ?";
            stmtStaff = conn.prepareStatement(sqlStaff);
            stmtStaff.setDouble(1, s.getSalaire() != null ? s.getSalaire() : 0.0);
            stmtStaff.setObject(2, s.getDateEmbauche());
            stmtStaff.setLong(3, s.getIdEntite());

            stmtStaff.executeUpdate();

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
    public void delete(staff s) {
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
    public Optional<staff> findByEmail(String email) {
        String sql = "SELECT t.id as idStaff, t.id as idUser, t.id as idEntite, " +
                     "t.salaire, t.dateRecrutement, " + 
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM staff t " + 
                     "JOIN utilisateur u ON t.id = u.id " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE u.email = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapStaff(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<staff> findByCin(String cin) {
        // CIN not in schema (Staff or Utilisateur?) 
        // Utilisateur schema: id, nom, prenom, email, tele, username, password, sexe, dateNaissance, role_id.
        // No CIN in Utilisateur. Skipping implementation.
        return Optional.empty();
    }
}
