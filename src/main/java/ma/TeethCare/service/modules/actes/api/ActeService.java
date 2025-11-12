package ma.TeethCare.service.modules.actes.api;

import java.util.List;

import ma.TeethCare.service.modules.actes.dto.ActeDTO;

public interface ActeService {

	Long create(ActeDTO acte);

	void update(Long idActe, ActeDTO acte);

	ActeDTO getById(Long idActe);

	List<ActeDTO> listAll();

	double getPrixDeBase(Long idActe);
}


