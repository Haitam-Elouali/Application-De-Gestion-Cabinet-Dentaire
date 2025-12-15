package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.api.CabinetMedicaleRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CabinetMedicaleRepositoryImpl implements CabinetMedicaleRepository {

    @Override
    public List<cabinetMedicale> findAll() throws SQLException {
        List<cabinetMedicale> cabinetList = new ArrayList<>();
        // Alias database columns to match propertiess expected by RowMapper
        // id -> idEntite
        // nomCabinet -> nom
        // tele -> tel1
        String sql = "SELECT t.id as idEntite, t.nomCabinet as nom, t.adresse, t.tele as tel1, t.email, t.logo, t.instagram, t.siteWeb, t.description, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM CabinetMedicale t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cabinetList.add(RowMappers.mapCabinetMedicale(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return cabinetList;
    }

    @Override
    public cabinetMedicale findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.nomCabinet as nom, t.adresse, t.tele as tel1, t.email, t.logo, t.instagram, t.siteWeb, t.description, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM CabinetMedicale t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapCabinetMedicale(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(cabinetMedicale c) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtCab = null;
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
                throw new SQLException("Creating Entite for CabinetMedicale failed, no ID obtained.");
            }

            // 2. Insert into CabinetMedicale
            String sqlCab = "INSERT INTO cabinetmedicale (id, nomCabinet, adresse, tele, email, logo, instagram, siteWeb, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            stmtCab = conn.prepareStatement(sqlCab);
            stmtCab.setLong(1, id);
            stmtCab.setString(2, c.getNomCabinet());
            stmtCab.setString(3, c.getAdresse());
            stmtCab.setString(4, c.getTele());
            stmtCab.setString(5, c.getEmail());
            stmtCab.setString(6, c.getLogo());
            stmtCab.setString(7, c.getInstagram());
            stmtCab.setString(8, c.getSiteWeb());
            stmtCab.setString(9, c.getDescription());

            stmtCab.executeUpdate();

            conn.commit();
            System.out.println("✓ CabinetMedicale créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour CabinetMedicale: " + e.getMessage());
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
                if (stmtCab != null) stmtCab.close();
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
    public void update(cabinetMedicale c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");

        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtCab = null;

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

            // Update CabinetMedicale
            String sqlCab = "UPDATE CabinetMedicale SET nomCabinet = ?, adresse = ?, email = ?, logo = ?, tele = ?, siteWeb = ?, instagram = ?, description = ? WHERE id = ?";
            stmtCab = conn.prepareStatement(sqlCab);
            stmtCab.setString(1, c.getNomCabinet());
            stmtCab.setString(2, c.getAdresse());
            stmtCab.setString(3, c.getEmail());
            stmtCab.setString(4, c.getLogo());
            stmtCab.setString(5, c.getTele());
            stmtCab.setString(6, c.getSiteWeb());
            stmtCab.setString(7, c.getInstagram());
            stmtCab.setString(8, c.getDescription());
            stmtCab.setLong(9, c.getIdEntite());
            
            stmtCab.executeUpdate();

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
                if (stmtCab != null) stmtCab.close();
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
    public void delete(cabinetMedicale c) {
        if (c != null && c.getIdEntite() != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        // Delete from Entite, relying on cascade or manual deletion order if needed
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
    public Optional<cabinetMedicale> findByEmail(String email) {
        String sql = "SELECT t.id as idEntite, t.nomCabinet as nom, t.adresse, t.tele as tel1, t.email, t.logo, t.instagram, t.siteWeb, t.description, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM CabinetMedicale t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.email = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapCabinetMedicale(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
