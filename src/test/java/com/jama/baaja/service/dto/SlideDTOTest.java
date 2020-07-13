package com.jama.baaja.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SlideDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SlideDTO.class);
        SlideDTO slideDTO1 = new SlideDTO();
        slideDTO1.setId(1L);
        SlideDTO slideDTO2 = new SlideDTO();
        assertThat(slideDTO1).isNotEqualTo(slideDTO2);
        slideDTO2.setId(slideDTO1.getId());
        assertThat(slideDTO1).isEqualTo(slideDTO2);
        slideDTO2.setId(2L);
        assertThat(slideDTO1).isNotEqualTo(slideDTO2);
        slideDTO1.setId(null);
        assertThat(slideDTO1).isNotEqualTo(slideDTO2);
    }
}
