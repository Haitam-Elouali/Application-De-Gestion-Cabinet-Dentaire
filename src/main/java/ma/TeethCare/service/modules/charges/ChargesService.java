package ma.TeethCare.service.modules.charges;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface ChargesService extends BaseService<charges, Long> {
    List<charges> findByCategorie(String categorie) throws Exception;
    Double getTotalCharges() throws Exception;
    List<charges> findAll() throws Exception;
}
