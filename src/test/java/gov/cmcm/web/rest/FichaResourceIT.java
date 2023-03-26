package gov.cmcm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.cmcm.IntegrationTest;
import gov.cmcm.domain.Ficha;
import gov.cmcm.domain.Projecto;
import gov.cmcm.repository.FichaRepository;
import gov.cmcm.service.FichaService;
import gov.cmcm.service.criteria.FichaCriteria;
import gov.cmcm.service.dto.FichaDTO;
import gov.cmcm.service.mapper.FichaMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FichaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FichaResourceIT {

    private static final String DEFAULT_PARCELA = "AAAAAAAAAA";
    private static final String UPDATED_PARCELA = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fichas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FichaRepository fichaRepository;

    @Mock
    private FichaRepository fichaRepositoryMock;

    @Autowired
    private FichaMapper fichaMapper;

    @Mock
    private FichaService fichaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFichaMockMvc;

    private Ficha ficha;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ficha createEntity(EntityManager em) {
        Ficha ficha = new Ficha().parcela(DEFAULT_PARCELA).processo(DEFAULT_PROCESSO);
        // Add required entity
        Projecto projecto;
        if (TestUtil.findAll(em, Projecto.class).isEmpty()) {
            projecto = ProjectoResourceIT.createEntity(em);
            em.persist(projecto);
            em.flush();
        } else {
            projecto = TestUtil.findAll(em, Projecto.class).get(0);
        }
        ficha.setProjecto(projecto);
        return ficha;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ficha createUpdatedEntity(EntityManager em) {
        Ficha ficha = new Ficha().parcela(UPDATED_PARCELA).processo(UPDATED_PROCESSO);
        // Add required entity
        Projecto projecto;
        if (TestUtil.findAll(em, Projecto.class).isEmpty()) {
            projecto = ProjectoResourceIT.createUpdatedEntity(em);
            em.persist(projecto);
            em.flush();
        } else {
            projecto = TestUtil.findAll(em, Projecto.class).get(0);
        }
        ficha.setProjecto(projecto);
        return ficha;
    }

    @BeforeEach
    public void initTest() {
        ficha = createEntity(em);
    }

    @Test
    @Transactional
    void createFicha() throws Exception {
        int databaseSizeBeforeCreate = fichaRepository.findAll().size();
        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);
        restFichaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fichaDTO)))
            .andExpect(status().isCreated());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeCreate + 1);
        Ficha testFicha = fichaList.get(fichaList.size() - 1);
        assertThat(testFicha.getParcela()).isEqualTo(DEFAULT_PARCELA);
        assertThat(testFicha.getProcesso()).isEqualTo(DEFAULT_PROCESSO);
    }

    @Test
    @Transactional
    void createFichaWithExistingId() throws Exception {
        // Create the Ficha with an existing ID
        ficha.setId(1L);
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        int databaseSizeBeforeCreate = fichaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fichaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFichas() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList
        restFichaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficha.getId().intValue())))
            .andExpect(jsonPath("$.[*].parcela").value(hasItem(DEFAULT_PARCELA)))
            .andExpect(jsonPath("$.[*].processo").value(hasItem(DEFAULT_PROCESSO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFichasWithEagerRelationshipsIsEnabled() throws Exception {
        when(fichaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFichaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fichaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFichasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fichaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFichaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fichaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get the ficha
        restFichaMockMvc
            .perform(get(ENTITY_API_URL_ID, ficha.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ficha.getId().intValue()))
            .andExpect(jsonPath("$.parcela").value(DEFAULT_PARCELA))
            .andExpect(jsonPath("$.processo").value(DEFAULT_PROCESSO));
    }

    @Test
    @Transactional
    void getFichasByIdFiltering() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        Long id = ficha.getId();

        defaultFichaShouldBeFound("id.equals=" + id);
        defaultFichaShouldNotBeFound("id.notEquals=" + id);

        defaultFichaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFichaShouldNotBeFound("id.greaterThan=" + id);

        defaultFichaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFichaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFichasByParcelaIsEqualToSomething() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where parcela equals to DEFAULT_PARCELA
        defaultFichaShouldBeFound("parcela.equals=" + DEFAULT_PARCELA);

        // Get all the fichaList where parcela equals to UPDATED_PARCELA
        defaultFichaShouldNotBeFound("parcela.equals=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllFichasByParcelaIsInShouldWork() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where parcela in DEFAULT_PARCELA or UPDATED_PARCELA
        defaultFichaShouldBeFound("parcela.in=" + DEFAULT_PARCELA + "," + UPDATED_PARCELA);

        // Get all the fichaList where parcela equals to UPDATED_PARCELA
        defaultFichaShouldNotBeFound("parcela.in=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllFichasByParcelaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where parcela is not null
        defaultFichaShouldBeFound("parcela.specified=true");

        // Get all the fichaList where parcela is null
        defaultFichaShouldNotBeFound("parcela.specified=false");
    }

    @Test
    @Transactional
    void getAllFichasByParcelaContainsSomething() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where parcela contains DEFAULT_PARCELA
        defaultFichaShouldBeFound("parcela.contains=" + DEFAULT_PARCELA);

        // Get all the fichaList where parcela contains UPDATED_PARCELA
        defaultFichaShouldNotBeFound("parcela.contains=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllFichasByParcelaNotContainsSomething() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where parcela does not contain DEFAULT_PARCELA
        defaultFichaShouldNotBeFound("parcela.doesNotContain=" + DEFAULT_PARCELA);

        // Get all the fichaList where parcela does not contain UPDATED_PARCELA
        defaultFichaShouldBeFound("parcela.doesNotContain=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllFichasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where processo equals to DEFAULT_PROCESSO
        defaultFichaShouldBeFound("processo.equals=" + DEFAULT_PROCESSO);

        // Get all the fichaList where processo equals to UPDATED_PROCESSO
        defaultFichaShouldNotBeFound("processo.equals=" + UPDATED_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFichasByProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where processo in DEFAULT_PROCESSO or UPDATED_PROCESSO
        defaultFichaShouldBeFound("processo.in=" + DEFAULT_PROCESSO + "," + UPDATED_PROCESSO);

        // Get all the fichaList where processo equals to UPDATED_PROCESSO
        defaultFichaShouldNotBeFound("processo.in=" + UPDATED_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFichasByProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where processo is not null
        defaultFichaShouldBeFound("processo.specified=true");

        // Get all the fichaList where processo is null
        defaultFichaShouldNotBeFound("processo.specified=false");
    }

    @Test
    @Transactional
    void getAllFichasByProcessoContainsSomething() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where processo contains DEFAULT_PROCESSO
        defaultFichaShouldBeFound("processo.contains=" + DEFAULT_PROCESSO);

        // Get all the fichaList where processo contains UPDATED_PROCESSO
        defaultFichaShouldNotBeFound("processo.contains=" + UPDATED_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFichasByProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList where processo does not contain DEFAULT_PROCESSO
        defaultFichaShouldNotBeFound("processo.doesNotContain=" + DEFAULT_PROCESSO);

        // Get all the fichaList where processo does not contain UPDATED_PROCESSO
        defaultFichaShouldBeFound("processo.doesNotContain=" + UPDATED_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFichasByProjectoIsEqualToSomething() throws Exception {
        Projecto projecto;
        if (TestUtil.findAll(em, Projecto.class).isEmpty()) {
            fichaRepository.saveAndFlush(ficha);
            projecto = ProjectoResourceIT.createEntity(em);
        } else {
            projecto = TestUtil.findAll(em, Projecto.class).get(0);
        }
        em.persist(projecto);
        em.flush();
        ficha.setProjecto(projecto);
        fichaRepository.saveAndFlush(ficha);
        Long projectoId = projecto.getId();

        // Get all the fichaList where projecto equals to projectoId
        defaultFichaShouldBeFound("projectoId.equals=" + projectoId);

        // Get all the fichaList where projecto equals to (projectoId + 1)
        defaultFichaShouldNotBeFound("projectoId.equals=" + (projectoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFichaShouldBeFound(String filter) throws Exception {
        restFichaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficha.getId().intValue())))
            .andExpect(jsonPath("$.[*].parcela").value(hasItem(DEFAULT_PARCELA)))
            .andExpect(jsonPath("$.[*].processo").value(hasItem(DEFAULT_PROCESSO)));

        // Check, that the count call also returns 1
        restFichaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFichaShouldNotBeFound(String filter) throws Exception {
        restFichaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFichaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFicha() throws Exception {
        // Get the ficha
        restFichaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();

        // Update the ficha
        Ficha updatedFicha = fichaRepository.findById(ficha.getId()).get();
        // Disconnect from session so that the updates on updatedFicha are not directly saved in db
        em.detach(updatedFicha);
        updatedFicha.parcela(UPDATED_PARCELA).processo(UPDATED_PROCESSO);
        FichaDTO fichaDTO = fichaMapper.toDto(updatedFicha);

        restFichaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fichaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fichaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
        Ficha testFicha = fichaList.get(fichaList.size() - 1);
        assertThat(testFicha.getParcela()).isEqualTo(UPDATED_PARCELA);
        assertThat(testFicha.getProcesso()).isEqualTo(UPDATED_PROCESSO);
    }

    @Test
    @Transactional
    void putNonExistingFicha() throws Exception {
        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();
        ficha.setId(count.incrementAndGet());

        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fichaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fichaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFicha() throws Exception {
        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();
        ficha.setId(count.incrementAndGet());

        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFichaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fichaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFicha() throws Exception {
        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();
        ficha.setId(count.incrementAndGet());

        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFichaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fichaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFichaWithPatch() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();

        // Update the ficha using partial update
        Ficha partialUpdatedFicha = new Ficha();
        partialUpdatedFicha.setId(ficha.getId());

        partialUpdatedFicha.parcela(UPDATED_PARCELA);

        restFichaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFicha.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFicha))
            )
            .andExpect(status().isOk());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
        Ficha testFicha = fichaList.get(fichaList.size() - 1);
        assertThat(testFicha.getParcela()).isEqualTo(UPDATED_PARCELA);
        assertThat(testFicha.getProcesso()).isEqualTo(DEFAULT_PROCESSO);
    }

    @Test
    @Transactional
    void fullUpdateFichaWithPatch() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();

        // Update the ficha using partial update
        Ficha partialUpdatedFicha = new Ficha();
        partialUpdatedFicha.setId(ficha.getId());

        partialUpdatedFicha.parcela(UPDATED_PARCELA).processo(UPDATED_PROCESSO);

        restFichaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFicha.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFicha))
            )
            .andExpect(status().isOk());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
        Ficha testFicha = fichaList.get(fichaList.size() - 1);
        assertThat(testFicha.getParcela()).isEqualTo(UPDATED_PARCELA);
        assertThat(testFicha.getProcesso()).isEqualTo(UPDATED_PROCESSO);
    }

    @Test
    @Transactional
    void patchNonExistingFicha() throws Exception {
        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();
        ficha.setId(count.incrementAndGet());

        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fichaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fichaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFicha() throws Exception {
        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();
        ficha.setId(count.incrementAndGet());

        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFichaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fichaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFicha() throws Exception {
        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();
        ficha.setId(count.incrementAndGet());

        // Create the Ficha
        FichaDTO fichaDTO = fichaMapper.toDto(ficha);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFichaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fichaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        int databaseSizeBeforeDelete = fichaRepository.findAll().size();

        // Delete the ficha
        restFichaMockMvc
            .perform(delete(ENTITY_API_URL_ID, ficha.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
