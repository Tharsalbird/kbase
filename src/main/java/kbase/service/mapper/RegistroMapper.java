package kbase.service.mapper;

import kbase.domain.*;
import kbase.service.dto.RegistroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Registro and its DTO RegistroDTO.
 */
@Mapper(componentModel = "spring", uses = {SecaoMapper.class, RotuloMapper.class})
public interface RegistroMapper extends EntityMapper<RegistroDTO, Registro> {

    @Mapping(source = "secao.id", target = "secaoId")
    @Mapping(source = "secao.nome", target = "secaoNome")
    RegistroDTO toDto(Registro registro);

    @Mapping(source = "secaoId", target = "secao")
    Registro toEntity(RegistroDTO registroDTO);

    default Registro fromId(Long id) {
        if (id == null) {
            return null;
        }
        Registro registro = new Registro();
        registro.setId(id);
        return registro;
    }
}
