package gov.cmcm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.cmcm.IntegrationTest;
import gov.cmcm.domain.Ficha;
import gov.cmcm.domain.SpatialUnit;
import gov.cmcm.repository.SpatialUnitRepository;
import gov.cmcm.service.SpatialUnitService;
import gov.cmcm.service.criteria.SpatialUnitCriteria;
import gov.cmcm.service.dto.SpatialUnitDTO;
import gov.cmcm.service.mapper.SpatialUnitMapper;
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
 * Integration tests for the {@link SpatialUnitResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SpatialUnitResourceIT {

    private static final String DEFAULT_PARCELA = "AAAAAAAAAA";
    private static final String UPDATED_PARCELA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/spatial-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpatialUnitRepository spatialUnitRepository;

    @Mock
    private SpatialUnitRepository spatialUnitRepositoryMock;

    @Autowired
    private SpatialUnitMapper spatialUnitMapper;

    @Mock
    private SpatialUnitService spatialUnitServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpatialUnitMockMvc;

    private SpatialUnit spatialUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpatialUnit createEntity(EntityManager em) {
        SpatialUnit spatialUnit = new SpatialUnit().parcela(DEFAULT_PARCELA);
        return spatialUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpatialUnit createUpdatedEntity(EntityManager em) {
        SpatialUnit spatialUnit = new SpatialUnit().parcela(UPDATED_PARCELA);
        return spatialUnit;
    }

    @BeforeEach
    public void initTest() {
        spatialUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createSpatialUnit() throws Exception {
        int databaseSizeBeforeCreate = spatialUnitRepository.findAll().size();
        // Create the SpatialUnit
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(spatialUnit);
        restSpatialUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeCreate + 1);
        SpatialUnit testSpatialUnit = spatialUnitList.get(spatialUnitList.size() - 1);
        assertThat(testSpatialUnit.getParcela()).isEqualTo(DEFAULT_PARCELA);
    }

    @Test
    @Transactional
    void createSpatialUnitWithExistingId() throws Exception {
        // Create the SpatialUnit with an existing ID
        spatialUnit.setId(1L);
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(spatialUnit);

        int databaseSizeBeforeCreate = spatialUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpatialUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpatialUnits() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        // Get all the spatialUnitList
        restSpatialUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spatialUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].parcela").value(hasItem(DEFAULT_PARCELA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSpatialUnitsWithEagerRelationshipsIsEnabled() throws Exception {
        when(spatialUnitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSpatialUnitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(spatialUnitServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSpatialUnitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(spatialUnitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSpatialUnitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(spatialUnitRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSpatialUnit() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        // Get the spatialUnit
        restSpatialUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, spatialUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(spatialUnit.getId().intValue()))
            .andExpect(jsonPath("$.parcela").value(DEFAULT_PARCELA));
    }

    @Test
    @Transactional
    void getSpatialUnitsByIdFiltering() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        Long id = spatialUnit.getId();

        defaultSpatialUnitShouldBeFound("id.equals=" + id);
        defaultSpatialUnitShouldNotBeFound("id.notEquals=" + id);

        defaultSpatialUnitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSpatialUnitShouldNotBeFound("id.greaterThan=" + id);

        defaultSpatialUnitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSpatialUnitShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSpatialUnitsByParcelaIsEqualToSomething() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        // Get all the spatialUnitList where parcela equals to DEFAULT_PARCELA
        defaultSpatialUnitShouldBeFound("parcela.equals=" + DEFAULT_PARCELA);

        // Get all the spatialUnitList where parcela equals to UPDATED_PARCELA
        defaultSpatialUnitShouldNotBeFound("parcela.equals=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllSpatialUnitsByParcelaIsInShouldWork() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        // Get all the spatialUnitList where parcela in DEFAULT_PARCELA or UPDATED_PARCELA
        defaultSpatialUnitShouldBeFound("parcela.in=" + DEFAULT_PARCELA + "," + UPDATED_PARCELA);

        // Get all the spatialUnitList where parcela equals to UPDATED_PARCELA
        defaultSpatialUnitShouldNotBeFound("parcela.in=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllSpatialUnitsByParcelaIsNullOrNotNull() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        // Get all the spatialUnitList where parcela is not null
        defaultSpatialUnitShouldBeFound("parcela.specified=true");

        // Get all the spatialUnitList where parcela is null
        defaultSpatialUnitShouldNotBeFound("parcela.specified=false");
    }

    @Test
    @Transactional
    void getAllSpatialUnitsByParcelaContainsSomething() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        // Get all the spatialUnitList where parcela contains DEFAULT_PARCELA
        defaultSpatialUnitShouldBeFound("parcela.contains=" + DEFAULT_PARCELA);

        // Get all the spatialUnitList where parcela contains UPDATED_PARCELA
        defaultSpatialUnitShouldNotBeFound("parcela.contains=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllSpatialUnitsByParcelaNotContainsSomething() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        // Get all the spatialUnitList where parcela does not contain DEFAULT_PARCELA
        defaultSpatialUnitShouldNotBeFound("parcela.doesNotContain=" + DEFAULT_PARCELA);

        // Get all the spatialUnitList where parcela does not contain UPDATED_PARCELA
        defaultSpatialUnitShouldBeFound("parcela.doesNotContain=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllSpatialUnitsByFichaIsEqualToSomething() throws Exception {
        Ficha ficha;
        if (TestUtil.findAll(em, Ficha.class).isEmpty()) {
            spatialUnitRepository.saveAndFlush(spatialUnit);
            ficha = FichaResourceIT.createEntity(em);
        } else {
            ficha = TestUtil.findAll(em, Ficha.class).get(0);
        }
        em.persist(ficha);
        em.flush();
        spatialUnit.setFicha(ficha);
        spatialUnitRepository.saveAndFlush(spatialUnit);
        Long fichaId = ficha.getId();

        // Get all the spatialUnitList where ficha equals to fichaId
        defaultSpatialUnitShouldBeFound("fichaId.equals=" + fichaId);

        // Get all the spatialUnitList where ficha equals to (fichaId + 1)
        defaultSpatialUnitShouldNotBeFound("fichaId.equals=" + (fichaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSpatialUnitShouldBeFound(String filter) throws Exception {
        restSpatialUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spatialUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].parcela").value(hasItem(DEFAULT_PARCELA)));

        // Check, that the count call also returns 1
        restSpatialUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSpatialUnitShouldNotBeFound(String filter) throws Exception {
        restSpatialUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSpatialUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSpatialUnit() throws Exception {
        // Get the spatialUnit
        restSpatialUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpatialUnit() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();

        // Update the spatialUnit
        SpatialUnit updatedSpatialUnit = spatialUnitRepository.findById(spatialUnit.getId()).get();
        // Disconnect from session so that the updates on updatedSpatialUnit are not directly saved in db
        em.detach(updatedSpatialUnit);
        updatedSpatialUnit.parcela(UPDATED_PARCELA);
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(updatedSpatialUnit);

        restSpatialUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, spatialUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
        SpatialUnit testSpatialUnit = spatialUnitList.get(spatialUnitList.size() - 1);
        assertThat(testSpatialUnit.getParcela()).isEqualTo(UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void putNonExistingSpatialUnit() throws Exception {
        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();
        spatialUnit.setId(count.incrementAndGet());

        // Create the SpatialUnit
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(spatialUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpatialUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, spatialUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpatialUnit() throws Exception {
        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();
        spatialUnit.setId(count.incrementAndGet());

        // Create the SpatialUnit
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(spatialUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpatialUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpatialUnit() throws Exception {
        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();
        spatialUnit.setId(count.incrementAndGet());

        // Create the SpatialUnit
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(spatialUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpatialUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpatialUnitWithPatch() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();

        // Update the spatialUnit using partial update
        SpatialUnit partialUpdatedSpatialUnit = new SpatialUnit();
        partialUpdatedSpatialUnit.setId(spatialUnit.getId());

        partialUpdatedSpatialUnit.parcela(UPDATED_PARCELA);

        restSpatialUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpatialUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpatialUnit))
            )
            .andExpect(status().isOk());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
        SpatialUnit testSpatialUnit = spatialUnitList.get(spatialUnitList.size() - 1);
        assertThat(testSpatialUnit.getParcela()).isEqualTo(UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void fullUpdateSpatialUnitWithPatch() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();

        // Update the spatialUnit using partial update
        SpatialUnit partialUpdatedSpatialUnit = new SpatialUnit();
        partialUpdatedSpatialUnit.setId(spatialUnit.getId());

        partialUpdatedSpatialUnit.parcela(UPDATED_PARCELA);

        restSpatialUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpatialUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpatialUnit))
            )
            .andExpect(status().isOk());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
        SpatialUnit testSpatialUnit = spatialUnitList.get(spatialUnitList.size() - 1);
        assertThat(testSpatialUnit.getParcela()).isEqualTo(UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void patchNonExistingSpatialUnit() throws Exception {
        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();
        spatialUnit.setId(count.incrementAndGet());

        // Create the SpatialUnit
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(spatialUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpatialUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, spatialUnitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpatialUnit() throws Exception {
        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();
        spatialUnit.setId(count.incrementAndGet());

        // Create the SpatialUnit
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(spatialUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpatialUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpatialUnit() throws Exception {
        int databaseSizeBeforeUpdate = spatialUnitRepository.findAll().size();
        spatialUnit.setId(count.incrementAndGet());

        // Create the SpatialUnit
        SpatialUnitDTO spatialUnitDTO = spatialUnitMapper.toDto(spatialUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpatialUnitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(spatialUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpatialUnit in the database
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpatialUnit() throws Exception {
        // Initialize the database
        spatialUnitRepository.saveAndFlush(spatialUnit);

        int databaseSizeBeforeDelete = spatialUnitRepository.findAll().size();

        // Delete the spatialUnit
        restSpatialUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, spatialUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpatialUnit> spatialUnitList = spatialUnitRepository.findAll();
        assertThat(spatialUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
