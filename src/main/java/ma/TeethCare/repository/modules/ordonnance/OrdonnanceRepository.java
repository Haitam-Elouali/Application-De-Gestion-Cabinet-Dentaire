package ma.TeethCare.repository.modules.ordonnance;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

/**
 * Repository pour l'entité Ordonnance
 * Gère les opérations CRUD sur les ordonnances
 */
public interface OrdonnanceRepository extends BaseRepository<ordonnance, Long> {

    /**
     * Trouve les ordonnances d'un patient
     */
    List<ordonnance> findByPatientId(Long patientId) throws Exception;

    /**
     * Trouve les ordonnances d'un médecin
     */
    List<ordonnance> findByMedecinId(Long medecinId) throws Exception;

    /**
     * Trouve les ordonnances d'une consultation
     */
    List<ordonnance> findByConsultationId(Long consultationId) throws Exception;

    /**
     * Trouve les ordonnances actives (non expirées)
     */
    List<ordonnance> findActiveOrdonnances() throws Exception;
}
