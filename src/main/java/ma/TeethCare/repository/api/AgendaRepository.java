package ma.TeethCare.repository.api;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.entities.enums.Mois;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface AgendaRepository extends CrudRepository<agenda, Long> {

    Optional<agenda> findByMois(Mois mois);

}

