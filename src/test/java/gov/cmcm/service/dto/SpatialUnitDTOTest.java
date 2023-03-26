package gov.cmcm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpatialUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpatialUnitDTO.class);
        SpatialUnitDTO spatialUnitDTO1 = new SpatialUnitDTO();
        spatialUnitDTO1.setId(1L);
        SpatialUnitDTO spatialUnitDTO2 = new SpatialUnitDTO();
        assertThat(spatialUnitDTO1).isNotEqualTo(spatialUnitDTO2);
        spatialUnitDTO2.setId(spatialUnitDTO1.getId());
        assertThat(spatialUnitDTO1).isEqualTo(spatialUnitDTO2);
        spatialUnitDTO2.setId(2L);
        assertThat(spatialUnitDTO1).isNotEqualTo(spatialUnitDTO2);
        spatialUnitDTO1.setId(null);
        assertThat(spatialUnitDTO1).isNotEqualTo(spatialUnitDTO2);
    }
}
