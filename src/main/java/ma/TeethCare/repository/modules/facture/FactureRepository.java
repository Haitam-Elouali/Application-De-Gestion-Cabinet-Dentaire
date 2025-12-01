package ma.TeethCare.repository.modules.facture;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

/**
 * Repository pour l'entité Facture
 * Gère les opérations CRUD sur les factures
 */
public interface FactureRepository extends BaseRepository<facture, Long> {

    /**
     * Trouve les factures d'un patient
     */
    List<facture> findByPatientId(Long patientId) throws Exception;

    /**
     * Trouve les factures d'une consultation
     */
    List<facture> findByConsultationId(Long consultationId) throws Exception;

    /**
     * Trouve les factures par statut
     */
    List<facture> findByStatut(String statut) throws Exception;

    /**
     * Trouve les factures non payées
     */
    List<facture> findUnpaidInvoices() throws Exception;

    /**
     * Calcule le total des factures d'un patient
     */
    Double getTotalByPatientId(Long patientId) throws Exception;
}
