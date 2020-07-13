package com.jama.baaja.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class BodyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyDTO.class);
        BodyDTO bodyDTO1 = new BodyDTO();
        bodyDTO1.setId(1L);
        BodyDTO bodyDTO2 = new BodyDTO();
        assertThat(bodyDTO1).isNotEqualTo(bodyDTO2);
        bodyDTO2.setId(bodyDTO1.getId());
        assertThat(bodyDTO1).isEqualTo(bodyDTO2);
        bodyDTO2.setId(2L);
        assertThat(bodyDTO1).isNotEqualTo(bodyDTO2);
        bodyDTO1.setId(null);
        assertThat(bodyDTO1).isNotEqualTo(bodyDTO2);
    }
}
