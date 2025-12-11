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
        String sql = "SELECT t.id as idEntite, t.id, t.permissionAdmin, t.cabinetId, " +
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
        String sql = "SELECT t.id as idEntite, t.id, t.permissionAdmin, t.cabinetId, " +
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
                entity.setIdUser(id);
            } else {
                throw new SQLException("Creating Entite for Admin failed, no ID obtained.");
            }

            // 2. Insert into Utilisateur
            String sqlUser = "INSERT INTO utilisateur (id, nom, email, tele, username, password, sexe, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setLong(1, id);
            stmtUser.setString(2, entity.getNom());
            stmtUser.setString(3, entity.getEmail());
            stmtUser.setString(4, entity.getTel());
            stmtUser.setString(5, entity.getLogin());
            stmtUser.setString(6, entity.getMotDePasse());
            stmtUser.setString(7, entity.getSexe() != null ? entity.getSexe().name() : null);
            stmtUser.setObject(8, entity.getDateNaissance());
            stmtUser.executeUpdate();

            // 3. Insert into Admin
            // Admin table: id, permissionAdmin, cabinetId
            String sqlAdmin = "INSERT INTO admin (id, permissionAdmin, cabinetId) VALUES (?, ?, ?)";
            stmtAdmin = conn.prepareStatement(sqlAdmin);
            stmtAdmin.setLong(1, id);
            stmtAdmin.setString(2, entity.getPermissionAdmin());
            if (entity.getCabinetMedicale() != null && entity.getCabinetMedicale().getIdEntite() != null) {
                stmtAdmin.setLong(3, entity.getCabinetMedicale().getIdEntite());
            } else {
                stmtAdmin.setObject(3, null);
            }
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
        PreparedStatement stmtAdmin = null;

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
            String sqlUser = "UPDATE utilisateur SET nom = ?, email = ?, tele = ?, username = ?, password = ?, sexe = ?, dateNaissance = ? WHERE id = ?";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setString(1, entity.getNom());
            stmtUser.setString(2, entity.getEmail());
            stmtUser.setString(3, entity.getTel());
            stmtUser.setString(4, entity.getLogin());
            stmtUser.setString(5, entity.getMotDePasse());
            stmtUser.setString(6, entity.getSexe() != null ? entity.getSexe().name() : null);
            stmtUser.setObject(7, entity.getDateNaissance());
            stmtUser.setLong(8, entity.getIdEntite());
            stmtUser.executeUpdate();

            // Update Admin (permission, cabinetId)
            String sqlAdmin = "UPDATE admin SET permissionAdmin = ?, cabinetId = ? WHERE id = ?";
            stmtAdmin = conn.prepareStatement(sqlAdmin);
            stmtAdmin.setString(1, entity.getPermissionAdmin());
            if (entity.getCabinetMedicale() != null && entity.getCabinetMedicale().getIdEntite() != null) {
                stmtAdmin.setLong(2, entity.getCabinetMedicale().getIdEntite());
            } else {
                stmtAdmin.setObject(2, null);
            }
            stmtAdmin.setLong(3, entity.getIdEntite());
            stmtAdmin.executeUpdate();

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
