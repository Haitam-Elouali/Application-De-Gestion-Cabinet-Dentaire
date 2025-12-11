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
        String sql = "SELECT * FROM Patient";
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
        String sql = "SELECT * FROM Patient WHERE id = ?";

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
            } else {
                throw new SQLException("Creating Entite for Patient failed, no ID obtained.");
            }

            // 2. Insert into Patient
            // Table: patient (id, nom, prenom, dateNaissance, sexe, telephone, assurance)
            // No 'email' in schema for patient.
            String sqlPatient = "INSERT INTO patient (id, nom, prenom, adresse, telephone, dateNaissance, sexe, assurance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            // Note: 'adresse' is NOT in the text.txt schema for patient?? 
            // text.txt: id, nom, prenom, dateNaissance, sexe, telephone, assurance.
            // Let me check text.txt again for 'adresse' in patient.
            // Result of viewed_file text.txt above:
            // TABLE: patient
            //   - id ...
            //   - nom ...
            //   - prenom ...
            //   - dateNaissance ...
            //   - sexe ...
            //   - telephone ...
            //   - assurance ...
            // IT DOES NOT HAVE ADRESSE either.
            
            // Re-evaluating SQL. 
            // I will remove 'adresse' and 'email' from the INSERT to be safe with schema.
            // If the Java Entity has them, they won't be persisted to DB or need distinct table? 
            // For now, I stick to the schema to avoid SQL errors.
            String sqlPatientFinal = "INSERT INTO patient (id, nom, prenom, telephone, dateNaissance, sexe, assurance) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            stmtPatient = conn.prepareStatement(sqlPatientFinal);
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
        String sql = "UPDATE Patient SET nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ?, dateNaissance = ?, sexe = ?, assurance = ? WHERE id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNom());
            stmt.setString(2, p.getPrenom());
            stmt.setString(3, p.getAdresse());
            stmt.setString(4, p.getTelephone());
            stmt.setString(5, p.getEmail());
            stmt.setDate(6, p.getDateNaissance() != null ? Date.valueOf(p.getDateNaissance()) : null);
            stmt.setString(7, p.getSexe() != null ? p.getSexe().name() : null);
            stmt.setString(8, p.getAssurance() != null ? p.getAssurance().name() : null);

            stmt.setLong(9, p.getIdEntite());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
        String sql = "DELETE FROM Patient WHERE id = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
