package kbase.service.mapper;

import kbase.domain.*;
import kbase.service.dto.SecaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Secao and its DTO SecaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SecaoMapper extends EntityMapper<SecaoDTO, Secao> {



    default Secao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Secao secao = new Secao();
        secao.setId(id);
        return secao;
    }
}
