package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.api.StaffRepository;

import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffRepositoryImpl implements StaffRepository {

    @Override
    public List<staff> findAll() {
        List<staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM Staff";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                staffList.add(RowMappers.mapStaff(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    @Override
    public staff findById(Long id) {
        String sql = "SELECT * FROM Staff WHERE idEntite = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapStaff(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(staff s) {
        s.setDateCreation(LocalDate.now());
        if (s.getCreePar() == null)
            s.setCreePar("SYSTEM");

        String sql = "INSERT INTO Staff (dateCreation, creePar, idUser, nom, email, adresse, cin, tel, sexe, login, motDePasse, lastLoginDate, dateNaissance, salaire, prime, dateRecrutement, soldeConge) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(s.getDateCreation()));
            ps.setString(2, s.getCreePar());

            ps.setLong(3, s.getIdUser());
            ps.setString(4, s.getNom());
            ps.setString(5, s.getEmail());
            ps.setString(6, s.getAdresse());
            ps.setString(7, s.getCin());
            ps.setString(8, s.getTel());
            ps.setString(9, s.getSexe() != null ? s.getSexe().name() : null);
            ps.setString(10, s.getLogin());
            ps.setString(11, s.getMotDePasse());
            ps.setDate(12, s.getLastLoginDate() != null ? Date.valueOf(s.getLastLoginDate()) : null);
            ps.setDate(13, s.getDateNaissance() != null ? Date.valueOf(s.getDateNaissance()) : null);

            // Staff
            ps.setDouble(14, s.getSalaire());
            ps.setDouble(15, s.getPrime());
            ps.setDate(16, s.getDateRecrutement() != null ? Date.valueOf(s.getDateRecrutement()) : null);
            ps.setInt(17, s.getSoldeConge());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    s.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(staff s) {
        s.setDateDerniereModification(LocalDateTime.now());
        if (s.getModifierPar() == null)
            s.setModifierPar("SYSTEM");

        String sql = "UPDATE Staff SET idUser = ?, nom = ?, email = ?, adresse = ?, cin = ?, tel = ?, sexe = ?, login = ?, motDePasse = ?, lastLoginDate = ?, dateNaissance = ?, salaire = ?, prime = ?, dateRecrutement = ?, soldeConge = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, s.getIdUser());
            ps.setString(2, s.getNom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getAdresse());
            ps.setString(5, s.getCin());
            ps.setString(6, s.getTel());
            ps.setString(7, s.getSexe() != null ? s.getSexe().name() : null);
            ps.setString(8, s.getLogin());
            ps.setString(9, s.getMotDePasse());
            ps.setDate(10, s.getLastLoginDate() != null ? Date.valueOf(s.getLastLoginDate()) : null);
            ps.setDate(11, s.getDateNaissance() != null ? Date.valueOf(s.getDateNaissance()) : null);

            ps.setDouble(12, s.getSalaire());
            ps.setDouble(13, s.getPrime());
            ps.setDate(14, s.getDateRecrutement() != null ? Date.valueOf(s.getDateRecrutement()) : null);
            ps.setInt(15, s.getSoldeConge());

            ps.setTimestamp(16, Timestamp.valueOf(s.getDateDerniereModification()));
            ps.setString(17, s.getModifierPar());

            ps.setLong(18, s.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(staff s) {
        if (s != null && s.getIdEntite() != null) {
            deleteById(s.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Staff WHERE idEntite = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<staff> findByEmail(String email) {
        String sql = "SELECT * FROM Staff WHERE email = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapStaff(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<staff> findByCin(String cin) {
        String sql = "SELECT * FROM Staff WHERE cin = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapStaff(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
