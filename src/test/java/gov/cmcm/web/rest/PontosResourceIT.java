package gov.cmcm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.cmcm.IntegrationTest;
import gov.cmcm.domain.Pontos;
import gov.cmcm.repository.PontosRepository;
import gov.cmcm.service.dto.PontosDTO;
import gov.cmcm.service.mapper.PontosMapper;
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
 * Integration tests for the {@link PontosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PontosResourceIT {

    private static final String DEFAULT_PARCELA = "AAAAAAAAAA";
    private static final String UPDATED_PARCELA = "BBBBBBBBBB";

    private static final Double DEFAULT_POINT_X = 1D;
    private static final Double UPDATED_POINT_X = 2D;

    private static final Double DEFAULT_POINT_Y = 1D;
    private static final Double UPDATED_POINT_Y = 2D;

    private static final String DEFAULT_MARCO = "AAAAAAAAAA";
    private static final String UPDATED_MARCO = "BBBBBBBBBB";

    private static final String DEFAULT_ZONA = "AAAAAAAAAA";
    private static final String UPDATED_ZONA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pontos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PontosRepository pontosRepository;

    @Autowired
    private PontosMapper pontosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPontosMockMvc;

    private Pontos pontos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pontos createEntity(EntityManager em) {
        Pontos pontos = new Pontos()
            .parcela(DEFAULT_PARCELA)
            .pointX(DEFAULT_POINT_X)
            .pointY(DEFAULT_POINT_Y)
            .marco(DEFAULT_MARCO)
            .zona(DEFAULT_ZONA);
        return pontos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pontos createUpdatedEntity(EntityManager em) {
        Pontos pontos = new Pontos()
            .parcela(UPDATED_PARCELA)
            .pointX(UPDATED_POINT_X)
            .pointY(UPDATED_POINT_Y)
            .marco(UPDATED_MARCO)
            .zona(UPDATED_ZONA);
        return pontos;
    }

    @BeforeEach
    public void initTest() {
        pontos = createEntity(em);
    }

    @Test
    @Transactional
    void createPontos() throws Exception {
        int databaseSizeBeforeCreate = pontosRepository.findAll().size();
        // Create the Pontos
        PontosDTO pontosDTO = pontosMapper.toDto(pontos);
        restPontosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pontosDTO)))
            .andExpect(status().isCreated());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeCreate + 1);
        Pontos testPontos = pontosList.get(pontosList.size() - 1);
        assertThat(testPontos.getParcela()).isEqualTo(DEFAULT_PARCELA);
        assertThat(testPontos.getPointX()).isEqualTo(DEFAULT_POINT_X);
        assertThat(testPontos.getPointY()).isEqualTo(DEFAULT_POINT_Y);
        assertThat(testPontos.getMarco()).isEqualTo(DEFAULT_MARCO);
        assertThat(testPontos.getZona()).isEqualTo(DEFAULT_ZONA);
    }

    @Test
    @Transactional
    void createPontosWithExistingId() throws Exception {
        // Create the Pontos with an existing ID
        pontos.setId(1L);
        PontosDTO pontosDTO = pontosMapper.toDto(pontos);

        int databaseSizeBeforeCreate = pontosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPontosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pontosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPontos() throws Exception {
        // Initialize the database
        pontosRepository.saveAndFlush(pontos);

        // Get all the pontosList
        restPontosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pontos.getId().intValue())))
            .andExpect(jsonPath("$.[*].parcela").value(hasItem(DEFAULT_PARCELA)))
            .andExpect(jsonPath("$.[*].pointX").value(hasItem(DEFAULT_POINT_X.doubleValue())))
            .andExpect(jsonPath("$.[*].pointY").value(hasItem(DEFAULT_POINT_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].marco").value(hasItem(DEFAULT_MARCO)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA)));
    }

    @Test
    @Transactional
    void getPontos() throws Exception {
        // Initialize the database
        pontosRepository.saveAndFlush(pontos);

        // Get the pontos
        restPontosMockMvc
            .perform(get(ENTITY_API_URL_ID, pontos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pontos.getId().intValue()))
            .andExpect(jsonPath("$.parcela").value(DEFAULT_PARCELA))
            .andExpect(jsonPath("$.pointX").value(DEFAULT_POINT_X.doubleValue()))
            .andExpect(jsonPath("$.pointY").value(DEFAULT_POINT_Y.doubleValue()))
            .andExpect(jsonPath("$.marco").value(DEFAULT_MARCO))
            .andExpect(jsonPath("$.zona").value(DEFAULT_ZONA));
    }

    @Test
    @Transactional
    void getNonExistingPontos() throws Exception {
        // Get the pontos
        restPontosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPontos() throws Exception {
        // Initialize the database
        pontosRepository.saveAndFlush(pontos);

        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();

        // Update the pontos
        Pontos updatedPontos = pontosRepository.findById(pontos.getId()).get();
        // Disconnect from session so that the updates on updatedPontos are not directly saved in db
        em.detach(updatedPontos);
        updatedPontos.parcela(UPDATED_PARCELA).pointX(UPDATED_POINT_X).pointY(UPDATED_POINT_Y).marco(UPDATED_MARCO).zona(UPDATED_ZONA);
        PontosDTO pontosDTO = pontosMapper.toDto(updatedPontos);

        restPontosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pontosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pontosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
        Pontos testPontos = pontosList.get(pontosList.size() - 1);
        assertThat(testPontos.getParcela()).isEqualTo(UPDATED_PARCELA);
        assertThat(testPontos.getPointX()).isEqualTo(UPDATED_POINT_X);
        assertThat(testPontos.getPointY()).isEqualTo(UPDATED_POINT_Y);
        assertThat(testPontos.getMarco()).isEqualTo(UPDATED_MARCO);
        assertThat(testPontos.getZona()).isEqualTo(UPDATED_ZONA);
    }

    @Test
    @Transactional
    void putNonExistingPontos() throws Exception {
        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();
        pontos.setId(count.incrementAndGet());

        // Create the Pontos
        PontosDTO pontosDTO = pontosMapper.toDto(pontos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPontosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pontosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pontosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPontos() throws Exception {
        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();
        pontos.setId(count.incrementAndGet());

        // Create the Pontos
        PontosDTO pontosDTO = pontosMapper.toDto(pontos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPontosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pontosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPontos() throws Exception {
        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();
        pontos.setId(count.incrementAndGet());

        // Create the Pontos
        PontosDTO pontosDTO = pontosMapper.toDto(pontos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPontosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pontosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePontosWithPatch() throws Exception {
        // Initialize the database
        pontosRepository.saveAndFlush(pontos);

        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();

        // Update the pontos using partial update
        Pontos partialUpdatedPontos = new Pontos();
        partialUpdatedPontos.setId(pontos.getId());

        partialUpdatedPontos.parcela(UPDATED_PARCELA).pointX(UPDATED_POINT_X);

        restPontosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPontos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPontos))
            )
            .andExpect(status().isOk());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
        Pontos testPontos = pontosList.get(pontosList.size() - 1);
        assertThat(testPontos.getParcela()).isEqualTo(UPDATED_PARCELA);
        assertThat(testPontos.getPointX()).isEqualTo(UPDATED_POINT_X);
        assertThat(testPontos.getPointY()).isEqualTo(DEFAULT_POINT_Y);
        assertThat(testPontos.getMarco()).isEqualTo(DEFAULT_MARCO);
        assertThat(testPontos.getZona()).isEqualTo(DEFAULT_ZONA);
    }

    @Test
    @Transactional
    void fullUpdatePontosWithPatch() throws Exception {
        // Initialize the database
        pontosRepository.saveAndFlush(pontos);

        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();

        // Update the pontos using partial update
        Pontos partialUpdatedPontos = new Pontos();
        partialUpdatedPontos.setId(pontos.getId());

        partialUpdatedPontos
            .parcela(UPDATED_PARCELA)
            .pointX(UPDATED_POINT_X)
            .pointY(UPDATED_POINT_Y)
            .marco(UPDATED_MARCO)
            .zona(UPDATED_ZONA);

        restPontosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPontos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPontos))
            )
            .andExpect(status().isOk());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
        Pontos testPontos = pontosList.get(pontosList.size() - 1);
        assertThat(testPontos.getParcela()).isEqualTo(UPDATED_PARCELA);
        assertThat(testPontos.getPointX()).isEqualTo(UPDATED_POINT_X);
        assertThat(testPontos.getPointY()).isEqualTo(UPDATED_POINT_Y);
        assertThat(testPontos.getMarco()).isEqualTo(UPDATED_MARCO);
        assertThat(testPontos.getZona()).isEqualTo(UPDATED_ZONA);
    }

    @Test
    @Transactional
    void patchNonExistingPontos() throws Exception {
        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();
        pontos.setId(count.incrementAndGet());

        // Create the Pontos
        PontosDTO pontosDTO = pontosMapper.toDto(pontos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPontosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pontosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pontosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPontos() throws Exception {
        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();
        pontos.setId(count.incrementAndGet());

        // Create the Pontos
        PontosDTO pontosDTO = pontosMapper.toDto(pontos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPontosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pontosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPontos() throws Exception {
        int databaseSizeBeforeUpdate = pontosRepository.findAll().size();
        pontos.setId(count.incrementAndGet());

        // Create the Pontos
        PontosDTO pontosDTO = pontosMapper.toDto(pontos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPontosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pontosDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pontos in the database
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePontos() throws Exception {
        // Initialize the database
        pontosRepository.saveAndFlush(pontos);

        int databaseSizeBeforeDelete = pontosRepository.findAll().size();

        // Delete the pontos
        restPontosMockMvc
            .perform(delete(ENTITY_API_URL_ID, pontos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pontos> pontosList = pontosRepository.findAll();
        assertThat(pontosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
