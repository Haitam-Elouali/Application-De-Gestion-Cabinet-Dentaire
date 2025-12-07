package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.api.FactureRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactureRepositoryImpl implements FactureRepository {

    @Override
    public List<facture> findAll() throws SQLException {
        List<facture> factureList = new ArrayList<>();
        String sql = "SELECT * FROM Facture";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                factureList.add(RowMappers.mapFacture(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return factureList;
    }

    @Override
    public facture findById(Long id) {
        String sql = "SELECT * FROM Facture WHERE idFacture = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapFacture(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(facture f) {
        f.setDateCreation(LocalDate.now());
        if (f.getCreePar() == null)
            f.setCreePar("SYSTEM");

        String sql = "INSERT INTO Facture (dateCreation, creePar, idFacture, consultationId, patientId, totaleFacture, totalPaye, reste, statut, dateFacture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(f.getDateCreation()));
            ps.setString(2, f.getCreePar());

            ps.setLong(3, f.getIdFacture());
            ps.setLong(4, f.getConsultationId());
            ps.setLong(5, f.getPatientId());
            ps.setDouble(6, f.getTotaleFacture());
            ps.setDouble(7, f.getTotalPaye());
            ps.setDouble(8, f.getReste());
            ps.setString(9, f.getStatut() != null ? f.getStatut().name() : null);
            ps.setTimestamp(10, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    f.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(facture f) {
        f.setDateDerniereModification(LocalDateTime.now());
        if (f.getModifierPar() == null)
            f.setModifierPar("SYSTEM");

        String sql = "UPDATE Facture SET idFacture = ?, consultationId = ?, patientId = ?, totaleFacture = ?, totalPaye = ?, reste = ?, statut = ?, dateFacture = ?, dateDerniereModification = ?, modifierPar = ? WHERE idFacture = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, f.getIdFacture());
            ps.setLong(2, f.getConsultationId());
            ps.setLong(3, f.getPatientId());
            ps.setDouble(4, f.getTotaleFacture());
            ps.setDouble(5, f.getTotalPaye());
            ps.setDouble(6, f.getReste());
            ps.setString(7, f.getStatut() != null ? f.getStatut().name() : null);
            ps.setTimestamp(8, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : null);

            ps.setTimestamp(9, Timestamp.valueOf(f.getDateDerniereModification()));
            ps.setString(10, f.getModifierPar());

            ps.setLong(11, f.getIdFacture());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(facture f) {
        if (f != null && f.getIdFacture() != null) {
            deleteById(f.getIdFacture());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Facture WHERE idFacture = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
