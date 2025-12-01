package ma.TeethCare.repository.common;

import java.util.List;
import java.util.Optional;

/**
 * Interface générique de repository
 * Fournit les opérations CRUD basiques pour toutes les entités
 * @param <T> Type de l'entité
 * @param <ID> Type de l'identifiant
 */
public interface BaseRepository<T, ID> {

    /**
     * Crée une nouvelle entité
     */
    T create(T entity) throws Exception;

    /**
     * Récupère une entité par son ID
     */
    Optional<T> findById(ID id) throws Exception;

    /**
     * Récupère toutes les entités
     */
    List<T> findAll() throws Exception;

    /**
     * Met à jour une entité
     */
    T update(T entity) throws Exception;

    /**
     * Supprime une entité par son ID
     */
    boolean delete(ID id) throws Exception;

    /**
     * Vérifie si une entité existe
     */
    boolean exists(ID id) throws Exception;

    /**
     * Compte le nombre d'entités
     */
    long count() throws Exception;
}
