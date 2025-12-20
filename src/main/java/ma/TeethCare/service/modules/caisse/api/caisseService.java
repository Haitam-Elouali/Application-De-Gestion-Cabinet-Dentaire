package ma.TeethCare.service.modules.caisse.api;

import ma.TeethCare.service.modules.caisse.dto.CaisseDto;

import java.util.List;

public interface caisseService {

    CaisseDto create(CaisseDto dto);

    CaisseDto update(Long id, CaisseDto dto);

    CaisseDto findById(Long id);

    List<CaisseDto> findAll();

    boolean delete(Long id) throws Exception;

    boolean exists(Long id) throws Exception;

    long count() throws Exception;
}
