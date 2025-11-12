package ma.TeethCare.service.modules.userManager.api;

import java.util.List;

import ma.TeethCare.service.modules.userManager.dto.UtilisateurDTO;

public interface UtilisateurService {

	Long create(UtilisateurDTO user);

	void update(Long idUtilisateur, UtilisateurDTO user);

	void assignRole(Long idUtilisateur, Long idRole);

	UtilisateurDTO getById(Long idUtilisateur);

	List<UtilisateurDTO> listByCabinet(Long idCabinet);
}


