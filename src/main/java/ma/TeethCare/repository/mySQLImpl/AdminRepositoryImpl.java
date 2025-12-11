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
        // JOIN Admin, Utilisateur, and Entite to get all fields
        String sql = "SELECT a.id, u.nom, u.prenom, u.email, u.tele, u.username, u.password, u.sexe, u.dateNaissance, e.dateCreation, e.dateDerniereModification, e.creePar " +
                     "FROM admin a " +
                     "JOIN utilisateur u ON a.id = u.id " +
                     "JOIN entite e ON a.id = e.id";
        System.out.println("Récupération de tous les admins");
        List<admin> results = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Manually map since RowMappers might expect different specific columns or flat structure
                // Or better: update RowMappers to handle this ResultSet. 
                // For now, let's look at RowMappers again. RowMappers.mapAdmin is barebones.
                // Let's create an admin object here carefully or delegate to RowMapper if it checks column names safely.
                results.add(RowMappers.mapAdmin(rs));
            }
            System.out.println("✓ " + results.size() + " admins récupérés");
            return results;
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de findAll() pour Admin: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public admin findById(Long id) {
        String sql = "SELECT a.id, u.nom, u.prenom, u.email, u.tele, u.username, u.password, u.sexe, u.dateNaissance, e.dateCreation, e.dateDerniereModification, e.creePar " +
                     "FROM admin a " +
                     "JOIN utilisateur u ON a.id = u.id " +
                     "JOIN entite e ON a.id = e.id " +
                     "WHERE a.id = ?";
        System.out.println("Recherche de l'admin avec id: " + id);

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    admin result = RowMappers.mapAdmin(rs);
                    System.out.println("✓ Admin trouvé avec id: " + id);
                    return result;
                }
            }
            System.out.println("⚠ Admin non trouvé avec id: " + id);
            return null;
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de findById(" + id + ") pour Admin: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(admin entity) {
        System.out.println("Création d'un nouveau admin: " + entity.getNom());
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtAdmin = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, entity.getDateCreation() != null ? entity.getDateCreation() : new java.util.Date());
            stmtEntite.setString(2, entity.getCreePar());
            stmtEntite.setObject(3, entity.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setIdEntite(id);
                entity.setIdUser(id);
            } else {
                throw new SQLException("Creating Entite failed, no ID obtained.");
            }

            // 2. Insert into Utilisateur
            // Columns: id, nom, email, tele, username, password, sexe, dateNaissance
            String sqlUser = "INSERT INTO utilisateur (id, nom, email, tele, username, password, sexe, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setLong(1, id);
            stmtUser.setString(2, entity.getNom());
            // stmtUser.setString(3, entity.getPrenom()); // Removed
            stmtUser.setString(3, entity.getEmail());     // Re-indexed 4->3
            stmtUser.setString(4, entity.getTel());       // Re-indexed 5->4
            stmtUser.setString(5, entity.getLogin());     // Re-indexed 6->5
            stmtUser.setString(6, entity.getMotDePasse());// Re-indexed 7->6
            stmtUser.setString(7, entity.getSexe() != null ? entity.getSexe().name() : null); // Re-indexed 8->7
            stmtUser.setObject(8, entity.getDateNaissance()); // Re-indexed 9->8
            // role_id ignored for now or set to default?
            
            stmtUser.executeUpdate();

            // 3. Insert into Staff (Required by DB constraint: Admin -> Staff -> Utilisateur)
            // Even though Java Admin extends Utilisateur, DB Admin extends Staff.
            // We insert default values for Staff specific fields.
            // Staff table columns: id, salaire, dateRecrutement, dateDepart
            // soldeConge and prime are NOT in the table based on schema inspection.
            String sqlStaff = "INSERT INTO staff (id, salaire) VALUES (?, 0)";
            PreparedStatement stmtStaff = conn.prepareStatement(sqlStaff);
            stmtStaff.setLong(1, id);
            stmtStaff.executeUpdate();
            stmtStaff.close();

            // 4. Insert into Admin
            // Admin table only has 'id'
            String sqlAdmin = "INSERT INTO admin (id) VALUES (?)";
            stmtAdmin = conn.prepareStatement(sqlAdmin);
            stmtAdmin.setLong(1, id);
            stmtAdmin.executeUpdate();

            conn.commit(); // Commit transaction
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
        // Update Utilisateur table (Admin table has no fields to update)
        System.out.println("Mise à jour de l'admin avec id: " + entity.getIdEntite());
        String sql = "UPDATE utilisateur SET nom = ?, email = ?, tele = ? WHERE id = ?"; 
        // Note: ignoring permission/domaine as they don't exist
        
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getNom());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getTel());
            stmt.setLong(4, entity.getIdEntite());

            stmt.executeUpdate();
            System.out.println("✓ Admin mis à jour avec id: " + entity.getIdEntite());
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de update() pour Admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(admin entity) {
        if (entity != null)
            deleteById(entity.getIdUser());
    }

    @Override
    public void deleteById(Long id) {
        System.out.println("Suppression de l'admin avec id: " + id);
        // Deleting from Entite should cascade delete Utilisateur and Admin if FKs are set correct.
        // If not, we should delete reversed order manually.
        // Assuming cascade for now or delete from Entite is safest root deletion.
        String sql = "DELETE FROM entite WHERE id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("✓ Admin (et Entite/Utilisateur) supprimé avec id: " + id);
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de deleteById(" + id + ") pour Admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<admin> findByDomaine(String domaine) throws Exception {
        System.out.println("Recherche des admins pour le domaine: " + domaine);
        String sql = "SELECT * FROM Admin WHERE domaine = ?";
        List<admin> results = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, domaine);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(RowMappers.mapAdmin(rs));
                }
            }
            System.out.println("✓ " + results.size() + " admin(s) trouvé(s) pour le domaine: " + domaine);
            return results;
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de findByDomaine(" + domaine + ") pour Admin: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}
