package ma.TeethCare.service.modules.auth.dto;

public record PasswordChangeRequest(
        String oldPassword,
        String newPassword,
        String confirmNewPassword) {
}
