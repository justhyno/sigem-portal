package gov.cmcm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FichaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaDTO.class);
        FichaDTO fichaDTO1 = new FichaDTO();
        fichaDTO1.setId(1L);
        FichaDTO fichaDTO2 = new FichaDTO();
        assertThat(fichaDTO1).isNotEqualTo(fichaDTO2);
        fichaDTO2.setId(fichaDTO1.getId());
        assertThat(fichaDTO1).isEqualTo(fichaDTO2);
        fichaDTO2.setId(2L);
        assertThat(fichaDTO1).isNotEqualTo(fichaDTO2);
        fichaDTO1.setId(null);
        assertThat(fichaDTO1).isNotEqualTo(fichaDTO2);
    }
}
