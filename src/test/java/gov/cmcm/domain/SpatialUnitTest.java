package gov.cmcm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpatialUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpatialUnit.class);
        SpatialUnit spatialUnit1 = new SpatialUnit();
        spatialUnit1.setId(1L);
        SpatialUnit spatialUnit2 = new SpatialUnit();
        spatialUnit2.setId(spatialUnit1.getId());
        assertThat(spatialUnit1).isEqualTo(spatialUnit2);
        spatialUnit2.setId(2L);
        assertThat(spatialUnit1).isNotEqualTo(spatialUnit2);
        spatialUnit1.setId(null);
        assertThat(spatialUnit1).isNotEqualTo(spatialUnit2);
    }
}
