package gov.cmcm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlfaMapperTest {

    private AlfaMapper alfaMapper;

    @BeforeEach
    public void setUp() {
        alfaMapper = new AlfaMapperImpl();
    }
}
