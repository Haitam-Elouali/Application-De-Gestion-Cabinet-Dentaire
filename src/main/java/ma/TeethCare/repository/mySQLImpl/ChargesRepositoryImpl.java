package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.api.ChargesRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChargesRepositoryImpl implements ChargesRepository {

    @Override
    public List<charges> findAll() throws SQLException {
        List<charges> chargesList = new ArrayList<>();
        // Table name is 'charge', not 'Charges'
        // Need to JOIN entite for inherited fields
        // Alias id -> idEntite, cabinet_id -> cabinetId for RowMapper
        String sql = "SELECT t.id as idEntite, t.titre, t.description, t.montant, t.categorie, t.date, t.cabinet_id as cabinetId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM charge t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                chargesList.add(RowMappers.mapCharges(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return chargesList;
    }

    @Override
    public charges findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.titre, t.description, t.montant, t.categorie, t.date, t.cabinet_id as cabinetId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM charge t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapCharges(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(charges c) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtCharge = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, c.getDateCreation() != null ? c.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, c.getCreePar() != null ? c.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, c.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                c.setIdEntite(id);
            } else {
                throw new SQLException("Creating Entite for Charge failed, no ID obtained.");
            }

            // 2. Insert into Charge
            String sqlCharge = "INSERT INTO charge (id, titre, description, montant, categorie, date) VALUES (?, ?, ?, ?, ?, ?)";
            
            stmtCharge = conn.prepareStatement(sqlCharge);
            stmtCharge.setLong(1, id);
            stmtCharge.setString(2, c.getTitre());
            stmtCharge.setString(3, c.getDescription());
            stmtCharge.setDouble(4, c.getMontant());
            stmtCharge.setString(5, c.getCategorie());
            stmtCharge.setTimestamp(6, c.getDate() != null ? Timestamp.valueOf(c.getDate()) : null);

            stmtCharge.executeUpdate();

            conn.commit();
            System.out.println("✓ Charge créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Charge: " + e.getMessage());
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
                if (stmtCharge != null) stmtCharge.close();
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
    public void update(charges c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtCharge = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(c.getDateDerniereModification()));
            stmtEntite.setString(2, c.getModifierPar());
            stmtEntite.setLong(3, c.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Charge
            String sqlCharge = "UPDATE charge SET titre = ?, description = ?, montant = ?, categorie = ?, date = ? WHERE id = ?";
            stmtCharge = conn.prepareStatement(sqlCharge);
            stmtCharge.setString(1, c.getTitre());
            stmtCharge.setString(2, c.getDescription());
            stmtCharge.setDouble(3, c.getMontant());
            stmtCharge.setString(4, c.getCategorie());
            stmtCharge.setTimestamp(5, c.getDate() != null ? Timestamp.valueOf(c.getDate()) : null);
            stmtCharge.setLong(6, c.getIdEntite());

            stmtCharge.executeUpdate();

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
                if (stmtCharge != null) stmtCharge.close();
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
    public void delete(charges c) {
        if (c != null && c.getIdEntite() != null) {
            deleteById(c.getIdEntite());
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
    public Optional<charges> findByTitre(String titre) {
        String sql = "SELECT t.id as idEntite, t.titre, t.description, t.montant, t.categorie, t.date, t.cabinet_id as cabinetId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM charge t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.titre = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapCharges(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
