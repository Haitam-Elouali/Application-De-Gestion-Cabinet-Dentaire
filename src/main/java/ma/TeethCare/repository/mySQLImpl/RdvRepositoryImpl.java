package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.api.RdvRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RdvRepositoryImpl implements RdvRepository {

    @Override
    public List<rdv> findAll() throws SQLException {
        List<rdv> rdvList = new ArrayList<>();
        // Table: rdv
        // Columns: id, numero, date, heure, statut, patient_id, situationfinancier_id
        // Entity: patientId, medecinId, motif, noteMedecin, date, heure, statut
        // Mapping:
        // id -> idEntite, id -> idRDV
        // patient_id -> patientId
        // medecinId -> ignored (not in schema)
        // motif -> ignored (not in schema)
        // noteMedecin -> ignored (not in schema)
        // numero -> ? (Entity doesn't have it)
        String sql = "SELECT t.id as idEntite, t.id as idRDV, t.date, t.heure, t.statut, t.patient_id as patientId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM rdv t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rdvList.add(RowMappers.mapRdv(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rdvList;
    }

    @Override
    public rdv findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idRDV, t.date, t.heure, t.statut, t.patient_id as patientId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM rdv t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapRdv(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(rdv r) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtRdv = null;
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
                r.setId(id);
            } else {
                throw new SQLException("Creating Entite for RDV failed, no ID obtained.");
            }

            // 2. Insert into Rdv
            // Schema: id, numero, date, heure, statut, patient_id, situationfinancier_id
            // Skipping medecinId, motif, noteMedecin as not in schema.
            // Using "RDV-{id}" for numero as placeholder if needed, or null.
            String sqlRdv = "INSERT INTO rdv (id, date, heure, statut, patient_id, numero) VALUES (?, ?, ?, ?, ?, ?)";
            
            stmtRdv = conn.prepareStatement(sqlRdv);
            stmtRdv.setLong(1, id);
            stmtRdv.setDate(2, r.getDate() != null ? Date.valueOf(r.getDate()) : null);
            stmtRdv.setTime(3, r.getHeure() != null ? Time.valueOf(r.getHeure()) : null);
            stmtRdv.setString(4, r.getStatut() != null ? r.getStatut().name() : null);
            stmtRdv.setLong(5, r.getPatientId() != null ? r.getPatientId() : 0);
            stmtRdv.setString(6, "RDV-"+id); // Generate a numero

            stmtRdv.executeUpdate();

            conn.commit();
            System.out.println("✓ RDV créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Rdv: " + e.getMessage());
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
                if (stmtRdv != null) stmtRdv.close();
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
    public void update(rdv r) {
        r.setDateDerniereModification(LocalDateTime.now());
        if (r.getModifierPar() == null)
            r.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtRdv = null;

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

            // Update Rdv
            String sqlRdv = "UPDATE rdv SET date = ?, heure = ?, statut = ?, patient_id = ? WHERE id = ?";
            stmtRdv = conn.prepareStatement(sqlRdv);
            stmtRdv.setDate(1, r.getDate() != null ? Date.valueOf(r.getDate()) : null);
            stmtRdv.setTime(2, r.getHeure() != null ? Time.valueOf(r.getHeure()) : null);
            stmtRdv.setString(3, r.getStatut() != null ? r.getStatut().name() : null);
            stmtRdv.setLong(4, r.getPatientId());
            stmtRdv.setLong(5, r.getId());

            stmtRdv.executeUpdate();

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
                if (stmtRdv != null) stmtRdv.close();
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
    public void delete(rdv r) {
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
    public Optional<rdv> findByIdRDV(Long idRDV) {
        // Delegate to findById as idRDV == idEntite
        return Optional.ofNullable(findById(idRDV));
    }

    @Override
    public List<rdv> findByDate(LocalDate date) {
        List<rdv> rdvList = new ArrayList<>();
        String sql = "SELECT t.id as idEntite, t.id as idRDV, t.date, t.heure, t.statut, t.patient_id as patientId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM rdv t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.date = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rdvList.add(RowMappers.mapRdv(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rdvList;
    }
}
