package ma.TeethCare.service.modules.revenues;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface RevenuesService extends BaseService<revenues, Long> {
    List<revenues> findByFactureId(Long factureId) throws Exception;
    Double getTotalRevenues() throws Exception;
}
