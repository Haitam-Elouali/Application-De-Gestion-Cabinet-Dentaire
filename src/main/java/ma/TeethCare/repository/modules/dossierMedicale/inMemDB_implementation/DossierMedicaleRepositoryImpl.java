package ma.TeethCare.repository.modules.dossierMedicale.inMemDB_implementation;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.modules.dossierMedicale.api.DossierMedicaleRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DossierMedicaleRepositoryImpl implements DossierMedicaleRepository {

    private dossierMedicale mapResultSetToEntity(ResultSet rs) throws SQLException {
        dossierMedicale d = new dossierMedicale();

        d.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            d.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            d.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        d.setCreePar(rs.getString("creePar"));
        d.setModifierPar(rs.getString("modifierPar"));

        d.setIdDM(rs.getLong("idDM"));
        d.setDateDeCreation(rs.getString("dateDeCreation"));

        return d;
    }

    @Override
    public List<dossierMedicale> findAll() {
        List<dossierMedicale> dmList = new ArrayList<>();
        String sql = "SELECT * FROM DossierMedicale";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                dmList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dmList;
    }

    @Override
    public dossierMedicale findById(Long id) {
        String sql = "SELECT * FROM DossierMedicale WHERE idEntite = ?";

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
    public void create(dossierMedicale d) {
        d.setDateCreation(LocalDate.now());
        if (d.getCreePar() == null) d.setCreePar("SYSTEM");

        String sql = "INSERT INTO DossierMedicale (dateCreation, creePar, idDM, dateDeCreation) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(d.getDateCreation()));
            ps.setString(2, d.getCreePar());

            ps.setLong(3, d.getIdDM());
            ps.setString(4, d.getDateDeCreation());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    d.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(dossierMedicale d) {
        d.setDateDerniereModification(LocalDateTime.now());
        if (d.getModifierPar() == null) d.setModifierPar("SYSTEM");

        String sql = "UPDATE DossierMedicale SET idDM = ?, dateDeCreation = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, d.getIdDM());
            ps.setString(2, d.getDateDeCreation());

            ps.setTimestamp(3, Timestamp.valueOf(d.getDateDerniereModification()));
            ps.setString(4, d.getModifierPar());

            ps.setLong(5, d.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(dossierMedicale d) {
        if (d != null && d.getIdEntite() != null) {
            deleteById(d.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM DossierMedicale WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<dossierMedicale> findByIdDM(Long idDM) {
        String sql = "SELECT * FROM DossierMedicale WHERE idDM = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idDM);
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