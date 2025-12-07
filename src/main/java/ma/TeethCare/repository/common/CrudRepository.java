package ma.TeethCare.repository.common;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T, ID> {

    List<T> findAll() throws SQLException;

    T findById(ID id);

    void create(T patient);

    void update(T patient);

    void delete(T patient);

    void deleteById(ID id);
}
