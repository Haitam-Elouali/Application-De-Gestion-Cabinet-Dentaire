package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.api.UtilisateurRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    @Override
    public List<utilisateur> findAll() throws SQLException {
        List<utilisateur> utilisateurList = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                utilisateurList.add(RowMappers.mapUtilisateur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return utilisateurList;
    }

    @Override
    public utilisateur findById(Long id) {
        String sql = "SELECT * FROM Utilisateur WHERE idUser = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapUtilisateur(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(utilisateur u) {
        u.setDateCreation(LocalDate.now());
        if (u.getCreePar() == null)
            u.setCreePar("SYSTEM");

        String sql = "INSERT INTO Utilisateur (dateCreation, creePar, idUser, nom, email, adresse, cin, tel, sexe, login, motDePasse, lastLoginDate, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(u.getDateCreation()));
            ps.setString(2, u.getCreePar());

            ps.setLong(3, u.getIdUser());
            ps.setString(4, u.getNom());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getAdresse());
            ps.setString(7, u.getCin());
            ps.setString(8, u.getTel());
            ps.setString(9, u.getSexe() != null ? u.getSexe().name() : null);
            ps.setString(10, u.getLogin());
            ps.setString(11, u.getMotDePasse());
            ps.setDate(12, u.getLastLoginDate() != null ? Date.valueOf(u.getLastLoginDate()) : null);
            ps.setDate(13, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    u.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(utilisateur u) {
        u.setDateDerniereModification(LocalDateTime.now());
        if (u.getModifierPar() == null)
            u.setModifierPar("SYSTEM");

        String sql = "UPDATE Utilisateur SET idUser = ?, nom = ?, email = ?, adresse = ?, cin = ?, tel = ?, sexe = ?, login = ?, motDePasse = ?, lastLoginDate = ?, dateNaissance = ?, dateDerniereModification = ?, modifierPar = ? WHERE idUser = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, u.getIdUser());
            ps.setString(2, u.getNom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getAdresse());
            ps.setString(5, u.getCin());
            ps.setString(6, u.getTel());
            ps.setString(7, u.getSexe() != null ? u.getSexe().name() : null);
            ps.setString(8, u.getLogin());
            ps.setString(9, u.getMotDePasse());
            ps.setDate(10, u.getLastLoginDate() != null ? Date.valueOf(u.getLastLoginDate()) : null);
            ps.setDate(11, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);

            ps.setTimestamp(12, Timestamp.valueOf(u.getDateDerniereModification()));
            ps.setString(13, u.getModifierPar());

            ps.setLong(14, u.getIdUser());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(utilisateur u) {
        if (u != null && u.getIdUser() != null) {
            deleteById(u.getIdUser());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Utilisateur WHERE idUser = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<utilisateur> findByEmail(String email) {
        String sql = "SELECT * FROM Utilisateur WHERE email = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapUtilisateur(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<utilisateur> findByLogin(String login) {
        String sql = "SELECT * FROM Utilisateur WHERE login = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapUtilisateur(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
