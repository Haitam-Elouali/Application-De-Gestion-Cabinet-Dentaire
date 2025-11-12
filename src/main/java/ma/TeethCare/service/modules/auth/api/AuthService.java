package ma.TeethCare.service.modules.auth.api;

import ma.TeethCare.service.modules.auth.dto.AuthRequestDTO;
import ma.TeethCare.service.modules.auth.dto.AuthResultDTO;

public interface AuthService {

	AuthResultDTO login(AuthRequestDTO request);

	boolean validateToken(String token);

	void logout(Long idUtilisateur);
}


