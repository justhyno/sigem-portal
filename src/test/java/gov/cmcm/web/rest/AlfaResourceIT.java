package gov.cmcm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.cmcm.IntegrationTest;
import gov.cmcm.domain.Alfa;
import gov.cmcm.domain.Ficha;
import gov.cmcm.repository.AlfaRepository;
import gov.cmcm.service.AlfaService;
import gov.cmcm.service.criteria.AlfaCriteria;
import gov.cmcm.service.dto.AlfaDTO;
import gov.cmcm.service.mapper.AlfaMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AlfaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AlfaResourceIT {

    private static final String DEFAULT_PARCELA = "AAAAAAAAAA";
    private static final String UPDATED_PARCELA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_LEVANTAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_LEVANTAMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_LEVANTAMENTO = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/alfas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlfaRepository alfaRepository;

    @Mock
    private AlfaRepository alfaRepositoryMock;

    @Autowired
    private AlfaMapper alfaMapper;

    @Mock
    private AlfaService alfaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlfaMockMvc;

    private Alfa alfa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alfa createEntity(EntityManager em) {
        Alfa alfa = new Alfa().parcela(DEFAULT_PARCELA).dataLevantamento(DEFAULT_DATA_LEVANTAMENTO);
        return alfa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alfa createUpdatedEntity(EntityManager em) {
        Alfa alfa = new Alfa().parcela(UPDATED_PARCELA).dataLevantamento(UPDATED_DATA_LEVANTAMENTO);
        return alfa;
    }

    @BeforeEach
    public void initTest() {
        alfa = createEntity(em);
    }

    @Test
    @Transactional
    void createAlfa() throws Exception {
        int databaseSizeBeforeCreate = alfaRepository.findAll().size();
        // Create the Alfa
        AlfaDTO alfaDTO = alfaMapper.toDto(alfa);
        restAlfaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alfaDTO)))
            .andExpect(status().isCreated());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeCreate + 1);
        Alfa testAlfa = alfaList.get(alfaList.size() - 1);
        assertThat(testAlfa.getParcela()).isEqualTo(DEFAULT_PARCELA);
        assertThat(testAlfa.getDataLevantamento()).isEqualTo(DEFAULT_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void createAlfaWithExistingId() throws Exception {
        // Create the Alfa with an existing ID
        alfa.setId(1L);
        AlfaDTO alfaDTO = alfaMapper.toDto(alfa);

        int databaseSizeBeforeCreate = alfaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlfaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alfaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAlfas() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList
        restAlfaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alfa.getId().intValue())))
            .andExpect(jsonPath("$.[*].parcela").value(hasItem(DEFAULT_PARCELA)))
            .andExpect(jsonPath("$.[*].dataLevantamento").value(hasItem(DEFAULT_DATA_LEVANTAMENTO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAlfasWithEagerRelationshipsIsEnabled() throws Exception {
        when(alfaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAlfaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(alfaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAlfasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(alfaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAlfaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(alfaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAlfa() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get the alfa
        restAlfaMockMvc
            .perform(get(ENTITY_API_URL_ID, alfa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alfa.getId().intValue()))
            .andExpect(jsonPath("$.parcela").value(DEFAULT_PARCELA))
            .andExpect(jsonPath("$.dataLevantamento").value(DEFAULT_DATA_LEVANTAMENTO.toString()));
    }

    @Test
    @Transactional
    void getAlfasByIdFiltering() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        Long id = alfa.getId();

        defaultAlfaShouldBeFound("id.equals=" + id);
        defaultAlfaShouldNotBeFound("id.notEquals=" + id);

        defaultAlfaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlfaShouldNotBeFound("id.greaterThan=" + id);

        defaultAlfaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlfaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAlfasByParcelaIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where parcela equals to DEFAULT_PARCELA
        defaultAlfaShouldBeFound("parcela.equals=" + DEFAULT_PARCELA);

        // Get all the alfaList where parcela equals to UPDATED_PARCELA
        defaultAlfaShouldNotBeFound("parcela.equals=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllAlfasByParcelaIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where parcela in DEFAULT_PARCELA or UPDATED_PARCELA
        defaultAlfaShouldBeFound("parcela.in=" + DEFAULT_PARCELA + "," + UPDATED_PARCELA);

        // Get all the alfaList where parcela equals to UPDATED_PARCELA
        defaultAlfaShouldNotBeFound("parcela.in=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllAlfasByParcelaIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where parcela is not null
        defaultAlfaShouldBeFound("parcela.specified=true");

        // Get all the alfaList where parcela is null
        defaultAlfaShouldNotBeFound("parcela.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByParcelaContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where parcela contains DEFAULT_PARCELA
        defaultAlfaShouldBeFound("parcela.contains=" + DEFAULT_PARCELA);

        // Get all the alfaList where parcela contains UPDATED_PARCELA
        defaultAlfaShouldNotBeFound("parcela.contains=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllAlfasByParcelaNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where parcela does not contain DEFAULT_PARCELA
        defaultAlfaShouldNotBeFound("parcela.doesNotContain=" + DEFAULT_PARCELA);

        // Get all the alfaList where parcela does not contain UPDATED_PARCELA
        defaultAlfaShouldBeFound("parcela.doesNotContain=" + UPDATED_PARCELA);
    }

    @Test
    @Transactional
    void getAllAlfasByDataLevantamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataLevantamento equals to DEFAULT_DATA_LEVANTAMENTO
        defaultAlfaShouldBeFound("dataLevantamento.equals=" + DEFAULT_DATA_LEVANTAMENTO);

        // Get all the alfaList where dataLevantamento equals to UPDATED_DATA_LEVANTAMENTO
        defaultAlfaShouldNotBeFound("dataLevantamento.equals=" + UPDATED_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataLevantamentoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataLevantamento in DEFAULT_DATA_LEVANTAMENTO or UPDATED_DATA_LEVANTAMENTO
        defaultAlfaShouldBeFound("dataLevantamento.in=" + DEFAULT_DATA_LEVANTAMENTO + "," + UPDATED_DATA_LEVANTAMENTO);

        // Get all the alfaList where dataLevantamento equals to UPDATED_DATA_LEVANTAMENTO
        defaultAlfaShouldNotBeFound("dataLevantamento.in=" + UPDATED_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataLevantamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataLevantamento is not null
        defaultAlfaShouldBeFound("dataLevantamento.specified=true");

        // Get all the alfaList where dataLevantamento is null
        defaultAlfaShouldNotBeFound("dataLevantamento.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByDataLevantamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataLevantamento is greater than or equal to DEFAULT_DATA_LEVANTAMENTO
        defaultAlfaShouldBeFound("dataLevantamento.greaterThanOrEqual=" + DEFAULT_DATA_LEVANTAMENTO);

        // Get all the alfaList where dataLevantamento is greater than or equal to UPDATED_DATA_LEVANTAMENTO
        defaultAlfaShouldNotBeFound("dataLevantamento.greaterThanOrEqual=" + UPDATED_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataLevantamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataLevantamento is less than or equal to DEFAULT_DATA_LEVANTAMENTO
        defaultAlfaShouldBeFound("dataLevantamento.lessThanOrEqual=" + DEFAULT_DATA_LEVANTAMENTO);

        // Get all the alfaList where dataLevantamento is less than or equal to SMALLER_DATA_LEVANTAMENTO
        defaultAlfaShouldNotBeFound("dataLevantamento.lessThanOrEqual=" + SMALLER_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataLevantamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataLevantamento is less than DEFAULT_DATA_LEVANTAMENTO
        defaultAlfaShouldNotBeFound("dataLevantamento.lessThan=" + DEFAULT_DATA_LEVANTAMENTO);

        // Get all the alfaList where dataLevantamento is less than UPDATED_DATA_LEVANTAMENTO
        defaultAlfaShouldBeFound("dataLevantamento.lessThan=" + UPDATED_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataLevantamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataLevantamento is greater than DEFAULT_DATA_LEVANTAMENTO
        defaultAlfaShouldNotBeFound("dataLevantamento.greaterThan=" + DEFAULT_DATA_LEVANTAMENTO);

        // Get all the alfaList where dataLevantamento is greater than SMALLER_DATA_LEVANTAMENTO
        defaultAlfaShouldBeFound("dataLevantamento.greaterThan=" + SMALLER_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByFichaIsEqualToSomething() throws Exception {
        Ficha ficha;
        if (TestUtil.findAll(em, Ficha.class).isEmpty()) {
            alfaRepository.saveAndFlush(alfa);
            ficha = FichaResourceIT.createEntity(em);
        } else {
            ficha = TestUtil.findAll(em, Ficha.class).get(0);
        }
        em.persist(ficha);
        em.flush();
        alfa.setFicha(ficha);
        alfaRepository.saveAndFlush(alfa);
        Long fichaId = ficha.getId();

        // Get all the alfaList where ficha equals to fichaId
        defaultAlfaShouldBeFound("fichaId.equals=" + fichaId);

        // Get all the alfaList where ficha equals to (fichaId + 1)
        defaultAlfaShouldNotBeFound("fichaId.equals=" + (fichaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlfaShouldBeFound(String filter) throws Exception {
        restAlfaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alfa.getId().intValue())))
            .andExpect(jsonPath("$.[*].parcela").value(hasItem(DEFAULT_PARCELA)))
            .andExpect(jsonPath("$.[*].dataLevantamento").value(hasItem(DEFAULT_DATA_LEVANTAMENTO.toString())));

        // Check, that the count call also returns 1
        restAlfaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlfaShouldNotBeFound(String filter) throws Exception {
        restAlfaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlfaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAlfa() throws Exception {
        // Get the alfa
        restAlfaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlfa() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();

        // Update the alfa
        Alfa updatedAlfa = alfaRepository.findById(alfa.getId()).get();
        // Disconnect from session so that the updates on updatedAlfa are not directly saved in db
        em.detach(updatedAlfa);
        updatedAlfa.parcela(UPDATED_PARCELA).dataLevantamento(UPDATED_DATA_LEVANTAMENTO);
        AlfaDTO alfaDTO = alfaMapper.toDto(updatedAlfa);

        restAlfaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alfaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alfaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
        Alfa testAlfa = alfaList.get(alfaList.size() - 1);
        assertThat(testAlfa.getParcela()).isEqualTo(UPDATED_PARCELA);
        assertThat(testAlfa.getDataLevantamento()).isEqualTo(UPDATED_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void putNonExistingAlfa() throws Exception {
        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();
        alfa.setId(count.incrementAndGet());

        // Create the Alfa
        AlfaDTO alfaDTO = alfaMapper.toDto(alfa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlfaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alfaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alfaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlfa() throws Exception {
        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();
        alfa.setId(count.incrementAndGet());

        // Create the Alfa
        AlfaDTO alfaDTO = alfaMapper.toDto(alfa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlfaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alfaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlfa() throws Exception {
        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();
        alfa.setId(count.incrementAndGet());

        // Create the Alfa
        AlfaDTO alfaDTO = alfaMapper.toDto(alfa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlfaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alfaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlfaWithPatch() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();

        // Update the alfa using partial update
        Alfa partialUpdatedAlfa = new Alfa();
        partialUpdatedAlfa.setId(alfa.getId());

        restAlfaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlfa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlfa))
            )
            .andExpect(status().isOk());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
        Alfa testAlfa = alfaList.get(alfaList.size() - 1);
        assertThat(testAlfa.getParcela()).isEqualTo(DEFAULT_PARCELA);
        assertThat(testAlfa.getDataLevantamento()).isEqualTo(DEFAULT_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void fullUpdateAlfaWithPatch() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();

        // Update the alfa using partial update
        Alfa partialUpdatedAlfa = new Alfa();
        partialUpdatedAlfa.setId(alfa.getId());

        partialUpdatedAlfa.parcela(UPDATED_PARCELA).dataLevantamento(UPDATED_DATA_LEVANTAMENTO);

        restAlfaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlfa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlfa))
            )
            .andExpect(status().isOk());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
        Alfa testAlfa = alfaList.get(alfaList.size() - 1);
        assertThat(testAlfa.getParcela()).isEqualTo(UPDATED_PARCELA);
        assertThat(testAlfa.getDataLevantamento()).isEqualTo(UPDATED_DATA_LEVANTAMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingAlfa() throws Exception {
        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();
        alfa.setId(count.incrementAndGet());

        // Create the Alfa
        AlfaDTO alfaDTO = alfaMapper.toDto(alfa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlfaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alfaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alfaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlfa() throws Exception {
        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();
        alfa.setId(count.incrementAndGet());

        // Create the Alfa
        AlfaDTO alfaDTO = alfaMapper.toDto(alfa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlfaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alfaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlfa() throws Exception {
        int databaseSizeBeforeUpdate = alfaRepository.findAll().size();
        alfa.setId(count.incrementAndGet());

        // Create the Alfa
        AlfaDTO alfaDTO = alfaMapper.toDto(alfa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlfaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alfaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alfa in the database
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlfa() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        int databaseSizeBeforeDelete = alfaRepository.findAll().size();

        // Delete the alfa
        restAlfaMockMvc
            .perform(delete(ENTITY_API_URL_ID, alfa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alfa> alfaList = alfaRepository.findAll();
        assertThat(alfaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
