package com.jama.baaja.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PresentationMapperTest {

    private PresentationMapper presentationMapper;

    @BeforeEach
    public void setUp() {
        presentationMapper = new PresentationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(presentationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(presentationMapper.fromId(null)).isNull();
    }
}
