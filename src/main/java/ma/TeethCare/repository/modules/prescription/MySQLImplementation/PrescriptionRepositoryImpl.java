package ma.TeethCare.repository.modules.prescription.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.modules.prescription.api.PrescriptionRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    private prescription mapResultSetToEntity(ResultSet rs) throws SQLException {
        prescription p = new prescription();

        p.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            p.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            p.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        p.setCreePar(rs.getString("creePar"));
        p.setModifierPar(rs.getString("modifierPar"));

        p.setIdPr(rs.getLong("idPr"));
        p.setQuantite(rs.getInt("quantite"));
        p.setFrequence(rs.getString("frequence"));
        p.setDureeEnjours(rs.getInt("dureeEnjours"));

        return p;
    }

    @Override
    public List<prescription> findAll() {
        List<prescription> prescriptionList = new ArrayList<>();
        String sql = "SELECT * FROM Prescription";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                prescriptionList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptionList;
    }

    @Override
    public prescription findById(Long id) {
        String sql = "SELECT * FROM Prescription WHERE idEntite = ?";

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
    public void create(prescription p) {
        p.setDateCreation(LocalDate.now());
        if (p.getCreePar() == null) p.setCreePar("SYSTEM");

        String sql = "INSERT INTO Prescription (dateCreation, creePar, idPr, quantite, frequence, dureeEnjours) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(p.getDateCreation()));
            ps.setString(2, p.getCreePar());

            ps.setLong(3, p.getIdPr());
            ps.setInt(4, p.getQuantite());
            ps.setString(5, p.getFrequence());
            ps.setInt(6, p.getDureeEnjours());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(prescription p) {
        p.setDateDerniereModification(LocalDateTime.now());
        if (p.getModifierPar() == null) p.setModifierPar("SYSTEM");

        String sql = "UPDATE Prescription SET idPr = ?, quantite = ?, frequence = ?, dureeEnjours = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, p.getIdPr());
            ps.setInt(2, p.getQuantite());
            ps.setString(3, p.getFrequence());
            ps.setInt(4, p.getDureeEnjours());

            ps.setTimestamp(5, Timestamp.valueOf(p.getDateDerniereModification()));
            ps.setString(6, p.getModifierPar());

            ps.setLong(7, p.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(prescription p) {
        if (p != null && p.getIdEntite() != null) {
            deleteById(p.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Prescription WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<prescription> findByIdPr(Long idPr) {
        String sql = "SELECT * FROM Prescription WHERE idPr = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idPr);
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

