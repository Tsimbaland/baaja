package com.jama.baaja.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BodyMapperTest {

    private BodyMapper bodyMapper;

    @BeforeEach
    public void setUp() {
        bodyMapper = new BodyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(bodyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bodyMapper.fromId(null)).isNull();
    }
}
