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
        String sql = "SELECT * FROM Admin";
        System.out.println("Récupération de tous les admins");
        List<admin> results = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
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
        String sql = "SELECT * FROM Admin WHERE idAdmin = ?";
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
        System.out.println("Création d'un nouveau admin: domaine=" + entity.getDomaine());
        String sql = "INSERT INTO Admin"
                + " (permissionAdmin, domaine, nom, email, tel, dateCreation, dateDerniereModification) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getPermissionAdmin());
            stmt.setString(2, entity.getDomaine());
            stmt.setString(3, entity.getNom());
            // prenom removed as it does not exist in entity
            stmt.setString(4, entity.getEmail());
            stmt.setString(5, entity.getTel()); // Changed from getTelephone to getTel
            stmt.setObject(6, entity.getDateCreation());
            stmt.setObject(7, entity.getDateDerniereModification());
                                                                     // getDateDerniereModification

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    entity.setIdUser(id);
                    System.out.println("✓ Admin créé avec id: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(admin entity) {
        System.out.println("Mise à jour de l'admin avec id: " + entity.getIdEntite());
        String sql = "UPDATE Admin"
                + " SET permissionAdmin = ?, domaine = ?, nom = ?, email = ?, tel = ?, dateDerniereModification = ? WHERE idAdmin = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getPermissionAdmin());
            stmt.setString(2, entity.getDomaine());
            stmt.setString(3, entity.getNom());
            // prenom removed
            stmt.setString(4, entity.getEmail());
            stmt.setString(5, entity.getTel());
            stmt.setObject(6, entity.getDateDerniereModification());
            stmt.setLong(7, entity.getIdUser());

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
        String sql = "DELETE FROM Admin WHERE idAdmin = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("✓ Admin supprimé avec id: " + id);
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
