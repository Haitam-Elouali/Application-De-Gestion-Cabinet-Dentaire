package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.api.LogRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogRepositoryImpl implements LogRepository {

    @Override
    public List<log> findAll() throws SQLException {
        List<log> logList = new ArrayList<>();
        String sql = "SELECT * FROM Log";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                logList.add(RowMappers.mapLog(rs));
            }
        }
        return logList;
    }

    @Override
    public log findById(Long id) {
        String sql = "SELECT * FROM Log WHERE idLog = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapLog(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(log l) {
        l.setDateCreation(LocalDate.now());
        if (l.getCreePar() == null)
            l.setCreePar("SYSTEM");

        String sql = "INSERT INTO Log (dateCreation, creePar, idLog, action, utilisateur, dateAction, description, adresseIP) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(l.getDateCreation()));
            ps.setString(2, l.getCreePar());

            ps.setLong(3, l.getIdLog());
            ps.setString(4, l.getAction());
            ps.setString(5, l.getUtilisateur());
            ps.setTimestamp(6, l.getDateAction() != null ? Timestamp.valueOf(l.getDateAction()) : null);
            ps.setString(7, l.getDescription());
            ps.setString(8, l.getAdresseIP());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    l.setIdLog(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(log l) {
        l.setDateDerniereModification(LocalDateTime.now());
        if (l.getModifierPar() == null)
            l.setModifierPar("SYSTEM");

        String sql = "UPDATE Log SET idLog = ?, action = ?, utilisateur = ?, dateAction = ?, description = ?, adresseIP = ?, dateDerniereModification = ?, modifierPar = ? WHERE idLog = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, l.getIdLog());
            ps.setString(2, l.getAction());
            ps.setString(3, l.getUtilisateur());
            ps.setTimestamp(4, l.getDateAction() != null ? Timestamp.valueOf(l.getDateAction()) : null);
            ps.setString(5, l.getDescription());
            ps.setString(6, l.getAdresseIP());

            ps.setTimestamp(7, Timestamp.valueOf(l.getDateDerniereModification()));
            ps.setString(8, l.getModifierPar());

            ps.setLong(9, l.getIdLog());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(log l) {
        if (l != null && l.getIdLog() != null) {
            deleteById(l.getIdLog());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Log WHERE idLog = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<log> findByUtilisateur(String utilisateur) throws SQLException {
        List<log> logList = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE utilisateur = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logList.add(RowMappers.mapLog(rs));
                }
            }
        }
        return logList;
    }

    @Override
    public List<log> findByAction(String action) throws SQLException {
        List<log> logList = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE action = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, action);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logList.add(RowMappers.mapLog(rs));
                }
            }
        }
        return logList;
    }

    @Override
    public List<log> findByDateRange(LocalDateTime debut, LocalDateTime fin) throws SQLException {
        List<log> logList = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE dateAction BETWEEN ? AND ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logList.add(RowMappers.mapLog(rs));
                }
            }
        }
        return logList;
    }
}
