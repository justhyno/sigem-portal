package gov.cmcm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlfaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alfa.class);
        Alfa alfa1 = new Alfa();
        alfa1.setId(1L);
        Alfa alfa2 = new Alfa();
        alfa2.setId(alfa1.getId());
        assertThat(alfa1).isEqualTo(alfa2);
        alfa2.setId(2L);
        assertThat(alfa1).isNotEqualTo(alfa2);
        alfa1.setId(null);
        assertThat(alfa1).isNotEqualTo(alfa2);
    }
}
