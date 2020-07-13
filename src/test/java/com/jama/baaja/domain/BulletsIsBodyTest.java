package com.jama.baaja.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class BulletsIsBodyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BulletsIsBody.class);
        BulletsIsBody bulletsIsBody1 = new BulletsIsBody();
        bulletsIsBody1.setId(1L);
        BulletsIsBody bulletsIsBody2 = new BulletsIsBody();
        bulletsIsBody2.setId(bulletsIsBody1.getId());
        assertThat(bulletsIsBody1).isEqualTo(bulletsIsBody2);
        bulletsIsBody2.setId(2L);
        assertThat(bulletsIsBody1).isNotEqualTo(bulletsIsBody2);
        bulletsIsBody1.setId(null);
        assertThat(bulletsIsBody1).isNotEqualTo(bulletsIsBody2);
    }
}
