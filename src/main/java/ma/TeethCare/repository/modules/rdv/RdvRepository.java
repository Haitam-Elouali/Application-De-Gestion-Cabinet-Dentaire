package ma.TeethCare.repository.modules.rdv;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.repository.common.BaseRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour l'entité RDV
 * Gère les opérations CRUD sur les rendez-vous
 */
public interface RdvRepository extends BaseRepository<rdv, Long> {

    /**
     * Trouve les RDV d'un patient
     */
    List<rdv> findByPatientId(Long patientId) throws Exception;

    /**
     * Trouve les RDV d'un médecin
     */
    List<rdv> findByMedecinId(Long medecinId) throws Exception;

    /**
     * Trouve les RDV à une date donnée
     */
    List<rdv> findByDate(LocalDate date) throws Exception;

    /**
     * Trouve les RDV d'un patient à une date
     */
    List<rdv> findByPatientIdAndDate(Long patientId, LocalDate date) throws Exception;

    /**
     * Trouve les RDV par statut
     */
    List<rdv> findByStatut(String statut) throws Exception;
}
