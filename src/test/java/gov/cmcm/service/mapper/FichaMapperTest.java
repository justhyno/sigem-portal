package gov.cmcm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FichaMapperTest {

    private FichaMapper fichaMapper;

    @BeforeEach
    public void setUp() {
        fichaMapper = new FichaMapperImpl();
    }
}
