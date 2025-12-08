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
        String sql = "SELECT * FROM Medecin";

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
        String sql = "SELECT * FROM Medecin WHERE idMedecin = ?";

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
        m.setDateCreation(LocalDate.now());
        if (m.getCreePar() == null)
            m.setCreePar("SYSTEM");

        String sql = "INSERT INTO Medecin (dateCreation, creePar, idUser, nom, email, adresse, cin, tel, sexe, login, motDePasse, lastLoginDate, dateNaissance, salaire, prime, dateRecrutement, soldeConge, idMedecin, specialite, numeroOrdre, diplome) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(m.getDateCreation()));
            ps.setString(2, m.getCreePar());

            ps.setLong(3, m.getIdUser());
            ps.setString(4, m.getNom());
            ps.setString(5, m.getEmail());
            ps.setString(6, m.getAdresse());
            ps.setString(7, m.getCin());
            ps.setString(8, m.getTel());
            ps.setString(9, m.getSexe() != null ? m.getSexe().name() : null);
            ps.setString(10, m.getLogin());
            ps.setString(11, m.getMotDePasse());
            ps.setDate(12, m.getLastLoginDate() != null ? Date.valueOf(m.getLastLoginDate()) : null);
            ps.setDate(13, m.getDateNaissance() != null ? Date.valueOf(m.getDateNaissance()) : null);

            // Staff
            ps.setDouble(14, m.getSalaire());
            ps.setDouble(15, m.getPrime());
            ps.setDate(16, m.getDateRecrutement() != null ? Date.valueOf(m.getDateRecrutement()) : null);
            ps.setInt(17, m.getSoldeConge());

            // Medecin
            ps.setLong(18, m.getIdMedecin());
            ps.setString(19, m.getSpecialite());
            ps.setString(20, m.getNumeroOrdre());
            ps.setString(21, m.getDiplome());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    m.setIdMedecin(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(medecin m) {
        m.setDateDerniereModification(LocalDateTime.now());
        if (m.getModifierPar() == null)
            m.setModifierPar("SYSTEM");

        String sql = "UPDATE Medecin SET idUser = ?, nom = ?, email = ?, adresse = ?, cin = ?, tel = ?, sexe = ?, login = ?, motDePasse = ?, lastLoginDate = ?, dateNaissance = ?, salaire = ?, prime = ?, dateRecrutement = ?, soldeConge = ?, idMedecin = ?, specialite = ?, numeroOrdre = ?, diplome = ?, dateDerniereModification = ?, modifierPar = ? WHERE idMedecin = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, m.getIdUser());
            ps.setString(2, m.getNom());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getAdresse());
            ps.setString(5, m.getCin());
            ps.setString(6, m.getTel());
            ps.setString(7, m.getSexe() != null ? m.getSexe().name() : null);
            ps.setString(8, m.getLogin());
            ps.setString(9, m.getMotDePasse());
            ps.setDate(10, m.getLastLoginDate() != null ? Date.valueOf(m.getLastLoginDate()) : null);
            ps.setDate(11, m.getDateNaissance() != null ? Date.valueOf(m.getDateNaissance()) : null);

            ps.setDouble(12, m.getSalaire());
            ps.setDouble(13, m.getPrime());
            ps.setDate(14, m.getDateRecrutement() != null ? Date.valueOf(m.getDateRecrutement()) : null);
            ps.setInt(15, m.getSoldeConge());

            ps.setLong(16, m.getIdMedecin());
            ps.setString(17, m.getSpecialite());
            ps.setString(18, m.getNumeroOrdre());
            ps.setString(19, m.getDiplome());

            ps.setTimestamp(20, Timestamp.valueOf(m.getDateDerniereModification()));
            ps.setString(21, m.getModifierPar());

            ps.setLong(22, m.getIdMedecin());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(medecin m) {
        if (m != null && m.getIdMedecin() != null) {
            deleteById(m.getIdMedecin());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Medecin WHERE idMedecin = ?";
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
        String sql = "SELECT * FROM Medecin WHERE cin = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cin);
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

    @Override
    public Optional<medecin> findByEmail(String email) {
        String sql = "SELECT * FROM Medecin WHERE email = ?";

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
