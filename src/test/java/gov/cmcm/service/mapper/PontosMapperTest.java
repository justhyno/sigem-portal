package gov.cmcm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PontosMapperTest {

    private PontosMapper pontosMapper;

    @BeforeEach
    public void setUp() {
        pontosMapper = new PontosMapperImpl();
    }
}
