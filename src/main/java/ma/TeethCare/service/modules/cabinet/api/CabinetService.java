package ma.TeethCare.service.modules.cabinet.api;

import ma.TeethCare.service.modules.cabinet.dto.CabinetDTO;

public interface CabinetService {

	Long create(CabinetDTO cabinet);

	void update(Long idCabinet, CabinetDTO cabinet);

	CabinetDTO getById(Long idCabinet);
}


