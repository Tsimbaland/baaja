package com.jama.baaja.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class BodyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Body.class);
        Body body1 = new Body();
        body1.setId(1L);
        Body body2 = new Body();
        body2.setId(body1.getId());
        assertThat(body1).isEqualTo(body2);
        body2.setId(2L);
        assertThat(body1).isNotEqualTo(body2);
        body1.setId(null);
        assertThat(body1).isNotEqualTo(body2);
    }
}
