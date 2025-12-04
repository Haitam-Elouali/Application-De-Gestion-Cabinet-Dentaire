package ma.TeethCare.repository.mySQLImpl;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.repository.common.GenericJdbcRepository;
import ma.TeethCare.repository.api.MedecinRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Implémentation JDBC MySQL du repository Médecin avec réflexion Java.
 * Utilise GenericJdbcRepository pour automatiser les opérations CRUD.
 * 
 * Démontre:
 * - Mapping de types complexes (enums, dates)
 * - Requêtes avec plusieurs conditions WHERE
 * - Ordonnement des résultats
 * - Gestion des valeurs NULL
 */
@Slf4j
public class MedecinRepositoryImpl extends GenericJdbcRepository<medecin> implements MedecinRepository {

    private static final String TABLE_NAME = "MEDECIN";

    public MedecinRepositoryImpl() {
        super(medecin.class, TABLE_NAME);
        log.info("✓ MedecinRepositoryImpl initialisé (MySQL JDBC avec réflexion)");
    }

    @Override
    public List<medecin> findAll() {
        log.info("Recherche de tous les médecins");
        String sql = buildSelectQuery(null) + " ORDER BY nom ASC";
        return executeQuery(sql);
    }

    @Override
    public medecin findById(Long id) {
        log.info("Recherche du médecin ID: {}", id);
        String sql = buildSelectQuery("idEntite = ?");
        return executeSingleQuery(sql, id);
    }

    @Override
    public void create(medecin medecin) {
        log.info("Création d'un nouveau médecin: {}", medecin.getNom());
        String sql = buildInsertQuery(List.of(
                "idUser", "nom", "email", "adresse", "cin", "tel", "sexe",
                "login", "motDePasse", "dateNaissance", "salaire", "prime",
                "dateRecrutement", "soldeConge", "specialite", "dateCreation"
        ));

        Long generatedId = executeInsertAndGetId(sql,
                medecin.getIdUser(),
                medecin.getNom(),
                medecin.getEmail(),
                medecin.getAdresse(),
                medecin.getCin(),
                medecin.getTel(),
                medecin.getSexe() != null ? medecin.getSexe().name() : null,
                medecin.getLogin(),
                medecin.getMotDePasse(),
                medecin.getDateNaissance(),
                medecin.getSalaire(),
                medecin.getPrime(),
                medecin.getDateRecrutement(),
                medecin.getSoldeConge(),
                medecin.getSpecialite(),
                medecin.getDateCreation()
        );

        if (generatedId != null) {
            medecin.setIdEntite(generatedId);
            log.info("✓ Médecin créé avec l'ID: {}", generatedId);
        }
    }

    @Override
    public void update(medecin medecin) {
        log.info("Mise à jour du médecin ID: {}", medecin.getIdEntite());
        String sql = buildUpdateQuery(
                List.of("nom", "email", "adresse", "tel", "sexe", "salaire", "prime", "specialite", "dateDerniereModification"),
                "idEntite = ?"
        );

        executeUpdate(sql,
                medecin.getNom(),
                medecin.getEmail(),
                medecin.getAdresse(),
                medecin.getTel(),
                medecin.getSexe() != null ? medecin.getSexe().name() : null,
                medecin.getSalaire(),
                medecin.getPrime(),
                medecin.getSpecialite(),
                medecin.getDateDerniereModification(),
                medecin.getIdEntite()
        );
        log.info("✓ Médecin ID {} mis à jour", medecin.getIdEntite());
    }

    @Override
    public void delete(medecin medecin) {
        if (medecin != null && medecin.getIdEntite() != null) {
            deleteById(medecin.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Suppression du médecin ID: {}", id);
        String sql = buildDeleteQuery("idEntite = ?");
        executeUpdate(sql, id);
    }

    @Override
    public List<medecin> findByNom(String nom) {
        log.info("Recherche de médecins par nom: {}", nom);
        String sql = buildSelectQuery("nom LIKE ?") + " ORDER BY nom ASC";
        return executeQuery(sql, "%" + nom + "%");
    }

    @Override
    public List<medecin> findBySpecialite(String specialite) {
        log.info("Recherche de médecins par spécialité: {}", specialite);
        String sql = buildSelectQuery("specialite = ?");
        return executeQuery(sql, specialite);
    }

    @Override
    public List<medecin> findByEmail(String email) {
        log.info("Recherche du médecin par email: {}", email);
        String sql = buildSelectQuery("email = ?");
        return executeQuery(sql, email);
    }

    @Override
    public List<medecin> findBySexe(Sexe sexe) {
        log.info("Recherche de médecins par sexe: {}", sexe);
        String sql = buildSelectQuery("sexe = ?");
        return executeQuery(sql, sexe != null ? sexe.name() : null);
    }

    @Override
    public List<medecin> findBySalaireBetween(Double minSalaire, Double maxSalaire) {
        log.info("Recherche de médecins avec salaire entre {} et {}", minSalaire, maxSalaire);
        String sql = buildSelectQuery("salaire BETWEEN ? AND ?");
        return executeQuery(sql, minSalaire, maxSalaire);
    }

    @Override
    public List<medecin> findWithHighestSalary() {
        log.info("Recherche du/des médecin(s) avec le plus haut salaire");
        String sql = buildSelectQuery(null) + " ORDER BY salaire DESC LIMIT 1";
        return executeQuery(sql);
    }

    @Override
    public long countBySpecialite(String specialite) {
        log.info("Comptage des médecins par spécialité: {}", specialite);
        String sql = "SELECT COUNT(*) as count FROM " + tableName + " WHERE specialite = ?";
        
        try (var conn = getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, specialite);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    long count = rs.getLong("count");
                    log.info("✓ {} médecin(s) avec la spécialité: {}", count, specialite);
                    return count;
                }
            }
        } catch (Exception e) {
            log.error("✗ Erreur lors du comptage: {}", e.getMessage());
        }
        return 0L;
    }
}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(medecin m) {
        m.setDateCreation(LocalDate.now());
        if (m.getCreePar() == null) m.setCreePar("SYSTEM");

        String sql = "INSERT INTO Medecin (dateCreation, creePar, idUser, nom, email, adresse, cin, tel, sexe, login, motDePasse, lastLoginDate, dateNaissance, salaire, prime, dateRecrutement, soldeConge, specialite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(m.getDateCreation()));
            ps.setString(2, m.getCreePar());

            ps.setLong(3, m.getIdUser());
            ps.setString(4, m.getNom());
            ps.setString(5, m.getEmail());
            ps.setString(6, m.getAdresse());
            ps.setString(7, m.getCin());
            ps.setString(8, m.getTel());
            ps.setString(9, m.getSexe() != null ? m.getSexe().name() : null);
            ps.setString(10, m.getLogin());
            ps.setString(11, m.getMotDePasse());
            ps.setDate(12, m.getLastLoginDate() != null ? Date.valueOf(m.getLastLoginDate()) : null);
            ps.setDate(13, m.getDateNaissance() != null ? Date.valueOf(m.getDateNaissance()) : null);

            ps.setDouble(14, m.getSalaire());
            ps.setDouble(15, m.getPrime());
            ps.setDate(16, m.getDateRecrutement() != null ? Date.valueOf(m.getDateRecrutement()) : null);
            ps.setInt(17, m.getSoldeConge());

            ps.setString(18, m.getSpecialite());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    m.setIdEntite(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(medecin m) {
        m.setDateDerniereModification(LocalDateTime.now());
        if (m.getModifierPar() == null) m.setModifierPar("SYSTEM");

        String sql = "UPDATE Medecin SET idUser = ?, nom = ?, email = ?, adresse = ?, cin = ?, tel = ?, sexe = ?, login = ?, motDePasse = ?, lastLoginDate = ?, dateNaissance = ?, salaire = ?, prime = ?, dateRecrutement = ?, soldeConge = ?, specialite = ?, dateDerniereModification = ?, modifierPar = ? WHERE idEntite = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, m.getIdUser());
            ps.setString(2, m.getNom());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getAdresse());
            ps.setString(5, m.getCin());
            ps.setString(6, m.getTel());
            ps.setString(7, m.getSexe() != null ? m.getSexe().name() : null);
            ps.setString(8, m.getLogin());
            ps.setString(9, m.getMotDePasse());
            ps.setDate(10, m.getLastLoginDate() != null ? Date.valueOf(m.getLastLoginDate()) : null);
            ps.setDate(11, m.getDateNaissance() != null ? Date.valueOf(m.getDateNaissance()) : null);

            ps.setDouble(12, m.getSalaire());
            ps.setDouble(13, m.getPrime());
            ps.setDate(14, m.getDateRecrutement() != null ? Date.valueOf(m.getDateRecrutement()) : null);
            ps.setInt(15, m.getSoldeConge());

            ps.setString(16, m.getSpecialite());

            ps.setTimestamp(17, Timestamp.valueOf(m.getDateDerniereModification()));
            ps.setString(18, m.getModifierPar());

            ps.setLong(19, m.getIdEntite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(medecin m) {
        if (m != null && m.getIdEntite() != null) {
            deleteById(m.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Medecin WHERE idEntite = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<medecin> findByCin(String cin) {
        String sql = "SELECT * FROM Medecin WHERE cin = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<medecin> findByEmail(String email) {
        String sql = "SELECT * FROM Medecin WHERE email = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}


