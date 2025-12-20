package ma.TeethCare.service.modules.users.api;

import ma.TeethCare.service.modules.users.dto.CreateSecretaireRequest;
import ma.TeethCare.service.modules.users.dto.UserAccountDto;
import java.util.List;
import java.util.Optional;

// Assuming we want strict DTO usage, removing BaseService
public interface secretaireService {

    UserAccountDto create(CreateSecretaireRequest request);

    UserAccountDto findById(Long id);

    List<UserAccountDto> findAll();

    // Do we update? `UserAccountDto` is likely read-only or we need
    // `UpdateUserProfileRequest`?
    // User requested removing old methods. I'll leave update out if no DTO for it,
    // or use UserAccountDto?
    // UserManagementService has `updateUserProfile`.
    // I will skip update here unless requested, or leave it mostly read-only +
    // create.

    boolean delete(Long id) throws Exception;

    boolean exists(Long id) throws Exception;

    long count() throws Exception;

    // Specific methods returning DTOs or keeping entities?
    // "remove old implementation methods that don't use dto"
    // findByNumCNSS returns entity currently. Should return DTO.
    Optional<UserAccountDto> findByNumCNSS(String numCNSS) throws Exception;

    Optional<UserAccountDto> findByCin(String cin) throws Exception;
}
