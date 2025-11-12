package ma.TeethCare.service.modules.userManager.api;

import java.util.List;

import ma.TeethCare.service.modules.userManager.dto.RoleDTO;

public interface RoleService {

	Long create(RoleDTO role);

	void update(Long idRole, RoleDTO role);

	RoleDTO getById(Long idRole);

	List<RoleDTO> listAll();
}


