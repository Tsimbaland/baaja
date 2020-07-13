package com.jama.baaja.service.mapper;

import com.jama.baaja.domain.Slide;
import com.jama.baaja.service.dto.SlideDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Slide} and its DTO {@link SlideDTO}.
 */
@Mapper(componentModel = "spring", uses = {BodyMapper.class, PresentationMapper.class})
public interface SlideMapper extends EntityMapper<SlideDTO, Slide> {

    @Mapping(source = "body.id", target = "bodyId")
    @Mapping(source = "presentation.id", target = "presentationId")
    SlideDTO toDto(Slide slide);

    @Mapping(source = "bodyId", target = "body")
    @Mapping(source = "presentationId", target = "presentation")
    Slide toEntity(SlideDTO slideDTO);

    default Slide fromId(Long id) {
        if (id == null) {
            return null;
        }
        Slide slide = new Slide();
        slide.setId(id);
        return slide;
    }
}
