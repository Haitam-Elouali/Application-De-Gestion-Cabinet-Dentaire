package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.repository.common.GenericJdbcRepository;
import ma.TeethCare.repository.api.PatientRepository;

import java.util.List;

/**
 * Implémentation JDBC MySQL du repository Patient avec réflexion Java.
 * Utilise GenericJdbcRepository pour automatiser les opérations CRUD.
 * 
 * - Charge le driver MySQL via Class.forName (réflexion)
 * - Mappe automatiquement les attributs Java aux colonnes SQL via réflexion
 * - Gère les connexions et les PreparedStatements
 */
@Slf4j
public class PatientRepositoryImpl extends GenericJdbcRepository<Patient> implements PatientRepository {

    // Configuration de connexion - à adapter selon votre environnement
    private static final String TABLE_NAME = "PATIENT";

    /**
     * Constructeur initialisant le GenericJdbcRepository
     */
    public PatientRepositoryImpl() {
        super(Patient.class, TABLE_NAME);
        log.info("✓ PatientRepositoryImpl initialisé (MySQL JDBC avec réflexion)");
    }

    @Override
    public List<Patient> findAll() {
        log.info("Recherche de tous les patients");
        String sql = buildSelectQuery(null);
        return executeQuery(sql);
    }

    @Override
    public Patient findById(Long id) {
        log.info("Recherche du patient avec l'ID: {}", id);
        String sql = buildSelectQuery("id = ?");
        return executeSingleQuery(sql, id);
    }

    @Override
    public void create(Patient patient) {
        log.info("Création d'un nouveau patient: {}", patient.getNom());
        String sql = buildInsertQuery(List.of("nom", "prenom", "email", "telephone", "dateNaissance", "sexe", "assurance", "dateCreation"));
        
        Long generatedId = executeInsertAndGetId(sql,
                patient.getNom(),
                patient.getPrenom(),
                patient.getEmail(),
                patient.getTelephone(),
                patient.getDateNaissance(),
                patient.getSexe(),
                patient.getAssurance(),
                patient.getDateCreation()
        );
        
        if (generatedId != null) {
            patient.setId(generatedId);
            log.info("✓ Patient créé avec l'ID: {}", generatedId);
        } else {
            log.warn("⚠ Patient créé mais ID non récupéré");
        }
    }

    @Override
    public void update(Patient patient) {
        log.info("Mise à jour du patient ID: {}", patient.getId());
        String sql = buildUpdateQuery(
                List.of("nom", "prenom", "email", "telephone", "dateNaissance", "sexe", "assurance", "dateModification"),
                "id = ?"
        );
        
        executeUpdate(sql,
                patient.getNom(),
                patient.getPrenom(),
                patient.getEmail(),
                patient.getTelephone(),
                patient.getDateNaissance(),
                patient.getSexe(),
                patient.getAssurance(),
                patient.getDateModification(),
                patient.getId()
        );
        log.info("✓ Patient ID {} mise à jour", patient.getId());
    }

    @Override
    public void delete(Patient patient) {
        if (patient != null && patient.getId() != null) {
            deleteById(patient.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Suppression du patient ID: {}", id);
        String sql = buildDeleteQuery("id = ?");
        int rowsAffected = executeUpdate(sql, id);
        log.info("✓ {} patient(s) supprimé(s)", rowsAffected);
    }

    @Override
    public List<Patient> findByNom(String nom) {
        log.info("Recherche des patients avec le nom: {}", nom);
        String sql = buildSelectQuery("nom LIKE ?");
        return executeQuery(sql, "%" + nom + "%");
    }

    @Override
    public List<Patient> findByPrenom(String prenom) {
        log.info("Recherche des patients avec le prénom: {}", prenom);
        String sql = buildSelectQuery("prenom LIKE ?");
        return executeQuery(sql, "%" + prenom + "%");
    }

    @Override
    public List<Patient> findByEmail(String email) {
        log.info("Recherche du patient avec l'email: {}", email);
        String sql = buildSelectQuery("email = ?");
        return executeQuery(sql, email);
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}


