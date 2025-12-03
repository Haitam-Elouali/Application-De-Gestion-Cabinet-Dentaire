package ma.TeethCare.repository.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe utilitaire générique pour les opérations JDBC avec réflexion Java.
 * Gère automatiquement le mapping entre les colonnes SQL et les attributs Java.
 *
 * @param <T> Le type d'entité
 */
@Slf4j
public abstract class GenericJdbcRepository<T> {

    // Classe du driver MySQL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    protected String jdbcUrl;
    protected String user;
    protected String password;
    protected Class<T> entityClass;
    protected String tableName;

    /**
     * Constructeur simplifié pour les classes filles qui gèrent leur propre connexion
     */
    public GenericJdbcRepository(Class<T> entityClass, String tableName) {
        this.entityClass = entityClass;
        this.tableName = tableName;
        loadJdbcDriver();
    }

    /**
     * Constructeur pour initialiser les paramètres de connexion
     */
    public GenericJdbcRepository(Class<T> entityClass, String tableName, 
                                 String jdbcUrl, String user, String password) {
        this.entityClass = entityClass;
        this.tableName = tableName;
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        
        // Charger le driver MySQL via réflexion
        loadJdbcDriver();
    }

    /**
     * Charge le driver JDBC via réflexion (Class.forName)
     */
    private static void loadJdbcDriver() {
        try {
            Class.forName(JDBC_DRIVER);
            log.info("✓ Driver MySQL chargé avec succès: {}", JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("✗ Erreur: Driver MySQL non trouvé. Ajouter mysql-connector-java à pom.xml", e);
            throw new RuntimeException("Driver JDBC non disponible", e);
        }
    }

    /**
     * Établit une connexion à la base de données
     */
    protected Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
            log.debug("Connexion à {} établie", jdbcUrl);
            return conn;
        } catch (SQLException e) {
            log.error("Erreur de connexion à la base de données: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Récupère les noms des colonnes via réflexion sur les champs de la classe
     */
    protected String getColumnNames() {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.getName().equals("serialVersionUID"))
                .map(this::getColumnName)
                .collect(Collectors.joining(", "));
    }

    /**
     * Récupère les placeholders ? pour une requête INSERT/UPDATE
     */
    protected String getPlaceholders(int count) {
        return String.join(", ", Collections.nCopies(count, "?"));
    }

    /**
     * Convertit un nom de champ Java en nom de colonne SQL (camelCase -> snake_case)
     */
    protected String getColumnName(Field field) {
        String name = field.getName();
        // Utiliser le nom du champ tel quel (adapter selon votre convention)
        return name;
    }

    /**
     * Récupère tous les champs de l'entité (excluant serialVersionUID)
     */
    protected List<Field> getEntityFields() {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.getName().equals("serialVersionUID"))
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toList());
    }

    /**
     * Définit les paramètres d'une PreparedStatement à partir d'une entité
     */
    protected void setStatementParameters(PreparedStatement stmt, T entity, List<String> fieldNames) throws SQLException {
        List<Field> fields = getEntityFields();
        int index = 1;
        
        for (String fieldName : fieldNames) {
            Field field = fields.stream()
                    .filter(f -> f.getName().equals(fieldName))
                    .findFirst()
                    .orElse(null);
            
            if (field != null) {
                try {
                    Object value = field.get(entity);
                    stmt.setObject(index++, value);
                } catch (IllegalAccessException e) {
                    log.error("Erreur d'accès au champ {}: {}", fieldName, e.getMessage());
                    throw new SQLException(e);
                }
            }
        }
    }

    /**
     * Crée une entité à partir d'une ligne ResultSet via réflexion
     */
    protected T mapResultSetToEntity(ResultSet rs) throws SQLException {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            List<Field> fields = getEntityFields();
            
            for (Field field : fields) {
                try {
                    Object value = rs.getObject(field.getName());
                    field.set(entity, value);
                } catch (SQLException e) {
                    log.debug("Colonne {} non trouvée dans le ResultSet", field.getName());
                    // Ignorer les colonnes manquantes
                }
            }
            
            return entity;
        } catch (Exception e) {
            log.error("Erreur lors du mapping de l'entité: {}", e.getMessage());
            throw new SQLException("Impossible de créer l'entité " + entityClass.getSimpleName(), e);
        }
    }

    /**
     * Exécute une requête SELECT et retourne la liste des résultats
     */
    protected List<T> executeQuery(String sql, Object... params) {
        List<T> results = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Définir les paramètres
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToEntity(rs));
                }
            }
            
            log.debug("✓ Requête exécutée: {} (résultats: {})", sql, results.size());
        } catch (SQLException e) {
            log.error("✗ Erreur lors de l'exécution de la requête: {}", e.getMessage());
        }
        
        return results;
    }

    /**
     * Exécute une requête SELECT retournant une seule entité
     */
    protected T executeSingleQuery(String sql, Object... params) {
        List<T> results = executeQuery(sql, params);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Exécute une requête de mise à jour (INSERT, UPDATE, DELETE)
     */
    protected int executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Définir les paramètres
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            int rowsAffected = stmt.executeUpdate();
            log.debug("✓ Requête mise à jour: {} (lignes affectées: {})", sql, rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            log.error("✗ Erreur lors de l'exécution de la mise à jour: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Exécute une requête INSERT et retourne l'ID généré
     */
    protected Long executeInsertAndGetId(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Définir les paramètres
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    log.debug("✓ Enregistrement inséré avec l'ID: {}", id);
                    return id;
                }
            }
        } catch (SQLException e) {
            log.error("✗ Erreur lors de l'insertion: {}", e.getMessage());
        }
        
        return null;
    }

    /**
     * Récupère les noms des colonnes de la table (via PRAGMA/INFORMATION_SCHEMA)
     */
    protected List<String> getTableColumns() {
        List<String> columns = new ArrayList<>();
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, tableName);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    columns.add(rs.getString("COLUMN_NAME"));
                }
            }
        } catch (SQLException e) {
            log.warn("Impossible de récupérer les colonnes de la table {}: {}", tableName, e.getMessage());
        }
        
        return columns;
    }

    /**
     * Construit une requête SELECT avec les colonnes disponibles
     */
    protected String buildSelectQuery(String whereClause) {
        String columns = getColumnNames();
        String sql = String.format("SELECT %s FROM %s", columns, tableName);
        
        if (whereClause != null && !whereClause.isEmpty()) {
            sql += " WHERE " + whereClause;
        }
        
        return sql;
    }

    /**
     * Construit une requête INSERT
     */
    protected String buildInsertQuery(List<String> fields) {
        String columns = String.join(", ", fields);
        String placeholders = getPlaceholders(fields.size());
        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);
    }

    /**
     * Construit une requête UPDATE
     */
    protected String buildUpdateQuery(List<String> fields, String whereClause) {
        String setClause = fields.stream()
                .map(f -> f + " = ?")
                .collect(Collectors.joining(", "));
        
        return String.format("UPDATE %s SET %s WHERE %s", tableName, setClause, whereClause);
    }

    /**
     * Construit une requête DELETE
     */
    protected String buildDeleteQuery(String whereClause) {
        return String.format("DELETE FROM %s WHERE %s", tableName, whereClause);
    }
}
