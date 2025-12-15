package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.common.enums.Jour;
import ma.TeethCare.common.enums.Mois;
import ma.TeethCare.repository.api.AgendaRepository;
import ma.TeethCare.repository.common.RowMappers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AgendaRepositoryImpl implements AgendaRepository {

    @Override
    public List<agenda> findAll() {
        List<agenda> agendaList = new ArrayList<>();
        String sql = "SELECT * FROM agendamensuel";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                agendaList.add(RowMappers.mapAgenda(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendaList;
    }

    @Override
    public agenda findById(Long id) {
        String sql = "SELECT * FROM agendamensuel WHERE id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapAgenda(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(agenda a) {
        // Schema: id, mois, annee, joursNonDisponibles, medecin_id
        String sql = "INSERT INTO agendamensuel (mois, annee, joursNonDisponibles, medecin_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getMois() != null ? a.getMois().name() : null);
            if (a.getDateCreation() == null) {
                a.setDateCreation(LocalDate.now());
            }
            ps.setInt(2, a.getDateCreation().getYear());

            String joursStr = null;
            if (a.getJoursNonDisponibles() != null && !a.getJoursNonDisponibles().isEmpty()) {
                joursStr = a.getJoursNonDisponibles().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","));
            }
            ps.setString(3, joursStr);
            ps.setLong(4, a.getMedecinId());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    a.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(agenda a) {
        String sql = "UPDATE agendamensuel SET mois = ?, annee = ?, joursNonDisponibles = ?, medecin_id = ? WHERE id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getMois() != null ? a.getMois().name() : null);
            if (a.getDateCreation() == null) {
                a.setDateCreation(LocalDate.now());
            }
            ps.setInt(2, a.getDateCreation().getYear());

            String joursStr = null;
            if (a.getJoursNonDisponibles() != null && !a.getJoursNonDisponibles().isEmpty()) {
                joursStr = a.getJoursNonDisponibles().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","));
            }
            ps.setString(3, joursStr);
            ps.setLong(4, a.getMedecinId());

            ps.setLong(5, a.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(agenda a) {
        if (a != null && a.getId() != null) {
            deleteById(a.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM agendamensuel WHERE id = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<agenda> findByMois(Mois mois) {
        String sql = "SELECT * FROM agendamensuel WHERE mois = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mois.name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapAgenda(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
