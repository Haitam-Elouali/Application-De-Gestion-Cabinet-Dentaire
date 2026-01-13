package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.repository.api.PatientRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {

    @Override
    public List<Patient> findAll() throws SQLException {
        // Table: patient
        // Columns: id, nom, prenom, dateNaissance, sexe, telephone, assurance
        // Joined with entite
        String sql = "SELECT t.id as idEntite, t.id, t.nom, t.prenom, t.dateNaissance, t.sexe, t.telephone, t.assurance, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM patient t " + 
                     "JOIN entite e ON t.id = e.id";
        List<Patient> patients = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                patients.add(RowMappers.mapPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return patients;
    }

    @Override
    public Patient findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id, t.nom, t.prenom, t.dateNaissance, t.sexe, t.telephone, t.assurance, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM patient t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapPatient(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Patient p) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtPatient = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, p.getDateCreation() != null ? p.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, p.getCreePar() != null ? p.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, p.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                p.setIdEntite(id);
                // p.setId(id); // If Patient has setId, but usually it uses idEntite as ID or idPatient. 
                // Assuming inherited ID logic or similar.
                // Looking at Patient.java might clarify if it has setId. But typically we set idEntite.
            } else {
                throw new SQLException("Creating Entite for Patient failed, no ID obtained.");
            }

            // 2. Insert into Patient
            // Table: patient (id, nom, prenom, dateNaissance, sexe, telephone, assurance)
            // SQL ignores adresse/email as they are not in schema
            String sqlPatient = "INSERT INTO patient (id, nom, prenom, telephone, dateNaissance, sexe, assurance) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            stmtPatient = conn.prepareStatement(sqlPatient);
            stmtPatient.setLong(1, id);
            stmtPatient.setString(2, p.getNom());
            stmtPatient.setString(3, p.getPrenom());
            stmtPatient.setString(4, p.getTelephone());
            stmtPatient.setDate(5, p.getDateNaissance() != null ? Date.valueOf(p.getDateNaissance()) : null);
            stmtPatient.setString(6, p.getSexe() != null ? p.getSexe().name() : null);
            stmtPatient.setString(7, p.getAssurance() != null ? p.getAssurance().name() : null);

            stmtPatient.executeUpdate();

            conn.commit();
            System.out.println("✓ Patient créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Patient: " + e.getMessage());
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
                if (stmtPatient != null) stmtPatient.close();
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
    public void update(Patient p) {
        p.setDateDerniereModification(LocalDateTime.now());
        if (p.getModifierPar() == null)
            p.setModifierPar("SYSTEM");
        
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtPatient = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(p.getDateDerniereModification()));
            stmtEntite.setString(2, p.getModifierPar());
            stmtEntite.setLong(3, p.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Patient
            String sqlPatient = "UPDATE patient SET nom = ?, prenom = ?, telephone = ?, dateNaissance = ?, sexe = ?, assurance = ? WHERE id = ?";
            stmtPatient = conn.prepareStatement(sqlPatient);
            stmtPatient.setString(1, p.getNom());
            stmtPatient.setString(2, p.getPrenom());
            stmtPatient.setString(3, p.getTelephone());
            stmtPatient.setDate(4, p.getDateNaissance() != null ? Date.valueOf(p.getDateNaissance()) : null);
            stmtPatient.setString(5, p.getSexe() != null ? p.getSexe().name() : null);
            stmtPatient.setString(6, p.getAssurance() != null ? p.getAssurance().name() : null);
            stmtPatient.setLong(7, p.getIdEntite());

            stmtPatient.executeUpdate();

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
                if (stmtPatient != null) stmtPatient.close();
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
    public void delete(Patient p) {
        if (p != null && p.getIdEntite() != null) {
            deleteById(p.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM entite WHERE id = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Patient> search(String keyword) {
        String sql = "SELECT t.id as idEntite, t.id, t.nom, t.prenom, t.dateNaissance, t.sexe, t.telephone, t.assurance, " + 
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM patient t " + 
                     "JOIN entite e ON t.id = e.id " +
                     "WHERE t.nom LIKE ? OR t.prenom LIKE ? OR t.telephone LIKE ?";
        List<Patient> patients = new ArrayList<>();

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    patients.add(RowMappers.mapPatient(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
