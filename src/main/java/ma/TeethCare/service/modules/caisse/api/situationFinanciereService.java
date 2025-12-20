package ma.TeethCare.service.modules.caisse.api;

import ma.TeethCare.service.modules.caisse.dto.SituationFinanciereDto;

import java.util.List;

public interface situationFinanciereService {

    SituationFinanciereDto create(SituationFinanciereDto dto);

    SituationFinanciereDto update(Long id, SituationFinanciereDto dto);

    SituationFinanciereDto findById(Long id);

    List<SituationFinanciereDto> findAll();

    boolean delete(Long id) throws Exception;

    boolean exists(Long id) throws Exception;

    long count() throws Exception;
}
