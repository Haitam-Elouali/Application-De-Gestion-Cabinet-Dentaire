package ma.TeethCare.repository.modules.certificat.api;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface CertificatRepository extends CrudRepository<certificat, Long> {
    Optional<certificat> findByIdCertif(Long idCertif);
}