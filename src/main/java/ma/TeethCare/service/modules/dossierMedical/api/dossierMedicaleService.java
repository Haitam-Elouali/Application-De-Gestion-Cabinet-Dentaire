package ma.TeethCare.service.modules.dossierMedical.api;

import ma.TeethCare.service.modules.dossierMedical.dto.DossierMedicalDto;

import java.util.List;

public interface dossierMedicaleService {

    DossierMedicalDto create(DossierMedicalDto dto);

    DossierMedicalDto update(Long id, DossierMedicalDto dto);

    DossierMedicalDto findById(Long id);

    List<DossierMedicalDto> findAll();

    boolean delete(Long id) throws Exception;

    boolean exists(Long id) throws Exception;

    long count() throws Exception;
}
