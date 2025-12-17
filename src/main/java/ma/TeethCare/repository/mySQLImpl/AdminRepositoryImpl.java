package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.repository.api.AdminRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {


    @Override
    public List<admin> findAll() throws SQLException {
        List<admin> results = new ArrayList<>();
        String sql = "SELECT t.id as idEntite, t.id, " +
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM admin t " + 
                     "JOIN utilisateur u ON t.id = u.id " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                results.add(RowMappers.mapAdmin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return results;
    }

    @Override
    public admin findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id, " +
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM admin t " + 
                     "JOIN utilisateur u ON t.id = u.id " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapAdmin(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(admin entity) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtAdmin = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, entity.getDateCreation() != null ? entity.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, entity.getCreePar() != null ? entity.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, entity.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setIdEntite(id);
                entity.setId(id);
            } else {
                throw new SQLException("Creating Entite for Admin failed, no ID obtained.");
            }

            // 2. Insert into Utilisateur
            String sqlUser = "INSERT INTO utilisateur (id, nom, prenom, email, tele, username, password, sexe, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setLong(1, id);
            stmtUser.setString(2, entity.getNom());
            stmtUser.setString(3, entity.getPrenom());
            stmtUser.setString(4, entity.getEmail());
            stmtUser.setString(5, entity.getTelephone());
            stmtUser.setString(6, entity.getUsername());
            stmtUser.setString(7, entity.getPassword());
            stmtUser.setString(8, entity.getSexe() != null ? entity.getSexe().name() : null);
            stmtUser.setObject(9, entity.getDateNaissance());
            stmtUser.executeUpdate();

            // 2.5 Insert into Staff (Required by DB Constraint Admin -> Staff)
            // Even though Admin entity doesn't extend Staff in Java, the DB table admin References staff.
            String sqlStaff = "INSERT INTO staff (id, salaire, dateRecrutement) VALUES (?, ?, ?)";
            try (PreparedStatement stmtStaff = conn.prepareStatement(sqlStaff)) {
                 stmtStaff.setLong(1, id);
                 stmtStaff.setDouble(2, 0.0); // Default or null if allowed
                 stmtStaff.setObject(3, java.time.LocalDate.now()); // Default date
                 stmtStaff.executeUpdate();
            }

            // 3. Insert into Admin
            // Admin table: id (Only id since permissionAdmin/cabinetId removed from entity)
            String sqlAdmin = "INSERT INTO admin (id) VALUES (?)";
            stmtAdmin = conn.prepareStatement(sqlAdmin);
            stmtAdmin.setLong(1, id);
            stmtAdmin.executeUpdate();

            conn.commit();
            System.out.println("✓ Admin créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Admin: " + e.getMessage());
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
                if (stmtAdmin != null) stmtAdmin.close();
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
    public void update(admin entity) {
        entity.setDateDerniereModification(java.time.LocalDateTime.now());
        if (entity.getModifierPar() == null)
            entity.setModifierPar("SYSTEM");
            
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(entity.getDateDerniereModification()));
            stmtEntite.setString(2, entity.getModifierPar());
            stmtEntite.setLong(3, entity.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Utilisateur
            String sqlUser = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, tele = ?, username = ?, password = ?, sexe = ?, dateNaissance = ? WHERE id = ?";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setString(1, entity.getNom());
            stmtUser.setString(2, entity.getPrenom());
            stmtUser.setString(3, entity.getEmail());
            stmtUser.setString(4, entity.getTelephone());
            stmtUser.setString(5, entity.getUsername());
            stmtUser.setString(6, entity.getPassword());
            stmtUser.setString(7, entity.getSexe() != null ? entity.getSexe().name() : null);
            stmtUser.setObject(8, entity.getDateNaissance());
            stmtUser.setLong(9, entity.getIdEntite());
            stmtUser.executeUpdate();

            // Admin table update skipped as no fields to update

            conn.commit();
            System.out.println("✓ Admin mis à jour avec id: " + entity.getIdEntite());
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
    public void delete(admin entity) {
        if (entity != null && entity.getIdEntite() != null) {
            deleteById(entity.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM entite WHERE id = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("✓ Admin (via Entite) supprimé avec id: " + id);
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de deleteById(" + id + ") pour Admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<admin> findByDomaine(String domaine) throws Exception {
        // Domaine field does not exist in Admin schema. Returning empty list.
        return new ArrayList<>();
    }

}
