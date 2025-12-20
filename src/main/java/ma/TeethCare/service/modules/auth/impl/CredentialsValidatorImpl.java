package ma.TeethCare.service.modules.auth.impl;

import ma.TeethCare.service.modules.auth.api.CredentialsValidator;
import ma.TeethCare.service.modules.auth.dto.AuthRequest;

public class CredentialsValidatorImpl implements CredentialsValidator {

    @Override
    public void validate(AuthRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("AuthRequest cannot be null");
        }
        if (request.getLogin() == null || request.getLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be empty");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
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
