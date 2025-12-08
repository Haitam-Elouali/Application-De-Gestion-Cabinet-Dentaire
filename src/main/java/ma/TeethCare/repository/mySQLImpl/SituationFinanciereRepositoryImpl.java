package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.api.SituationFinanciereRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SituationFinanciereRepositoryImpl implements SituationFinanciereRepository {

    @Override
    public List<situationFinanciere> findAll() throws SQLException {
        List<situationFinanciere> sfList = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sfList.add(RowMappers.mapSituationFinanciere(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return sfList;
    }

    @Override
    public situationFinanciere findById(Long id) {
        String sql = "SELECT * FROM SituationFinanciere WHERE idSF = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapSituationFinanciere(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(situationFinanciere sf) {
        sf.setDateCreation(LocalDate.now());
        if (sf.getCreePar() == null)
            sf.setCreePar("SYSTEM");

        String sql = "INSERT INTO SituationFinanciere (dateCreation, creePar, patientId, totaleDesActes, totalPaye, credit, reste, statut, enPromo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(sf.getDateCreation()));
            ps.setString(2, sf.getCreePar());
            ps.setLong(3, sf.getPatientId());
            ps.setDouble(4, sf.getTotaleDesActes());
            ps.setDouble(5, sf.getTotalPaye());
            ps.setDouble(6, sf.getCredit());
            ps.setDouble(7, sf.getReste());
            ps.setString(8, sf.getStatut() != null ? sf.getStatut().name() : null);
            ps.setString(9, sf.getEnPromo() != null ? sf.getEnPromo().name() : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sf.setIdSF(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(situationFinanciere sf) {
        sf.setDateDerniereModification(LocalDateTime.now());
        if (sf.getModifierPar() == null)
            sf.setModifierPar("SYSTEM");

        String sql = "UPDATE SituationFinanciere SET patientId = ?, totaleDesActes = ?, totalPaye = ?, credit = ?, reste = ?, statut = ?, enPromo = ?, dateDerniereModification = ?, modifierPar = ? WHERE idSF = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, sf.getPatientId());
            ps.setDouble(2, sf.getTotaleDesActes());
            ps.setDouble(3, sf.getTotalPaye());
            ps.setDouble(4, sf.getCredit());
            ps.setDouble(5, sf.getReste());
            ps.setString(6, sf.getStatut() != null ? sf.getStatut().name() : null);
            ps.setString(7, sf.getEnPromo() != null ? sf.getEnPromo().name() : null);

            ps.setTimestamp(8, Timestamp.valueOf(sf.getDateDerniereModification()));
            ps.setString(9, sf.getModifierPar());

            ps.setLong(10, sf.getIdSF());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(situationFinanciere sf) {
        if (sf != null && sf.getIdSF() != null) {
            deleteById(sf.getIdSF());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM SituationFinanciere WHERE idSF = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
