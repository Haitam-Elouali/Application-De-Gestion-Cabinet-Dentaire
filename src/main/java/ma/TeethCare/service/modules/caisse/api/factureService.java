package ma.TeethCare.service.modules.caisse.api;

import ma.TeethCare.service.modules.caisse.dto.FactureDto;

import java.util.List;

public interface factureService {

    FactureDto create(FactureDto dto);

    FactureDto update(Long id, FactureDto dto);

    FactureDto findById(Long id);

    List<FactureDto> findAll();

    boolean delete(Long id) throws Exception;

    boolean exists(Long id) throws Exception;

    long count() throws Exception;
}
