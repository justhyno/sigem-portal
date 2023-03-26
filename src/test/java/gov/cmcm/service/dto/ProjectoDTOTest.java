package gov.cmcm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cmcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectoDTO.class);
        ProjectoDTO projectoDTO1 = new ProjectoDTO();
        projectoDTO1.setId(1L);
        ProjectoDTO projectoDTO2 = new ProjectoDTO();
        assertThat(projectoDTO1).isNotEqualTo(projectoDTO2);
        projectoDTO2.setId(projectoDTO1.getId());
        assertThat(projectoDTO1).isEqualTo(projectoDTO2);
        projectoDTO2.setId(2L);
        assertThat(projectoDTO1).isNotEqualTo(projectoDTO2);
        projectoDTO1.setId(null);
        assertThat(projectoDTO1).isNotEqualTo(projectoDTO2);
    }
}
