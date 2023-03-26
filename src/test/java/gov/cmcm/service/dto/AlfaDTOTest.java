package gov.cmcm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlfaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlfaDTO.class);
        AlfaDTO alfaDTO1 = new AlfaDTO();
        alfaDTO1.setId(1L);
        AlfaDTO alfaDTO2 = new AlfaDTO();
        assertThat(alfaDTO1).isNotEqualTo(alfaDTO2);
        alfaDTO2.setId(alfaDTO1.getId());
        assertThat(alfaDTO1).isEqualTo(alfaDTO2);
        alfaDTO2.setId(2L);
        assertThat(alfaDTO1).isNotEqualTo(alfaDTO2);
        alfaDTO1.setId(null);
        assertThat(alfaDTO1).isNotEqualTo(alfaDTO2);
    }
}
