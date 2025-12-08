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
        p.setDateCreation(LocalDate.now());

        String sql = "INSERT INTO Patient (dateCreation, nom, prenom, adresse, telephone, email, dateNaissance, sexe, assurance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(p.getDateCreation()));
            stmt.setString(2, p.getNom());
            stmt.setString(3, p.getPrenom());
            stmt.setString(4, p.getAdresse());
            stmt.setString(5, p.getTelephone());
            stmt.setString(6, p.getEmail());
            stmt.setDate(7, p.getDateNaissance() != null ? Date.valueOf(p.getDateNaissance()) : null);
            stmt.setString(8, p.getSexe() != null ? p.getSexe().name() : null);
            stmt.setString(9, p.getAssurance() != null ? p.getAssurance().name() : null);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
