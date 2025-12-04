package ma.TeethCare.repository.modules.cabinetMedicale.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;


import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.api.CabinetMedicaleRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CabinetMedicaleRepositoryImpl implements CabinetMedicaleRepository {

    private cabinetMedicale mapResultSetToEntity(ResultSet rs) throws SQLException {
        cabinetMedicale c = new cabinetMedicale();

        c.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            c.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            c.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        c.setCreePar(rs.getString("creePar"));
        c.setModifierPar(rs.getString("modifierPar"));

        c.setIdUser(rs.getLong("idUser"));
        c.setNom(rs.getString("nom"));
        c.setEmail(rs.getString("email"));
        c.setLogo(rs.getString("logo"));
        c.setCin(rs.getString("cin"));
        c.setTel1(rs.getString("tel1"));
        c.setTel2(rs.getString("tel2"));
        c.setSiteWeb(rs.getString("siteWeb"));
        c.setInstagram(rs.getString("instagram"));
        c.setFacebook(rs.getString("facebook"));
        c.setDescription(rs.getString("description"));

        return c;
    }

    @Override
    public List<cabinetMedicale> findAll() {
        List<cabinetMedicale> cabinetList = new ArrayList<>();
        String sql = "SELECT * FROM CabinetMedicale";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cabinetList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cabinetList;
    }

    @Override
    public cabinetMedicale findById(Long id) {
        String sql = "SELECT * FROM CabinetMedicale WHERE idEntite = ?";

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
    public void create(cabinetMedicale c) {
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null) c.setCreePar("SYSTEM");

        String sql = "INSERT INTO CabinetMedicale (dateCreation, creePar, idUser, nom, email, logo, cin, tel1, tel2, siteWeb, instagram, facebook, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateCreation()));
            ps.setString(2, c.getCreePar());

            ps.setLong(3, c.getIdUser());
            ps.setString(4, c.getNom());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getLogo());
            ps.setString(7, c.getCin());
            ps.setString(8, c.getTel1());
            ps.setString(9, c.getTel2());
            ps.setString(10, c.getSiteWeb());
            ps.setString(11, c.getInstagram());
            ps.setString(12, c.getFacebook());
            ps.setString(13, c.getDescription());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    c.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(cabinetMedicale c) {
        c.setDateDerniereModification(LocalDateTime.now());
        if (c.getModifierPar() == null) c.setModifierPar("SYSTEM");

        String sql = "UPDATE CabinetMedicale SET idUser = ?, nom = ?, email = ?, logo = ?, cin = ?, tel1 = ?, tel2 = ?, siteWeb = ?, instagram = ?, facebook = ?, description = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, c.getIdUser());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getLogo());
            ps.setString(5, c.getCin());
            ps.setString(6, c.getTel1());
            ps.setString(7, c.getTel2());
            ps.setString(8, c.getSiteWeb());
            ps.setString(9, c.getInstagram());
            ps.setString(10, c.getFacebook());
            ps.setString(11, c.getDescription());

            ps.setTimestamp(12, Timestamp.valueOf(c.getDateDerniereModification()));
            ps.setString(13, c.getModifierPar());

            ps.setLong(14, c.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(cabinetMedicale c) {
        if (c != null && c.getIdEntite() != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM CabinetMedicale WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<cabinetMedicale> findByEmail(String email) {
        String sql = "SELECT * FROM CabinetMedicale WHERE email = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
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
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}


