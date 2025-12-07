package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.entities.enums.niveauDeRisque;
import ma.TeethCare.repository.api.AntecedentRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AntecedentRepositoryImpl implements AntecedentRepository {

    @Override
    public List<antecedent> findAll() throws SQLException {
        List<antecedent> antecedentList = new ArrayList<>();
        String sql = "SELECT * FROM Antecedent";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                antecedentList.add(RowMappers.mapAntecedent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return antecedentList;
    }

    @Override
    public antecedent findById(Long id) {
        String sql = "SELECT * FROM Antecedent WHERE idAntecedent = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapAntecedent(rs);
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
        if (a.getCreePar() == null)
            a.setCreePar("SYSTEM");

        String sql = "INSERT INTO Antecedent (dateCreation, creePar, idAntecedent, dossierMedicaleId, nom, categorie, niveauRisque) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(a.getDateCreation()));
            ps.setString(2, a.getCreePar());

            ps.setLong(3, a.getIdAntecedent());
            ps.setLong(4, a.getDossierMedicaleId());
            ps.setString(5, a.getNom());
            ps.setString(6, a.getCategorie());
            ps.setString(7, a.getNiveauRisque().name());

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
        if (a.getModifierPar() == null)
            a.setModifierPar("SYSTEM");

        String sql = "UPDATE Antecedent SET idAntecedent = ?, dossierMedicaleId = ?, nom = ?, categorie = ?, niveauRisque = ?, dateDerniereModification = ?, modifierPar = ? WHERE idAntecedent = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, a.getIdAntecedent());
            ps.setLong(2, a.getDossierMedicaleId());
            ps.setString(3, a.getNom());
            ps.setString(4, a.getCategorie());
            ps.setString(5, a.getNiveauRisque().name());

            ps.setTimestamp(6, Timestamp.valueOf(a.getDateDerniereModification()));
            ps.setString(7, a.getModifierPar());

            ps.setLong(8, a.getIdAntecedent());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(antecedent a) {
        if (a != null && a.getIdAntecedent() != null) {
            deleteById(a.getIdAntecedent());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Antecedent WHERE idAntecedent = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
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

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    antecedentList.add(RowMappers.mapAntecedent(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return antecedentList;
    }
}
