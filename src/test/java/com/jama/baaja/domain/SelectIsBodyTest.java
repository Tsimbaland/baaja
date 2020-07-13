package com.jama.baaja.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SelectIsBodyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectIsBody.class);
        SelectIsBody selectIsBody1 = new SelectIsBody();
        selectIsBody1.setId(1L);
        SelectIsBody selectIsBody2 = new SelectIsBody();
        selectIsBody2.setId(selectIsBody1.getId());
        assertThat(selectIsBody1).isEqualTo(selectIsBody2);
        selectIsBody2.setId(2L);
        assertThat(selectIsBody1).isNotEqualTo(selectIsBody2);
        selectIsBody1.setId(null);
        assertThat(selectIsBody1).isNotEqualTo(selectIsBody2);
    }
}
