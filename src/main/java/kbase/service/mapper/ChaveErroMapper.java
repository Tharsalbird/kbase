package kbase.service.mapper;

import kbase.domain.*;
import kbase.service.dto.ChaveErroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ChaveErro and its DTO ChaveErroDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChaveErroMapper extends EntityMapper<ChaveErroDTO, ChaveErro> {



    default ChaveErro fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChaveErro chaveErro = new ChaveErro();
        chaveErro.setId(id);
        return chaveErro;
    }
}
