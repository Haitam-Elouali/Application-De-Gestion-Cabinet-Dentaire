package ma.TeethCare.service.modules.auth.impl;

import ma.TeethCare.service.modules.auth.api.CredentialsValidator;
import ma.TeethCare.service.modules.auth.dto.AuthRequest;

public class CredentialsValidatorImpl implements CredentialsValidator {

    @Override
    public void validate(AuthRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("AuthRequest cannot be null");
        }
        if (request.login() == null || request.login().trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be empty");
        }
        if (request.password() == null || request.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }

    @Override
    public void validateNewPassword(String newPassword) {
        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        // Add more complexity rules if needed (e.g., regex for special chars)
    }
}
