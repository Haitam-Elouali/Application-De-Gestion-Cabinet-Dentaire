package ma.TeethCare.service.modules.dashboard_statistiques.api;

import ma.TeethCare.service.modules.dashboard_statistiques.dto.StatistiqueDto;

import java.util.List;

public interface statistiqueService {

    StatistiqueDto create(StatistiqueDto dto);

    StatistiqueDto update(Long id, StatistiqueDto dto);

    StatistiqueDto findById(Long id);

    List<StatistiqueDto> findAll();

    boolean delete(Long id) throws Exception;

    boolean exists(Long id) throws Exception;

    long count() throws Exception;
}
