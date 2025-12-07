package ma.TeethCare.repository.api;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.common.BaseRepository;
import ma.TeethCare.repository.common.CrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.time.LocalDateTime;

public interface LogRepository extends CrudRepository<log, Long> {
    List<log> findByUtilisateur(String utilisateur) throws SQLException;

    List<log> findByAction(String action) throws SQLException;

    List<log> findByDateRange(LocalDateTime debut, LocalDateTime fin) throws SQLException;

}
