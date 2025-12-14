package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.api.RevenuesRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RevenuesRepositoryImpl implements RevenuesRepository {

    @Override
    public List<revenues> findAll() throws SQLException {
        List<revenues> revenuesList = new ArrayList<>();
        // Table name is 'revenue', not 'Revenues'
        // Join with entite
        // Alias id -> idEntite for RowMapper
        // Note: Schema has 'categorie' and 'cabinet_id', Entity has 'factureId'. 
        // We skip factureId/cabinet_id mismatch for now or alias if mapper expects.
        // Mapper expects 'factureId', but schema has 'cabinet_id'. 
        // We can't map unrelated columns. We'll leave checks null.
        String sql = "SELECT t.id as idEntite, t.titre, t.description, t.montant, t.date, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM revenue t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                revenuesList.add(RowMappers.mapRevenues(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return revenuesList;
    }

    @Override
    public revenues findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.titre, t.description, t.montant, t.date, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM revenue t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapRevenues(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(revenues r) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtRev = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, r.getDateCreation() != null ? r.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, r.getCreePar() != null ? r.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, r.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                r.setIdEntite(id);
                r.setId(id); // Set specific ID if needed by entity
            } else {
                throw new SQLException("Creating Entite for Revenue failed, no ID obtained.");
            }

            // 2. Insert into Revenue
            // Table: revenue (id, titre, description, montant, categorie, date, cabinet_id)
            // Entity: idRevenue, factureId, titre, description, montant, date
            // Mismatch: factureId vs cabinet_id. Skipping factureId/cabinet_id. 
            // Categorie not in entity => null.
            String sqlRev = "INSERT INTO revenue (id, titre, description, montant, date) VALUES (?, ?, ?, ?, ?)";
            
            stmtRev = conn.prepareStatement(sqlRev);
            stmtRev.setLong(1, id);
            stmtRev.setString(2, r.getTitre());
            stmtRev.setString(3, r.getDescription());
            stmtRev.setDouble(4, r.getMontant());
            stmtRev.setTimestamp(5, r.getDate() != null ? Timestamp.valueOf(r.getDate()) : null);

            stmtRev.executeUpdate();

            conn.commit();
            System.out.println("✓ Revenue créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Revenue: " + e.getMessage());
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
                if (stmtRev != null) stmtRev.close();
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
    public void update(revenues r) {
        r.setDateDerniereModification(LocalDateTime.now());
        if (r.getModifierPar() == null)
            r.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtRev = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(r.getDateDerniereModification()));
            stmtEntite.setString(2, r.getModifierPar());
            stmtEntite.setLong(3, r.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Revenue
            String sqlRev = "UPDATE revenue SET titre = ?, description = ?, montant = ?, date = ? WHERE id = ?";
            stmtRev = conn.prepareStatement(sqlRev);
            stmtRev.setString(1, r.getTitre());
            stmtRev.setString(2, r.getDescription());
            stmtRev.setDouble(3, r.getMontant());
            stmtRev.setTimestamp(4, r.getDate() != null ? Timestamp.valueOf(r.getDate()) : null);
            stmtRev.setLong(5, r.getIdEntite());

            stmtRev.executeUpdate();

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
                if (stmtRev != null) stmtRev.close();
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
    public void delete(revenues r) {
        if (r != null && r.getIdEntite() != null) {
            deleteById(r.getIdEntite());
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
    public Optional<revenues> findByTitre(String titre) {
        String sql = "SELECT t.id as idEntite, t.titre, t.description, t.montant, t.date, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM revenue t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.titre = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapRevenues(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<revenues> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<revenues> revenuesList = new ArrayList<>();
        String sql = "SELECT t.id as idEntite, t.titre, t.description, t.montant, t.date, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM revenue t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.date BETWEEN ? AND ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    revenuesList.add(RowMappers.mapRevenues(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenuesList;
    }
}
