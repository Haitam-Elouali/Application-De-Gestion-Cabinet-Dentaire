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
        System.out.println("Recherche de tous les actes");
        String sql = "SELECT * FROM ACTES";
        List<actes> results = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                results.add(RowMappers.mapActes(rs));
            }
            return results;
        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la recherche des actes: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public actes findById(Long id) {
        System.out.println("Recherche de l'acte avec l'ID: " + id);
        String sql = "SELECT * FROM ACTES WHERE idEntite = ?";

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
        String sql = "INSERT INTO ACTES (libeller, categorie, prixDeBase) VALUES (?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, acte.getLibeller());
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
        String sql = "UPDATE ACTES SET libeller = ?, categorie = ?, prixDeBase = ? WHERE idEntite = ?";

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
        String sql = "DELETE FROM ACTES WHERE idEntite = ?";

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
        System.out.println("Recherche des actes par catégorie: " + categorie);
        String sql = "SELECT * FROM ACTES WHERE categorie = ?";
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
            System.err.println("✗ Erreur lors de la recherche des actes par catégorie: " + e.getMessage());
        }
        return results;
    }
}
