package ma.TeethCare.service.modules.caisse.api;

import ma.TeethCare.service.modules.caisse.dto.ChargesDto;

import java.util.List;

public interface chargesService {

    ChargesDto create(ChargesDto dto);

    ChargesDto update(Long id, ChargesDto dto);

    ChargesDto findById(Long id);

    List<ChargesDto> findAll();

    boolean delete(Long id) throws Exception;

    boolean exists(Long id) throws Exception;

    long count() throws Exception;
}
