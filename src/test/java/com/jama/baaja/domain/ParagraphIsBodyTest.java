package com.jama.baaja.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jama.baaja.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ParagraphIsBodyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParagraphIsBody.class);
        ParagraphIsBody paragraphIsBody1 = new ParagraphIsBody();
        paragraphIsBody1.setId(1L);
        ParagraphIsBody paragraphIsBody2 = new ParagraphIsBody();
        paragraphIsBody2.setId(paragraphIsBody1.getId());
        assertThat(paragraphIsBody1).isEqualTo(paragraphIsBody2);
        paragraphIsBody2.setId(2L);
        assertThat(paragraphIsBody1).isNotEqualTo(paragraphIsBody2);
        paragraphIsBody1.setId(null);
        assertThat(paragraphIsBody1).isNotEqualTo(paragraphIsBody2);
    }
}
