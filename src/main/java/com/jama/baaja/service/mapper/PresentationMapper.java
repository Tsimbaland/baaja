package com.jama.baaja.service.mapper;

import com.jama.baaja.domain.Presentation;
import com.jama.baaja.service.dto.PresentationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Presentation} and its DTO {@link PresentationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PresentationMapper extends EntityMapper<PresentationDTO, Presentation> {

    @Mapping(source = "author.id", target = "authorId")
    PresentationDTO toDto(Presentation presentation);

    @Mapping(target = "slides", ignore = true)
    @Mapping(target = "removeSlides", ignore = true)
    @Mapping(source = "authorId", target = "author")
    Presentation toEntity(PresentationDTO presentationDTO);

    default Presentation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Presentation presentation = new Presentation();
        presentation.setId(id);
        return presentation;
    }
}
