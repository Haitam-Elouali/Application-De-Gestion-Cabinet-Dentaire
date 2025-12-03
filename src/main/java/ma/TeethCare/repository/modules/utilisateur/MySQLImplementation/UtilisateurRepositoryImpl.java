package ma.TeethCare.repository.modules.utilisateur.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.modules.utilisateur.api.UtilisateurRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    private utilisateur mapResultSetToEntity(ResultSet rs) throws SQLException {
        utilisateur u = new utilisateur();

        u.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            u.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            u.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        u.setCreePar(rs.getString("creePar"));
        u.setModifierPar(rs.getString("modifierPar"));

        u.setIdUser(rs.getLong("idUser"));
        u.setNom(rs.getString("nom"));
        u.setEmail(rs.getString("email"));
        u.setAdresse(rs.getString("adresse"));
        u.setCin(rs.getString("cin"));
        u.setTel(rs.getString("tel"));

        String sexeStr = rs.getString("sexe");
        if (sexeStr != null) {
            u.setSexe(Sexe.valueOf(sexeStr));
        }

        u.setLogin(rs.getString("login"));
        u.setMotDePasse(rs.getString("motDePasse"));

        Date lastLoginSql = rs.getDate("lastLoginDate");
        if (lastLoginSql != null) {
            u.setLastLoginDate(lastLoginSql.toLocalDate());
        }

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null) {
            u.setDateNaissance(dateNaissanceSql.toLocalDate());
        }

        return u;
    }

    @Override
    public List<utilisateur> findAll() {
        List<utilisateur> utilisateurList = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                utilisateurList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurList;
    }

    @Override
    public utilisateur findById(Long id) {
        String sql = "SELECT * FROM Utilisateur WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
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
        if (u.getCreePar() == null) u.setCreePar("SYSTEM");

        String sql = "INSERT INTO Utilisateur (dateCreation, creePar, idUser, nom, email, adresse, cin, tel, sexe, login, motDePasse, lastLoginDate, dateNaissance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
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
        if (u.getModifierPar() == null) u.setModifierPar("SYSTEM");

        String sql = "UPDATE Utilisateur SET idUser = ?, nom = ?, email = ?, adresse = ?, cin = ?, tel = ?, sexe = ?, login = ?, motDePasse = ?, lastLoginDate = ?, dateNaissance = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
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

            ps.setLong(14, u.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(utilisateur u) {
        if (u != null && u.getIdEntite() != null) {
            deleteById(u.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Utilisateur WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
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

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
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

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}

