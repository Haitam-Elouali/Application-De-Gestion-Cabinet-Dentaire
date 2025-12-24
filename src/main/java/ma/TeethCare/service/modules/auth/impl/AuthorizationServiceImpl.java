package ma.TeethCare.service.modules.auth.impl;

import ma.TeethCare.common.enums.RoleType;
import ma.TeethCare.service.modules.auth.api.AuthorizationService;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;

import java.util.Arrays;
import java.util.List;

public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public boolean hasRole(UserPrincipal principal, RoleType role) {
        if (principal == null || principal.roles() == null || role == null) {
            return false;
        }
        return principal.roles().contains(role.name());
    }

    @Override
    public boolean hasAnyRole(UserPrincipal principal, RoleType... roles) {
        if (principal == null || principal.roles() == null || roles == null) {
            return false;
        }
        List<String> userRoles = principal.roles();
        return Arrays.stream(roles)
                .anyMatch(r -> userRoles.contains(r.name()));
    }

    @Override
    public boolean hasPrivilege(UserPrincipal principal, String privilege) {
        if (principal == null || principal.privileges() == null || privilege == null) {
            return false;
        }
        return principal.privileges().contains(privilege);
    }

    @Override
    public void checkRole(UserPrincipal principal, RoleType role) {
        if (!hasRole(principal, role)) {
            throw new RuntimeException("Access Denied: Missing role " + (role != null ? role.name() : "NULL"));
            // In a real app, define and throw AccessDeniedException
        }
    }

    @Override
    public void checkAnyRole(UserPrincipal principal, RoleType... roles) {
        if (!hasAnyRole(principal, roles)) {
            throw new RuntimeException("Access Denied: Missing required roles");
        }
    }

    @Override
    public void checkPrivilege(UserPrincipal principal, String privilege) {
        if (!hasPrivilege(principal, privilege)) {
            throw new RuntimeException("Access Denied: Missing privilege " + privilege);
        }
    }
}
