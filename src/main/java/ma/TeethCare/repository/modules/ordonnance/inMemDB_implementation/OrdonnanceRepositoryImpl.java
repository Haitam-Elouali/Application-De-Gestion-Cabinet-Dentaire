package ma.TeethCare.repository.modules.ordonnance.inMemDB_implementation;


import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.modules.ordonnance.api.OrdonnanceRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdonnanceRepositoryImpl implements OrdonnanceRepository {

    private ordonnance mapResultSetToEntity(ResultSet rs) throws SQLException {
        ordonnance o = new ordonnance();

        o.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            o.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            o.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        o.setCreePar(rs.getString("creePar"));
        o.setModifierPar(rs.getString("modifierPar"));

        o.setIdOrd(rs.getLong("idOrd"));
        Date dateSql = rs.getDate("date");
        if (dateSql != null) {
            o.setDate(dateSql.toLocalDate());
        }

        return o;
    }

    @Override
    public List<ordonnance> findAll() {
        List<ordonnance> ordonnanceList = new ArrayList<>();
        String sql = "SELECT * FROM Ordonnance";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ordonnanceList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordonnanceList;
    }

    @Override
    public ordonnance findById(Long id) {
        String sql = "SELECT * FROM Ordonnance WHERE idEntite = ?";

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
    public void create(ordonnance o) {
        o.setDateCreation(LocalDate.now());
        if (o.getCreePar() == null) o.setCreePar("SYSTEM");

        String sql = "INSERT INTO Ordonnance (dateCreation, creePar, idOrd, date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(o.getDateCreation()));
            ps.setString(2, o.getCreePar());

            ps.setLong(3, o.getIdOrd());
            ps.setDate(4, o.getDate() != null ? Date.valueOf(o.getDate()) : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    o.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ordonnance o) {
        o.setDateDerniereModification(LocalDateTime.now());
        if (o.getModifierPar() == null) o.setModifierPar("SYSTEM");

        String sql = "UPDATE Ordonnance SET idOrd = ?, date = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, o.getIdOrd());
            ps.setDate(2, o.getDate() != null ? Date.valueOf(o.getDate()) : null);

            ps.setTimestamp(3, Timestamp.valueOf(o.getDateDerniereModification()));
            ps.setString(4, o.getModifierPar());

            ps.setLong(5, o.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ordonnance o) {
        if (o != null && o.getIdEntite() != null) {
            deleteById(o.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Ordonnance WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ordonnance> findByIdOrd(Long idOrd) {
        String sql = "SELECT * FROM Ordonnance WHERE idOrd = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idOrd);
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