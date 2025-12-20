package ma.TeethCare.service.modules.caisse.api;

import ma.TeethCare.service.modules.caisse.dto.RevenuesDto;

import java.util.List;

public interface revenuesService {

    RevenuesDto create(RevenuesDto dto);

    RevenuesDto update(Long id, RevenuesDto dto);

    RevenuesDto findById(Long id);

    List<RevenuesDto> findAll();

    boolean delete(Long id) throws Exception;

    boolean exists(Long id) throws Exception;

    long count() throws Exception;
}
