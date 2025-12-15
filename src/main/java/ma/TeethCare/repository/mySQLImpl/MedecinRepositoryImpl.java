package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.repository.api.MedecinRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinRepositoryImpl implements MedecinRepository {

    @Override
    public List<medecin> findAll() throws SQLException {
        List<medecin> medecinList = new ArrayList<>();
        String sql = "SELECT t.id as idEntite, t.id as idMedecin, t.id as idUser, t.specialite, " +
                     "s.salaire, s.dateRecrutement, s.dateDepart, " + 
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM medecin t " + 
                     "JOIN staff s ON t.id = s.id " + 
                     "JOIN utilisateur u ON t.id = u.id " + 
                     "JOIN entite e ON t.id = e.id";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medecinList.add(RowMappers.mapMedecin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return medecinList;
    }

    @Override
    public medecin findById(Long id) {
        String sql = "SELECT t.id as idEntite, t.id as idMedecin, t.id as idUser, t.specialite, " +
                     "s.salaire, s.dateRecrutement, s.dateDepart, " + 
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM medecin t " + 
                     "JOIN staff s ON t.id = s.id " + 
                     "JOIN utilisateur u ON t.id = u.id " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapMedecin(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(medecin m) {
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtStaff = null;
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
                m.setId(id); // Was setIdMedecin
                // m.setIdUser(id); // Was setIdUser, now covered by setId or inheritance
                m.setIdEntite(id);
            } else {
                throw new SQLException("Creating Entite for Medecin failed, no ID obtained.");
            }

            // 2. Insert into Utilisateur
            String sqlUser = "INSERT INTO utilisateur (id, nom, email, tele, username, password, sexe, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setLong(1, id);
            stmtUser.setString(2, m.getNom());
            stmtUser.setString(3, m.getEmail());
            stmtUser.setString(4, m.getTelephone());
            stmtUser.setString(5, m.getUsername());
            stmtUser.setString(6, m.getPassword());
            stmtUser.setString(7, m.getSexe() != null ? m.getSexe().name() : null);
            stmtUser.setObject(8, m.getDateNaissance());
            stmtUser.executeUpdate();

            // 3. Insert into Staff
            String sqlStaff = "INSERT INTO staff (id, salaire, dateRecrutement) VALUES (?, ?, ?)";
            stmtStaff = conn.prepareStatement(sqlStaff);
            stmtStaff.setLong(1, id);
            stmtStaff.setDouble(2, m.getSalaire() != null ? m.getSalaire() : 0.0);
            stmtStaff.setObject(3, m.getDateEmbauche());
            stmtStaff.executeUpdate();

            // 4. Insert into Medecin
            String sqlMed = "INSERT INTO medecin (id, specialite) VALUES (?, ?)";
            stmtMed = conn.prepareStatement(sqlMed);
            stmtMed.setLong(1, id);
            stmtMed.setString(2, m.getSpecialite());
            stmtMed.executeUpdate();

            conn.commit();
            System.out.println("✓ Medecin créé avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Medecin: " + e.getMessage());
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
                if (stmtUser != null) stmtUser.close();
                if (stmtStaff != null) stmtStaff.close();
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
    public void update(medecin m) {
        m.setDateDerniereModification(LocalDateTime.now());
        if (m.getModifierPar() == null)
            m.setModifierPar("SYSTEM");

        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtStaff = null;
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

            // Update Utilisateur
            String sqlUser = "UPDATE utilisateur SET nom = ?, email = ?, tele = ?, username = ?, password = ?, sexe = ?, dateNaissance = ? WHERE id = ?";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setString(1, m.getNom());
            stmtUser.setString(2, m.getEmail());
            stmtUser.setString(3, m.getTelephone());
            stmtUser.setString(4, m.getUsername());
            stmtUser.setString(5, m.getPassword());
            stmtUser.setString(6, m.getSexe() != null ? m.getSexe().name() : null);
            stmtUser.setObject(7, m.getDateNaissance());
            stmtUser.setLong(8, m.getIdEntite());
            stmtUser.executeUpdate();

            // Update Staff
            String sqlStaff = "UPDATE staff SET salaire = ?, dateRecrutement = ? WHERE id = ?";
            stmtStaff = conn.prepareStatement(sqlStaff);
            stmtStaff.setDouble(1, m.getSalaire() != null ? m.getSalaire() : 0.0);
            stmtStaff.setObject(2, m.getDateEmbauche());
            stmtStaff.setLong(3, m.getIdEntite());
            stmtStaff.executeUpdate();

            // Update Medecin
            String sqlMed = "UPDATE medecin SET specialite = ? WHERE id = ?";
            stmtMed = conn.prepareStatement(sqlMed);
            stmtMed.setString(1, m.getSpecialite());
            stmtMed.setLong(2, m.getId()); // Was getIdMedecin
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
                if (stmtUser != null) stmtUser.close();
                if (stmtStaff != null) stmtStaff.close();
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
    public void delete(medecin m) {
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
    public Optional<medecin> findByCin(String cin) {
        // CIN is not in schema. Returning empty.
        return Optional.empty();
    }

    @Override
    public Optional<medecin> findByEmail(String email) {
        String sql = "SELECT t.id as idEntite, t.id as idMedecin, t.id as idUser, t.specialite, " +
                     "s.salaire, s.dateRecrutement, s.dateDepart, " + 
                     "u.nom, u.prenom, u.email, u.tele as tel, u.username as login, u.password as motDePasse, u.sexe, u.dateNaissance, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM medecin t " + 
                     "JOIN staff s ON t.id = s.id " + 
                     "JOIN utilisateur u ON t.id = u.id " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE u.email = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapMedecin(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
