package ma.TeethCare.service.modules.staff.api;

import java.util.List;

import ma.TeethCare.service.modules.staff.dto.StaffDTO;

public interface StaffService {

	Long create(StaffDTO staff);

	void update(Long idStaff, StaffDTO staff);

	List<StaffDTO> listByCabinet(Long idCabinet);
}


