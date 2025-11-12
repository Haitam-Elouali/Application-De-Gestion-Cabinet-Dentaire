package ma.TeethCare.repository.modules.consultation.api;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface ConsultationRepository extends CrudRepository<consultation, Long> {
    Optional<consultation> findByIdConsultation(Long idConsultation);
}