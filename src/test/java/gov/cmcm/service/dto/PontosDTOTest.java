package gov.cmcm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PontosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PontosDTO.class);
        PontosDTO pontosDTO1 = new PontosDTO();
        pontosDTO1.setId(1L);
        PontosDTO pontosDTO2 = new PontosDTO();
        assertThat(pontosDTO1).isNotEqualTo(pontosDTO2);
        pontosDTO2.setId(pontosDTO1.getId());
        assertThat(pontosDTO1).isEqualTo(pontosDTO2);
        pontosDTO2.setId(2L);
        assertThat(pontosDTO1).isNotEqualTo(pontosDTO2);
        pontosDTO1.setId(null);
        assertThat(pontosDTO1).isNotEqualTo(pontosDTO2);
    }
}
