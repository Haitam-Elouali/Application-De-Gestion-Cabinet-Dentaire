package ma.TeethCare.mvc.controllers.revenues;

import ma.TeethCare.mvc.dto.revenues.RevenuesDTO;
import java.util.List;

public interface RevenuesController {
    void create(RevenuesDTO revenues) throws Exception;
    RevenuesDTO getById(Long id) throws Exception;
    List<RevenuesDTO> getAll() throws Exception;
    void update(Long id, RevenuesDTO revenues) throws Exception;
    void delete(Long id) throws Exception;
    Double getTotalRevenues() throws Exception;
}
