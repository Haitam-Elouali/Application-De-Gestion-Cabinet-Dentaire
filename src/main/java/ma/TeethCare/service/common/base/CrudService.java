package ma.TeethCare.service.common.base;

import java.util.List;

/**
 * Generic service-level CRUD contract to be reused by feature modules.
 * Prefer DTO types at the service boundary for decoupling from persistence entities.
 */
public interface CrudService<T, ID> {

	/**
	 * Retrieve all items.
	 */
	List<T> findAll();

	/**
	 * Retrieve one item by its identifier.
	 */
	T findById(ID id);

	/**
	 * Create a new item.
	 */
	T create(T item);

	/**
	 * Update an existing item.
	 */
	T update(ID id, T item);

	/**
	 * Delete an item.
	 */
	void delete(T item);

	/**
	 * Delete an item by its identifier.
	 */
	void deleteById(ID id);
}


