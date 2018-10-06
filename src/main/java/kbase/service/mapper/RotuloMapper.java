package kbase.service.mapper;

import kbase.domain.*;
import kbase.service.dto.RotuloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rotulo and its DTO RotuloDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RotuloMapper extends EntityMapper<RotuloDTO, Rotulo> {



    default Rotulo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rotulo rotulo = new Rotulo();
        rotulo.setId(id);
        return rotulo;
    }
}
