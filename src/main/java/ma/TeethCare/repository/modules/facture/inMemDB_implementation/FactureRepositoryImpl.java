package ma.TeethCare.repository.modules.facture.inMemDB_implementation;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.modules.facture.api.FactureRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactureRepositoryImpl implements FactureRepository {

    private facture mapResultSetToEntity(ResultSet rs) throws SQLException {
        facture f = new facture();

        f.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            f.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            f.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        f.setCreePar(rs.getString("creePar"));
        f.setModifierPar(rs.getString("modifierPar"));

        f.setIdFacture(rs.getLong("idFacture"));
        f.setTotaleFacture(rs.getDouble("totaleFacture"));
        f.setTotalPaye(rs.getDouble("totalPaye"));
        f.setReste(rs.getDouble("reste"));

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            f.setStatut(Statut.valueOf(statutStr));
        }

        Timestamp dateFactureSql = rs.getTimestamp("dateFacture");
        if (dateFactureSql != null) {
            f.setDateFacture(dateFactureSql.toLocalDateTime());
        }

        return f;
    }

    @Override
    public List<facture> findAll() {
        List<facture> factureList = new ArrayList<>();
        String sql = "SELECT * FROM Facture";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                factureList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factureList;
    }

    @Override
    public facture findById(Long id) {
        String sql = "SELECT * FROM Facture WHERE idEntite = ?";

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
    public void create(facture f) {
        f.setDateCreation(LocalDate.now());
        if (f.getCreePar() == null) f.setCreePar("SYSTEM");

        String sql = "INSERT INTO Facture (dateCreation, creePar, idFacture, totaleFacture, totalPaye, reste, statut, dateFacture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(f.getDateCreation()));
            ps.setString(2, f.getCreePar());

            ps.setLong(3, f.getIdFacture());
            ps.setDouble(4, f.getTotaleFacture());
            ps.setDouble(5, f.getTotalPaye());
            ps.setDouble(6, f.getReste());
            ps.setString(7, f.getStatut() != null ? f.getStatut().name() : null);
            ps.setTimestamp(8, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : null);

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
        if (f.getModifierPar() == null) f.setModifierPar("SYSTEM");

        String sql = "UPDATE Facture SET idFacture = ?, totaleFacture = ?, totalPaye = ?, reste = ?, statut = ?, dateFacture = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, f.getIdFacture());
            ps.setDouble(2, f.getTotaleFacture());
            ps.setDouble(3, f.getTotalPaye());
            ps.setDouble(4, f.getReste());
            ps.setString(5, f.getStatut() != null ? f.getStatut().name() : null);
            ps.setTimestamp(6, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : null);

            ps.setTimestamp(7, Timestamp.valueOf(f.getDateDerniereModification()));
            ps.setString(8, f.getModifierPar());

            ps.setLong(9, f.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(facture f) {
        if (f != null && f.getIdEntite() != null) {
            deleteById(f.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Facture WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<facture> findByIdFacture(Long idFacture) {
        String sql = "SELECT * FROM Facture WHERE idFacture = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idFacture);
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