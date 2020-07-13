package com.jama.baaja.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class HeadingIsBodyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HeadingIsBody.class);
        HeadingIsBody headingIsBody1 = new HeadingIsBody();
        headingIsBody1.setId(1L);
        HeadingIsBody headingIsBody2 = new HeadingIsBody();
        headingIsBody2.setId(headingIsBody1.getId());
        assertThat(headingIsBody1).isEqualTo(headingIsBody2);
        headingIsBody2.setId(2L);
        assertThat(headingIsBody1).isNotEqualTo(headingIsBody2);
        headingIsBody1.setId(null);
        assertThat(headingIsBody1).isNotEqualTo(headingIsBody2);
    }
}
