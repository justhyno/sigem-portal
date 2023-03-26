package gov.cmcm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectoMapperTest {

    private ProjectoMapper projectoMapper;

    @BeforeEach
    public void setUp() {
        projectoMapper = new ProjectoMapperImpl();
    }
}
