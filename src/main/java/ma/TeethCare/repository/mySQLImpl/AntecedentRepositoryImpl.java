package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.common.enums.niveauDeRisque;
import ma.TeethCare.repository.api.AntecedentRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AntecedentRepositoryImpl implements AntecedentRepository {

    @Override
    public List<antecedent> findAll() throws SQLException {
        List<antecedent> antecedentList = new ArrayList<>();
        // JOIN to get inherited fields from Entite
        String sql = "SELECT t.*, e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Antecedants t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                antecedentList.add(RowMappers.mapAntecedent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return antecedentList;
    }

    @Override
    public antecedent findById(Long id) {
        // JOIN to get inherited fields from Entite
        String sql = "SELECT t.*, e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Antecedants t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapAntecedent(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(antecedent a) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtAntecedent = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, a.getDateCreation() != null ? a.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, a.getCreePar() != null ? a.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, a.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                a.setIdEntite(id);
<<<<<<< HEAD
                a.setId(id);
=======
                a.setIdAntecedent(id);
>>>>>>> 3c4bc274fcef546a5d61a180aad73956f24a2bb5
            } else {
                throw new SQLException("Creating Entite for Antecedants failed, no ID obtained.");
            }

            // 2. Insert into antecedants
            // Table: antecedants (id, nom, categorie, niveauDeRisque, dossiermedicale_id)
            String sqlAnt = "INSERT INTO antecedants (id, nom, categorie, niveauDeRisque, dossiermedicale_id) VALUES (?, ?, ?, ?, ?)";
            stmtAntecedent = conn.prepareStatement(sqlAnt);
            stmtAntecedent.setLong(1, id);
            stmtAntecedent.setString(2, a.getNom());
            stmtAntecedent.setString(3, a.getCategorie());
<<<<<<< HEAD
            stmtAntecedent.setString(4, a.getNiveauDeRisque() != null ? a.getNiveauDeRisque().name() : null);
=======
            stmtAntecedent.setString(4, a.getNiveauRisque() != null ? a.getNiveauRisque().name() : null);
>>>>>>> 3c4bc274fcef546a5d61a180aad73956f24a2bb5
            stmtAntecedent.setObject(5, a.getDossierMedicaleId());
            
            stmtAntecedent.executeUpdate();

            conn.commit();
            System.out.println("✓ Antecedants créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Antecedants: " + e.getMessage());
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
                if (stmtAntecedent != null) stmtAntecedent.close();
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
    public void update(antecedent a) {
        a.setDateDerniereModification(LocalDateTime.now());
        if (a.getModifierPar() == null)
            a.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtAnt = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(a.getDateDerniereModification()));
            stmtEntite.setString(2, a.getModifierPar());
<<<<<<< HEAD
            stmtEntite.setLong(3, a.getId());
=======
            stmtEntite.setLong(3, a.getIdAntecedent());
>>>>>>> 3c4bc274fcef546a5d61a180aad73956f24a2bb5
            stmtEntite.executeUpdate();

            // Update Antecedants
            String sqlAnt = "UPDATE Antecedants SET dossiermedicale_id = ?, nom = ?, categorie = ?, niveauDeRisque = ? WHERE id = ?";
            stmtAnt = conn.prepareStatement(sqlAnt);
            stmtAnt.setLong(1, a.getDossierMedicaleId());
            stmtAnt.setString(2, a.getNom());
            stmtAnt.setString(3, a.getCategorie());
<<<<<<< HEAD
            stmtAnt.setString(4, a.getNiveauDeRisque().name());
            stmtAnt.setLong(5, a.getId());
=======
            stmtAnt.setString(4, a.getNiveauRisque().name());
            stmtAnt.setLong(5, a.getIdAntecedent());
>>>>>>> 3c4bc274fcef546a5d61a180aad73956f24a2bb5
            stmtAnt.executeUpdate();

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
                if (stmtAnt != null) stmtAnt.close();
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
    public void delete(antecedent a) {
<<<<<<< HEAD
        if (a != null && a.getId() != null) {
            deleteById(a.getId());
=======
        if (a != null && a.getIdAntecedent() != null) {
            deleteById(a.getIdAntecedent());
>>>>>>> 3c4bc274fcef546a5d61a180aad73956f24a2bb5
        }
    }

    @Override
    public void deleteById(Long id) {
        // Deleting from Entite should cascade to Antecedants if FK is configured with ON DELETE CASCADE.
        // If not, we should delete from Antecedants then Entite. 
        // Assuming Entite is parent. But 'Antecedants.id' maps to 'Entite.id'.
        // Let's delete from Entite. If constraint fails, we'll know.
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
    public List<antecedent> findByCategorie(String categorie) {
        List<antecedent> antecedentList = new ArrayList<>();
        // JOIN to get inherited fields from Entite
        String sql = "SELECT t.*, e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Antecedants t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.categorie = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    antecedentList.add(RowMappers.mapAntecedent(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return antecedentList;
    }
}
