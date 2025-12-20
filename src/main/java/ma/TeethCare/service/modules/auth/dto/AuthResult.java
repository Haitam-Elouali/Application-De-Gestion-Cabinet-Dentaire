package ma.TeethCare.service.modules.auth.dto;

public class AuthResult {
    private boolean success;
    private String token;
    private UserPrincipal principal;
    private String errorMessage;

    public AuthResult() {
    }

    public AuthResult(boolean success, String token, UserPrincipal principal, String errorMessage) {
        this.success = success;
        this.token = token;
        this.principal = principal;
        this.errorMessage = errorMessage;
    }

    public static AuthResult success(String token, UserPrincipal principal) {
        return new AuthResult(true, token, principal, null);
    }

    public static AuthResult failure(String errorMessage) {
        return new AuthResult(false, null, null, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(UserPrincipal principal) {
        this.principal = principal;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
