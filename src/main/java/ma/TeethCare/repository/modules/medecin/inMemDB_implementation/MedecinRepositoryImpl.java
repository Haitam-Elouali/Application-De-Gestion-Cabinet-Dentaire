package ma.TeethCare.repository.modules.medecin.inMemDB_implementation;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.modules.medecin.api.MedecinRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinRepositoryImpl implements MedecinRepository {

    private medecin mapResultSetToEntity(ResultSet rs) throws SQLException {
        medecin m = new medecin();

        m.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            m.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            m.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        m.setCreePar(rs.getString("creePar"));
        m.setModifierPar(rs.getString("modifierPar"));

        m.setIdUser(rs.getLong("idUser"));
        m.setNom(rs.getString("nom"));
        m.setEmail(rs.getString("email"));
        m.setAdresse(rs.getString("adresse"));
        m.setCin(rs.getString("cin"));
        m.setTel(rs.getString("tel"));

        String sexeStr = rs.getString("sexe");
        if (sexeStr != null) {
            m.setSexe(Sexe.valueOf(sexeStr));
        }

        m.setLogin(rs.getString("login"));
        m.setMotDePasse(rs.getString("motDePasse"));

        Date lastLoginSql = rs.getDate("lastLoginDate");
        if (lastLoginSql != null) {
            m.setLastLoginDate(lastLoginSql.toLocalDate());
        }

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null) {
            m.setDateNaissance(dateNaissanceSql.toLocalDate());
        }

        m.setSalaire(rs.getDouble("salaire"));
        m.setPrime(rs.getDouble("prime"));
        Date dateRecrutementSql = rs.getDate("dateRecrutement");
        if (dateRecrutementSql != null) {
            m.setDateRecrutement(dateRecrutementSql.toLocalDate());
        }
        m.setSoldeConge(rs.getInt("soldeConge"));

        m.setSpecialite(rs.getString("specialite"));

        return m;
    }

    @Override
    public List<medecin> findAll() {
        List<medecin> medecinList = new ArrayList<>();
        String sql = "SELECT * FROM Medecin";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medecinList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecinList;
    }

    @Override
    public medecin findById(Long id) {
        String sql = "SELECT * FROM Medecin WHERE idEntite = ?";

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
    public void create(medecin m) {
        m.setDateCreation(LocalDate.now());
        if (m.getCreePar() == null) m.setCreePar("SYSTEM");

        String sql = "INSERT INTO Medecin (dateCreation, creePar, idUser, nom, email, adresse, cin, tel, sexe, login, motDePasse, lastLoginDate, dateNaissance, salaire, prime, dateRecrutement, soldeConge, specialite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
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

            ps.setDouble(14, m.getSalaire());
            ps.setDouble(15, m.getPrime());
            ps.setDate(16, m.getDateRecrutement() != null ? Date.valueOf(m.getDateRecrutement()) : null);
            ps.setInt(17, m.getSoldeConge());

            ps.setString(18, m.getSpecialite());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    m.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(medecin m) {
        m.setDateDerniereModification(LocalDateTime.now());
        if (m.getModifierPar() == null) m.setModifierPar("SYSTEM");

        String sql = "UPDATE Medecin SET idUser = ?, nom = ?, email = ?, adresse = ?, cin = ?, tel = ?, sexe = ?, login = ?, motDePasse = ?, lastLoginDate = ?, dateNaissance = ?, salaire = ?, prime = ?, dateRecrutement = ?, soldeConge = ?, specialite = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
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

            ps.setString(16, m.getSpecialite());

            ps.setTimestamp(17, Timestamp.valueOf(m.getDateDerniereModification()));
            ps.setString(18, m.getModifierPar());

            ps.setLong(19, m.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
        String sql = "DELETE FROM Medecin WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
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

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cin);
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
    public Optional<medecin> findByEmail(String email) {
        String sql = "SELECT * FROM Medecin WHERE email = ?";

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
}