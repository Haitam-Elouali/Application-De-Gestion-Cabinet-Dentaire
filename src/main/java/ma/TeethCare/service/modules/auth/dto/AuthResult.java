package ma.TeethCare.service.modules.auth.dto;

public record AuthResult(boolean success, String token, UserPrincipal principal, String errorMessage) {

    public static AuthResult success(String token, UserPrincipal principal) {
        return new AuthResult(true, token, principal, null);
    }

    public static AuthResult failure(String errorMessage) {
        return new AuthResult(false, null, null, errorMessage);
    }
}
