package com.jama.baaja.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jama.baaja.domain.Body} entity.
 */
public class BodyDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BodyDTO bodyDTO = (BodyDTO) o;
        if (bodyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bodyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BodyDTO{" +
            "id=" + getId() +
            "}";
    }
}
