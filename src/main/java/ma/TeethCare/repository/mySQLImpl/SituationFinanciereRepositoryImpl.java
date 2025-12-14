package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.api.SituationFinanciereRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SituationFinanciereRepositoryImpl implements SituationFinanciereRepository {

    @Override
    public List<situationFinanciere> findAll() throws SQLException {
        List<situationFinanciere> sfList = new ArrayList<>();
        // Join with entite
        String sql = "SELECT t.id as idSF, t.id as idEntite, t.totalDesActes, t.totalPaye, t.credit, t.statut, t.enPromo, t.dossiermedicale_id as dossierId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM situationfinancier t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sfList.add(RowMappers.mapSituationFinanciere(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return sfList;
    }

    @Override
    public situationFinanciere findById(Long id) {
        String sql = "SELECT t.id as idSF, t.id as idEntite, t.totalDesActes, t.totalPaye, t.credit, t.statut, t.enPromo, t.dossiermedicale_id as dossierId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM situationfinancier t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapSituationFinanciere(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(situationFinanciere sf) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtSf = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, sf.getDateCreation() != null ? sf.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, sf.getCreePar() != null ? sf.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, sf.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                sf.setIdEntite(id);
                sf.setId(id);
            } else {
                throw new SQLException("Creating Entite for SituationFinanciere failed, no ID obtained.");
            }

            // 2. Insert into SituationFinancier
            // Table: situationfinancier (id, totalDesActes, totalPaye, credit, statut, enPromo, dossiermedicale_id)
            String sqlSf = "INSERT INTO situationfinancier (id, totalDesActes, totalPaye, credit, statut, enPromo, dossiermedicale_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            stmtSf = conn.prepareStatement(sqlSf);
            stmtSf.setLong(1, id);
            stmtSf.setDouble(2, sf.getTotalDesActes());
            stmtSf.setDouble(3, sf.getTotalPaye());
            stmtSf.setDouble(4, sf.getCredit());
            stmtSf.setString(5, sf.getStatut() != null ? sf.getStatut().name() : null);
            stmtSf.setString(6, sf.getEnPromo() != null ? sf.getEnPromo().name() : null);
            stmtSf.setObject(7, sf.getDossierMedicaleId());
            stmtSf.executeUpdate();

            conn.commit();
            System.out.println("✓ SituationFinanciere créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour SituationFinanciere: " + e.getMessage());
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
                if (stmtSf != null) stmtSf.close();
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
    public void update(situationFinanciere sf) {
        sf.setDateDerniereModification(LocalDateTime.now());
        if (sf.getModifierPar() == null)
            sf.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtSf = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(sf.getDateDerniereModification()));
            stmtEntite.setString(2, sf.getModifierPar());
            stmtEntite.setLong(3, sf.getIdEntite());
            stmtEntite.executeUpdate();

            // Update SituationFinancier
            String sqlSf = "UPDATE situationfinancier SET totalDesActes = ?, totalPaye = ?, credit = ?, statut = ?, enPromo = ?, dossiermedicale_id = ? WHERE id = ?";
            stmtSf = conn.prepareStatement(sqlSf);
            stmtSf.setDouble(1, sf.getTotalDesActes());
            stmtSf.setDouble(2, sf.getTotalPaye());
            stmtSf.setDouble(3, sf.getCredit());
            stmtSf.setString(4, sf.getStatut() != null ? sf.getStatut().name() : null);
            stmtSf.setString(5, sf.getEnPromo() != null ? sf.getEnPromo().name() : null);
            stmtSf.setObject(6, sf.getDossierMedicaleId());
            stmtSf.setLong(7, sf.getId());

            stmtSf.executeUpdate();

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
                if (stmtSf != null) stmtSf.close();
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
    public void delete(situationFinanciere sf) {
        if (sf != null && sf.getIdEntite() != null) {
            deleteById(sf.getIdEntite());
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
