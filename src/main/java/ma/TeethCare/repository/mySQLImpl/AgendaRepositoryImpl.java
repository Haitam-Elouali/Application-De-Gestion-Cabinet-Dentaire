package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.entities.enums.Jour;
import ma.TeethCare.entities.enums.Mois;
import ma.TeethCare.repository.api.AgendaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AgendaRepositoryImpl implements AgendaRepository {

    private static final List<agenda> db = new ArrayList<>();
    private static Long idCounter = 0L;

    @Override
    public List<agenda> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public agenda findById(Long id) {
        return db.stream()
                .filter(a -> Objects.equals(a.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(agenda a) {

        synchronized (AgendaRepositoryImpl.class) {
            idCounter++;
            a.setIdEntite(idCounter);
        }

        a.setDateCreation(LocalDate.now());
        if (a.getCreePar() == null) a.setCreePar("FILE_SYSTEM");

        db.add(a);
    }

    @Override
    public void update(agenda updatedAgenda) {
        agenda existingAgenda = findById(updatedAgenda.getIdEntite());
        if (existingAgenda != null) {
            existingAgenda.setMois(updatedAgenda.getMois());
            existingAgenda.setJoursonDisponible(updatedAgenda.getJoursonDisponible());
            existingAgenda.setDateDerniereModification(LocalDateTime.now());
            if (existingAgenda.getModifierPar() == null) existingAgenda.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(agenda a) {
        if (a != null) {
            deleteById(a.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(a -> Objects.equals(a.getIdEntite(), id));
    }

    @Override
    public Optional<agenda> findByMois(Mois mois) {
        return db.stream()
                .filter(a -> a.getMois() == mois)
                .findFirst();
    }

    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}


