package kbase.service.mapper;

import kbase.domain.*;
import kbase.service.dto.GlossarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Glossario and its DTO GlossarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GlossarioMapper extends EntityMapper<GlossarioDTO, Glossario> {



    default Glossario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Glossario glossario = new Glossario();
        glossario.setId(id);
        return glossario;
    }
}
