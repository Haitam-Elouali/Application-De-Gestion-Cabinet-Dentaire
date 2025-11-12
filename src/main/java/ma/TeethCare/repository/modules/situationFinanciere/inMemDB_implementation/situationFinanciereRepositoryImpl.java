package ma.TeethCare.repository.modules.situationFinanciere.inMemDB_implementation;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.entities.enums.Promo;
import ma.TeethCare.repository.modules.situationFinanciere.api.situationFinanciereRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class situationFinanciereRepositoryImpl implements situationFinanciereRepository {

    private situationFinanciere mapResultSetToEntity(ResultSet rs) throws SQLException {
        situationFinanciere sf = new situationFinanciere();

        sf.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            sf.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            sf.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        sf.setCreePar(rs.getString("creePar"));
        sf.setModifierPar(rs.getString("modifierPar"));

        sf.setIdSF(rs.getLong("idSF"));
        sf.setTotaleDesActes(rs.getDouble("totaleDesActes"));
        sf.setTotalPaye(rs.getDouble("totalPaye"));
        sf.setCredit(rs.getDouble("credit"));

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            sf.setStatut(Statut.valueOf(statutStr));
        }

        String promoStr = rs.getString("enPromo");
        if (promoStr != null) {
            sf.setEnPromo(Promo.valueOf(promoStr));
        }

        return sf;
    }

    @Override
    public List<situationFinanciere> findAll() {
        List<situationFinanciere> sfList = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sfList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sfList;
    }

    @Override
    public situationFinanciere findById(Long id) {
        String sql = "SELECT * FROM SituationFinanciere WHERE idEntite = ?";

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
    public void create(situationFinanciere sf) {
        sf.setDateCreation(LocalDate.now());
        if (sf.getCreePar() == null) sf.setCreePar("SYSTEM");

        String sql = "INSERT INTO SituationFinanciere (dateCreation, creePar, idSF, totaleDesActes, totalPaye, credit, statut, enPromo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(sf.getDateCreation()));
            ps.setString(2, sf.getCreePar());

            ps.setLong(3, sf.getIdSF());
            ps.setDouble(4, sf.getTotaleDesActes());
            ps.setDouble(5, sf.getTotalPaye());
            ps.setDouble(6, sf.getCredit());
            ps.setString(7, sf.getStatut() != null ? sf.getStatut().name() : null);
            ps.setString(8, sf.getEnPromo() != null ? sf.getEnPromo().name() : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sf.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(situationFinanciere sf) {
        sf.setDateDerniereModification(LocalDateTime.now());
        if (sf.getModifierPar() == null) sf.setModifierPar("SYSTEM");

        String sql = "UPDATE SituationFinanciere SET idSF = ?, totaleDesActes = ?, totalPaye = ?, credit = ?, statut = ?, enPromo = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, sf.getIdSF());
            ps.setDouble(2, sf.getTotaleDesActes());
            ps.setDouble(3, sf.getTotalPaye());
            ps.setDouble(4, sf.getCredit());
            ps.setString(5, sf.getStatut() != null ? sf.getStatut().name() : null);
            ps.setString(6, sf.getEnPromo() != null ? sf.getEnPromo().name() : null);

            ps.setTimestamp(7, Timestamp.valueOf(sf.getDateDerniereModification()));
            ps.setString(8, sf.getModifierPar());

            ps.setLong(9, sf.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(situationFinanciere sf) {
        if (sf != null && sf.getIdEntite() != null) {
            deleteById(sf.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM SituationFinanciere WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<situationFinanciere> findByIdSF(Long idSF) {
        String sql = "SELECT * FROM SituationFinanciere WHERE idSF = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idSF);
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