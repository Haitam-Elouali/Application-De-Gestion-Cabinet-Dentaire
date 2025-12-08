package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.api.CertificatRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificatRepositoryImpl implements CertificatRepository {

    @Override
    public List<certificat> findAll() throws SQLException {
        List<certificat> certificatList = new ArrayList<>();
        String sql = "SELECT * FROM Certificat";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                certificatList.add(RowMappers.mapCertificat(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return certificatList;
    }

    @Override
    public certificat findById(Long id) {
        String sql = "SELECT * FROM Certificat WHERE idCertif = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapCertificat(rs);
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
        if (c.getCreePar() == null)
            c.setCreePar("SYSTEM");

        String sql = "INSERT INTO Certificat (dateCreation, creePar, idCertif, dateDebut, dateFin, dureer, noteMedecin) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setLong(3, c.getIdCertif());
            ps.setDate(4, c.getDateDebut() != null ? Date.valueOf(c.getDateDebut()) : null);
            ps.setDate(5, c.getDateFin() != null ? Date.valueOf(c.getDateFin()) : null);
            ps.setInt(6, c.getDuree());
            ps.setString(7, c.getNoteMedecin());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    c.setIdCertif(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(certificat c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null)
            c.setModifierPar("SYSTEM");

        String sql = "UPDATE Certificat SET idCertif = ?, dateDebut = ?, dateFin = ?, dureer = ?, noteMedecin = ?, dateDerniereModification = ?, modifierPar = ? WHERE idCertif = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, c.getIdCertif());
            ps.setDate(2, c.getDateDebut() != null ? Date.valueOf(c.getDateDebut()) : null);
            ps.setDate(3, c.getDateFin() != null ? Date.valueOf(c.getDateFin()) : null);
            ps.setInt(4, c.getDuree());
            ps.setString(5, c.getNoteMedecin());

            ps.setTimestamp(6, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(7, c.getModifierPar());

            ps.setLong(8, c.getIdCertif());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(certificat c) {
        if (c != null && c.getIdCertif() != null) {
            deleteById(c.getIdCertif());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Certificat WHERE idCertif = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
