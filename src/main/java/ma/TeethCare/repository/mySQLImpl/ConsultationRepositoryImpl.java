package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.repository.api.ConsultationRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    @Override
    public List<consultation> findAll() throws SQLException {
        List<consultation> consultationList = new ArrayList<>();
        // Table name is 'consultation' (lowercase per schema)
        // Join with entite
        // Aliases for RowMapper:
        // id -> idEntite
        // id -> idConsultation
        // observation -> observationMedecin
        // diagnostic -> diagnostique (if mapper supports it, otherwise it's just helpful)
        // Note: rdvId is not in schema, so we can't select it easily unless we join rdv? 
        // For now, we omit rdvId selection to avoid error if column missing.
        String sql = "SELECT t.id as idEntite, t.id as idConsultation, t.date as Date, t.statut, t.observation as observationMedecin, t.diagnostic as diagnostique, " + 
                     "t.patient_id as patientId, t.medecin_id as medecinId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM consultation t " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                consultationList.add(RowMappers.mapConsultation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return consultationList;
    }

    @Override
    public consultation findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idConsultation, t.date as Date, t.statut, t.observation as observationMedecin, t.diagnostic as diagnostique, " + 
                     "t.patient_id as patientId, t.medecin_id as medecinId, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM consultation t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapConsultation(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(consultation c) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtCons = null;
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
                c.setId(id);
            } else {
                throw new SQLException("Creating Entite for Consultation failed, no ID obtained.");
            }

            // 2. Insert into Consultation
            // Schema: id, date, motif, diagnostic, observation, statut, patient_id, medecin_id
            // Entity: date, statut, observationMedecin, diagnostique, patientId, medecinId
            // Mapping: 
            // observationMedecin -> observation
            // diagnostique -> diagnostic
            // motif -> ? (Entity doesn't have motif? Schema has it. We leave it null or map description if existing.)
            // We skip rdvId as it is not in schema.
            String sqlCons = "INSERT INTO consultation (id, date, observation, diagnostic, statut, patient_id, medecin_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            stmtCons = conn.prepareStatement(sqlCons);
            stmtCons.setLong(1, id);
            stmtCons.setDate(2, c.getDate() != null ? Date.valueOf(c.getDate()) : null);
            stmtCons.setString(3, c.getObservation());
            stmtCons.setString(4, c.getDiagnostic());
            stmtCons.setString(5, c.getStatut() != null ? c.getStatut().name() : null);
            stmtCons.setLong(6, c.getPatientId() != null ? c.getPatientId() : 0); // Handle nulls safely?
            stmtCons.setLong(7, c.getMedecinId() != null ? c.getMedecinId() : 0);

            stmtCons.executeUpdate();

            conn.commit();
            System.out.println("✓ Consultation créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Consultation: " + e.getMessage());
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
                if (stmtCons != null) stmtCons.close();
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
    public void update(consultation c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtCons = null;

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

            // Update Consultation
            String sqlCons = "UPDATE consultation SET date = ?, observation = ?, diagnostic = ?, statut = ?, patient_id = ?, medecin_id = ? WHERE id = ?";
            stmtCons = conn.prepareStatement(sqlCons);
            stmtCons.setDate(1, c.getDate() != null ? Date.valueOf(c.getDate()) : null);
            stmtCons.setString(2, c.getObservation());
            stmtCons.setString(3, c.getDiagnostic());
            stmtCons.setString(4, c.getStatut() != null ? c.getStatut().name() : null);
            stmtCons.setLong(5, c.getPatientId());
            stmtCons.setLong(6, c.getMedecinId());
            stmtCons.setLong(7, c.getId());

            stmtCons.executeUpdate();

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
                if (stmtCons != null) stmtCons.close();
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
    public void delete(consultation c) {
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

}
