package ma.TeethCare.mvc.controllers.charges;

import ma.TeethCare.mvc.dto.charges.ChargesDTO;
import java.util.List;

public interface ChargesController {
    void create(ChargesDTO charges) throws Exception;
    ChargesDTO getById(Long id) throws Exception;
    List<ChargesDTO> getAll() throws Exception;
    void update(Long id, ChargesDTO charges) throws Exception;
    void delete(Long id) throws Exception;
    List<ChargesDTO> findByCategorie(String categorie) throws Exception;
    Double getTotalCharges() throws Exception;
}
