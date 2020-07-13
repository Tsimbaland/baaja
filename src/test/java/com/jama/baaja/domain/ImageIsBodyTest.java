package com.jama.baaja.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ImageIsBodyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageIsBody.class);
        ImageIsBody imageIsBody1 = new ImageIsBody();
        imageIsBody1.setId(1L);
        ImageIsBody imageIsBody2 = new ImageIsBody();
        imageIsBody2.setId(imageIsBody1.getId());
        assertThat(imageIsBody1).isEqualTo(imageIsBody2);
        imageIsBody2.setId(2L);
        assertThat(imageIsBody1).isNotEqualTo(imageIsBody2);
        imageIsBody1.setId(null);
        assertThat(imageIsBody1).isNotEqualTo(imageIsBody2);
    }
}
