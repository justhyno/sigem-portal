package gov.cmcm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PontosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pontos.class);
        Pontos pontos1 = new Pontos();
        pontos1.setId(1L);
        Pontos pontos2 = new Pontos();
        pontos2.setId(pontos1.getId());
        assertThat(pontos1).isEqualTo(pontos2);
        pontos2.setId(2L);
        assertThat(pontos1).isNotEqualTo(pontos2);
        pontos1.setId(null);
        assertThat(pontos1).isNotEqualTo(pontos2);
    }
}
