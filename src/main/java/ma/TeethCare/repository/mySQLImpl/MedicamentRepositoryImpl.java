package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.repository.api.MedicamentRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicamentRepositoryImpl implements MedicamentRepository {

    @Override
    public List<medicaments> findAll() throws SQLException {
        List<medicaments> medicamentsList = new ArrayList<>();
        // Table: medicament
        // Columns: id, nomCommercial, principeActif, forme, dosage, type, remboursable, prixUnitaire, description
        // Entity: nom, laboratoire, type, remboursable, prixUnitaire, description
        // Mapping: nom -> nomCommercial. laboratoire -> ignored.
        String sql = "SELECT t.id as idEntite, t.id as idMed, t.nomCommercial as nom, t.type, t.remboursable, t.prixUnitaire, t.description, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM medicament t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medicamentsList.add(RowMappers.mapMedicaments(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return medicamentsList;
    }

    @Override
    public medicaments findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idMed, t.nomCommercial as nom, t.type, t.remboursable, t.prixUnitaire, t.description, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM medicament t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapMedicaments(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(medicaments m) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtMed = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, m.getDateCreation() != null ? m.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, m.getCreePar() != null ? m.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, m.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                m.setIdEntite(id);
                m.setId(id);
            } else {
                throw new SQLException("Creating Entite for Medicament failed, no ID obtained.");
            }

            // 2. Insert into Medicament
            // Table: medicament (id, nomCommercial, principeActif, forme, dosage, type, remboursable, prixUnitaire, description)
            // Entity: nom -> nomCommercial. Ignore laboratoire.
            String sqlMed = "INSERT INTO medicament (id, nomCommercial, type, remboursable, prixUnitaire, description) VALUES (?, ?, ?, ?, ?, ?)";
            
            stmtMed = conn.prepareStatement(sqlMed);
            stmtMed.setLong(1, id);
            stmtMed.setString(2, m.getNomCommercial());
            stmtMed.setString(3, m.getType());
            stmtMed.setBoolean(4, m.isRemboursable());
            stmtMed.setDouble(5, m.getPrixUnitaire());
            stmtMed.setString(6, m.getDescription());

            stmtMed.executeUpdate();

            conn.commit();
            System.out.println("✓ Medicament créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Medicament: " + e.getMessage());
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
                if (stmtMed != null) stmtMed.close();
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
    public void update(medicaments m) {
        m.setDateDerniereModification(LocalDateTime.now());
        if (m.getModifierPar() == null)
            m.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtMed = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(m.getDateDerniereModification()));
            stmtEntite.setString(2, m.getModifierPar());
            stmtEntite.setLong(3, m.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Medicament
            String sqlMed = "UPDATE medicament SET nomCommercial = ?, type = ?, remboursable = ?, prixUnitaire = ?, description = ? WHERE id = ?";
            stmtMed = conn.prepareStatement(sqlMed);
            stmtMed.setString(1, m.getNomCommercial());
            stmtMed.setString(2, m.getType());
            stmtMed.setBoolean(3, m.isRemboursable());
            stmtMed.setDouble(4, m.getPrixUnitaire());
            stmtMed.setString(5, m.getDescription());
            stmtMed.setLong(6, m.getId());

            stmtMed.executeUpdate();

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
                if (stmtMed != null) stmtMed.close();
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
    public void delete(medicaments m) {
        if (m != null && m.getIdEntite() != null) {
            deleteById(m.getIdEntite());
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
    public Optional<medicaments> findByNom(String nom) {
        String sql = "SELECT t.id as idEntite, t.id as idMed, t.nomCommercial as nom, t.type, t.remboursable, t.prixUnitaire, t.description, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM medicament t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.nomCommercial = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapMedicaments(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
