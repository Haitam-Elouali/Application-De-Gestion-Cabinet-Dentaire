package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import ma.TeethCare.entities.statistique.statistique;
import ma.TeethCare.repository.api.StatistiqueRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatistiqueRepositoryImpl implements StatistiqueRepository {

    @Override
    public List<statistique> findAll() throws SQLException {
        List<statistique> list = new ArrayList<>();
        // JOIN to get inherited fields from Entite
        String sql = "SELECT t.*, e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM statistique t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(RowMappers.mapStatistique(rs));
            }
        }
        return list;
    }

    @Override
    public statistique findById(Long id) {
        String sql = "SELECT t.*, e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM statistique t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapStatistique(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(statistique s) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtStat = null;
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
                throw new SQLException("Creating Entite for Statistique failed, no ID obtained.");
            }

            // 2. Insert into statistique
            // Table: statistique(id, nom, chiffre, type, dateCalcul, cabinet_id)
            String sqlStat = "INSERT INTO statistique (id, nom, chiffre, type, dateCalcul, cabinet_id) VALUES (?, ?, ?, ?, ?, ?)";
            stmtStat = conn.prepareStatement(sqlStat);
            stmtStat.setLong(1, id);
            stmtStat.setString(2, s.getNom());
            stmtStat.setObject(3, s.getChiffre());
            stmtStat.setString(4, s.getType());
            stmtStat.setObject(5, s.getDateCalcul());
            stmtStat.setObject(6, s.getCabinetId());
            
            stmtStat.executeUpdate();

            conn.commit();
            System.out.println("✓ Statistique créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Statistique: " + e.getMessage());
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
                if (stmtStat != null) stmtStat.close();
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
    public void update(statistique s) {
        s.setDateDerniereModification(LocalDateTime.now());
        if (s.getModifierPar() == null)
            s.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtStat = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(s.getDateDerniereModification()));
            stmtEntite.setString(2, s.getModifierPar());
            stmtEntite.setLong(3, s.getId());
            stmtEntite.executeUpdate();

            // Update Statistique
            String sqlStat = "UPDATE statistique SET nom = ?, chiffre = ?, type = ?, dateCalcul = ?, cabinet_id = ? WHERE id = ?";
            stmtStat = conn.prepareStatement(sqlStat);
            stmtStat.setString(1, s.getNom());
            stmtStat.setObject(2, s.getChiffre());
            stmtStat.setString(3, s.getType());
            stmtStat.setObject(4, s.getDateCalcul());
            stmtStat.setObject(5, s.getCabinetId());
            stmtStat.setLong(6, s.getId());
            stmtStat.executeUpdate();

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
                if (stmtStat != null) stmtStat.close();
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
    public void delete(statistique s) {
        if (s != null && s.getId() != null) {
            deleteById(s.getId());
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
}
