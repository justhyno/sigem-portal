package gov.cmcm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.cmcm.IntegrationTest;
import gov.cmcm.domain.Projecto;
import gov.cmcm.repository.ProjectoRepository;
import gov.cmcm.service.criteria.ProjectoCriteria;
import gov.cmcm.service.dto.ProjectoDTO;
import gov.cmcm.service.mapper.ProjectoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ZONA = "AAAAAAAAAA";
    private static final String UPDATED_ZONA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projectos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectoRepository projectoRepository;

    @Autowired
    private ProjectoMapper projectoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectoMockMvc;

    private Projecto projecto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projecto createEntity(EntityManager em) {
        Projecto projecto = new Projecto().nome(DEFAULT_NOME).zona(DEFAULT_ZONA).descricao(DEFAULT_DESCRICAO);
        return projecto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projecto createUpdatedEntity(EntityManager em) {
        Projecto projecto = new Projecto().nome(UPDATED_NOME).zona(UPDATED_ZONA).descricao(UPDATED_DESCRICAO);
        return projecto;
    }

    @BeforeEach
    public void initTest() {
        projecto = createEntity(em);
    }

    @Test
    @Transactional
    void createProjecto() throws Exception {
        int databaseSizeBeforeCreate = projectoRepository.findAll().size();
        // Create the Projecto
        ProjectoDTO projectoDTO = projectoMapper.toDto(projecto);
        restProjectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectoDTO)))
            .andExpect(status().isCreated());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeCreate + 1);
        Projecto testProjecto = projectoList.get(projectoList.size() - 1);
        assertThat(testProjecto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProjecto.getZona()).isEqualTo(DEFAULT_ZONA);
        assertThat(testProjecto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createProjectoWithExistingId() throws Exception {
        // Create the Projecto with an existing ID
        projecto.setId(1L);
        ProjectoDTO projectoDTO = projectoMapper.toDto(projecto);

        int databaseSizeBeforeCreate = projectoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectos() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList
        restProjectoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getProjecto() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get the projecto
        restProjectoMockMvc
            .perform(get(ENTITY_API_URL_ID, projecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projecto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.zona").value(DEFAULT_ZONA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getProjectosByIdFiltering() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        Long id = projecto.getId();

        defaultProjectoShouldBeFound("id.equals=" + id);
        defaultProjectoShouldNotBeFound("id.notEquals=" + id);

        defaultProjectoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectoShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where nome equals to DEFAULT_NOME
        defaultProjectoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the projectoList where nome equals to UPDATED_NOME
        defaultProjectoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProjectosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultProjectoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the projectoList where nome equals to UPDATED_NOME
        defaultProjectoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProjectosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where nome is not null
        defaultProjectoShouldBeFound("nome.specified=true");

        // Get all the projectoList where nome is null
        defaultProjectoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectosByNomeContainsSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where nome contains DEFAULT_NOME
        defaultProjectoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the projectoList where nome contains UPDATED_NOME
        defaultProjectoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProjectosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where nome does not contain DEFAULT_NOME
        defaultProjectoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the projectoList where nome does not contain UPDATED_NOME
        defaultProjectoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProjectosByZonaIsEqualToSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where zona equals to DEFAULT_ZONA
        defaultProjectoShouldBeFound("zona.equals=" + DEFAULT_ZONA);

        // Get all the projectoList where zona equals to UPDATED_ZONA
        defaultProjectoShouldNotBeFound("zona.equals=" + UPDATED_ZONA);
    }

    @Test
    @Transactional
    void getAllProjectosByZonaIsInShouldWork() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where zona in DEFAULT_ZONA or UPDATED_ZONA
        defaultProjectoShouldBeFound("zona.in=" + DEFAULT_ZONA + "," + UPDATED_ZONA);

        // Get all the projectoList where zona equals to UPDATED_ZONA
        defaultProjectoShouldNotBeFound("zona.in=" + UPDATED_ZONA);
    }

    @Test
    @Transactional
    void getAllProjectosByZonaIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where zona is not null
        defaultProjectoShouldBeFound("zona.specified=true");

        // Get all the projectoList where zona is null
        defaultProjectoShouldNotBeFound("zona.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectosByZonaContainsSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where zona contains DEFAULT_ZONA
        defaultProjectoShouldBeFound("zona.contains=" + DEFAULT_ZONA);

        // Get all the projectoList where zona contains UPDATED_ZONA
        defaultProjectoShouldNotBeFound("zona.contains=" + UPDATED_ZONA);
    }

    @Test
    @Transactional
    void getAllProjectosByZonaNotContainsSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where zona does not contain DEFAULT_ZONA
        defaultProjectoShouldNotBeFound("zona.doesNotContain=" + DEFAULT_ZONA);

        // Get all the projectoList where zona does not contain UPDATED_ZONA
        defaultProjectoShouldBeFound("zona.doesNotContain=" + UPDATED_ZONA);
    }

    @Test
    @Transactional
    void getAllProjectosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where descricao equals to DEFAULT_DESCRICAO
        defaultProjectoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the projectoList where descricao equals to UPDATED_DESCRICAO
        defaultProjectoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllProjectosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultProjectoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the projectoList where descricao equals to UPDATED_DESCRICAO
        defaultProjectoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllProjectosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where descricao is not null
        defaultProjectoShouldBeFound("descricao.specified=true");

        // Get all the projectoList where descricao is null
        defaultProjectoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where descricao contains DEFAULT_DESCRICAO
        defaultProjectoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the projectoList where descricao contains UPDATED_DESCRICAO
        defaultProjectoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllProjectosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        // Get all the projectoList where descricao does not contain DEFAULT_DESCRICAO
        defaultProjectoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the projectoList where descricao does not contain UPDATED_DESCRICAO
        defaultProjectoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectoShouldBeFound(String filter) throws Exception {
        restProjectoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restProjectoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectoShouldNotBeFound(String filter) throws Exception {
        restProjectoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProjecto() throws Exception {
        // Get the projecto
        restProjectoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjecto() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();

        // Update the projecto
        Projecto updatedProjecto = projectoRepository.findById(projecto.getId()).get();
        // Disconnect from session so that the updates on updatedProjecto are not directly saved in db
        em.detach(updatedProjecto);
        updatedProjecto.nome(UPDATED_NOME).zona(UPDATED_ZONA).descricao(UPDATED_DESCRICAO);
        ProjectoDTO projectoDTO = projectoMapper.toDto(updatedProjecto);

        restProjectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
        Projecto testProjecto = projectoList.get(projectoList.size() - 1);
        assertThat(testProjecto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProjecto.getZona()).isEqualTo(UPDATED_ZONA);
        assertThat(testProjecto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingProjecto() throws Exception {
        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();
        projecto.setId(count.incrementAndGet());

        // Create the Projecto
        ProjectoDTO projectoDTO = projectoMapper.toDto(projecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjecto() throws Exception {
        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();
        projecto.setId(count.incrementAndGet());

        // Create the Projecto
        ProjectoDTO projectoDTO = projectoMapper.toDto(projecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjecto() throws Exception {
        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();
        projecto.setId(count.incrementAndGet());

        // Create the Projecto
        ProjectoDTO projectoDTO = projectoMapper.toDto(projecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectoWithPatch() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();

        // Update the projecto using partial update
        Projecto partialUpdatedProjecto = new Projecto();
        partialUpdatedProjecto.setId(projecto.getId());

        partialUpdatedProjecto.zona(UPDATED_ZONA).descricao(UPDATED_DESCRICAO);

        restProjectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjecto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjecto))
            )
            .andExpect(status().isOk());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
        Projecto testProjecto = projectoList.get(projectoList.size() - 1);
        assertThat(testProjecto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProjecto.getZona()).isEqualTo(UPDATED_ZONA);
        assertThat(testProjecto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateProjectoWithPatch() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();

        // Update the projecto using partial update
        Projecto partialUpdatedProjecto = new Projecto();
        partialUpdatedProjecto.setId(projecto.getId());

        partialUpdatedProjecto.nome(UPDATED_NOME).zona(UPDATED_ZONA).descricao(UPDATED_DESCRICAO);

        restProjectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjecto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjecto))
            )
            .andExpect(status().isOk());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
        Projecto testProjecto = projectoList.get(projectoList.size() - 1);
        assertThat(testProjecto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProjecto.getZona()).isEqualTo(UPDATED_ZONA);
        assertThat(testProjecto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingProjecto() throws Exception {
        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();
        projecto.setId(count.incrementAndGet());

        // Create the Projecto
        ProjectoDTO projectoDTO = projectoMapper.toDto(projecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjecto() throws Exception {
        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();
        projecto.setId(count.incrementAndGet());

        // Create the Projecto
        ProjectoDTO projectoDTO = projectoMapper.toDto(projecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjecto() throws Exception {
        int databaseSizeBeforeUpdate = projectoRepository.findAll().size();
        projecto.setId(count.incrementAndGet());

        // Create the Projecto
        ProjectoDTO projectoDTO = projectoMapper.toDto(projecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projectoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projecto in the database
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjecto() throws Exception {
        // Initialize the database
        projectoRepository.saveAndFlush(projecto);

        int databaseSizeBeforeDelete = projectoRepository.findAll().size();

        // Delete the projecto
        restProjectoMockMvc
            .perform(delete(ENTITY_API_URL_ID, projecto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projecto> projectoList = projectoRepository.findAll();
        assertThat(projectoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
