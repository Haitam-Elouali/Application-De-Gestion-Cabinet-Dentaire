package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.repository.api.NotificationRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotificationRepositoryImpl implements NotificationRepository {
    public List<notification> findAll() throws SQLException {
        List<notification> notificationList = new ArrayList<>();
        String sql = "SELECT * FROM Notification";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                notificationList.add(RowMappers.mapNotification(rs));
            }
        }
        return notificationList;
    }

    @Override
    public notification findById(Long id) {
        String sql = "SELECT * FROM Notification WHERE idNotif = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapNotification(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(notification n) {
        n.setDateCreation(LocalDate.now());
        if (n.getCreePar() == null)
            n.setCreePar("SYSTEM");

        String sql = "INSERT INTO Notification (dateCreation, creePar, idNotif, titre, message, dateEnvoi, type, lue) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(n.getDateCreation()));
            ps.setString(2, n.getCreePar());

            ps.setLong(3, n.getIdNotif());
            ps.setString(4, n.getTitre());
            ps.setString(5, n.getMessage());
            ps.setTimestamp(6, n.getDateEnvoi() != null ? Timestamp.valueOf(n.getDateEnvoi()) : null);
            ps.setString(7, n.getType());
            ps.setBoolean(8, n.isLue());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    n.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(notification n) {
        n.setDateDerniereModification(LocalDateTime.now());
        if (n.getModifierPar() == null)
            n.setModifierPar("SYSTEM");

        String sql = "UPDATE Notification SET idNotif = ?, titre = ?, message = ?, dateEnvoi = ?, type = ?, lue = ?, dateDerniereModification = ?, modifierPar = ? WHERE idNotif = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, n.getIdNotif());
            ps.setString(2, n.getTitre());
            ps.setString(3, n.getMessage());
            ps.setTimestamp(4, n.getDateEnvoi() != null ? Timestamp.valueOf(n.getDateEnvoi()) : null);
            ps.setString(5, n.getType());
            ps.setBoolean(6, n.isLue());

            ps.setTimestamp(7, Timestamp.valueOf(n.getDateDerniereModification()));
            ps.setString(8, n.getModifierPar());

            ps.setLong(9, n.getIdNotif());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(notification n) {
        if (n != null && n.getIdNotif() != null) {
            deleteById(n.getIdNotif());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Notification WHERE idNotif = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<notification> findByNonLues() throws SQLException {
        List<notification> notificationList = new ArrayList<>();
        String sql = "SELECT * FROM Notification WHERE lue = false";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                notificationList.add(RowMappers.mapNotification(rs));
            }
        }
        return notificationList;
    }

    @Override
    public List<notification> findByType(String type) throws SQLException {
        List<notification> notificationList = new ArrayList<>();
        String sql = "SELECT * FROM Notification WHERE type = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    notificationList.add(RowMappers.mapNotification(rs));
                }
            }
        }
        return notificationList;
    }
}
