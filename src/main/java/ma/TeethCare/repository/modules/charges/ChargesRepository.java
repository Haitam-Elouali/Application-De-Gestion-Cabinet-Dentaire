package ma.TeethCare.repository.modules.charges;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface ChargesRepository extends BaseRepository<charges, Long> {
    List<charges> findByCategorie(String categorie) throws Exception;
    List<charges> findAll() throws Exception;
    Double getTotalCharges() throws Exception;
}
