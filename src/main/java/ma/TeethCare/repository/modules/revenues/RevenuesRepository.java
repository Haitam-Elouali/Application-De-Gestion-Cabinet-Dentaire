package ma.TeethCare.repository.modules.revenues;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface RevenuesRepository extends BaseRepository<revenues, Long> {
    List<revenues> findByFactureId(Long factureId) throws Exception;
    Double getTotalRevenues() throws Exception;
}
