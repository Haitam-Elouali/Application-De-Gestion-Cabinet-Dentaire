package ma.TeethCare.service.modules.dossierMedical.api;

import ma.TeethCare.mvc.dto.actes.ActesDTO;
import ma.TeethCare.service.common.BaseService;

import java.util.List;

public interface actesService extends BaseService<ActesDTO, Long> {
    List<ActesDTO> findByCategorie(String categorie) throws Exception;
}
