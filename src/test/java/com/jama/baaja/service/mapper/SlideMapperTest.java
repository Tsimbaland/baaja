package com.jama.baaja.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SlideMapperTest {

    private SlideMapper slideMapper;

    @BeforeEach
    public void setUp() {
        slideMapper = new SlideMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(slideMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(slideMapper.fromId(null)).isNull();
    }
}
