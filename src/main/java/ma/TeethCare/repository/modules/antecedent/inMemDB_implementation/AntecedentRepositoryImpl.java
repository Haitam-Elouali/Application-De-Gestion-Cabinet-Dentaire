package ma.TeethCare.repository.modules.antecedent.inMemDB_implementation;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.entities.enums.niveauDeRisque;
import ma.TeethCare.repository.modules.antecedent.api.AntecedentRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AntecedentRepositoryImpl implements AntecedentRepository {

    private antecedent mapResultSetToEntity(ResultSet rs) throws SQLException {
        antecedent a = new antecedent();

        a.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            a.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            a.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        a.setCreePar(rs.getString("creePar"));
        a.setModifierPar(rs.getString("modifierPar"));

        a.setId_Antecedent(rs.getLong("id_Antecedent"));
        a.setNom(rs.getString("nom"));
        a.setCategorie(rs.getString("categorie"));

        String niveauRisqueStr = rs.getString("niveauRisque");
        if (niveauRisqueStr != null) {
            a.setNiveauRisque(niveauDeRisque.valueOf(niveauRisqueStr));
        }

        return a;
    }

    @Override
    public List<antecedent> findAll() {
        List<antecedent> antecedentList = new ArrayList<>();
        String sql = "SELECT * FROM Antecedent";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                antecedentList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return antecedentList;
    }

    @Override
    public antecedent findById(Long id) {
        String sql = "SELECT * FROM Antecedent WHERE idEntite = ?";

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
    public void create(antecedent a) {
        a.setDateCreation(LocalDate.now());
        if (a.getCreePar() == null) a.setCreePar("SYSTEM");

        String sql = "INSERT INTO Antecedent (dateCreation, creePar, id_Antecedent, nom, categorie, niveauRisque) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(a.getDateCreation()));
            ps.setString(2, a.getCreePar());

            ps.setLong(3, a.getId_Antecedent());
            ps.setString(4, a.getNom());
            ps.setString(5, a.getCategorie());
            ps.setString(6, a.getNiveauRisque().name());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    a.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(antecedent a) {
        a.setDateDerniereModification(LocalDateTime.now());
        if (a.getModifierPar() == null) a.setModifierPar("SYSTEM");

        String sql = "UPDATE Antecedent SET id_Antecedent = ?, nom = ?, categorie = ?, niveauRisque = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, a.getId_Antecedent());
            ps.setString(2, a.getNom());
            ps.setString(3, a.getCategorie());
            ps.setString(4, a.getNiveauRisque().name());

            ps.setTimestamp(5, Timestamp.valueOf(a.getDateDerniereModification()));
            ps.setString(6, a.getModifierPar());

            ps.setLong(7, a.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(antecedent a) {
        if (a != null && a.getIdEntite() != null) {
            deleteById(a.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Antecedent WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<antecedent> findByCategorie(String categorie) {
        List<antecedent> antecedentList = new ArrayList<>();
        String sql = "SELECT * FROM Antecedent WHERE categorie = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    antecedentList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return antecedentList;
    }
}