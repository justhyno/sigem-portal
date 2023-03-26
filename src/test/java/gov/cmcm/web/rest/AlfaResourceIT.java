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

    private static final String DEFAULT_TIPO_TITULAR = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_TITULAR = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_TITULAR = "AAAAAAAAAA";
    private static final String UPDATED_NOME_TITULAR = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_SOCIAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_NASCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DAT_EMISSAO = "AAAAAAAAAA";
    private static final String UPDATED_DAT_EMISSAO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_EMISSAO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_EMISSAO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_ALTERNATIVO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_ALTERNATIVO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_CIVIL = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_CIVIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_CONJUGUE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CONJUGUE = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRITO_MUNICIPAL = "AAAAAAAAAA";
    private static final String UPDATED_DISTRITO_MUNICIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_QUATREIRAO = "AAAAAAAAAA";
    private static final String UPDATED_QUATREIRAO = "BBBBBBBBBB";

    private static final String DEFAULT_TALHAO = "AAAAAAAAAA";
    private static final String UPDATED_TALHAO = "BBBBBBBBBB";

    private static final String DEFAULT_CELULA = "AAAAAAAAAA";
    private static final String UPDATED_CELULA = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCO = "AAAAAAAAAA";
    private static final String UPDATED_BLOCO = "BBBBBBBBBB";

    private static final String DEFAULT_AVENIDA = "AAAAAAAAAA";
    private static final String UPDATED_AVENIDA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_USO_ACTUAL = "AAAAAAAAAA";
    private static final String UPDATED_USO_ACTUAL = "BBBBBBBBBB";

    private static final String DEFAULT_FORMA_USO = "AAAAAAAAAA";
    private static final String UPDATED_FORMA_USO = "BBBBBBBBBB";

    private static final String DEFAULT_FORMA_OBTENCAO = "AAAAAAAAAA";
    private static final String UPDATED_FORMA_OBTENCAO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO_OCUPACAO = 1;
    private static final Integer UPDATED_ANO_OCUPACAO = 2;
    private static final Integer SMALLER_ANO_OCUPACAO = 1 - 1;

    private static final String DEFAULT_TIPO_ACESSO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ACESSO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONFLITO = false;
    private static final Boolean UPDATED_CONFLITO = true;

    private static final Boolean DEFAULT_DETALHES_CONFLITO = false;
    private static final Boolean UPDATED_DETALHES_CONFLITO = true;

    private static final Boolean DEFAULT_CONSTRUCAO_PRECARIA = false;
    private static final Boolean UPDATED_CONSTRUCAO_PRECARIA = true;

    private static final Integer DEFAULT_PISOS_ACIMA_SOLEIRA = 1;
    private static final Integer UPDATED_PISOS_ACIMA_SOLEIRA = 2;
    private static final Integer SMALLER_PISOS_ACIMA_SOLEIRA = 1 - 1;

    private static final Integer DEFAULT_PISOS_ABAIXO_SOLEIRA = 1;
    private static final Integer UPDATED_PISOS_ABAIXO_SOLEIRA = 2;
    private static final Integer SMALLER_PISOS_ABAIXO_SOLEIRA = 1 - 1;

    private static final Boolean DEFAULT_MATERIAL_CONSTRUCAO_BARROTE = false;
    private static final Boolean UPDATED_MATERIAL_CONSTRUCAO_BARROTE = true;

    private static final Boolean DEFAULT_MATERIAL_CONSTRUCAO_IBR = false;
    private static final Boolean UPDATED_MATERIAL_CONSTRUCAO_IBR = true;

    private static final Boolean DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS = false;
    private static final Boolean UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS = true;

    private static final Boolean DEFAULT_MATERIAL_CONSTRUCAO_PAU = false;
    private static final Boolean UPDATED_MATERIAL_CONSTRUCAO_PAU = true;

    private static final Boolean DEFAULT_MATERIAL_CONSTRUCAO_CANICO = false;
    private static final Boolean UPDATED_MATERIAL_CONSTRUCAO_CANICO = true;

    private static final Boolean DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO = false;
    private static final Boolean UPDATED_MATERIAL_CONSTRUCAO_CIMENTO = true;

    private static final String DEFAULT_OCUPACAO = "AAAAAAAAAA";
    private static final String UPDATED_OCUPACAO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CONTRUCAO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CONTRUCAO = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHES_TIPO_CONTRUCAO = "AAAAAAAAAA";
    private static final String UPDATED_DETALHES_TIPO_CONTRUCAO = "BBBBBBBBBB";

    private static final String DEFAULT_INFRAESTRUTURA_EXISTENTE = "AAAAAAAAAA";
    private static final String UPDATED_INFRAESTRUTURA_EXISTENTE = "BBBBBBBBBB";

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
        Alfa alfa = new Alfa()
            .parcela(DEFAULT_PARCELA)
            .tipoTitular(DEFAULT_TIPO_TITULAR)
            .nomeTitular(DEFAULT_NOME_TITULAR)
            .estadoSocial(DEFAULT_ESTADO_SOCIAL)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .sexo(DEFAULT_SEXO)
            .documento(DEFAULT_DOCUMENTO)
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .datEmissao(DEFAULT_DAT_EMISSAO)
            .localEmissao(DEFAULT_LOCAL_EMISSAO)
            .contactoPrincipal(DEFAULT_CONTACTO_PRINCIPAL)
            .contactoAlternativo(DEFAULT_CONTACTO_ALTERNATIVO)
            .estadoCivil(DEFAULT_ESTADO_CIVIL)
            .nomeConjugue(DEFAULT_NOME_CONJUGUE)
            .distritoMunicipal(DEFAULT_DISTRITO_MUNICIPAL)
            .bairro(DEFAULT_BAIRRO)
            .quatreirao(DEFAULT_QUATREIRAO)
            .talhao(DEFAULT_TALHAO)
            .celula(DEFAULT_CELULA)
            .bloco(DEFAULT_BLOCO)
            .avenida(DEFAULT_AVENIDA)
            .numeroPolicia(DEFAULT_NUMERO_POLICIA)
            .usoActual(DEFAULT_USO_ACTUAL)
            .formaUso(DEFAULT_FORMA_USO)
            .formaObtencao(DEFAULT_FORMA_OBTENCAO)
            .tipo(DEFAULT_TIPO)
            .anoOcupacao(DEFAULT_ANO_OCUPACAO)
            .tipoAcesso(DEFAULT_TIPO_ACESSO)
            .conflito(DEFAULT_CONFLITO)
            .detalhesConflito(DEFAULT_DETALHES_CONFLITO)
            .construcaoPrecaria(DEFAULT_CONSTRUCAO_PRECARIA)
            .pisosAcimaSoleira(DEFAULT_PISOS_ACIMA_SOLEIRA)
            .pisosAbaixoSoleira(DEFAULT_PISOS_ABAIXO_SOLEIRA)
            .materialConstrucaoBarrote(DEFAULT_MATERIAL_CONSTRUCAO_BARROTE)
            .materialConstrucaoIBR(DEFAULT_MATERIAL_CONSTRUCAO_IBR)
            .materialConstrucaoPranchas(DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS)
            .materialConstrucaoPau(DEFAULT_MATERIAL_CONSTRUCAO_PAU)
            .materialConstrucaoCanico(DEFAULT_MATERIAL_CONSTRUCAO_CANICO)
            .materialConstrucaoCimento(DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO)
            .ocupacao(DEFAULT_OCUPACAO)
            .TipoContrucao(DEFAULT_TIPO_CONTRUCAO)
            .detalhesTipoContrucao(DEFAULT_DETALHES_TIPO_CONTRUCAO)
            .infraestruturaExistente(DEFAULT_INFRAESTRUTURA_EXISTENTE)
            .dataLevantamento(DEFAULT_DATA_LEVANTAMENTO);
        return alfa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alfa createUpdatedEntity(EntityManager em) {
        Alfa alfa = new Alfa()
            .parcela(UPDATED_PARCELA)
            .tipoTitular(UPDATED_TIPO_TITULAR)
            .nomeTitular(UPDATED_NOME_TITULAR)
            .estadoSocial(UPDATED_ESTADO_SOCIAL)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .sexo(UPDATED_SEXO)
            .documento(UPDATED_DOCUMENTO)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .datEmissao(UPDATED_DAT_EMISSAO)
            .localEmissao(UPDATED_LOCAL_EMISSAO)
            .contactoPrincipal(UPDATED_CONTACTO_PRINCIPAL)
            .contactoAlternativo(UPDATED_CONTACTO_ALTERNATIVO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .nomeConjugue(UPDATED_NOME_CONJUGUE)
            .distritoMunicipal(UPDATED_DISTRITO_MUNICIPAL)
            .bairro(UPDATED_BAIRRO)
            .quatreirao(UPDATED_QUATREIRAO)
            .talhao(UPDATED_TALHAO)
            .celula(UPDATED_CELULA)
            .bloco(UPDATED_BLOCO)
            .avenida(UPDATED_AVENIDA)
            .numeroPolicia(UPDATED_NUMERO_POLICIA)
            .usoActual(UPDATED_USO_ACTUAL)
            .formaUso(UPDATED_FORMA_USO)
            .formaObtencao(UPDATED_FORMA_OBTENCAO)
            .tipo(UPDATED_TIPO)
            .anoOcupacao(UPDATED_ANO_OCUPACAO)
            .tipoAcesso(UPDATED_TIPO_ACESSO)
            .conflito(UPDATED_CONFLITO)
            .detalhesConflito(UPDATED_DETALHES_CONFLITO)
            .construcaoPrecaria(UPDATED_CONSTRUCAO_PRECARIA)
            .pisosAcimaSoleira(UPDATED_PISOS_ACIMA_SOLEIRA)
            .pisosAbaixoSoleira(UPDATED_PISOS_ABAIXO_SOLEIRA)
            .materialConstrucaoBarrote(UPDATED_MATERIAL_CONSTRUCAO_BARROTE)
            .materialConstrucaoIBR(UPDATED_MATERIAL_CONSTRUCAO_IBR)
            .materialConstrucaoPranchas(UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS)
            .materialConstrucaoPau(UPDATED_MATERIAL_CONSTRUCAO_PAU)
            .materialConstrucaoCanico(UPDATED_MATERIAL_CONSTRUCAO_CANICO)
            .materialConstrucaoCimento(UPDATED_MATERIAL_CONSTRUCAO_CIMENTO)
            .ocupacao(UPDATED_OCUPACAO)
            .TipoContrucao(UPDATED_TIPO_CONTRUCAO)
            .detalhesTipoContrucao(UPDATED_DETALHES_TIPO_CONTRUCAO)
            .infraestruturaExistente(UPDATED_INFRAESTRUTURA_EXISTENTE)
            .dataLevantamento(UPDATED_DATA_LEVANTAMENTO);
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
        assertThat(testAlfa.getTipoTitular()).isEqualTo(DEFAULT_TIPO_TITULAR);
        assertThat(testAlfa.getNomeTitular()).isEqualTo(DEFAULT_NOME_TITULAR);
        assertThat(testAlfa.getEstadoSocial()).isEqualTo(DEFAULT_ESTADO_SOCIAL);
        assertThat(testAlfa.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testAlfa.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testAlfa.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testAlfa.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
        assertThat(testAlfa.getDatEmissao()).isEqualTo(DEFAULT_DAT_EMISSAO);
        assertThat(testAlfa.getLocalEmissao()).isEqualTo(DEFAULT_LOCAL_EMISSAO);
        assertThat(testAlfa.getContactoPrincipal()).isEqualTo(DEFAULT_CONTACTO_PRINCIPAL);
        assertThat(testAlfa.getContactoAlternativo()).isEqualTo(DEFAULT_CONTACTO_ALTERNATIVO);
        assertThat(testAlfa.getEstadoCivil()).isEqualTo(DEFAULT_ESTADO_CIVIL);
        assertThat(testAlfa.getNomeConjugue()).isEqualTo(DEFAULT_NOME_CONJUGUE);
        assertThat(testAlfa.getDistritoMunicipal()).isEqualTo(DEFAULT_DISTRITO_MUNICIPAL);
        assertThat(testAlfa.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testAlfa.getQuatreirao()).isEqualTo(DEFAULT_QUATREIRAO);
        assertThat(testAlfa.getTalhao()).isEqualTo(DEFAULT_TALHAO);
        assertThat(testAlfa.getCelula()).isEqualTo(DEFAULT_CELULA);
        assertThat(testAlfa.getBloco()).isEqualTo(DEFAULT_BLOCO);
        assertThat(testAlfa.getAvenida()).isEqualTo(DEFAULT_AVENIDA);
        assertThat(testAlfa.getNumeroPolicia()).isEqualTo(DEFAULT_NUMERO_POLICIA);
        assertThat(testAlfa.getUsoActual()).isEqualTo(DEFAULT_USO_ACTUAL);
        assertThat(testAlfa.getFormaUso()).isEqualTo(DEFAULT_FORMA_USO);
        assertThat(testAlfa.getFormaObtencao()).isEqualTo(DEFAULT_FORMA_OBTENCAO);
        assertThat(testAlfa.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAlfa.getAnoOcupacao()).isEqualTo(DEFAULT_ANO_OCUPACAO);
        assertThat(testAlfa.getTipoAcesso()).isEqualTo(DEFAULT_TIPO_ACESSO);
        assertThat(testAlfa.getConflito()).isEqualTo(DEFAULT_CONFLITO);
        assertThat(testAlfa.getDetalhesConflito()).isEqualTo(DEFAULT_DETALHES_CONFLITO);
        assertThat(testAlfa.getConstrucaoPrecaria()).isEqualTo(DEFAULT_CONSTRUCAO_PRECARIA);
        assertThat(testAlfa.getPisosAcimaSoleira()).isEqualTo(DEFAULT_PISOS_ACIMA_SOLEIRA);
        assertThat(testAlfa.getPisosAbaixoSoleira()).isEqualTo(DEFAULT_PISOS_ABAIXO_SOLEIRA);
        assertThat(testAlfa.getMaterialConstrucaoBarrote()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_BARROTE);
        assertThat(testAlfa.getMaterialConstrucaoIBR()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_IBR);
        assertThat(testAlfa.getMaterialConstrucaoPranchas()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS);
        assertThat(testAlfa.getMaterialConstrucaoPau()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_PAU);
        assertThat(testAlfa.getMaterialConstrucaoCanico()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_CANICO);
        assertThat(testAlfa.getMaterialConstrucaoCimento()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO);
        assertThat(testAlfa.getOcupacao()).isEqualTo(DEFAULT_OCUPACAO);
        assertThat(testAlfa.getTipoContrucao()).isEqualTo(DEFAULT_TIPO_CONTRUCAO);
        assertThat(testAlfa.getDetalhesTipoContrucao()).isEqualTo(DEFAULT_DETALHES_TIPO_CONTRUCAO);
        assertThat(testAlfa.getInfraestruturaExistente()).isEqualTo(DEFAULT_INFRAESTRUTURA_EXISTENTE);
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
            .andExpect(jsonPath("$.[*].tipoTitular").value(hasItem(DEFAULT_TIPO_TITULAR)))
            .andExpect(jsonPath("$.[*].nomeTitular").value(hasItem(DEFAULT_NOME_TITULAR)))
            .andExpect(jsonPath("$.[*].estadoSocial").value(hasItem(DEFAULT_ESTADO_SOCIAL)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].datEmissao").value(hasItem(DEFAULT_DAT_EMISSAO)))
            .andExpect(jsonPath("$.[*].localEmissao").value(hasItem(DEFAULT_LOCAL_EMISSAO)))
            .andExpect(jsonPath("$.[*].contactoPrincipal").value(hasItem(DEFAULT_CONTACTO_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].contactoAlternativo").value(hasItem(DEFAULT_CONTACTO_ALTERNATIVO)))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL)))
            .andExpect(jsonPath("$.[*].nomeConjugue").value(hasItem(DEFAULT_NOME_CONJUGUE)))
            .andExpect(jsonPath("$.[*].distritoMunicipal").value(hasItem(DEFAULT_DISTRITO_MUNICIPAL)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].quatreirao").value(hasItem(DEFAULT_QUATREIRAO)))
            .andExpect(jsonPath("$.[*].talhao").value(hasItem(DEFAULT_TALHAO)))
            .andExpect(jsonPath("$.[*].celula").value(hasItem(DEFAULT_CELULA)))
            .andExpect(jsonPath("$.[*].bloco").value(hasItem(DEFAULT_BLOCO)))
            .andExpect(jsonPath("$.[*].avenida").value(hasItem(DEFAULT_AVENIDA)))
            .andExpect(jsonPath("$.[*].numeroPolicia").value(hasItem(DEFAULT_NUMERO_POLICIA)))
            .andExpect(jsonPath("$.[*].usoActual").value(hasItem(DEFAULT_USO_ACTUAL)))
            .andExpect(jsonPath("$.[*].formaUso").value(hasItem(DEFAULT_FORMA_USO)))
            .andExpect(jsonPath("$.[*].formaObtencao").value(hasItem(DEFAULT_FORMA_OBTENCAO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].anoOcupacao").value(hasItem(DEFAULT_ANO_OCUPACAO)))
            .andExpect(jsonPath("$.[*].tipoAcesso").value(hasItem(DEFAULT_TIPO_ACESSO)))
            .andExpect(jsonPath("$.[*].conflito").value(hasItem(DEFAULT_CONFLITO.booleanValue())))
            .andExpect(jsonPath("$.[*].detalhesConflito").value(hasItem(DEFAULT_DETALHES_CONFLITO.booleanValue())))
            .andExpect(jsonPath("$.[*].construcaoPrecaria").value(hasItem(DEFAULT_CONSTRUCAO_PRECARIA.booleanValue())))
            .andExpect(jsonPath("$.[*].pisosAcimaSoleira").value(hasItem(DEFAULT_PISOS_ACIMA_SOLEIRA)))
            .andExpect(jsonPath("$.[*].pisosAbaixoSoleira").value(hasItem(DEFAULT_PISOS_ABAIXO_SOLEIRA)))
            .andExpect(jsonPath("$.[*].materialConstrucaoBarrote").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_BARROTE.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoIBR").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_IBR.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoPranchas").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoPau").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_PAU.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoCanico").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_CANICO.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoCimento").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].ocupacao").value(hasItem(DEFAULT_OCUPACAO)))
            .andExpect(jsonPath("$.[*].TipoContrucao").value(hasItem(DEFAULT_TIPO_CONTRUCAO)))
            .andExpect(jsonPath("$.[*].detalhesTipoContrucao").value(hasItem(DEFAULT_DETALHES_TIPO_CONTRUCAO)))
            .andExpect(jsonPath("$.[*].infraestruturaExistente").value(hasItem(DEFAULT_INFRAESTRUTURA_EXISTENTE)))
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
            .andExpect(jsonPath("$.tipoTitular").value(DEFAULT_TIPO_TITULAR))
            .andExpect(jsonPath("$.nomeTitular").value(DEFAULT_NOME_TITULAR))
            .andExpect(jsonPath("$.estadoSocial").value(DEFAULT_ESTADO_SOCIAL))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO))
            .andExpect(jsonPath("$.datEmissao").value(DEFAULT_DAT_EMISSAO))
            .andExpect(jsonPath("$.localEmissao").value(DEFAULT_LOCAL_EMISSAO))
            .andExpect(jsonPath("$.contactoPrincipal").value(DEFAULT_CONTACTO_PRINCIPAL))
            .andExpect(jsonPath("$.contactoAlternativo").value(DEFAULT_CONTACTO_ALTERNATIVO))
            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL))
            .andExpect(jsonPath("$.nomeConjugue").value(DEFAULT_NOME_CONJUGUE))
            .andExpect(jsonPath("$.distritoMunicipal").value(DEFAULT_DISTRITO_MUNICIPAL))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.quatreirao").value(DEFAULT_QUATREIRAO))
            .andExpect(jsonPath("$.talhao").value(DEFAULT_TALHAO))
            .andExpect(jsonPath("$.celula").value(DEFAULT_CELULA))
            .andExpect(jsonPath("$.bloco").value(DEFAULT_BLOCO))
            .andExpect(jsonPath("$.avenida").value(DEFAULT_AVENIDA))
            .andExpect(jsonPath("$.numeroPolicia").value(DEFAULT_NUMERO_POLICIA))
            .andExpect(jsonPath("$.usoActual").value(DEFAULT_USO_ACTUAL))
            .andExpect(jsonPath("$.formaUso").value(DEFAULT_FORMA_USO))
            .andExpect(jsonPath("$.formaObtencao").value(DEFAULT_FORMA_OBTENCAO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.anoOcupacao").value(DEFAULT_ANO_OCUPACAO))
            .andExpect(jsonPath("$.tipoAcesso").value(DEFAULT_TIPO_ACESSO))
            .andExpect(jsonPath("$.conflito").value(DEFAULT_CONFLITO.booleanValue()))
            .andExpect(jsonPath("$.detalhesConflito").value(DEFAULT_DETALHES_CONFLITO.booleanValue()))
            .andExpect(jsonPath("$.construcaoPrecaria").value(DEFAULT_CONSTRUCAO_PRECARIA.booleanValue()))
            .andExpect(jsonPath("$.pisosAcimaSoleira").value(DEFAULT_PISOS_ACIMA_SOLEIRA))
            .andExpect(jsonPath("$.pisosAbaixoSoleira").value(DEFAULT_PISOS_ABAIXO_SOLEIRA))
            .andExpect(jsonPath("$.materialConstrucaoBarrote").value(DEFAULT_MATERIAL_CONSTRUCAO_BARROTE.booleanValue()))
            .andExpect(jsonPath("$.materialConstrucaoIBR").value(DEFAULT_MATERIAL_CONSTRUCAO_IBR.booleanValue()))
            .andExpect(jsonPath("$.materialConstrucaoPranchas").value(DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS.booleanValue()))
            .andExpect(jsonPath("$.materialConstrucaoPau").value(DEFAULT_MATERIAL_CONSTRUCAO_PAU.booleanValue()))
            .andExpect(jsonPath("$.materialConstrucaoCanico").value(DEFAULT_MATERIAL_CONSTRUCAO_CANICO.booleanValue()))
            .andExpect(jsonPath("$.materialConstrucaoCimento").value(DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO.booleanValue()))
            .andExpect(jsonPath("$.ocupacao").value(DEFAULT_OCUPACAO))
            .andExpect(jsonPath("$.TipoContrucao").value(DEFAULT_TIPO_CONTRUCAO))
            .andExpect(jsonPath("$.detalhesTipoContrucao").value(DEFAULT_DETALHES_TIPO_CONTRUCAO))
            .andExpect(jsonPath("$.infraestruturaExistente").value(DEFAULT_INFRAESTRUTURA_EXISTENTE))
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
    void getAllAlfasByTipoTitularIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoTitular equals to DEFAULT_TIPO_TITULAR
        defaultAlfaShouldBeFound("tipoTitular.equals=" + DEFAULT_TIPO_TITULAR);

        // Get all the alfaList where tipoTitular equals to UPDATED_TIPO_TITULAR
        defaultAlfaShouldNotBeFound("tipoTitular.equals=" + UPDATED_TIPO_TITULAR);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoTitularIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoTitular in DEFAULT_TIPO_TITULAR or UPDATED_TIPO_TITULAR
        defaultAlfaShouldBeFound("tipoTitular.in=" + DEFAULT_TIPO_TITULAR + "," + UPDATED_TIPO_TITULAR);

        // Get all the alfaList where tipoTitular equals to UPDATED_TIPO_TITULAR
        defaultAlfaShouldNotBeFound("tipoTitular.in=" + UPDATED_TIPO_TITULAR);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoTitularIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoTitular is not null
        defaultAlfaShouldBeFound("tipoTitular.specified=true");

        // Get all the alfaList where tipoTitular is null
        defaultAlfaShouldNotBeFound("tipoTitular.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByTipoTitularContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoTitular contains DEFAULT_TIPO_TITULAR
        defaultAlfaShouldBeFound("tipoTitular.contains=" + DEFAULT_TIPO_TITULAR);

        // Get all the alfaList where tipoTitular contains UPDATED_TIPO_TITULAR
        defaultAlfaShouldNotBeFound("tipoTitular.contains=" + UPDATED_TIPO_TITULAR);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoTitularNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoTitular does not contain DEFAULT_TIPO_TITULAR
        defaultAlfaShouldNotBeFound("tipoTitular.doesNotContain=" + DEFAULT_TIPO_TITULAR);

        // Get all the alfaList where tipoTitular does not contain UPDATED_TIPO_TITULAR
        defaultAlfaShouldBeFound("tipoTitular.doesNotContain=" + UPDATED_TIPO_TITULAR);
    }

    @Test
    @Transactional
    void getAllAlfasByNomeTitularIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeTitular equals to DEFAULT_NOME_TITULAR
        defaultAlfaShouldBeFound("nomeTitular.equals=" + DEFAULT_NOME_TITULAR);

        // Get all the alfaList where nomeTitular equals to UPDATED_NOME_TITULAR
        defaultAlfaShouldNotBeFound("nomeTitular.equals=" + UPDATED_NOME_TITULAR);
    }

    @Test
    @Transactional
    void getAllAlfasByNomeTitularIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeTitular in DEFAULT_NOME_TITULAR or UPDATED_NOME_TITULAR
        defaultAlfaShouldBeFound("nomeTitular.in=" + DEFAULT_NOME_TITULAR + "," + UPDATED_NOME_TITULAR);

        // Get all the alfaList where nomeTitular equals to UPDATED_NOME_TITULAR
        defaultAlfaShouldNotBeFound("nomeTitular.in=" + UPDATED_NOME_TITULAR);
    }

    @Test
    @Transactional
    void getAllAlfasByNomeTitularIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeTitular is not null
        defaultAlfaShouldBeFound("nomeTitular.specified=true");

        // Get all the alfaList where nomeTitular is null
        defaultAlfaShouldNotBeFound("nomeTitular.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByNomeTitularContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeTitular contains DEFAULT_NOME_TITULAR
        defaultAlfaShouldBeFound("nomeTitular.contains=" + DEFAULT_NOME_TITULAR);

        // Get all the alfaList where nomeTitular contains UPDATED_NOME_TITULAR
        defaultAlfaShouldNotBeFound("nomeTitular.contains=" + UPDATED_NOME_TITULAR);
    }

    @Test
    @Transactional
    void getAllAlfasByNomeTitularNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeTitular does not contain DEFAULT_NOME_TITULAR
        defaultAlfaShouldNotBeFound("nomeTitular.doesNotContain=" + DEFAULT_NOME_TITULAR);

        // Get all the alfaList where nomeTitular does not contain UPDATED_NOME_TITULAR
        defaultAlfaShouldBeFound("nomeTitular.doesNotContain=" + UPDATED_NOME_TITULAR);
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoSocial equals to DEFAULT_ESTADO_SOCIAL
        defaultAlfaShouldBeFound("estadoSocial.equals=" + DEFAULT_ESTADO_SOCIAL);

        // Get all the alfaList where estadoSocial equals to UPDATED_ESTADO_SOCIAL
        defaultAlfaShouldNotBeFound("estadoSocial.equals=" + UPDATED_ESTADO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoSocialIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoSocial in DEFAULT_ESTADO_SOCIAL or UPDATED_ESTADO_SOCIAL
        defaultAlfaShouldBeFound("estadoSocial.in=" + DEFAULT_ESTADO_SOCIAL + "," + UPDATED_ESTADO_SOCIAL);

        // Get all the alfaList where estadoSocial equals to UPDATED_ESTADO_SOCIAL
        defaultAlfaShouldNotBeFound("estadoSocial.in=" + UPDATED_ESTADO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoSocial is not null
        defaultAlfaShouldBeFound("estadoSocial.specified=true");

        // Get all the alfaList where estadoSocial is null
        defaultAlfaShouldNotBeFound("estadoSocial.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoSocialContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoSocial contains DEFAULT_ESTADO_SOCIAL
        defaultAlfaShouldBeFound("estadoSocial.contains=" + DEFAULT_ESTADO_SOCIAL);

        // Get all the alfaList where estadoSocial contains UPDATED_ESTADO_SOCIAL
        defaultAlfaShouldNotBeFound("estadoSocial.contains=" + UPDATED_ESTADO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoSocialNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoSocial does not contain DEFAULT_ESTADO_SOCIAL
        defaultAlfaShouldNotBeFound("estadoSocial.doesNotContain=" + DEFAULT_ESTADO_SOCIAL);

        // Get all the alfaList where estadoSocial does not contain UPDATED_ESTADO_SOCIAL
        defaultAlfaShouldBeFound("estadoSocial.doesNotContain=" + UPDATED_ESTADO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllAlfasByDataNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataNascimento equals to DEFAULT_DATA_NASCIMENTO
        defaultAlfaShouldBeFound("dataNascimento.equals=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the alfaList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultAlfaShouldNotBeFound("dataNascimento.equals=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataNascimento in DEFAULT_DATA_NASCIMENTO or UPDATED_DATA_NASCIMENTO
        defaultAlfaShouldBeFound("dataNascimento.in=" + DEFAULT_DATA_NASCIMENTO + "," + UPDATED_DATA_NASCIMENTO);

        // Get all the alfaList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultAlfaShouldNotBeFound("dataNascimento.in=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataNascimento is not null
        defaultAlfaShouldBeFound("dataNascimento.specified=true");

        // Get all the alfaList where dataNascimento is null
        defaultAlfaShouldNotBeFound("dataNascimento.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByDataNascimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataNascimento is greater than or equal to DEFAULT_DATA_NASCIMENTO
        defaultAlfaShouldBeFound("dataNascimento.greaterThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the alfaList where dataNascimento is greater than or equal to UPDATED_DATA_NASCIMENTO
        defaultAlfaShouldNotBeFound("dataNascimento.greaterThanOrEqual=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataNascimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataNascimento is less than or equal to DEFAULT_DATA_NASCIMENTO
        defaultAlfaShouldBeFound("dataNascimento.lessThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the alfaList where dataNascimento is less than or equal to SMALLER_DATA_NASCIMENTO
        defaultAlfaShouldNotBeFound("dataNascimento.lessThanOrEqual=" + SMALLER_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataNascimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataNascimento is less than DEFAULT_DATA_NASCIMENTO
        defaultAlfaShouldNotBeFound("dataNascimento.lessThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the alfaList where dataNascimento is less than UPDATED_DATA_NASCIMENTO
        defaultAlfaShouldBeFound("dataNascimento.lessThan=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDataNascimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where dataNascimento is greater than DEFAULT_DATA_NASCIMENTO
        defaultAlfaShouldNotBeFound("dataNascimento.greaterThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the alfaList where dataNascimento is greater than SMALLER_DATA_NASCIMENTO
        defaultAlfaShouldBeFound("dataNascimento.greaterThan=" + SMALLER_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where sexo equals to DEFAULT_SEXO
        defaultAlfaShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the alfaList where sexo equals to UPDATED_SEXO
        defaultAlfaShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllAlfasBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultAlfaShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the alfaList where sexo equals to UPDATED_SEXO
        defaultAlfaShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllAlfasBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where sexo is not null
        defaultAlfaShouldBeFound("sexo.specified=true");

        // Get all the alfaList where sexo is null
        defaultAlfaShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasBySexoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where sexo contains DEFAULT_SEXO
        defaultAlfaShouldBeFound("sexo.contains=" + DEFAULT_SEXO);

        // Get all the alfaList where sexo contains UPDATED_SEXO
        defaultAlfaShouldNotBeFound("sexo.contains=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllAlfasBySexoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where sexo does not contain DEFAULT_SEXO
        defaultAlfaShouldNotBeFound("sexo.doesNotContain=" + DEFAULT_SEXO);

        // Get all the alfaList where sexo does not contain UPDATED_SEXO
        defaultAlfaShouldBeFound("sexo.doesNotContain=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllAlfasByDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where documento equals to DEFAULT_DOCUMENTO
        defaultAlfaShouldBeFound("documento.equals=" + DEFAULT_DOCUMENTO);

        // Get all the alfaList where documento equals to UPDATED_DOCUMENTO
        defaultAlfaShouldNotBeFound("documento.equals=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where documento in DEFAULT_DOCUMENTO or UPDATED_DOCUMENTO
        defaultAlfaShouldBeFound("documento.in=" + DEFAULT_DOCUMENTO + "," + UPDATED_DOCUMENTO);

        // Get all the alfaList where documento equals to UPDATED_DOCUMENTO
        defaultAlfaShouldNotBeFound("documento.in=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where documento is not null
        defaultAlfaShouldBeFound("documento.specified=true");

        // Get all the alfaList where documento is null
        defaultAlfaShouldNotBeFound("documento.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByDocumentoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where documento contains DEFAULT_DOCUMENTO
        defaultAlfaShouldBeFound("documento.contains=" + DEFAULT_DOCUMENTO);

        // Get all the alfaList where documento contains UPDATED_DOCUMENTO
        defaultAlfaShouldNotBeFound("documento.contains=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDocumentoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where documento does not contain DEFAULT_DOCUMENTO
        defaultAlfaShouldNotBeFound("documento.doesNotContain=" + DEFAULT_DOCUMENTO);

        // Get all the alfaList where documento does not contain UPDATED_DOCUMENTO
        defaultAlfaShouldBeFound("documento.doesNotContain=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroDocumento equals to DEFAULT_NUMERO_DOCUMENTO
        defaultAlfaShouldBeFound("numeroDocumento.equals=" + DEFAULT_NUMERO_DOCUMENTO);

        // Get all the alfaList where numeroDocumento equals to UPDATED_NUMERO_DOCUMENTO
        defaultAlfaShouldNotBeFound("numeroDocumento.equals=" + UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroDocumento in DEFAULT_NUMERO_DOCUMENTO or UPDATED_NUMERO_DOCUMENTO
        defaultAlfaShouldBeFound("numeroDocumento.in=" + DEFAULT_NUMERO_DOCUMENTO + "," + UPDATED_NUMERO_DOCUMENTO);

        // Get all the alfaList where numeroDocumento equals to UPDATED_NUMERO_DOCUMENTO
        defaultAlfaShouldNotBeFound("numeroDocumento.in=" + UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroDocumento is not null
        defaultAlfaShouldBeFound("numeroDocumento.specified=true");

        // Get all the alfaList where numeroDocumento is null
        defaultAlfaShouldNotBeFound("numeroDocumento.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroDocumentoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroDocumento contains DEFAULT_NUMERO_DOCUMENTO
        defaultAlfaShouldBeFound("numeroDocumento.contains=" + DEFAULT_NUMERO_DOCUMENTO);

        // Get all the alfaList where numeroDocumento contains UPDATED_NUMERO_DOCUMENTO
        defaultAlfaShouldNotBeFound("numeroDocumento.contains=" + UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroDocumentoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroDocumento does not contain DEFAULT_NUMERO_DOCUMENTO
        defaultAlfaShouldNotBeFound("numeroDocumento.doesNotContain=" + DEFAULT_NUMERO_DOCUMENTO);

        // Get all the alfaList where numeroDocumento does not contain UPDATED_NUMERO_DOCUMENTO
        defaultAlfaShouldBeFound("numeroDocumento.doesNotContain=" + UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByDatEmissaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where datEmissao equals to DEFAULT_DAT_EMISSAO
        defaultAlfaShouldBeFound("datEmissao.equals=" + DEFAULT_DAT_EMISSAO);

        // Get all the alfaList where datEmissao equals to UPDATED_DAT_EMISSAO
        defaultAlfaShouldNotBeFound("datEmissao.equals=" + UPDATED_DAT_EMISSAO);
    }

    @Test
    @Transactional
    void getAllAlfasByDatEmissaoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where datEmissao in DEFAULT_DAT_EMISSAO or UPDATED_DAT_EMISSAO
        defaultAlfaShouldBeFound("datEmissao.in=" + DEFAULT_DAT_EMISSAO + "," + UPDATED_DAT_EMISSAO);

        // Get all the alfaList where datEmissao equals to UPDATED_DAT_EMISSAO
        defaultAlfaShouldNotBeFound("datEmissao.in=" + UPDATED_DAT_EMISSAO);
    }

    @Test
    @Transactional
    void getAllAlfasByDatEmissaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where datEmissao is not null
        defaultAlfaShouldBeFound("datEmissao.specified=true");

        // Get all the alfaList where datEmissao is null
        defaultAlfaShouldNotBeFound("datEmissao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByDatEmissaoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where datEmissao contains DEFAULT_DAT_EMISSAO
        defaultAlfaShouldBeFound("datEmissao.contains=" + DEFAULT_DAT_EMISSAO);

        // Get all the alfaList where datEmissao contains UPDATED_DAT_EMISSAO
        defaultAlfaShouldNotBeFound("datEmissao.contains=" + UPDATED_DAT_EMISSAO);
    }

    @Test
    @Transactional
    void getAllAlfasByDatEmissaoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where datEmissao does not contain DEFAULT_DAT_EMISSAO
        defaultAlfaShouldNotBeFound("datEmissao.doesNotContain=" + DEFAULT_DAT_EMISSAO);

        // Get all the alfaList where datEmissao does not contain UPDATED_DAT_EMISSAO
        defaultAlfaShouldBeFound("datEmissao.doesNotContain=" + UPDATED_DAT_EMISSAO);
    }

    @Test
    @Transactional
    void getAllAlfasByLocalEmissaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where localEmissao equals to DEFAULT_LOCAL_EMISSAO
        defaultAlfaShouldBeFound("localEmissao.equals=" + DEFAULT_LOCAL_EMISSAO);

        // Get all the alfaList where localEmissao equals to UPDATED_LOCAL_EMISSAO
        defaultAlfaShouldNotBeFound("localEmissao.equals=" + UPDATED_LOCAL_EMISSAO);
    }

    @Test
    @Transactional
    void getAllAlfasByLocalEmissaoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where localEmissao in DEFAULT_LOCAL_EMISSAO or UPDATED_LOCAL_EMISSAO
        defaultAlfaShouldBeFound("localEmissao.in=" + DEFAULT_LOCAL_EMISSAO + "," + UPDATED_LOCAL_EMISSAO);

        // Get all the alfaList where localEmissao equals to UPDATED_LOCAL_EMISSAO
        defaultAlfaShouldNotBeFound("localEmissao.in=" + UPDATED_LOCAL_EMISSAO);
    }

    @Test
    @Transactional
    void getAllAlfasByLocalEmissaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where localEmissao is not null
        defaultAlfaShouldBeFound("localEmissao.specified=true");

        // Get all the alfaList where localEmissao is null
        defaultAlfaShouldNotBeFound("localEmissao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByLocalEmissaoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where localEmissao contains DEFAULT_LOCAL_EMISSAO
        defaultAlfaShouldBeFound("localEmissao.contains=" + DEFAULT_LOCAL_EMISSAO);

        // Get all the alfaList where localEmissao contains UPDATED_LOCAL_EMISSAO
        defaultAlfaShouldNotBeFound("localEmissao.contains=" + UPDATED_LOCAL_EMISSAO);
    }

    @Test
    @Transactional
    void getAllAlfasByLocalEmissaoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where localEmissao does not contain DEFAULT_LOCAL_EMISSAO
        defaultAlfaShouldNotBeFound("localEmissao.doesNotContain=" + DEFAULT_LOCAL_EMISSAO);

        // Get all the alfaList where localEmissao does not contain UPDATED_LOCAL_EMISSAO
        defaultAlfaShouldBeFound("localEmissao.doesNotContain=" + UPDATED_LOCAL_EMISSAO);
    }

    @Test
    @Transactional
    void getAllAlfasByContactoPrincipalIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoPrincipal equals to DEFAULT_CONTACTO_PRINCIPAL
        defaultAlfaShouldBeFound("contactoPrincipal.equals=" + DEFAULT_CONTACTO_PRINCIPAL);

        // Get all the alfaList where contactoPrincipal equals to UPDATED_CONTACTO_PRINCIPAL
        defaultAlfaShouldNotBeFound("contactoPrincipal.equals=" + UPDATED_CONTACTO_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllAlfasByContactoPrincipalIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoPrincipal in DEFAULT_CONTACTO_PRINCIPAL or UPDATED_CONTACTO_PRINCIPAL
        defaultAlfaShouldBeFound("contactoPrincipal.in=" + DEFAULT_CONTACTO_PRINCIPAL + "," + UPDATED_CONTACTO_PRINCIPAL);

        // Get all the alfaList where contactoPrincipal equals to UPDATED_CONTACTO_PRINCIPAL
        defaultAlfaShouldNotBeFound("contactoPrincipal.in=" + UPDATED_CONTACTO_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllAlfasByContactoPrincipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoPrincipal is not null
        defaultAlfaShouldBeFound("contactoPrincipal.specified=true");

        // Get all the alfaList where contactoPrincipal is null
        defaultAlfaShouldNotBeFound("contactoPrincipal.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByContactoPrincipalContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoPrincipal contains DEFAULT_CONTACTO_PRINCIPAL
        defaultAlfaShouldBeFound("contactoPrincipal.contains=" + DEFAULT_CONTACTO_PRINCIPAL);

        // Get all the alfaList where contactoPrincipal contains UPDATED_CONTACTO_PRINCIPAL
        defaultAlfaShouldNotBeFound("contactoPrincipal.contains=" + UPDATED_CONTACTO_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllAlfasByContactoPrincipalNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoPrincipal does not contain DEFAULT_CONTACTO_PRINCIPAL
        defaultAlfaShouldNotBeFound("contactoPrincipal.doesNotContain=" + DEFAULT_CONTACTO_PRINCIPAL);

        // Get all the alfaList where contactoPrincipal does not contain UPDATED_CONTACTO_PRINCIPAL
        defaultAlfaShouldBeFound("contactoPrincipal.doesNotContain=" + UPDATED_CONTACTO_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllAlfasByContactoAlternativoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoAlternativo equals to DEFAULT_CONTACTO_ALTERNATIVO
        defaultAlfaShouldBeFound("contactoAlternativo.equals=" + DEFAULT_CONTACTO_ALTERNATIVO);

        // Get all the alfaList where contactoAlternativo equals to UPDATED_CONTACTO_ALTERNATIVO
        defaultAlfaShouldNotBeFound("contactoAlternativo.equals=" + UPDATED_CONTACTO_ALTERNATIVO);
    }

    @Test
    @Transactional
    void getAllAlfasByContactoAlternativoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoAlternativo in DEFAULT_CONTACTO_ALTERNATIVO or UPDATED_CONTACTO_ALTERNATIVO
        defaultAlfaShouldBeFound("contactoAlternativo.in=" + DEFAULT_CONTACTO_ALTERNATIVO + "," + UPDATED_CONTACTO_ALTERNATIVO);

        // Get all the alfaList where contactoAlternativo equals to UPDATED_CONTACTO_ALTERNATIVO
        defaultAlfaShouldNotBeFound("contactoAlternativo.in=" + UPDATED_CONTACTO_ALTERNATIVO);
    }

    @Test
    @Transactional
    void getAllAlfasByContactoAlternativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoAlternativo is not null
        defaultAlfaShouldBeFound("contactoAlternativo.specified=true");

        // Get all the alfaList where contactoAlternativo is null
        defaultAlfaShouldNotBeFound("contactoAlternativo.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByContactoAlternativoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoAlternativo contains DEFAULT_CONTACTO_ALTERNATIVO
        defaultAlfaShouldBeFound("contactoAlternativo.contains=" + DEFAULT_CONTACTO_ALTERNATIVO);

        // Get all the alfaList where contactoAlternativo contains UPDATED_CONTACTO_ALTERNATIVO
        defaultAlfaShouldNotBeFound("contactoAlternativo.contains=" + UPDATED_CONTACTO_ALTERNATIVO);
    }

    @Test
    @Transactional
    void getAllAlfasByContactoAlternativoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where contactoAlternativo does not contain DEFAULT_CONTACTO_ALTERNATIVO
        defaultAlfaShouldNotBeFound("contactoAlternativo.doesNotContain=" + DEFAULT_CONTACTO_ALTERNATIVO);

        // Get all the alfaList where contactoAlternativo does not contain UPDATED_CONTACTO_ALTERNATIVO
        defaultAlfaShouldBeFound("contactoAlternativo.doesNotContain=" + UPDATED_CONTACTO_ALTERNATIVO);
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoCivilIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoCivil equals to DEFAULT_ESTADO_CIVIL
        defaultAlfaShouldBeFound("estadoCivil.equals=" + DEFAULT_ESTADO_CIVIL);

        // Get all the alfaList where estadoCivil equals to UPDATED_ESTADO_CIVIL
        defaultAlfaShouldNotBeFound("estadoCivil.equals=" + UPDATED_ESTADO_CIVIL);
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoCivilIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoCivil in DEFAULT_ESTADO_CIVIL or UPDATED_ESTADO_CIVIL
        defaultAlfaShouldBeFound("estadoCivil.in=" + DEFAULT_ESTADO_CIVIL + "," + UPDATED_ESTADO_CIVIL);

        // Get all the alfaList where estadoCivil equals to UPDATED_ESTADO_CIVIL
        defaultAlfaShouldNotBeFound("estadoCivil.in=" + UPDATED_ESTADO_CIVIL);
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoCivilIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoCivil is not null
        defaultAlfaShouldBeFound("estadoCivil.specified=true");

        // Get all the alfaList where estadoCivil is null
        defaultAlfaShouldNotBeFound("estadoCivil.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoCivilContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoCivil contains DEFAULT_ESTADO_CIVIL
        defaultAlfaShouldBeFound("estadoCivil.contains=" + DEFAULT_ESTADO_CIVIL);

        // Get all the alfaList where estadoCivil contains UPDATED_ESTADO_CIVIL
        defaultAlfaShouldNotBeFound("estadoCivil.contains=" + UPDATED_ESTADO_CIVIL);
    }

    @Test
    @Transactional
    void getAllAlfasByEstadoCivilNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where estadoCivil does not contain DEFAULT_ESTADO_CIVIL
        defaultAlfaShouldNotBeFound("estadoCivil.doesNotContain=" + DEFAULT_ESTADO_CIVIL);

        // Get all the alfaList where estadoCivil does not contain UPDATED_ESTADO_CIVIL
        defaultAlfaShouldBeFound("estadoCivil.doesNotContain=" + UPDATED_ESTADO_CIVIL);
    }

    @Test
    @Transactional
    void getAllAlfasByNomeConjugueIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeConjugue equals to DEFAULT_NOME_CONJUGUE
        defaultAlfaShouldBeFound("nomeConjugue.equals=" + DEFAULT_NOME_CONJUGUE);

        // Get all the alfaList where nomeConjugue equals to UPDATED_NOME_CONJUGUE
        defaultAlfaShouldNotBeFound("nomeConjugue.equals=" + UPDATED_NOME_CONJUGUE);
    }

    @Test
    @Transactional
    void getAllAlfasByNomeConjugueIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeConjugue in DEFAULT_NOME_CONJUGUE or UPDATED_NOME_CONJUGUE
        defaultAlfaShouldBeFound("nomeConjugue.in=" + DEFAULT_NOME_CONJUGUE + "," + UPDATED_NOME_CONJUGUE);

        // Get all the alfaList where nomeConjugue equals to UPDATED_NOME_CONJUGUE
        defaultAlfaShouldNotBeFound("nomeConjugue.in=" + UPDATED_NOME_CONJUGUE);
    }

    @Test
    @Transactional
    void getAllAlfasByNomeConjugueIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeConjugue is not null
        defaultAlfaShouldBeFound("nomeConjugue.specified=true");

        // Get all the alfaList where nomeConjugue is null
        defaultAlfaShouldNotBeFound("nomeConjugue.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByNomeConjugueContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeConjugue contains DEFAULT_NOME_CONJUGUE
        defaultAlfaShouldBeFound("nomeConjugue.contains=" + DEFAULT_NOME_CONJUGUE);

        // Get all the alfaList where nomeConjugue contains UPDATED_NOME_CONJUGUE
        defaultAlfaShouldNotBeFound("nomeConjugue.contains=" + UPDATED_NOME_CONJUGUE);
    }

    @Test
    @Transactional
    void getAllAlfasByNomeConjugueNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where nomeConjugue does not contain DEFAULT_NOME_CONJUGUE
        defaultAlfaShouldNotBeFound("nomeConjugue.doesNotContain=" + DEFAULT_NOME_CONJUGUE);

        // Get all the alfaList where nomeConjugue does not contain UPDATED_NOME_CONJUGUE
        defaultAlfaShouldBeFound("nomeConjugue.doesNotContain=" + UPDATED_NOME_CONJUGUE);
    }

    @Test
    @Transactional
    void getAllAlfasByDistritoMunicipalIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where distritoMunicipal equals to DEFAULT_DISTRITO_MUNICIPAL
        defaultAlfaShouldBeFound("distritoMunicipal.equals=" + DEFAULT_DISTRITO_MUNICIPAL);

        // Get all the alfaList where distritoMunicipal equals to UPDATED_DISTRITO_MUNICIPAL
        defaultAlfaShouldNotBeFound("distritoMunicipal.equals=" + UPDATED_DISTRITO_MUNICIPAL);
    }

    @Test
    @Transactional
    void getAllAlfasByDistritoMunicipalIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where distritoMunicipal in DEFAULT_DISTRITO_MUNICIPAL or UPDATED_DISTRITO_MUNICIPAL
        defaultAlfaShouldBeFound("distritoMunicipal.in=" + DEFAULT_DISTRITO_MUNICIPAL + "," + UPDATED_DISTRITO_MUNICIPAL);

        // Get all the alfaList where distritoMunicipal equals to UPDATED_DISTRITO_MUNICIPAL
        defaultAlfaShouldNotBeFound("distritoMunicipal.in=" + UPDATED_DISTRITO_MUNICIPAL);
    }

    @Test
    @Transactional
    void getAllAlfasByDistritoMunicipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where distritoMunicipal is not null
        defaultAlfaShouldBeFound("distritoMunicipal.specified=true");

        // Get all the alfaList where distritoMunicipal is null
        defaultAlfaShouldNotBeFound("distritoMunicipal.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByDistritoMunicipalContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where distritoMunicipal contains DEFAULT_DISTRITO_MUNICIPAL
        defaultAlfaShouldBeFound("distritoMunicipal.contains=" + DEFAULT_DISTRITO_MUNICIPAL);

        // Get all the alfaList where distritoMunicipal contains UPDATED_DISTRITO_MUNICIPAL
        defaultAlfaShouldNotBeFound("distritoMunicipal.contains=" + UPDATED_DISTRITO_MUNICIPAL);
    }

    @Test
    @Transactional
    void getAllAlfasByDistritoMunicipalNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where distritoMunicipal does not contain DEFAULT_DISTRITO_MUNICIPAL
        defaultAlfaShouldNotBeFound("distritoMunicipal.doesNotContain=" + DEFAULT_DISTRITO_MUNICIPAL);

        // Get all the alfaList where distritoMunicipal does not contain UPDATED_DISTRITO_MUNICIPAL
        defaultAlfaShouldBeFound("distritoMunicipal.doesNotContain=" + UPDATED_DISTRITO_MUNICIPAL);
    }

    @Test
    @Transactional
    void getAllAlfasByBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bairro equals to DEFAULT_BAIRRO
        defaultAlfaShouldBeFound("bairro.equals=" + DEFAULT_BAIRRO);

        // Get all the alfaList where bairro equals to UPDATED_BAIRRO
        defaultAlfaShouldNotBeFound("bairro.equals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllAlfasByBairroIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bairro in DEFAULT_BAIRRO or UPDATED_BAIRRO
        defaultAlfaShouldBeFound("bairro.in=" + DEFAULT_BAIRRO + "," + UPDATED_BAIRRO);

        // Get all the alfaList where bairro equals to UPDATED_BAIRRO
        defaultAlfaShouldNotBeFound("bairro.in=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllAlfasByBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bairro is not null
        defaultAlfaShouldBeFound("bairro.specified=true");

        // Get all the alfaList where bairro is null
        defaultAlfaShouldNotBeFound("bairro.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByBairroContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bairro contains DEFAULT_BAIRRO
        defaultAlfaShouldBeFound("bairro.contains=" + DEFAULT_BAIRRO);

        // Get all the alfaList where bairro contains UPDATED_BAIRRO
        defaultAlfaShouldNotBeFound("bairro.contains=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllAlfasByBairroNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bairro does not contain DEFAULT_BAIRRO
        defaultAlfaShouldNotBeFound("bairro.doesNotContain=" + DEFAULT_BAIRRO);

        // Get all the alfaList where bairro does not contain UPDATED_BAIRRO
        defaultAlfaShouldBeFound("bairro.doesNotContain=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllAlfasByQuatreiraoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where quatreirao equals to DEFAULT_QUATREIRAO
        defaultAlfaShouldBeFound("quatreirao.equals=" + DEFAULT_QUATREIRAO);

        // Get all the alfaList where quatreirao equals to UPDATED_QUATREIRAO
        defaultAlfaShouldNotBeFound("quatreirao.equals=" + UPDATED_QUATREIRAO);
    }

    @Test
    @Transactional
    void getAllAlfasByQuatreiraoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where quatreirao in DEFAULT_QUATREIRAO or UPDATED_QUATREIRAO
        defaultAlfaShouldBeFound("quatreirao.in=" + DEFAULT_QUATREIRAO + "," + UPDATED_QUATREIRAO);

        // Get all the alfaList where quatreirao equals to UPDATED_QUATREIRAO
        defaultAlfaShouldNotBeFound("quatreirao.in=" + UPDATED_QUATREIRAO);
    }

    @Test
    @Transactional
    void getAllAlfasByQuatreiraoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where quatreirao is not null
        defaultAlfaShouldBeFound("quatreirao.specified=true");

        // Get all the alfaList where quatreirao is null
        defaultAlfaShouldNotBeFound("quatreirao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByQuatreiraoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where quatreirao contains DEFAULT_QUATREIRAO
        defaultAlfaShouldBeFound("quatreirao.contains=" + DEFAULT_QUATREIRAO);

        // Get all the alfaList where quatreirao contains UPDATED_QUATREIRAO
        defaultAlfaShouldNotBeFound("quatreirao.contains=" + UPDATED_QUATREIRAO);
    }

    @Test
    @Transactional
    void getAllAlfasByQuatreiraoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where quatreirao does not contain DEFAULT_QUATREIRAO
        defaultAlfaShouldNotBeFound("quatreirao.doesNotContain=" + DEFAULT_QUATREIRAO);

        // Get all the alfaList where quatreirao does not contain UPDATED_QUATREIRAO
        defaultAlfaShouldBeFound("quatreirao.doesNotContain=" + UPDATED_QUATREIRAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTalhaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where talhao equals to DEFAULT_TALHAO
        defaultAlfaShouldBeFound("talhao.equals=" + DEFAULT_TALHAO);

        // Get all the alfaList where talhao equals to UPDATED_TALHAO
        defaultAlfaShouldNotBeFound("talhao.equals=" + UPDATED_TALHAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTalhaoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where talhao in DEFAULT_TALHAO or UPDATED_TALHAO
        defaultAlfaShouldBeFound("talhao.in=" + DEFAULT_TALHAO + "," + UPDATED_TALHAO);

        // Get all the alfaList where talhao equals to UPDATED_TALHAO
        defaultAlfaShouldNotBeFound("talhao.in=" + UPDATED_TALHAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTalhaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where talhao is not null
        defaultAlfaShouldBeFound("talhao.specified=true");

        // Get all the alfaList where talhao is null
        defaultAlfaShouldNotBeFound("talhao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByTalhaoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where talhao contains DEFAULT_TALHAO
        defaultAlfaShouldBeFound("talhao.contains=" + DEFAULT_TALHAO);

        // Get all the alfaList where talhao contains UPDATED_TALHAO
        defaultAlfaShouldNotBeFound("talhao.contains=" + UPDATED_TALHAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTalhaoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where talhao does not contain DEFAULT_TALHAO
        defaultAlfaShouldNotBeFound("talhao.doesNotContain=" + DEFAULT_TALHAO);

        // Get all the alfaList where talhao does not contain UPDATED_TALHAO
        defaultAlfaShouldBeFound("talhao.doesNotContain=" + UPDATED_TALHAO);
    }

    @Test
    @Transactional
    void getAllAlfasByCelulaIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where celula equals to DEFAULT_CELULA
        defaultAlfaShouldBeFound("celula.equals=" + DEFAULT_CELULA);

        // Get all the alfaList where celula equals to UPDATED_CELULA
        defaultAlfaShouldNotBeFound("celula.equals=" + UPDATED_CELULA);
    }

    @Test
    @Transactional
    void getAllAlfasByCelulaIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where celula in DEFAULT_CELULA or UPDATED_CELULA
        defaultAlfaShouldBeFound("celula.in=" + DEFAULT_CELULA + "," + UPDATED_CELULA);

        // Get all the alfaList where celula equals to UPDATED_CELULA
        defaultAlfaShouldNotBeFound("celula.in=" + UPDATED_CELULA);
    }

    @Test
    @Transactional
    void getAllAlfasByCelulaIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where celula is not null
        defaultAlfaShouldBeFound("celula.specified=true");

        // Get all the alfaList where celula is null
        defaultAlfaShouldNotBeFound("celula.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByCelulaContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where celula contains DEFAULT_CELULA
        defaultAlfaShouldBeFound("celula.contains=" + DEFAULT_CELULA);

        // Get all the alfaList where celula contains UPDATED_CELULA
        defaultAlfaShouldNotBeFound("celula.contains=" + UPDATED_CELULA);
    }

    @Test
    @Transactional
    void getAllAlfasByCelulaNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where celula does not contain DEFAULT_CELULA
        defaultAlfaShouldNotBeFound("celula.doesNotContain=" + DEFAULT_CELULA);

        // Get all the alfaList where celula does not contain UPDATED_CELULA
        defaultAlfaShouldBeFound("celula.doesNotContain=" + UPDATED_CELULA);
    }

    @Test
    @Transactional
    void getAllAlfasByBlocoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bloco equals to DEFAULT_BLOCO
        defaultAlfaShouldBeFound("bloco.equals=" + DEFAULT_BLOCO);

        // Get all the alfaList where bloco equals to UPDATED_BLOCO
        defaultAlfaShouldNotBeFound("bloco.equals=" + UPDATED_BLOCO);
    }

    @Test
    @Transactional
    void getAllAlfasByBlocoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bloco in DEFAULT_BLOCO or UPDATED_BLOCO
        defaultAlfaShouldBeFound("bloco.in=" + DEFAULT_BLOCO + "," + UPDATED_BLOCO);

        // Get all the alfaList where bloco equals to UPDATED_BLOCO
        defaultAlfaShouldNotBeFound("bloco.in=" + UPDATED_BLOCO);
    }

    @Test
    @Transactional
    void getAllAlfasByBlocoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bloco is not null
        defaultAlfaShouldBeFound("bloco.specified=true");

        // Get all the alfaList where bloco is null
        defaultAlfaShouldNotBeFound("bloco.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByBlocoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bloco contains DEFAULT_BLOCO
        defaultAlfaShouldBeFound("bloco.contains=" + DEFAULT_BLOCO);

        // Get all the alfaList where bloco contains UPDATED_BLOCO
        defaultAlfaShouldNotBeFound("bloco.contains=" + UPDATED_BLOCO);
    }

    @Test
    @Transactional
    void getAllAlfasByBlocoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where bloco does not contain DEFAULT_BLOCO
        defaultAlfaShouldNotBeFound("bloco.doesNotContain=" + DEFAULT_BLOCO);

        // Get all the alfaList where bloco does not contain UPDATED_BLOCO
        defaultAlfaShouldBeFound("bloco.doesNotContain=" + UPDATED_BLOCO);
    }

    @Test
    @Transactional
    void getAllAlfasByAvenidaIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where avenida equals to DEFAULT_AVENIDA
        defaultAlfaShouldBeFound("avenida.equals=" + DEFAULT_AVENIDA);

        // Get all the alfaList where avenida equals to UPDATED_AVENIDA
        defaultAlfaShouldNotBeFound("avenida.equals=" + UPDATED_AVENIDA);
    }

    @Test
    @Transactional
    void getAllAlfasByAvenidaIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where avenida in DEFAULT_AVENIDA or UPDATED_AVENIDA
        defaultAlfaShouldBeFound("avenida.in=" + DEFAULT_AVENIDA + "," + UPDATED_AVENIDA);

        // Get all the alfaList where avenida equals to UPDATED_AVENIDA
        defaultAlfaShouldNotBeFound("avenida.in=" + UPDATED_AVENIDA);
    }

    @Test
    @Transactional
    void getAllAlfasByAvenidaIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where avenida is not null
        defaultAlfaShouldBeFound("avenida.specified=true");

        // Get all the alfaList where avenida is null
        defaultAlfaShouldNotBeFound("avenida.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByAvenidaContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where avenida contains DEFAULT_AVENIDA
        defaultAlfaShouldBeFound("avenida.contains=" + DEFAULT_AVENIDA);

        // Get all the alfaList where avenida contains UPDATED_AVENIDA
        defaultAlfaShouldNotBeFound("avenida.contains=" + UPDATED_AVENIDA);
    }

    @Test
    @Transactional
    void getAllAlfasByAvenidaNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where avenida does not contain DEFAULT_AVENIDA
        defaultAlfaShouldNotBeFound("avenida.doesNotContain=" + DEFAULT_AVENIDA);

        // Get all the alfaList where avenida does not contain UPDATED_AVENIDA
        defaultAlfaShouldBeFound("avenida.doesNotContain=" + UPDATED_AVENIDA);
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroPoliciaIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroPolicia equals to DEFAULT_NUMERO_POLICIA
        defaultAlfaShouldBeFound("numeroPolicia.equals=" + DEFAULT_NUMERO_POLICIA);

        // Get all the alfaList where numeroPolicia equals to UPDATED_NUMERO_POLICIA
        defaultAlfaShouldNotBeFound("numeroPolicia.equals=" + UPDATED_NUMERO_POLICIA);
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroPoliciaIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroPolicia in DEFAULT_NUMERO_POLICIA or UPDATED_NUMERO_POLICIA
        defaultAlfaShouldBeFound("numeroPolicia.in=" + DEFAULT_NUMERO_POLICIA + "," + UPDATED_NUMERO_POLICIA);

        // Get all the alfaList where numeroPolicia equals to UPDATED_NUMERO_POLICIA
        defaultAlfaShouldNotBeFound("numeroPolicia.in=" + UPDATED_NUMERO_POLICIA);
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroPoliciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroPolicia is not null
        defaultAlfaShouldBeFound("numeroPolicia.specified=true");

        // Get all the alfaList where numeroPolicia is null
        defaultAlfaShouldNotBeFound("numeroPolicia.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroPoliciaContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroPolicia contains DEFAULT_NUMERO_POLICIA
        defaultAlfaShouldBeFound("numeroPolicia.contains=" + DEFAULT_NUMERO_POLICIA);

        // Get all the alfaList where numeroPolicia contains UPDATED_NUMERO_POLICIA
        defaultAlfaShouldNotBeFound("numeroPolicia.contains=" + UPDATED_NUMERO_POLICIA);
    }

    @Test
    @Transactional
    void getAllAlfasByNumeroPoliciaNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where numeroPolicia does not contain DEFAULT_NUMERO_POLICIA
        defaultAlfaShouldNotBeFound("numeroPolicia.doesNotContain=" + DEFAULT_NUMERO_POLICIA);

        // Get all the alfaList where numeroPolicia does not contain UPDATED_NUMERO_POLICIA
        defaultAlfaShouldBeFound("numeroPolicia.doesNotContain=" + UPDATED_NUMERO_POLICIA);
    }

    @Test
    @Transactional
    void getAllAlfasByUsoActualIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where usoActual equals to DEFAULT_USO_ACTUAL
        defaultAlfaShouldBeFound("usoActual.equals=" + DEFAULT_USO_ACTUAL);

        // Get all the alfaList where usoActual equals to UPDATED_USO_ACTUAL
        defaultAlfaShouldNotBeFound("usoActual.equals=" + UPDATED_USO_ACTUAL);
    }

    @Test
    @Transactional
    void getAllAlfasByUsoActualIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where usoActual in DEFAULT_USO_ACTUAL or UPDATED_USO_ACTUAL
        defaultAlfaShouldBeFound("usoActual.in=" + DEFAULT_USO_ACTUAL + "," + UPDATED_USO_ACTUAL);

        // Get all the alfaList where usoActual equals to UPDATED_USO_ACTUAL
        defaultAlfaShouldNotBeFound("usoActual.in=" + UPDATED_USO_ACTUAL);
    }

    @Test
    @Transactional
    void getAllAlfasByUsoActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where usoActual is not null
        defaultAlfaShouldBeFound("usoActual.specified=true");

        // Get all the alfaList where usoActual is null
        defaultAlfaShouldNotBeFound("usoActual.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByUsoActualContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where usoActual contains DEFAULT_USO_ACTUAL
        defaultAlfaShouldBeFound("usoActual.contains=" + DEFAULT_USO_ACTUAL);

        // Get all the alfaList where usoActual contains UPDATED_USO_ACTUAL
        defaultAlfaShouldNotBeFound("usoActual.contains=" + UPDATED_USO_ACTUAL);
    }

    @Test
    @Transactional
    void getAllAlfasByUsoActualNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where usoActual does not contain DEFAULT_USO_ACTUAL
        defaultAlfaShouldNotBeFound("usoActual.doesNotContain=" + DEFAULT_USO_ACTUAL);

        // Get all the alfaList where usoActual does not contain UPDATED_USO_ACTUAL
        defaultAlfaShouldBeFound("usoActual.doesNotContain=" + UPDATED_USO_ACTUAL);
    }

    @Test
    @Transactional
    void getAllAlfasByFormaUsoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaUso equals to DEFAULT_FORMA_USO
        defaultAlfaShouldBeFound("formaUso.equals=" + DEFAULT_FORMA_USO);

        // Get all the alfaList where formaUso equals to UPDATED_FORMA_USO
        defaultAlfaShouldNotBeFound("formaUso.equals=" + UPDATED_FORMA_USO);
    }

    @Test
    @Transactional
    void getAllAlfasByFormaUsoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaUso in DEFAULT_FORMA_USO or UPDATED_FORMA_USO
        defaultAlfaShouldBeFound("formaUso.in=" + DEFAULT_FORMA_USO + "," + UPDATED_FORMA_USO);

        // Get all the alfaList where formaUso equals to UPDATED_FORMA_USO
        defaultAlfaShouldNotBeFound("formaUso.in=" + UPDATED_FORMA_USO);
    }

    @Test
    @Transactional
    void getAllAlfasByFormaUsoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaUso is not null
        defaultAlfaShouldBeFound("formaUso.specified=true");

        // Get all the alfaList where formaUso is null
        defaultAlfaShouldNotBeFound("formaUso.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByFormaUsoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaUso contains DEFAULT_FORMA_USO
        defaultAlfaShouldBeFound("formaUso.contains=" + DEFAULT_FORMA_USO);

        // Get all the alfaList where formaUso contains UPDATED_FORMA_USO
        defaultAlfaShouldNotBeFound("formaUso.contains=" + UPDATED_FORMA_USO);
    }

    @Test
    @Transactional
    void getAllAlfasByFormaUsoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaUso does not contain DEFAULT_FORMA_USO
        defaultAlfaShouldNotBeFound("formaUso.doesNotContain=" + DEFAULT_FORMA_USO);

        // Get all the alfaList where formaUso does not contain UPDATED_FORMA_USO
        defaultAlfaShouldBeFound("formaUso.doesNotContain=" + UPDATED_FORMA_USO);
    }

    @Test
    @Transactional
    void getAllAlfasByFormaObtencaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaObtencao equals to DEFAULT_FORMA_OBTENCAO
        defaultAlfaShouldBeFound("formaObtencao.equals=" + DEFAULT_FORMA_OBTENCAO);

        // Get all the alfaList where formaObtencao equals to UPDATED_FORMA_OBTENCAO
        defaultAlfaShouldNotBeFound("formaObtencao.equals=" + UPDATED_FORMA_OBTENCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByFormaObtencaoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaObtencao in DEFAULT_FORMA_OBTENCAO or UPDATED_FORMA_OBTENCAO
        defaultAlfaShouldBeFound("formaObtencao.in=" + DEFAULT_FORMA_OBTENCAO + "," + UPDATED_FORMA_OBTENCAO);

        // Get all the alfaList where formaObtencao equals to UPDATED_FORMA_OBTENCAO
        defaultAlfaShouldNotBeFound("formaObtencao.in=" + UPDATED_FORMA_OBTENCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByFormaObtencaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaObtencao is not null
        defaultAlfaShouldBeFound("formaObtencao.specified=true");

        // Get all the alfaList where formaObtencao is null
        defaultAlfaShouldNotBeFound("formaObtencao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByFormaObtencaoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaObtencao contains DEFAULT_FORMA_OBTENCAO
        defaultAlfaShouldBeFound("formaObtencao.contains=" + DEFAULT_FORMA_OBTENCAO);

        // Get all the alfaList where formaObtencao contains UPDATED_FORMA_OBTENCAO
        defaultAlfaShouldNotBeFound("formaObtencao.contains=" + UPDATED_FORMA_OBTENCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByFormaObtencaoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where formaObtencao does not contain DEFAULT_FORMA_OBTENCAO
        defaultAlfaShouldNotBeFound("formaObtencao.doesNotContain=" + DEFAULT_FORMA_OBTENCAO);

        // Get all the alfaList where formaObtencao does not contain UPDATED_FORMA_OBTENCAO
        defaultAlfaShouldBeFound("formaObtencao.doesNotContain=" + UPDATED_FORMA_OBTENCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipo equals to DEFAULT_TIPO
        defaultAlfaShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the alfaList where tipo equals to UPDATED_TIPO
        defaultAlfaShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultAlfaShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the alfaList where tipo equals to UPDATED_TIPO
        defaultAlfaShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipo is not null
        defaultAlfaShouldBeFound("tipo.specified=true");

        // Get all the alfaList where tipo is null
        defaultAlfaShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByTipoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipo contains DEFAULT_TIPO
        defaultAlfaShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

        // Get all the alfaList where tipo contains UPDATED_TIPO
        defaultAlfaShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipo does not contain DEFAULT_TIPO
        defaultAlfaShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

        // Get all the alfaList where tipo does not contain UPDATED_TIPO
        defaultAlfaShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAlfasByAnoOcupacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where anoOcupacao equals to DEFAULT_ANO_OCUPACAO
        defaultAlfaShouldBeFound("anoOcupacao.equals=" + DEFAULT_ANO_OCUPACAO);

        // Get all the alfaList where anoOcupacao equals to UPDATED_ANO_OCUPACAO
        defaultAlfaShouldNotBeFound("anoOcupacao.equals=" + UPDATED_ANO_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByAnoOcupacaoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where anoOcupacao in DEFAULT_ANO_OCUPACAO or UPDATED_ANO_OCUPACAO
        defaultAlfaShouldBeFound("anoOcupacao.in=" + DEFAULT_ANO_OCUPACAO + "," + UPDATED_ANO_OCUPACAO);

        // Get all the alfaList where anoOcupacao equals to UPDATED_ANO_OCUPACAO
        defaultAlfaShouldNotBeFound("anoOcupacao.in=" + UPDATED_ANO_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByAnoOcupacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where anoOcupacao is not null
        defaultAlfaShouldBeFound("anoOcupacao.specified=true");

        // Get all the alfaList where anoOcupacao is null
        defaultAlfaShouldNotBeFound("anoOcupacao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByAnoOcupacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where anoOcupacao is greater than or equal to DEFAULT_ANO_OCUPACAO
        defaultAlfaShouldBeFound("anoOcupacao.greaterThanOrEqual=" + DEFAULT_ANO_OCUPACAO);

        // Get all the alfaList where anoOcupacao is greater than or equal to UPDATED_ANO_OCUPACAO
        defaultAlfaShouldNotBeFound("anoOcupacao.greaterThanOrEqual=" + UPDATED_ANO_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByAnoOcupacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where anoOcupacao is less than or equal to DEFAULT_ANO_OCUPACAO
        defaultAlfaShouldBeFound("anoOcupacao.lessThanOrEqual=" + DEFAULT_ANO_OCUPACAO);

        // Get all the alfaList where anoOcupacao is less than or equal to SMALLER_ANO_OCUPACAO
        defaultAlfaShouldNotBeFound("anoOcupacao.lessThanOrEqual=" + SMALLER_ANO_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByAnoOcupacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where anoOcupacao is less than DEFAULT_ANO_OCUPACAO
        defaultAlfaShouldNotBeFound("anoOcupacao.lessThan=" + DEFAULT_ANO_OCUPACAO);

        // Get all the alfaList where anoOcupacao is less than UPDATED_ANO_OCUPACAO
        defaultAlfaShouldBeFound("anoOcupacao.lessThan=" + UPDATED_ANO_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByAnoOcupacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where anoOcupacao is greater than DEFAULT_ANO_OCUPACAO
        defaultAlfaShouldNotBeFound("anoOcupacao.greaterThan=" + DEFAULT_ANO_OCUPACAO);

        // Get all the alfaList where anoOcupacao is greater than SMALLER_ANO_OCUPACAO
        defaultAlfaShouldBeFound("anoOcupacao.greaterThan=" + SMALLER_ANO_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoAcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoAcesso equals to DEFAULT_TIPO_ACESSO
        defaultAlfaShouldBeFound("tipoAcesso.equals=" + DEFAULT_TIPO_ACESSO);

        // Get all the alfaList where tipoAcesso equals to UPDATED_TIPO_ACESSO
        defaultAlfaShouldNotBeFound("tipoAcesso.equals=" + UPDATED_TIPO_ACESSO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoAcessoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoAcesso in DEFAULT_TIPO_ACESSO or UPDATED_TIPO_ACESSO
        defaultAlfaShouldBeFound("tipoAcesso.in=" + DEFAULT_TIPO_ACESSO + "," + UPDATED_TIPO_ACESSO);

        // Get all the alfaList where tipoAcesso equals to UPDATED_TIPO_ACESSO
        defaultAlfaShouldNotBeFound("tipoAcesso.in=" + UPDATED_TIPO_ACESSO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoAcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoAcesso is not null
        defaultAlfaShouldBeFound("tipoAcesso.specified=true");

        // Get all the alfaList where tipoAcesso is null
        defaultAlfaShouldNotBeFound("tipoAcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByTipoAcessoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoAcesso contains DEFAULT_TIPO_ACESSO
        defaultAlfaShouldBeFound("tipoAcesso.contains=" + DEFAULT_TIPO_ACESSO);

        // Get all the alfaList where tipoAcesso contains UPDATED_TIPO_ACESSO
        defaultAlfaShouldNotBeFound("tipoAcesso.contains=" + UPDATED_TIPO_ACESSO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoAcessoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where tipoAcesso does not contain DEFAULT_TIPO_ACESSO
        defaultAlfaShouldNotBeFound("tipoAcesso.doesNotContain=" + DEFAULT_TIPO_ACESSO);

        // Get all the alfaList where tipoAcesso does not contain UPDATED_TIPO_ACESSO
        defaultAlfaShouldBeFound("tipoAcesso.doesNotContain=" + UPDATED_TIPO_ACESSO);
    }

    @Test
    @Transactional
    void getAllAlfasByConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where conflito equals to DEFAULT_CONFLITO
        defaultAlfaShouldBeFound("conflito.equals=" + DEFAULT_CONFLITO);

        // Get all the alfaList where conflito equals to UPDATED_CONFLITO
        defaultAlfaShouldNotBeFound("conflito.equals=" + UPDATED_CONFLITO);
    }

    @Test
    @Transactional
    void getAllAlfasByConflitoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where conflito in DEFAULT_CONFLITO or UPDATED_CONFLITO
        defaultAlfaShouldBeFound("conflito.in=" + DEFAULT_CONFLITO + "," + UPDATED_CONFLITO);

        // Get all the alfaList where conflito equals to UPDATED_CONFLITO
        defaultAlfaShouldNotBeFound("conflito.in=" + UPDATED_CONFLITO);
    }

    @Test
    @Transactional
    void getAllAlfasByConflitoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where conflito is not null
        defaultAlfaShouldBeFound("conflito.specified=true");

        // Get all the alfaList where conflito is null
        defaultAlfaShouldNotBeFound("conflito.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByDetalhesConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where detalhesConflito equals to DEFAULT_DETALHES_CONFLITO
        defaultAlfaShouldBeFound("detalhesConflito.equals=" + DEFAULT_DETALHES_CONFLITO);

        // Get all the alfaList where detalhesConflito equals to UPDATED_DETALHES_CONFLITO
        defaultAlfaShouldNotBeFound("detalhesConflito.equals=" + UPDATED_DETALHES_CONFLITO);
    }

    @Test
    @Transactional
    void getAllAlfasByDetalhesConflitoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where detalhesConflito in DEFAULT_DETALHES_CONFLITO or UPDATED_DETALHES_CONFLITO
        defaultAlfaShouldBeFound("detalhesConflito.in=" + DEFAULT_DETALHES_CONFLITO + "," + UPDATED_DETALHES_CONFLITO);

        // Get all the alfaList where detalhesConflito equals to UPDATED_DETALHES_CONFLITO
        defaultAlfaShouldNotBeFound("detalhesConflito.in=" + UPDATED_DETALHES_CONFLITO);
    }

    @Test
    @Transactional
    void getAllAlfasByDetalhesConflitoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where detalhesConflito is not null
        defaultAlfaShouldBeFound("detalhesConflito.specified=true");

        // Get all the alfaList where detalhesConflito is null
        defaultAlfaShouldNotBeFound("detalhesConflito.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByConstrucaoPrecariaIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where construcaoPrecaria equals to DEFAULT_CONSTRUCAO_PRECARIA
        defaultAlfaShouldBeFound("construcaoPrecaria.equals=" + DEFAULT_CONSTRUCAO_PRECARIA);

        // Get all the alfaList where construcaoPrecaria equals to UPDATED_CONSTRUCAO_PRECARIA
        defaultAlfaShouldNotBeFound("construcaoPrecaria.equals=" + UPDATED_CONSTRUCAO_PRECARIA);
    }

    @Test
    @Transactional
    void getAllAlfasByConstrucaoPrecariaIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where construcaoPrecaria in DEFAULT_CONSTRUCAO_PRECARIA or UPDATED_CONSTRUCAO_PRECARIA
        defaultAlfaShouldBeFound("construcaoPrecaria.in=" + DEFAULT_CONSTRUCAO_PRECARIA + "," + UPDATED_CONSTRUCAO_PRECARIA);

        // Get all the alfaList where construcaoPrecaria equals to UPDATED_CONSTRUCAO_PRECARIA
        defaultAlfaShouldNotBeFound("construcaoPrecaria.in=" + UPDATED_CONSTRUCAO_PRECARIA);
    }

    @Test
    @Transactional
    void getAllAlfasByConstrucaoPrecariaIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where construcaoPrecaria is not null
        defaultAlfaShouldBeFound("construcaoPrecaria.specified=true");

        // Get all the alfaList where construcaoPrecaria is null
        defaultAlfaShouldNotBeFound("construcaoPrecaria.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAcimaSoleiraIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAcimaSoleira equals to DEFAULT_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldBeFound("pisosAcimaSoleira.equals=" + DEFAULT_PISOS_ACIMA_SOLEIRA);

        // Get all the alfaList where pisosAcimaSoleira equals to UPDATED_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAcimaSoleira.equals=" + UPDATED_PISOS_ACIMA_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAcimaSoleiraIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAcimaSoleira in DEFAULT_PISOS_ACIMA_SOLEIRA or UPDATED_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldBeFound("pisosAcimaSoleira.in=" + DEFAULT_PISOS_ACIMA_SOLEIRA + "," + UPDATED_PISOS_ACIMA_SOLEIRA);

        // Get all the alfaList where pisosAcimaSoleira equals to UPDATED_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAcimaSoleira.in=" + UPDATED_PISOS_ACIMA_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAcimaSoleiraIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAcimaSoleira is not null
        defaultAlfaShouldBeFound("pisosAcimaSoleira.specified=true");

        // Get all the alfaList where pisosAcimaSoleira is null
        defaultAlfaShouldNotBeFound("pisosAcimaSoleira.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAcimaSoleiraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAcimaSoleira is greater than or equal to DEFAULT_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldBeFound("pisosAcimaSoleira.greaterThanOrEqual=" + DEFAULT_PISOS_ACIMA_SOLEIRA);

        // Get all the alfaList where pisosAcimaSoleira is greater than or equal to UPDATED_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAcimaSoleira.greaterThanOrEqual=" + UPDATED_PISOS_ACIMA_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAcimaSoleiraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAcimaSoleira is less than or equal to DEFAULT_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldBeFound("pisosAcimaSoleira.lessThanOrEqual=" + DEFAULT_PISOS_ACIMA_SOLEIRA);

        // Get all the alfaList where pisosAcimaSoleira is less than or equal to SMALLER_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAcimaSoleira.lessThanOrEqual=" + SMALLER_PISOS_ACIMA_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAcimaSoleiraIsLessThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAcimaSoleira is less than DEFAULT_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAcimaSoleira.lessThan=" + DEFAULT_PISOS_ACIMA_SOLEIRA);

        // Get all the alfaList where pisosAcimaSoleira is less than UPDATED_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldBeFound("pisosAcimaSoleira.lessThan=" + UPDATED_PISOS_ACIMA_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAcimaSoleiraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAcimaSoleira is greater than DEFAULT_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAcimaSoleira.greaterThan=" + DEFAULT_PISOS_ACIMA_SOLEIRA);

        // Get all the alfaList where pisosAcimaSoleira is greater than SMALLER_PISOS_ACIMA_SOLEIRA
        defaultAlfaShouldBeFound("pisosAcimaSoleira.greaterThan=" + SMALLER_PISOS_ACIMA_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAbaixoSoleiraIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAbaixoSoleira equals to DEFAULT_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldBeFound("pisosAbaixoSoleira.equals=" + DEFAULT_PISOS_ABAIXO_SOLEIRA);

        // Get all the alfaList where pisosAbaixoSoleira equals to UPDATED_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAbaixoSoleira.equals=" + UPDATED_PISOS_ABAIXO_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAbaixoSoleiraIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAbaixoSoleira in DEFAULT_PISOS_ABAIXO_SOLEIRA or UPDATED_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldBeFound("pisosAbaixoSoleira.in=" + DEFAULT_PISOS_ABAIXO_SOLEIRA + "," + UPDATED_PISOS_ABAIXO_SOLEIRA);

        // Get all the alfaList where pisosAbaixoSoleira equals to UPDATED_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAbaixoSoleira.in=" + UPDATED_PISOS_ABAIXO_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAbaixoSoleiraIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAbaixoSoleira is not null
        defaultAlfaShouldBeFound("pisosAbaixoSoleira.specified=true");

        // Get all the alfaList where pisosAbaixoSoleira is null
        defaultAlfaShouldNotBeFound("pisosAbaixoSoleira.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAbaixoSoleiraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAbaixoSoleira is greater than or equal to DEFAULT_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldBeFound("pisosAbaixoSoleira.greaterThanOrEqual=" + DEFAULT_PISOS_ABAIXO_SOLEIRA);

        // Get all the alfaList where pisosAbaixoSoleira is greater than or equal to UPDATED_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAbaixoSoleira.greaterThanOrEqual=" + UPDATED_PISOS_ABAIXO_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAbaixoSoleiraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAbaixoSoleira is less than or equal to DEFAULT_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldBeFound("pisosAbaixoSoleira.lessThanOrEqual=" + DEFAULT_PISOS_ABAIXO_SOLEIRA);

        // Get all the alfaList where pisosAbaixoSoleira is less than or equal to SMALLER_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAbaixoSoleira.lessThanOrEqual=" + SMALLER_PISOS_ABAIXO_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAbaixoSoleiraIsLessThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAbaixoSoleira is less than DEFAULT_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAbaixoSoleira.lessThan=" + DEFAULT_PISOS_ABAIXO_SOLEIRA);

        // Get all the alfaList where pisosAbaixoSoleira is less than UPDATED_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldBeFound("pisosAbaixoSoleira.lessThan=" + UPDATED_PISOS_ABAIXO_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByPisosAbaixoSoleiraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where pisosAbaixoSoleira is greater than DEFAULT_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldNotBeFound("pisosAbaixoSoleira.greaterThan=" + DEFAULT_PISOS_ABAIXO_SOLEIRA);

        // Get all the alfaList where pisosAbaixoSoleira is greater than SMALLER_PISOS_ABAIXO_SOLEIRA
        defaultAlfaShouldBeFound("pisosAbaixoSoleira.greaterThan=" + SMALLER_PISOS_ABAIXO_SOLEIRA);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoBarroteIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoBarrote equals to DEFAULT_MATERIAL_CONSTRUCAO_BARROTE
        defaultAlfaShouldBeFound("materialConstrucaoBarrote.equals=" + DEFAULT_MATERIAL_CONSTRUCAO_BARROTE);

        // Get all the alfaList where materialConstrucaoBarrote equals to UPDATED_MATERIAL_CONSTRUCAO_BARROTE
        defaultAlfaShouldNotBeFound("materialConstrucaoBarrote.equals=" + UPDATED_MATERIAL_CONSTRUCAO_BARROTE);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoBarroteIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoBarrote in DEFAULT_MATERIAL_CONSTRUCAO_BARROTE or UPDATED_MATERIAL_CONSTRUCAO_BARROTE
        defaultAlfaShouldBeFound(
            "materialConstrucaoBarrote.in=" + DEFAULT_MATERIAL_CONSTRUCAO_BARROTE + "," + UPDATED_MATERIAL_CONSTRUCAO_BARROTE
        );

        // Get all the alfaList where materialConstrucaoBarrote equals to UPDATED_MATERIAL_CONSTRUCAO_BARROTE
        defaultAlfaShouldNotBeFound("materialConstrucaoBarrote.in=" + UPDATED_MATERIAL_CONSTRUCAO_BARROTE);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoBarroteIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoBarrote is not null
        defaultAlfaShouldBeFound("materialConstrucaoBarrote.specified=true");

        // Get all the alfaList where materialConstrucaoBarrote is null
        defaultAlfaShouldNotBeFound("materialConstrucaoBarrote.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoIBRIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoIBR equals to DEFAULT_MATERIAL_CONSTRUCAO_IBR
        defaultAlfaShouldBeFound("materialConstrucaoIBR.equals=" + DEFAULT_MATERIAL_CONSTRUCAO_IBR);

        // Get all the alfaList where materialConstrucaoIBR equals to UPDATED_MATERIAL_CONSTRUCAO_IBR
        defaultAlfaShouldNotBeFound("materialConstrucaoIBR.equals=" + UPDATED_MATERIAL_CONSTRUCAO_IBR);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoIBRIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoIBR in DEFAULT_MATERIAL_CONSTRUCAO_IBR or UPDATED_MATERIAL_CONSTRUCAO_IBR
        defaultAlfaShouldBeFound("materialConstrucaoIBR.in=" + DEFAULT_MATERIAL_CONSTRUCAO_IBR + "," + UPDATED_MATERIAL_CONSTRUCAO_IBR);

        // Get all the alfaList where materialConstrucaoIBR equals to UPDATED_MATERIAL_CONSTRUCAO_IBR
        defaultAlfaShouldNotBeFound("materialConstrucaoIBR.in=" + UPDATED_MATERIAL_CONSTRUCAO_IBR);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoIBRIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoIBR is not null
        defaultAlfaShouldBeFound("materialConstrucaoIBR.specified=true");

        // Get all the alfaList where materialConstrucaoIBR is null
        defaultAlfaShouldNotBeFound("materialConstrucaoIBR.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoPranchasIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoPranchas equals to DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS
        defaultAlfaShouldBeFound("materialConstrucaoPranchas.equals=" + DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS);

        // Get all the alfaList where materialConstrucaoPranchas equals to UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS
        defaultAlfaShouldNotBeFound("materialConstrucaoPranchas.equals=" + UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoPranchasIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoPranchas in DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS or UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS
        defaultAlfaShouldBeFound(
            "materialConstrucaoPranchas.in=" + DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS + "," + UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS
        );

        // Get all the alfaList where materialConstrucaoPranchas equals to UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS
        defaultAlfaShouldNotBeFound("materialConstrucaoPranchas.in=" + UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoPranchasIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoPranchas is not null
        defaultAlfaShouldBeFound("materialConstrucaoPranchas.specified=true");

        // Get all the alfaList where materialConstrucaoPranchas is null
        defaultAlfaShouldNotBeFound("materialConstrucaoPranchas.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoPauIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoPau equals to DEFAULT_MATERIAL_CONSTRUCAO_PAU
        defaultAlfaShouldBeFound("materialConstrucaoPau.equals=" + DEFAULT_MATERIAL_CONSTRUCAO_PAU);

        // Get all the alfaList where materialConstrucaoPau equals to UPDATED_MATERIAL_CONSTRUCAO_PAU
        defaultAlfaShouldNotBeFound("materialConstrucaoPau.equals=" + UPDATED_MATERIAL_CONSTRUCAO_PAU);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoPauIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoPau in DEFAULT_MATERIAL_CONSTRUCAO_PAU or UPDATED_MATERIAL_CONSTRUCAO_PAU
        defaultAlfaShouldBeFound("materialConstrucaoPau.in=" + DEFAULT_MATERIAL_CONSTRUCAO_PAU + "," + UPDATED_MATERIAL_CONSTRUCAO_PAU);

        // Get all the alfaList where materialConstrucaoPau equals to UPDATED_MATERIAL_CONSTRUCAO_PAU
        defaultAlfaShouldNotBeFound("materialConstrucaoPau.in=" + UPDATED_MATERIAL_CONSTRUCAO_PAU);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoPauIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoPau is not null
        defaultAlfaShouldBeFound("materialConstrucaoPau.specified=true");

        // Get all the alfaList where materialConstrucaoPau is null
        defaultAlfaShouldNotBeFound("materialConstrucaoPau.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoCanicoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoCanico equals to DEFAULT_MATERIAL_CONSTRUCAO_CANICO
        defaultAlfaShouldBeFound("materialConstrucaoCanico.equals=" + DEFAULT_MATERIAL_CONSTRUCAO_CANICO);

        // Get all the alfaList where materialConstrucaoCanico equals to UPDATED_MATERIAL_CONSTRUCAO_CANICO
        defaultAlfaShouldNotBeFound("materialConstrucaoCanico.equals=" + UPDATED_MATERIAL_CONSTRUCAO_CANICO);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoCanicoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoCanico in DEFAULT_MATERIAL_CONSTRUCAO_CANICO or UPDATED_MATERIAL_CONSTRUCAO_CANICO
        defaultAlfaShouldBeFound(
            "materialConstrucaoCanico.in=" + DEFAULT_MATERIAL_CONSTRUCAO_CANICO + "," + UPDATED_MATERIAL_CONSTRUCAO_CANICO
        );

        // Get all the alfaList where materialConstrucaoCanico equals to UPDATED_MATERIAL_CONSTRUCAO_CANICO
        defaultAlfaShouldNotBeFound("materialConstrucaoCanico.in=" + UPDATED_MATERIAL_CONSTRUCAO_CANICO);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoCanicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoCanico is not null
        defaultAlfaShouldBeFound("materialConstrucaoCanico.specified=true");

        // Get all the alfaList where materialConstrucaoCanico is null
        defaultAlfaShouldNotBeFound("materialConstrucaoCanico.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoCimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoCimento equals to DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO
        defaultAlfaShouldBeFound("materialConstrucaoCimento.equals=" + DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO);

        // Get all the alfaList where materialConstrucaoCimento equals to UPDATED_MATERIAL_CONSTRUCAO_CIMENTO
        defaultAlfaShouldNotBeFound("materialConstrucaoCimento.equals=" + UPDATED_MATERIAL_CONSTRUCAO_CIMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoCimentoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoCimento in DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO or UPDATED_MATERIAL_CONSTRUCAO_CIMENTO
        defaultAlfaShouldBeFound(
            "materialConstrucaoCimento.in=" + DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO + "," + UPDATED_MATERIAL_CONSTRUCAO_CIMENTO
        );

        // Get all the alfaList where materialConstrucaoCimento equals to UPDATED_MATERIAL_CONSTRUCAO_CIMENTO
        defaultAlfaShouldNotBeFound("materialConstrucaoCimento.in=" + UPDATED_MATERIAL_CONSTRUCAO_CIMENTO);
    }

    @Test
    @Transactional
    void getAllAlfasByMaterialConstrucaoCimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where materialConstrucaoCimento is not null
        defaultAlfaShouldBeFound("materialConstrucaoCimento.specified=true");

        // Get all the alfaList where materialConstrucaoCimento is null
        defaultAlfaShouldNotBeFound("materialConstrucaoCimento.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByOcupacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where ocupacao equals to DEFAULT_OCUPACAO
        defaultAlfaShouldBeFound("ocupacao.equals=" + DEFAULT_OCUPACAO);

        // Get all the alfaList where ocupacao equals to UPDATED_OCUPACAO
        defaultAlfaShouldNotBeFound("ocupacao.equals=" + UPDATED_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByOcupacaoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where ocupacao in DEFAULT_OCUPACAO or UPDATED_OCUPACAO
        defaultAlfaShouldBeFound("ocupacao.in=" + DEFAULT_OCUPACAO + "," + UPDATED_OCUPACAO);

        // Get all the alfaList where ocupacao equals to UPDATED_OCUPACAO
        defaultAlfaShouldNotBeFound("ocupacao.in=" + UPDATED_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByOcupacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where ocupacao is not null
        defaultAlfaShouldBeFound("ocupacao.specified=true");

        // Get all the alfaList where ocupacao is null
        defaultAlfaShouldNotBeFound("ocupacao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByOcupacaoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where ocupacao contains DEFAULT_OCUPACAO
        defaultAlfaShouldBeFound("ocupacao.contains=" + DEFAULT_OCUPACAO);

        // Get all the alfaList where ocupacao contains UPDATED_OCUPACAO
        defaultAlfaShouldNotBeFound("ocupacao.contains=" + UPDATED_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByOcupacaoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where ocupacao does not contain DEFAULT_OCUPACAO
        defaultAlfaShouldNotBeFound("ocupacao.doesNotContain=" + DEFAULT_OCUPACAO);

        // Get all the alfaList where ocupacao does not contain UPDATED_OCUPACAO
        defaultAlfaShouldBeFound("ocupacao.doesNotContain=" + UPDATED_OCUPACAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoContrucaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where TipoContrucao equals to DEFAULT_TIPO_CONTRUCAO
        defaultAlfaShouldBeFound("TipoContrucao.equals=" + DEFAULT_TIPO_CONTRUCAO);

        // Get all the alfaList where TipoContrucao equals to UPDATED_TIPO_CONTRUCAO
        defaultAlfaShouldNotBeFound("TipoContrucao.equals=" + UPDATED_TIPO_CONTRUCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoContrucaoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where TipoContrucao in DEFAULT_TIPO_CONTRUCAO or UPDATED_TIPO_CONTRUCAO
        defaultAlfaShouldBeFound("TipoContrucao.in=" + DEFAULT_TIPO_CONTRUCAO + "," + UPDATED_TIPO_CONTRUCAO);

        // Get all the alfaList where TipoContrucao equals to UPDATED_TIPO_CONTRUCAO
        defaultAlfaShouldNotBeFound("TipoContrucao.in=" + UPDATED_TIPO_CONTRUCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoContrucaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where TipoContrucao is not null
        defaultAlfaShouldBeFound("TipoContrucao.specified=true");

        // Get all the alfaList where TipoContrucao is null
        defaultAlfaShouldNotBeFound("TipoContrucao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByTipoContrucaoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where TipoContrucao contains DEFAULT_TIPO_CONTRUCAO
        defaultAlfaShouldBeFound("TipoContrucao.contains=" + DEFAULT_TIPO_CONTRUCAO);

        // Get all the alfaList where TipoContrucao contains UPDATED_TIPO_CONTRUCAO
        defaultAlfaShouldNotBeFound("TipoContrucao.contains=" + UPDATED_TIPO_CONTRUCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByTipoContrucaoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where TipoContrucao does not contain DEFAULT_TIPO_CONTRUCAO
        defaultAlfaShouldNotBeFound("TipoContrucao.doesNotContain=" + DEFAULT_TIPO_CONTRUCAO);

        // Get all the alfaList where TipoContrucao does not contain UPDATED_TIPO_CONTRUCAO
        defaultAlfaShouldBeFound("TipoContrucao.doesNotContain=" + UPDATED_TIPO_CONTRUCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByDetalhesTipoContrucaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where detalhesTipoContrucao equals to DEFAULT_DETALHES_TIPO_CONTRUCAO
        defaultAlfaShouldBeFound("detalhesTipoContrucao.equals=" + DEFAULT_DETALHES_TIPO_CONTRUCAO);

        // Get all the alfaList where detalhesTipoContrucao equals to UPDATED_DETALHES_TIPO_CONTRUCAO
        defaultAlfaShouldNotBeFound("detalhesTipoContrucao.equals=" + UPDATED_DETALHES_TIPO_CONTRUCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByDetalhesTipoContrucaoIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where detalhesTipoContrucao in DEFAULT_DETALHES_TIPO_CONTRUCAO or UPDATED_DETALHES_TIPO_CONTRUCAO
        defaultAlfaShouldBeFound("detalhesTipoContrucao.in=" + DEFAULT_DETALHES_TIPO_CONTRUCAO + "," + UPDATED_DETALHES_TIPO_CONTRUCAO);

        // Get all the alfaList where detalhesTipoContrucao equals to UPDATED_DETALHES_TIPO_CONTRUCAO
        defaultAlfaShouldNotBeFound("detalhesTipoContrucao.in=" + UPDATED_DETALHES_TIPO_CONTRUCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByDetalhesTipoContrucaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where detalhesTipoContrucao is not null
        defaultAlfaShouldBeFound("detalhesTipoContrucao.specified=true");

        // Get all the alfaList where detalhesTipoContrucao is null
        defaultAlfaShouldNotBeFound("detalhesTipoContrucao.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByDetalhesTipoContrucaoContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where detalhesTipoContrucao contains DEFAULT_DETALHES_TIPO_CONTRUCAO
        defaultAlfaShouldBeFound("detalhesTipoContrucao.contains=" + DEFAULT_DETALHES_TIPO_CONTRUCAO);

        // Get all the alfaList where detalhesTipoContrucao contains UPDATED_DETALHES_TIPO_CONTRUCAO
        defaultAlfaShouldNotBeFound("detalhesTipoContrucao.contains=" + UPDATED_DETALHES_TIPO_CONTRUCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByDetalhesTipoContrucaoNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where detalhesTipoContrucao does not contain DEFAULT_DETALHES_TIPO_CONTRUCAO
        defaultAlfaShouldNotBeFound("detalhesTipoContrucao.doesNotContain=" + DEFAULT_DETALHES_TIPO_CONTRUCAO);

        // Get all the alfaList where detalhesTipoContrucao does not contain UPDATED_DETALHES_TIPO_CONTRUCAO
        defaultAlfaShouldBeFound("detalhesTipoContrucao.doesNotContain=" + UPDATED_DETALHES_TIPO_CONTRUCAO);
    }

    @Test
    @Transactional
    void getAllAlfasByInfraestruturaExistenteIsEqualToSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where infraestruturaExistente equals to DEFAULT_INFRAESTRUTURA_EXISTENTE
        defaultAlfaShouldBeFound("infraestruturaExistente.equals=" + DEFAULT_INFRAESTRUTURA_EXISTENTE);

        // Get all the alfaList where infraestruturaExistente equals to UPDATED_INFRAESTRUTURA_EXISTENTE
        defaultAlfaShouldNotBeFound("infraestruturaExistente.equals=" + UPDATED_INFRAESTRUTURA_EXISTENTE);
    }

    @Test
    @Transactional
    void getAllAlfasByInfraestruturaExistenteIsInShouldWork() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where infraestruturaExistente in DEFAULT_INFRAESTRUTURA_EXISTENTE or UPDATED_INFRAESTRUTURA_EXISTENTE
        defaultAlfaShouldBeFound("infraestruturaExistente.in=" + DEFAULT_INFRAESTRUTURA_EXISTENTE + "," + UPDATED_INFRAESTRUTURA_EXISTENTE);

        // Get all the alfaList where infraestruturaExistente equals to UPDATED_INFRAESTRUTURA_EXISTENTE
        defaultAlfaShouldNotBeFound("infraestruturaExistente.in=" + UPDATED_INFRAESTRUTURA_EXISTENTE);
    }

    @Test
    @Transactional
    void getAllAlfasByInfraestruturaExistenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where infraestruturaExistente is not null
        defaultAlfaShouldBeFound("infraestruturaExistente.specified=true");

        // Get all the alfaList where infraestruturaExistente is null
        defaultAlfaShouldNotBeFound("infraestruturaExistente.specified=false");
    }

    @Test
    @Transactional
    void getAllAlfasByInfraestruturaExistenteContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where infraestruturaExistente contains DEFAULT_INFRAESTRUTURA_EXISTENTE
        defaultAlfaShouldBeFound("infraestruturaExistente.contains=" + DEFAULT_INFRAESTRUTURA_EXISTENTE);

        // Get all the alfaList where infraestruturaExistente contains UPDATED_INFRAESTRUTURA_EXISTENTE
        defaultAlfaShouldNotBeFound("infraestruturaExistente.contains=" + UPDATED_INFRAESTRUTURA_EXISTENTE);
    }

    @Test
    @Transactional
    void getAllAlfasByInfraestruturaExistenteNotContainsSomething() throws Exception {
        // Initialize the database
        alfaRepository.saveAndFlush(alfa);

        // Get all the alfaList where infraestruturaExistente does not contain DEFAULT_INFRAESTRUTURA_EXISTENTE
        defaultAlfaShouldNotBeFound("infraestruturaExistente.doesNotContain=" + DEFAULT_INFRAESTRUTURA_EXISTENTE);

        // Get all the alfaList where infraestruturaExistente does not contain UPDATED_INFRAESTRUTURA_EXISTENTE
        defaultAlfaShouldBeFound("infraestruturaExistente.doesNotContain=" + UPDATED_INFRAESTRUTURA_EXISTENTE);
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
            .andExpect(jsonPath("$.[*].tipoTitular").value(hasItem(DEFAULT_TIPO_TITULAR)))
            .andExpect(jsonPath("$.[*].nomeTitular").value(hasItem(DEFAULT_NOME_TITULAR)))
            .andExpect(jsonPath("$.[*].estadoSocial").value(hasItem(DEFAULT_ESTADO_SOCIAL)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].datEmissao").value(hasItem(DEFAULT_DAT_EMISSAO)))
            .andExpect(jsonPath("$.[*].localEmissao").value(hasItem(DEFAULT_LOCAL_EMISSAO)))
            .andExpect(jsonPath("$.[*].contactoPrincipal").value(hasItem(DEFAULT_CONTACTO_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].contactoAlternativo").value(hasItem(DEFAULT_CONTACTO_ALTERNATIVO)))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL)))
            .andExpect(jsonPath("$.[*].nomeConjugue").value(hasItem(DEFAULT_NOME_CONJUGUE)))
            .andExpect(jsonPath("$.[*].distritoMunicipal").value(hasItem(DEFAULT_DISTRITO_MUNICIPAL)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].quatreirao").value(hasItem(DEFAULT_QUATREIRAO)))
            .andExpect(jsonPath("$.[*].talhao").value(hasItem(DEFAULT_TALHAO)))
            .andExpect(jsonPath("$.[*].celula").value(hasItem(DEFAULT_CELULA)))
            .andExpect(jsonPath("$.[*].bloco").value(hasItem(DEFAULT_BLOCO)))
            .andExpect(jsonPath("$.[*].avenida").value(hasItem(DEFAULT_AVENIDA)))
            .andExpect(jsonPath("$.[*].numeroPolicia").value(hasItem(DEFAULT_NUMERO_POLICIA)))
            .andExpect(jsonPath("$.[*].usoActual").value(hasItem(DEFAULT_USO_ACTUAL)))
            .andExpect(jsonPath("$.[*].formaUso").value(hasItem(DEFAULT_FORMA_USO)))
            .andExpect(jsonPath("$.[*].formaObtencao").value(hasItem(DEFAULT_FORMA_OBTENCAO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].anoOcupacao").value(hasItem(DEFAULT_ANO_OCUPACAO)))
            .andExpect(jsonPath("$.[*].tipoAcesso").value(hasItem(DEFAULT_TIPO_ACESSO)))
            .andExpect(jsonPath("$.[*].conflito").value(hasItem(DEFAULT_CONFLITO.booleanValue())))
            .andExpect(jsonPath("$.[*].detalhesConflito").value(hasItem(DEFAULT_DETALHES_CONFLITO.booleanValue())))
            .andExpect(jsonPath("$.[*].construcaoPrecaria").value(hasItem(DEFAULT_CONSTRUCAO_PRECARIA.booleanValue())))
            .andExpect(jsonPath("$.[*].pisosAcimaSoleira").value(hasItem(DEFAULT_PISOS_ACIMA_SOLEIRA)))
            .andExpect(jsonPath("$.[*].pisosAbaixoSoleira").value(hasItem(DEFAULT_PISOS_ABAIXO_SOLEIRA)))
            .andExpect(jsonPath("$.[*].materialConstrucaoBarrote").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_BARROTE.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoIBR").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_IBR.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoPranchas").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_PRANCHAS.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoPau").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_PAU.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoCanico").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_CANICO.booleanValue())))
            .andExpect(jsonPath("$.[*].materialConstrucaoCimento").value(hasItem(DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].ocupacao").value(hasItem(DEFAULT_OCUPACAO)))
            .andExpect(jsonPath("$.[*].TipoContrucao").value(hasItem(DEFAULT_TIPO_CONTRUCAO)))
            .andExpect(jsonPath("$.[*].detalhesTipoContrucao").value(hasItem(DEFAULT_DETALHES_TIPO_CONTRUCAO)))
            .andExpect(jsonPath("$.[*].infraestruturaExistente").value(hasItem(DEFAULT_INFRAESTRUTURA_EXISTENTE)))
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
        updatedAlfa
            .parcela(UPDATED_PARCELA)
            .tipoTitular(UPDATED_TIPO_TITULAR)
            .nomeTitular(UPDATED_NOME_TITULAR)
            .estadoSocial(UPDATED_ESTADO_SOCIAL)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .sexo(UPDATED_SEXO)
            .documento(UPDATED_DOCUMENTO)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .datEmissao(UPDATED_DAT_EMISSAO)
            .localEmissao(UPDATED_LOCAL_EMISSAO)
            .contactoPrincipal(UPDATED_CONTACTO_PRINCIPAL)
            .contactoAlternativo(UPDATED_CONTACTO_ALTERNATIVO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .nomeConjugue(UPDATED_NOME_CONJUGUE)
            .distritoMunicipal(UPDATED_DISTRITO_MUNICIPAL)
            .bairro(UPDATED_BAIRRO)
            .quatreirao(UPDATED_QUATREIRAO)
            .talhao(UPDATED_TALHAO)
            .celula(UPDATED_CELULA)
            .bloco(UPDATED_BLOCO)
            .avenida(UPDATED_AVENIDA)
            .numeroPolicia(UPDATED_NUMERO_POLICIA)
            .usoActual(UPDATED_USO_ACTUAL)
            .formaUso(UPDATED_FORMA_USO)
            .formaObtencao(UPDATED_FORMA_OBTENCAO)
            .tipo(UPDATED_TIPO)
            .anoOcupacao(UPDATED_ANO_OCUPACAO)
            .tipoAcesso(UPDATED_TIPO_ACESSO)
            .conflito(UPDATED_CONFLITO)
            .detalhesConflito(UPDATED_DETALHES_CONFLITO)
            .construcaoPrecaria(UPDATED_CONSTRUCAO_PRECARIA)
            .pisosAcimaSoleira(UPDATED_PISOS_ACIMA_SOLEIRA)
            .pisosAbaixoSoleira(UPDATED_PISOS_ABAIXO_SOLEIRA)
            .materialConstrucaoBarrote(UPDATED_MATERIAL_CONSTRUCAO_BARROTE)
            .materialConstrucaoIBR(UPDATED_MATERIAL_CONSTRUCAO_IBR)
            .materialConstrucaoPranchas(UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS)
            .materialConstrucaoPau(UPDATED_MATERIAL_CONSTRUCAO_PAU)
            .materialConstrucaoCanico(UPDATED_MATERIAL_CONSTRUCAO_CANICO)
            .materialConstrucaoCimento(UPDATED_MATERIAL_CONSTRUCAO_CIMENTO)
            .ocupacao(UPDATED_OCUPACAO)
            .TipoContrucao(UPDATED_TIPO_CONTRUCAO)
            .detalhesTipoContrucao(UPDATED_DETALHES_TIPO_CONTRUCAO)
            .infraestruturaExistente(UPDATED_INFRAESTRUTURA_EXISTENTE)
            .dataLevantamento(UPDATED_DATA_LEVANTAMENTO);
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
        assertThat(testAlfa.getTipoTitular()).isEqualTo(UPDATED_TIPO_TITULAR);
        assertThat(testAlfa.getNomeTitular()).isEqualTo(UPDATED_NOME_TITULAR);
        assertThat(testAlfa.getEstadoSocial()).isEqualTo(UPDATED_ESTADO_SOCIAL);
        assertThat(testAlfa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testAlfa.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testAlfa.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testAlfa.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testAlfa.getDatEmissao()).isEqualTo(UPDATED_DAT_EMISSAO);
        assertThat(testAlfa.getLocalEmissao()).isEqualTo(UPDATED_LOCAL_EMISSAO);
        assertThat(testAlfa.getContactoPrincipal()).isEqualTo(UPDATED_CONTACTO_PRINCIPAL);
        assertThat(testAlfa.getContactoAlternativo()).isEqualTo(UPDATED_CONTACTO_ALTERNATIVO);
        assertThat(testAlfa.getEstadoCivil()).isEqualTo(UPDATED_ESTADO_CIVIL);
        assertThat(testAlfa.getNomeConjugue()).isEqualTo(UPDATED_NOME_CONJUGUE);
        assertThat(testAlfa.getDistritoMunicipal()).isEqualTo(UPDATED_DISTRITO_MUNICIPAL);
        assertThat(testAlfa.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testAlfa.getQuatreirao()).isEqualTo(UPDATED_QUATREIRAO);
        assertThat(testAlfa.getTalhao()).isEqualTo(UPDATED_TALHAO);
        assertThat(testAlfa.getCelula()).isEqualTo(UPDATED_CELULA);
        assertThat(testAlfa.getBloco()).isEqualTo(UPDATED_BLOCO);
        assertThat(testAlfa.getAvenida()).isEqualTo(UPDATED_AVENIDA);
        assertThat(testAlfa.getNumeroPolicia()).isEqualTo(UPDATED_NUMERO_POLICIA);
        assertThat(testAlfa.getUsoActual()).isEqualTo(UPDATED_USO_ACTUAL);
        assertThat(testAlfa.getFormaUso()).isEqualTo(UPDATED_FORMA_USO);
        assertThat(testAlfa.getFormaObtencao()).isEqualTo(UPDATED_FORMA_OBTENCAO);
        assertThat(testAlfa.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAlfa.getAnoOcupacao()).isEqualTo(UPDATED_ANO_OCUPACAO);
        assertThat(testAlfa.getTipoAcesso()).isEqualTo(UPDATED_TIPO_ACESSO);
        assertThat(testAlfa.getConflito()).isEqualTo(UPDATED_CONFLITO);
        assertThat(testAlfa.getDetalhesConflito()).isEqualTo(UPDATED_DETALHES_CONFLITO);
        assertThat(testAlfa.getConstrucaoPrecaria()).isEqualTo(UPDATED_CONSTRUCAO_PRECARIA);
        assertThat(testAlfa.getPisosAcimaSoleira()).isEqualTo(UPDATED_PISOS_ACIMA_SOLEIRA);
        assertThat(testAlfa.getPisosAbaixoSoleira()).isEqualTo(UPDATED_PISOS_ABAIXO_SOLEIRA);
        assertThat(testAlfa.getMaterialConstrucaoBarrote()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_BARROTE);
        assertThat(testAlfa.getMaterialConstrucaoIBR()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_IBR);
        assertThat(testAlfa.getMaterialConstrucaoPranchas()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS);
        assertThat(testAlfa.getMaterialConstrucaoPau()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_PAU);
        assertThat(testAlfa.getMaterialConstrucaoCanico()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_CANICO);
        assertThat(testAlfa.getMaterialConstrucaoCimento()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_CIMENTO);
        assertThat(testAlfa.getOcupacao()).isEqualTo(UPDATED_OCUPACAO);
        assertThat(testAlfa.getTipoContrucao()).isEqualTo(UPDATED_TIPO_CONTRUCAO);
        assertThat(testAlfa.getDetalhesTipoContrucao()).isEqualTo(UPDATED_DETALHES_TIPO_CONTRUCAO);
        assertThat(testAlfa.getInfraestruturaExistente()).isEqualTo(UPDATED_INFRAESTRUTURA_EXISTENTE);
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

        partialUpdatedAlfa
            .nomeTitular(UPDATED_NOME_TITULAR)
            .estadoSocial(UPDATED_ESTADO_SOCIAL)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .contactoPrincipal(UPDATED_CONTACTO_PRINCIPAL)
            .distritoMunicipal(UPDATED_DISTRITO_MUNICIPAL)
            .quatreirao(UPDATED_QUATREIRAO)
            .talhao(UPDATED_TALHAO)
            .bloco(UPDATED_BLOCO)
            .avenida(UPDATED_AVENIDA)
            .numeroPolicia(UPDATED_NUMERO_POLICIA)
            .formaUso(UPDATED_FORMA_USO)
            .formaObtencao(UPDATED_FORMA_OBTENCAO)
            .anoOcupacao(UPDATED_ANO_OCUPACAO)
            .pisosAbaixoSoleira(UPDATED_PISOS_ABAIXO_SOLEIRA)
            .materialConstrucaoBarrote(UPDATED_MATERIAL_CONSTRUCAO_BARROTE)
            .materialConstrucaoIBR(UPDATED_MATERIAL_CONSTRUCAO_IBR)
            .materialConstrucaoPranchas(UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS)
            .ocupacao(UPDATED_OCUPACAO)
            .TipoContrucao(UPDATED_TIPO_CONTRUCAO);

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
        assertThat(testAlfa.getTipoTitular()).isEqualTo(DEFAULT_TIPO_TITULAR);
        assertThat(testAlfa.getNomeTitular()).isEqualTo(UPDATED_NOME_TITULAR);
        assertThat(testAlfa.getEstadoSocial()).isEqualTo(UPDATED_ESTADO_SOCIAL);
        assertThat(testAlfa.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testAlfa.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testAlfa.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testAlfa.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testAlfa.getDatEmissao()).isEqualTo(DEFAULT_DAT_EMISSAO);
        assertThat(testAlfa.getLocalEmissao()).isEqualTo(DEFAULT_LOCAL_EMISSAO);
        assertThat(testAlfa.getContactoPrincipal()).isEqualTo(UPDATED_CONTACTO_PRINCIPAL);
        assertThat(testAlfa.getContactoAlternativo()).isEqualTo(DEFAULT_CONTACTO_ALTERNATIVO);
        assertThat(testAlfa.getEstadoCivil()).isEqualTo(DEFAULT_ESTADO_CIVIL);
        assertThat(testAlfa.getNomeConjugue()).isEqualTo(DEFAULT_NOME_CONJUGUE);
        assertThat(testAlfa.getDistritoMunicipal()).isEqualTo(UPDATED_DISTRITO_MUNICIPAL);
        assertThat(testAlfa.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testAlfa.getQuatreirao()).isEqualTo(UPDATED_QUATREIRAO);
        assertThat(testAlfa.getTalhao()).isEqualTo(UPDATED_TALHAO);
        assertThat(testAlfa.getCelula()).isEqualTo(DEFAULT_CELULA);
        assertThat(testAlfa.getBloco()).isEqualTo(UPDATED_BLOCO);
        assertThat(testAlfa.getAvenida()).isEqualTo(UPDATED_AVENIDA);
        assertThat(testAlfa.getNumeroPolicia()).isEqualTo(UPDATED_NUMERO_POLICIA);
        assertThat(testAlfa.getUsoActual()).isEqualTo(DEFAULT_USO_ACTUAL);
        assertThat(testAlfa.getFormaUso()).isEqualTo(UPDATED_FORMA_USO);
        assertThat(testAlfa.getFormaObtencao()).isEqualTo(UPDATED_FORMA_OBTENCAO);
        assertThat(testAlfa.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAlfa.getAnoOcupacao()).isEqualTo(UPDATED_ANO_OCUPACAO);
        assertThat(testAlfa.getTipoAcesso()).isEqualTo(DEFAULT_TIPO_ACESSO);
        assertThat(testAlfa.getConflito()).isEqualTo(DEFAULT_CONFLITO);
        assertThat(testAlfa.getDetalhesConflito()).isEqualTo(DEFAULT_DETALHES_CONFLITO);
        assertThat(testAlfa.getConstrucaoPrecaria()).isEqualTo(DEFAULT_CONSTRUCAO_PRECARIA);
        assertThat(testAlfa.getPisosAcimaSoleira()).isEqualTo(DEFAULT_PISOS_ACIMA_SOLEIRA);
        assertThat(testAlfa.getPisosAbaixoSoleira()).isEqualTo(UPDATED_PISOS_ABAIXO_SOLEIRA);
        assertThat(testAlfa.getMaterialConstrucaoBarrote()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_BARROTE);
        assertThat(testAlfa.getMaterialConstrucaoIBR()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_IBR);
        assertThat(testAlfa.getMaterialConstrucaoPranchas()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS);
        assertThat(testAlfa.getMaterialConstrucaoPau()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_PAU);
        assertThat(testAlfa.getMaterialConstrucaoCanico()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_CANICO);
        assertThat(testAlfa.getMaterialConstrucaoCimento()).isEqualTo(DEFAULT_MATERIAL_CONSTRUCAO_CIMENTO);
        assertThat(testAlfa.getOcupacao()).isEqualTo(UPDATED_OCUPACAO);
        assertThat(testAlfa.getTipoContrucao()).isEqualTo(UPDATED_TIPO_CONTRUCAO);
        assertThat(testAlfa.getDetalhesTipoContrucao()).isEqualTo(DEFAULT_DETALHES_TIPO_CONTRUCAO);
        assertThat(testAlfa.getInfraestruturaExistente()).isEqualTo(DEFAULT_INFRAESTRUTURA_EXISTENTE);
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

        partialUpdatedAlfa
            .parcela(UPDATED_PARCELA)
            .tipoTitular(UPDATED_TIPO_TITULAR)
            .nomeTitular(UPDATED_NOME_TITULAR)
            .estadoSocial(UPDATED_ESTADO_SOCIAL)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .sexo(UPDATED_SEXO)
            .documento(UPDATED_DOCUMENTO)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .datEmissao(UPDATED_DAT_EMISSAO)
            .localEmissao(UPDATED_LOCAL_EMISSAO)
            .contactoPrincipal(UPDATED_CONTACTO_PRINCIPAL)
            .contactoAlternativo(UPDATED_CONTACTO_ALTERNATIVO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .nomeConjugue(UPDATED_NOME_CONJUGUE)
            .distritoMunicipal(UPDATED_DISTRITO_MUNICIPAL)
            .bairro(UPDATED_BAIRRO)
            .quatreirao(UPDATED_QUATREIRAO)
            .talhao(UPDATED_TALHAO)
            .celula(UPDATED_CELULA)
            .bloco(UPDATED_BLOCO)
            .avenida(UPDATED_AVENIDA)
            .numeroPolicia(UPDATED_NUMERO_POLICIA)
            .usoActual(UPDATED_USO_ACTUAL)
            .formaUso(UPDATED_FORMA_USO)
            .formaObtencao(UPDATED_FORMA_OBTENCAO)
            .tipo(UPDATED_TIPO)
            .anoOcupacao(UPDATED_ANO_OCUPACAO)
            .tipoAcesso(UPDATED_TIPO_ACESSO)
            .conflito(UPDATED_CONFLITO)
            .detalhesConflito(UPDATED_DETALHES_CONFLITO)
            .construcaoPrecaria(UPDATED_CONSTRUCAO_PRECARIA)
            .pisosAcimaSoleira(UPDATED_PISOS_ACIMA_SOLEIRA)
            .pisosAbaixoSoleira(UPDATED_PISOS_ABAIXO_SOLEIRA)
            .materialConstrucaoBarrote(UPDATED_MATERIAL_CONSTRUCAO_BARROTE)
            .materialConstrucaoIBR(UPDATED_MATERIAL_CONSTRUCAO_IBR)
            .materialConstrucaoPranchas(UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS)
            .materialConstrucaoPau(UPDATED_MATERIAL_CONSTRUCAO_PAU)
            .materialConstrucaoCanico(UPDATED_MATERIAL_CONSTRUCAO_CANICO)
            .materialConstrucaoCimento(UPDATED_MATERIAL_CONSTRUCAO_CIMENTO)
            .ocupacao(UPDATED_OCUPACAO)
            .TipoContrucao(UPDATED_TIPO_CONTRUCAO)
            .detalhesTipoContrucao(UPDATED_DETALHES_TIPO_CONTRUCAO)
            .infraestruturaExistente(UPDATED_INFRAESTRUTURA_EXISTENTE)
            .dataLevantamento(UPDATED_DATA_LEVANTAMENTO);

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
        assertThat(testAlfa.getTipoTitular()).isEqualTo(UPDATED_TIPO_TITULAR);
        assertThat(testAlfa.getNomeTitular()).isEqualTo(UPDATED_NOME_TITULAR);
        assertThat(testAlfa.getEstadoSocial()).isEqualTo(UPDATED_ESTADO_SOCIAL);
        assertThat(testAlfa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testAlfa.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testAlfa.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testAlfa.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testAlfa.getDatEmissao()).isEqualTo(UPDATED_DAT_EMISSAO);
        assertThat(testAlfa.getLocalEmissao()).isEqualTo(UPDATED_LOCAL_EMISSAO);
        assertThat(testAlfa.getContactoPrincipal()).isEqualTo(UPDATED_CONTACTO_PRINCIPAL);
        assertThat(testAlfa.getContactoAlternativo()).isEqualTo(UPDATED_CONTACTO_ALTERNATIVO);
        assertThat(testAlfa.getEstadoCivil()).isEqualTo(UPDATED_ESTADO_CIVIL);
        assertThat(testAlfa.getNomeConjugue()).isEqualTo(UPDATED_NOME_CONJUGUE);
        assertThat(testAlfa.getDistritoMunicipal()).isEqualTo(UPDATED_DISTRITO_MUNICIPAL);
        assertThat(testAlfa.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testAlfa.getQuatreirao()).isEqualTo(UPDATED_QUATREIRAO);
        assertThat(testAlfa.getTalhao()).isEqualTo(UPDATED_TALHAO);
        assertThat(testAlfa.getCelula()).isEqualTo(UPDATED_CELULA);
        assertThat(testAlfa.getBloco()).isEqualTo(UPDATED_BLOCO);
        assertThat(testAlfa.getAvenida()).isEqualTo(UPDATED_AVENIDA);
        assertThat(testAlfa.getNumeroPolicia()).isEqualTo(UPDATED_NUMERO_POLICIA);
        assertThat(testAlfa.getUsoActual()).isEqualTo(UPDATED_USO_ACTUAL);
        assertThat(testAlfa.getFormaUso()).isEqualTo(UPDATED_FORMA_USO);
        assertThat(testAlfa.getFormaObtencao()).isEqualTo(UPDATED_FORMA_OBTENCAO);
        assertThat(testAlfa.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAlfa.getAnoOcupacao()).isEqualTo(UPDATED_ANO_OCUPACAO);
        assertThat(testAlfa.getTipoAcesso()).isEqualTo(UPDATED_TIPO_ACESSO);
        assertThat(testAlfa.getConflito()).isEqualTo(UPDATED_CONFLITO);
        assertThat(testAlfa.getDetalhesConflito()).isEqualTo(UPDATED_DETALHES_CONFLITO);
        assertThat(testAlfa.getConstrucaoPrecaria()).isEqualTo(UPDATED_CONSTRUCAO_PRECARIA);
        assertThat(testAlfa.getPisosAcimaSoleira()).isEqualTo(UPDATED_PISOS_ACIMA_SOLEIRA);
        assertThat(testAlfa.getPisosAbaixoSoleira()).isEqualTo(UPDATED_PISOS_ABAIXO_SOLEIRA);
        assertThat(testAlfa.getMaterialConstrucaoBarrote()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_BARROTE);
        assertThat(testAlfa.getMaterialConstrucaoIBR()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_IBR);
        assertThat(testAlfa.getMaterialConstrucaoPranchas()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_PRANCHAS);
        assertThat(testAlfa.getMaterialConstrucaoPau()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_PAU);
        assertThat(testAlfa.getMaterialConstrucaoCanico()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_CANICO);
        assertThat(testAlfa.getMaterialConstrucaoCimento()).isEqualTo(UPDATED_MATERIAL_CONSTRUCAO_CIMENTO);
        assertThat(testAlfa.getOcupacao()).isEqualTo(UPDATED_OCUPACAO);
        assertThat(testAlfa.getTipoContrucao()).isEqualTo(UPDATED_TIPO_CONTRUCAO);
        assertThat(testAlfa.getDetalhesTipoContrucao()).isEqualTo(UPDATED_DETALHES_TIPO_CONTRUCAO);
        assertThat(testAlfa.getInfraestruturaExistente()).isEqualTo(UPDATED_INFRAESTRUTURA_EXISTENTE);
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
