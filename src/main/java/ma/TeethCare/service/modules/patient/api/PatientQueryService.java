package ma.TeethCare.service.modules.patient.api;

import ma.TeethCare.service.common.dto.PageRequestDTO;
import ma.TeethCare.service.common.dto.PageResponseDTO;
import ma.TeethCare.service.modules.patient.dto.PatientFilter;
import ma.TeethCare.service.modules.patient.dto.PatientView;

public interface PatientQueryService {

	PageResponseDTO<PatientView> search(PatientFilter filter, PageRequestDTO page);
}


