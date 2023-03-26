package gov.cmcm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projecto.class);
        Projecto projecto1 = new Projecto();
        projecto1.setId(1L);
        Projecto projecto2 = new Projecto();
        projecto2.setId(projecto1.getId());
        assertThat(projecto1).isEqualTo(projecto2);
        projecto2.setId(2L);
        assertThat(projecto1).isNotEqualTo(projecto2);
        projecto1.setId(null);
        assertThat(projecto1).isNotEqualTo(projecto2);
    }
}
