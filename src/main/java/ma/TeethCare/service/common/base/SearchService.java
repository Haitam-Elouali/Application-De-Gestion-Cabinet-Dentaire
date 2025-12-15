package ma.TeethCare.service.common.base;

import ma.TeethCare.service.common.dto.PageRequestDTO;
import ma.TeethCare.service.common.dto.PageResponseDTO;

/**
 * Contract for filtered, paginated queries on services.
 */
public interface SearchService<T, F> {

	PageResponseDTO<T> search(F filter, PageRequestDTO page);
}


