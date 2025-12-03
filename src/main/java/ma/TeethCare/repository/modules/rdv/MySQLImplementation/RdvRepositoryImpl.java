package ma.TeethCare.repository.modules.rdv.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.modules.rdv.api.RdvRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RdvRepositoryImpl implements RdvRepository {

    private rdv mapResultSetToEntity(ResultSet rs) throws SQLException {
        rdv r = new rdv();

        r.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            r.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            r.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        r.setCreePar(rs.getString("creePar"));
        r.setModifierPar(rs.getString("modifierPar"));

        r.setIdRDV(rs.getLong("idRDV"));

        Date dateSql = rs.getDate("date");
        if (dateSql != null) {
            r.setDate(dateSql.toLocalDate());
        }

        Time heureSql = rs.getTime("heure");
        if (heureSql != null) {
            r.setHeure(heureSql.toLocalTime());
        }

        r.setMotif(rs.getString("motif"));

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            r.setStatut(Statut.valueOf(statutStr));
        }

        r.setNoteMedecin(rs.getString("noteMedecin"));

        return r;
    }

    @Override
    public List<rdv> findAll() {
        List<rdv> rdvList = new ArrayList<>();
        String sql = "SELECT * FROM Rdv";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rdvList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rdvList;
    }

    @Override
    public rdv findById(Long id) {
        String sql = "SELECT * FROM Rdv WHERE idEntite = ?";

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
    public void create(rdv r) {
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null) r.setCreePar("SYSTEM");

        String sql = "INSERT INTO Rdv (dateCreation, creePar, idRDV, date, heure, motif, statut, noteMedecin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(r.getDateCreation()));
            ps.setString(2, r.getCreePar());

            ps.setLong(3, r.getIdRDV());
            ps.setDate(4, r.getDate() != null ? Date.valueOf(r.getDate()) : null);
            ps.setTime(5, r.getHeure() != null ? Time.valueOf(r.getHeure()) : null);
            ps.setString(6, r.getMotif());
            ps.setString(7, r.getStatut() != null ? r.getStatut().name() : null);
            ps.setString(8, r.getNoteMedecin());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    r.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(rdv r) {
        r.setDateDerniereModification(LocalDateTime.now());
        if (r.getModifierPar() == null) r.setModifierPar("SYSTEM");

        String sql = "UPDATE Rdv SET idRDV = ?, date = ?, heure = ?, motif = ?, statut = ?, noteMedecin = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, r.getIdRDV());
            ps.setDate(2, r.getDate() != null ? Date.valueOf(r.getDate()) : null);
            ps.setTime(3, r.getHeure() != null ? Time.valueOf(r.getHeure()) : null);
            ps.setString(4, r.getMotif());
            ps.setString(5, r.getStatut() != null ? r.getStatut().name() : null);
            ps.setString(6, r.getNoteMedecin());

            ps.setTimestamp(7, Timestamp.valueOf(r.getDateDerniereModification()));
            ps.setString(8, r.getModifierPar());

            ps.setLong(9, r.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(rdv r) {
        if (r != null && r.getIdEntite() != null) {
            deleteById(r.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Rdv WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<rdv> findByIdRDV(Long idRDV) {
        String sql = "SELECT * FROM Rdv WHERE idRDV = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idRDV);
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
    public List<rdv> findByDate(LocalDate date) {
        List<rdv> rdvList = new ArrayList<>();
        String sql = "SELECT * FROM Rdv WHERE date = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rdvList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rdvList;
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}

