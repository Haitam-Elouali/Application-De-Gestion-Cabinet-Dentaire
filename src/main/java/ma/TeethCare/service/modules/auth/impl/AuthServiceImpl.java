package ma.TeethCare.service.modules.auth.impl;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.repository.api.UtilisateurRepository;
import ma.TeethCare.service.modules.auth.api.AuthService;
import ma.TeethCare.service.modules.auth.api.CredentialsValidator;
import ma.TeethCare.service.modules.auth.api.PasswordEncoder;
import ma.TeethCare.service.modules.auth.dto.AuthRequest;
import ma.TeethCare.service.modules.auth.dto.AuthResult;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsValidator credentialsValidator;

    public AuthServiceImpl(UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder,
            CredentialsValidator credentialsValidator) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
    }

    @Override
    public AuthResult authenticate(AuthRequest request) {
        try {
            // 1. Validate credentials
            credentialsValidator.validate(request);

            // 2. Find user
            return utilisateurRepository.findByLogin(request.getLogin())
                    .map(user -> {
                        // 3. Check password
                        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                            String token = UUID.randomUUID().toString(); // Placeholder token
                            UserPrincipal principal = mapToPrincipal(user);
                            return AuthResult.success(token, principal);
                        } else {
                            return AuthResult.failure("Invalid password");
                        }
                    })
                    .orElseGet(() -> AuthResult.failure("User not found"));

        } catch (IllegalArgumentException e) {
            return AuthResult.failure(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return AuthResult.failure("Internal login error");
        }
    }

    @Override
    public UserPrincipal loadUserPrincipalByLogin(String login) {
        return utilisateurRepository.findByLogin(login)
                .map(this::mapToPrincipal)
                .orElse(null);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        utilisateur user = utilisateurRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password does not match");
        }

        credentialsValidator.validateNewPassword(newPassword);

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        utilisateurRepository.update(user);
    }

    private UserPrincipal mapToPrincipal(utilisateur user) {
        List<String> roles = new ArrayList<>();
        if (user.getRoles() != null) {
            roles = user.getRoles().stream()
                    .map(ma.TeethCare.entities.role.role::getLibelle)
                    .collect(Collectors.toList());
        }

        // Privileges can be mapped from roles or loaded separately
        List<String> privileges = new ArrayList<>();

        return new UserPrincipal(
                user.getIdEntite(), // Use idEntite or id (parent/child)
                user.getUsername(),
                user.getEmail(),
                roles,
                privileges);
    }
}
