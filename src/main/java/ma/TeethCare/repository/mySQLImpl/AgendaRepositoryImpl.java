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
        String sql = "SELECT * FROM Agenda";

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
        String sql = "SELECT * FROM Agenda WHERE idAgenda = ?";

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
        a.setDateCreation(LocalDate.now());
        if (a.getCreePar() == null)
            a.setCreePar("SYSTEM");

        String sql = "INSERT INTO Agenda (dateCreation, creePar, medecinId, mois, joursDisponible, dateDebut, dateFin) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(a.getDateCreation()));
            ps.setString(2, a.getCreePar());


            ps.setLong(3, a.getMedecinId());
            ps.setString(4, a.getMois() != null ? a.getMois().name() : null);

            String joursStr = null;
            if (a.getJoursDisponible() != null && !a.getJoursDisponible().isEmpty()) {
                joursStr = a.getJoursDisponible().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","));
            }
            ps.setString(5, joursStr);

            ps.setDate(6, a.getDateDebut() != null ? Date.valueOf(a.getDateDebut()) : null);
            ps.setDate(7, a.getDateFin() != null ? Date.valueOf(a.getDateFin()) : null);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    a.setIdAgenda(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(agenda a) {
        a.setDateDerniereModification(LocalDateTime.now());
        if (a.getModifierPar() == null)
            a.setModifierPar("SYSTEM");

        String sql = "UPDATE Agenda SET medecinId = ?, mois = ?, joursDisponible = ?, dateDebut = ?, dateFin = ?, dateDerniereModification = ?, modifierPar = ? WHERE idAgenda = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setLong(1, a.getMedecinId());
            ps.setString(2, a.getMois() != null ? a.getMois().name() : null);

            String joursStr = null;
            if (a.getJoursDisponible() != null && !a.getJoursDisponible().isEmpty()) {
                joursStr = a.getJoursDisponible().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","));
            }
            ps.setString(3, joursStr);

            ps.setDate(4, a.getDateDebut() != null ? Date.valueOf(a.getDateDebut()) : null);
            ps.setDate(5, a.getDateFin() != null ? Date.valueOf(a.getDateFin()) : null);

            ps.setTimestamp(6, Timestamp.valueOf(a.getDateDerniereModification()));
            ps.setString(7, a.getModifierPar());

            ps.setLong(8, a.getIdAgenda());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(agenda a) {
        if (a != null && a.getIdAgenda() != null) {
            deleteById(a.getIdAgenda());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Agenda WHERE idAgenda = ?";
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
        String sql = "SELECT * FROM Agenda WHERE mois = ?";

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
