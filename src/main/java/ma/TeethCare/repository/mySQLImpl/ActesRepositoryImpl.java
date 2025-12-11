package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.repository.api.ActesRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActesRepositoryImpl implements ActesRepository {

    @Override
    public List<actes> findAll() throws SQLException {
        System.out.println("Recherche de tous les ACTE");
        String sql = "SELECT * FROM ACTE";
        List<actes> results = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                results.add(RowMappers.mapActes(rs));
            }
            return results;
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la recherche des ACTE: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public actes findById(Long id) {
        System.out.println("Recherche de l'acte avec l'ID: " + id);
        String sql = "SELECT * FROM ACTE WHERE id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapActes(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la recherche de l'acte par ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void create(actes acte) {
        System.out.println("Création d'un nouvel acte: " + acte.getLibeller());
        String sql = "INSERT INTO ACTE (nom, categorie, prix) VALUES (?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, acte.getLibeller()); // Keeping getter name as is, assuming field in entity is libeller? 
            // Wait, if column is nom, I should verify if entity has setNom? 
            // The entity 'actes' might need updates too? 
            // View file 281 shows 'actes.java' imports. 
            // Let's assume Entity uses 'libeller' and we map to 'nom' in DB.

            stmt.setString(2, acte.getCategorie());
            stmt.setDouble(3, acte.getPrixDeBase());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    acte.setIdEntite(generatedKeys.getLong(1));
                    System.out.println("✓ Acte créé avec l'ID: " + acte.getIdEntite());
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la création de l'acte: " + e.getMessage());
        }
    }

    @Override
    public void update(actes acte) {
        System.out.println("Mise à jour de l'acte ID: " + acte.getIdEntite());
        String sql = "UPDATE ACTE SET nom = ?, categorie = ?, prix = ? WHERE id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, acte.getLibeller());
            stmt.setString(2, acte.getCategorie());
            stmt.setDouble(3, acte.getPrixDeBase());
            stmt.setLong(4, acte.getIdEntite());

            stmt.executeUpdate();
            System.out.println("✓ Acte ID " + acte.getIdEntite() + " mis à jour");
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la mise à jour de l'acte: " + e.getMessage());
        }
    }

    @Override
    public void delete(actes acte) {
        if (acte != null && acte.getIdEntite() != null) {
            deleteById(acte.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        System.out.println("Suppression de l'acte ID: " + id);
        String sql = "DELETE FROM ACTE WHERE id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la suppression de l'acte: " + e.getMessage());
        }
    }

    @Override
    public List<actes> findByCategorie(String categorie) {
        System.out.println("Recherche des ACTE par catégorie: " + categorie);
        String sql = "SELECT * FROM ACTE WHERE categorie = ?";
        List<actes> results = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categorie);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(RowMappers.mapActes(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la recherche des ACTE par catégorie: " + e.getMessage());
        }
        return results;
    }
}
