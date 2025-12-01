package ma.TeethCare.mvc.controllers.medecin;

import ma.TeethCare.mvc.dto.medecin.MedecinDTO;
import java.util.List;

public interface MedecinController {
    void create(MedecinDTO medecin) throws Exception;
    MedecinDTO getById(Long id) throws Exception;
    List<MedecinDTO> getAll() throws Exception;
    void update(Long id, MedecinDTO medecin) throws Exception;
    void delete(Long id) throws Exception;
    List<MedecinDTO> findBySpecialite(String specialite) throws Exception;
}
