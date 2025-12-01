package ma.TeethCare.mvc.controllers.medicament;

import ma.TeethCare.mvc.dto.medicament.MedicamentDTO;
import java.util.List;

public interface MedicamentController {
    void create(MedicamentDTO medicament) throws Exception;
    MedicamentDTO getById(Long id) throws Exception;
    List<MedicamentDTO> getAll() throws Exception;
    void update(Long id, MedicamentDTO medicament) throws Exception;
    void delete(Long id) throws Exception;
    List<MedicamentDTO> findByLaboratoire(String laboratoire) throws Exception;
}
