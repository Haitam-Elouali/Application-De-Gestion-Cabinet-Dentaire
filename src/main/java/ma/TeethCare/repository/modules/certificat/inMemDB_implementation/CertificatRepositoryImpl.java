package ma.TeethCare.repository.modules.certificat.inMemDB_implementation;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.modules.certificat.api.CertificatRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificatRepositoryImpl implements CertificatRepository {

    private certificat mapResultSetToEntity(ResultSet rs) throws SQLException {
        certificat c = new certificat();

        c.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            c.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            c.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        c.setCreePar(rs.getString("creePar"));
        c.setModifierPar(rs.getString("modifierPar"));

        c.setIdCertif(rs.getLong("idCertif"));

        Date dateDebutSql = rs.getDate("dateDebut");
        if (dateDebutSql != null) {
            c.setDateDebut(dateDebutSql.toLocalDate());
        }

        Date dateFinSql = rs.getDate("dateFin");
        if (dateFinSql != null) {
            c.setDateFin(dateFinSql.toLocalDate());
        }

        c.setDureer(rs.getInt("dureer"));
        c.setNoteMedecin(rs.getString("noteMedecin"));

        return c;
    }

    @Override
    public List<certificat> findAll() {
        List<certificat> certificatList = new ArrayList<>();
        String sql = "SELECT * FROM Certificat";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                certificatList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return certificatList;
    }

    @Override
    public certificat findById(Long id) {
        String sql = "SELECT * FROM Certificat WHERE idEntite = ?";

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
    public void create(certificat c) {
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null) c.setCreePar("SYSTEM");

        String sql = "INSERT INTO Certificat (dateCreation, creePar, idCertif, dateDebut, dateFin, dureer, noteMedecin) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setLong(3, c.getIdCertif());
            ps.setDate(4, c.getDateDebut() != null ? Date.valueOf(c.getDateDebut()) : null);
            ps.setDate(5, c.getDateFin() != null ? Date.valueOf(c.getDateFin()) : null);
            ps.setInt(6, c.getDureer());
            ps.setString(7, c.getNoteMedecin());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    c.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(certificat c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null) c.setModifierPar("SYSTEM");

        String sql = "UPDATE Certificat SET idCertif = ?, dateDebut = ?, dateFin = ?, dureer = ?, noteMedecin = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, c.getIdCertif());
            ps.setDate(2, c.getDateDebut() != null ? Date.valueOf(c.getDateDebut()) : null);
            ps.setDate(3, c.getDateFin() != null ? Date.valueOf(c.getDateFin()) : null);
            ps.setInt(4, c.getDureer());
            ps.setString(5, c.getNoteMedecin());

            ps.setTimestamp(6, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(7, c.getModifierPar());

            ps.setLong(8, c.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(certificat c) {
        if (c != null && c.getIdEntite() != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Certificat WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<certificat> findByIdCertif(Long idCertif) {
        String sql = "SELECT * FROM Certificat WHERE idCertif = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idCertif);
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