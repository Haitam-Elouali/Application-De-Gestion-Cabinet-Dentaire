package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.service.modules.api.utilisateurService;
import java.util.List;
import java.util.Optional;

public class utilisateurServiceImpl implements utilisateurService {

    @Override
    public utilisateur create(utilisateur entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<utilisateur> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<utilisateur> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public utilisateur update(utilisateur entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        // TODO: Implement method
        return false;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        // TODO: Implement method
        return false;
    }

    @Override
    public long count() throws Exception {
        // TODO: Implement method
        return 0;
    }
}
