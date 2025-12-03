package ma.TeethCare.repository.modules.medicament.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.repository.modules.medicament.api.MedicamentRepository;
import ma.TeethCare.repository.common.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicamentRepositoryImpl implements MedicamentRepository {

    private medicaments mapResultSetToEntity(ResultSet rs) throws SQLException {
        medicaments m = new medicaments();

        m.setIdEntite(rs.getLong("idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null) {
            m.setDateCreation(dateCreationSql.toLocalDate());
        }
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null) {
            m.setDateDerniereModification(dateModifSql.toLocalDateTime());
        }
        m.setCreePar(rs.getString("creePar"));
        m.setModifierPar(rs.getString("modifierPar"));

        m.setIdMed(rs.getLong("idMed"));
        m.setNom(rs.getString("nom"));
        m.setLaboratoire(rs.getString("laboratoire"));
        m.setType(rs.getString("type"));
        m.setRemboursable(rs.getBoolean("remboursable"));
        m.setPrixUnitaire(rs.getDouble("prixUnitaire"));
        m.setDescription(rs.getString("description"));

        return m;
    }

    @Override
    public List<medicaments> findAll() {
        List<medicaments> medicamentsList = new ArrayList<>();
        String sql = "SELECT * FROM Medicaments";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medicamentsList.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentsList;
    }

    @Override
    public medicaments findById(Long id) {
        String sql = "SELECT * FROM Medicaments WHERE idEntite = ?";

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
    public void create(medicaments m) {
        m.setDateCreation(LocalDate.now());
        if (m.getCreePar() == null) m.setCreePar("SYSTEM");

        String sql = "INSERT INTO Medicaments (dateCreation, creePar, idMed, nom, laboratoire, type, remboursable, prixUnitaire, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(m.getDateCreation()));
            ps.setString(2, m.getCreePar());

            ps.setLong(3, m.getIdMed());
            ps.setString(4, m.getNom());
            ps.setString(5, m.getLaboratoire());
            ps.setString(6, m.getType());
            ps.setBoolean(7, m.isRemboursable());
            ps.setDouble(8, m.getPrixUnitaire());
            ps.setString(9, m.getDescription());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    m.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(medicaments m) {
        m.setDateDerniereModification(LocalDateTime.now());
        if (m.getModifierPar() == null) m.setModifierPar("SYSTEM");

        String sql = "UPDATE Medicaments SET idMed = ?, nom = ?, laboratoire = ?, type = ?, remboursable = ?, prixUnitaire = ?, description = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, m.getIdMed());
            ps.setString(2, m.getNom());
            ps.setString(3, m.getLaboratoire());
            ps.setString(4, m.getType());
            ps.setBoolean(5, m.isRemboursable());
            ps.setDouble(6, m.getPrixUnitaire());
            ps.setString(7, m.getDescription());

            ps.setTimestamp(8, Timestamp.valueOf(m.getDateDerniereModification()));
            ps.setString(9, m.getModifierPar());

            ps.setLong(10, m.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(medicaments m) {
        if (m != null && m.getIdEntite() != null) {
            deleteById(m.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Medicaments WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<medicaments> findByIdMed(Long idMed) {
        String sql = "SELECT * FROM Medicaments WHERE idMed = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idMed);
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
    public Optional<medicaments> findByNom(String nom) {
        String sql = "SELECT * FROM Medicaments WHERE nom = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nom);
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

