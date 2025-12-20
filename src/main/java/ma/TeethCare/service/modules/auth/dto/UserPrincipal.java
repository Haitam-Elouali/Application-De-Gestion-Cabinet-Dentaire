package ma.TeethCare.service.modules.auth.dto;

import java.util.List;

public record UserPrincipal(
        Long id,
        String username,
        String email,
        List<String> roles,
        List<String> privileges) {
}
