package com.jama.baaja.service.mapper;

import com.jama.baaja.domain.Body;
import com.jama.baaja.service.dto.BodyDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Body} and its DTO {@link BodyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BodyMapper extends EntityMapper<BodyDTO, Body> {



    default Body fromId(Long id) {
        if (id == null) {
            return null;
        }
        Body body = new Body();
        body.setId(id);
        return body;
    }
}
