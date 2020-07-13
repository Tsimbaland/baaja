package com.jama.baaja.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TypeIsBodyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeIsBody.class);
        TypeIsBody typeIsBody1 = new TypeIsBody();
        typeIsBody1.setId(1L);
        TypeIsBody typeIsBody2 = new TypeIsBody();
        typeIsBody2.setId(typeIsBody1.getId());
        assertThat(typeIsBody1).isEqualTo(typeIsBody2);
        typeIsBody2.setId(2L);
        assertThat(typeIsBody1).isNotEqualTo(typeIsBody2);
        typeIsBody1.setId(null);
        assertThat(typeIsBody1).isNotEqualTo(typeIsBody2);
    }
}
