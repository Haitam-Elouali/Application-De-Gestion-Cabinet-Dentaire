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
    @Override
    public List<notification> findAll() throws SQLException {
        List<notification> notificationList = new ArrayList<>();
        String sql = "SELECT t.id as idNotif, t.id as idEntite, t.titre, t.message, t.date, t.time, t.type, t.statut, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Notification t " + 
                     "JOIN entite e ON t.id = e.id";

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
        String sql = "SELECT t.id as idNotif, t.id as idEntite, t.titre, t.message, t.date, t.time, t.type, t.statut, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Notification t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.id = ?";

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
        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtNotif = null;
        ResultSet generatedKeys = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into Entite
            String sqlEntite = "INSERT INTO entite (dateCreation, creePar, dateDerniereModification) VALUES (?, ?, ?)";
            stmtEntite = conn.prepareStatement(sqlEntite, Statement.RETURN_GENERATED_KEYS);
            stmtEntite.setObject(1, n.getDateCreation() != null ? n.getDateCreation() : java.time.LocalDate.now());
            stmtEntite.setString(2, n.getCreePar() != null ? n.getCreePar() : "SYSTEM");
            stmtEntite.setObject(3, n.getDateDerniereModification());
            stmtEntite.executeUpdate();

            Long id = null;
            generatedKeys = stmtEntite.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                n.setIdEntite(id);
                n.setIdNotif(id);
            } else {
                throw new SQLException("Creating Entite for Notification failed, no ID obtained.");
            }

            // 2. Insert into Notification
            String sqlNotif = "INSERT INTO Notification (id, titre, message, date, time, type, statut) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmtNotif = conn.prepareStatement(sqlNotif);
            stmtNotif.setLong(1, id);
            stmtNotif.setString(2, n.getTitre());
            stmtNotif.setString(3, n.getMessage());
            
            if (n.getDateEnvoi() != null) {
                stmtNotif.setDate(4, Date.valueOf(n.getDateEnvoi().toLocalDate()));
                stmtNotif.setTime(5, Time.valueOf(n.getDateEnvoi().toLocalTime()));
            } else {
                stmtNotif.setDate(4, null);
                stmtNotif.setTime(5, null);
            }
            
            stmtNotif.setString(6, n.getType());
            stmtNotif.setString(7, n.isLue() ? "LUE" : "NON_LUE");

            stmtNotif.executeUpdate();

            conn.commit();
            System.out.println("✓ Notification créée avec id: " + id);

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de create() pour Notification: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (stmtEntite != null) stmtEntite.close();
                if (stmtNotif != null) stmtNotif.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(notification n) {
        n.setDateDerniereModification(LocalDateTime.now());
        if (n.getModifierPar() == null)
            n.setModifierPar("SYSTEM");

        Connection conn = null;
        PreparedStatement stmtEntite = null;
        PreparedStatement stmtNotif = null;

        try {
            conn = SessionFactory.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // Update Entite
            String sqlEntite = "UPDATE entite SET dateDerniereModification = ?, modifiePar = ? WHERE id = ?";
            stmtEntite = conn.prepareStatement(sqlEntite);
            stmtEntite.setTimestamp(1, Timestamp.valueOf(n.getDateDerniereModification()));
            stmtEntite.setString(2, n.getModifierPar());
            stmtEntite.setLong(3, n.getIdEntite());
            stmtEntite.executeUpdate();

            // Update Notification
            String sqlNotif = "UPDATE Notification SET titre = ?, message = ?, date = ?, time = ?, type = ?, statut = ? WHERE id = ?";
            stmtNotif = conn.prepareStatement(sqlNotif);
            stmtNotif.setString(1, n.getTitre());
            stmtNotif.setString(2, n.getMessage());
            
            if (n.getDateEnvoi() != null) {
                stmtNotif.setDate(3, Date.valueOf(n.getDateEnvoi().toLocalDate()));
                stmtNotif.setTime(4, Time.valueOf(n.getDateEnvoi().toLocalTime()));
            } else {
                stmtNotif.setDate(3, null);
                stmtNotif.setTime(4, null);
            }
            
            stmtNotif.setString(5, n.getType());
            stmtNotif.setString(6, n.isLue() ? "LUE" : "NON_LUE");
            stmtNotif.setLong(7, n.getIdNotif());

            stmtNotif.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
             if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (stmtEntite != null) stmtEntite.close();
                if (stmtNotif != null) stmtNotif.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        String sql = "DELETE FROM entite WHERE id = ?";
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
        String sql = "SELECT t.id as idNotif, t.id as idEntite, t.titre, t.message, t.date, t.time, t.type, t.statut, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Notification t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.statut = 'NON_LUE'";

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
        String sql = "SELECT t.id as idNotif, t.id as idEntite, t.titre, t.message, t.date, t.time, t.type, t.statut, " +
                     "e.dateCreation, e.creePar, e.dateDerniereModification, e.modifiePar " + 
                     "FROM Notification t " + 
                     "JOIN entite e ON t.id = e.id " + 
                     "WHERE t.type = ?";

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
