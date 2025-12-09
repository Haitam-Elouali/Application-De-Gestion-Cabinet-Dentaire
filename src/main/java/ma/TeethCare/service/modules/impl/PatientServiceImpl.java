package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.service.api.PatientService;
import java.util.List;
import java.util.Optional;

public class PatientServiceImpl implements PatientService {

    @Override
    public Patient create(Patient entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<Patient> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<Patient> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Patient update(Patient entity) throws Exception {
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
