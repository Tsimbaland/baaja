package com.jama.baaja.service.dto;

import com.jama.baaja.domain.enumeration.SlideType;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jama.baaja.domain.Slide} entity.
 */
public class SlideDTO implements Serializable {

    private Long id;

    private Long order;

    private String name;

    private SlideType type;


    private Long bodyId;

    private Long presentationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SlideType getType() {
        return type;
    }

    public void setType(SlideType type) {
        this.type = type;
    }

    public Long getBodyId() {
        return bodyId;
    }

    public void setBodyId(Long bodyId) {
        this.bodyId = bodyId;
    }

    public Long getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(Long presentationId) {
        this.presentationId = presentationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SlideDTO slideDTO = (SlideDTO) o;
        if (slideDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), slideDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SlideDTO{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", bodyId=" + getBodyId() +
            ", presentationId=" + getPresentationId() +
            "}";
    }
}
