package gov.cmcm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpatialUnitMapperTest {

    private SpatialUnitMapper spatialUnitMapper;

    @BeforeEach
    public void setUp() {
        spatialUnitMapper = new SpatialUnitMapperImpl();
    }
}
