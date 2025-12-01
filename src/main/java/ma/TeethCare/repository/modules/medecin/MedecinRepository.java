package ma.TeethCare.repository.modules.medecin;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends BaseRepository<medecin, Long> {
    List<medecin> findBySpecialite(String specialite) throws Exception;
    Optional<medecin> findByNumeroOrdre(String numeroOrdre) throws Exception;
    List<medecin> findAll() throws Exception;
}
