package gov.cmcm.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link gov.cmcm.domain.Alfa} entity. This class is used
 * in {@link gov.cmcm.web.rest.AlfaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alfas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlfaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter parcela;

    private StringFilter tipoTitular;

    private StringFilter nomeTitular;

    private StringFilter estadoSocial;

    private LocalDateFilter dataNascimento;

    private StringFilter sexo;

    private StringFilter documento;

    private StringFilter numeroDocumento;

    private StringFilter datEmissao;

    private StringFilter localEmissao;

    private StringFilter contactoPrincipal;

    private StringFilter contactoAlternativo;

    private StringFilter estadoCivil;

    private StringFilter nomeConjugue;

    private StringFilter distritoMunicipal;

    private StringFilter bairro;

    private StringFilter quatreirao;

    private StringFilter talhao;

    private StringFilter celula;

    private StringFilter bloco;

    private StringFilter avenida;

    private StringFilter numeroPolicia;

    private StringFilter usoActual;

    private StringFilter formaUso;

    private StringFilter formaObtencao;

    private StringFilter tipo;

    private IntegerFilter anoOcupacao;

    private StringFilter tipoAcesso;

    private BooleanFilter conflito;

    private BooleanFilter detalhesConflito;

    private BooleanFilter construcaoPrecaria;

    private IntegerFilter pisosAcimaSoleira;

    private IntegerFilter pisosAbaixoSoleira;

    private BooleanFilter materialConstrucaoBarrote;

    private BooleanFilter materialConstrucaoIBR;

    private BooleanFilter materialConstrucaoPranchas;

    private BooleanFilter materialConstrucaoPau;

    private BooleanFilter materialConstrucaoCanico;

    private BooleanFilter materialConstrucaoCimento;

    private StringFilter ocupacao;

    private StringFilter TipoContrucao;

    private StringFilter detalhesTipoContrucao;

    private StringFilter infraestruturaExistente;

    private LocalDateFilter dataLevantamento;

    private LongFilter fichaId;

    private Boolean distinct;

    public AlfaCriteria() {}

    public AlfaCriteria(AlfaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.parcela = other.parcela == null ? null : other.parcela.copy();
        this.tipoTitular = other.tipoTitular == null ? null : other.tipoTitular.copy();
        this.nomeTitular = other.nomeTitular == null ? null : other.nomeTitular.copy();
        this.estadoSocial = other.estadoSocial == null ? null : other.estadoSocial.copy();
        this.dataNascimento = other.dataNascimento == null ? null : other.dataNascimento.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.documento = other.documento == null ? null : other.documento.copy();
        this.numeroDocumento = other.numeroDocumento == null ? null : other.numeroDocumento.copy();
        this.datEmissao = other.datEmissao == null ? null : other.datEmissao.copy();
        this.localEmissao = other.localEmissao == null ? null : other.localEmissao.copy();
        this.contactoPrincipal = other.contactoPrincipal == null ? null : other.contactoPrincipal.copy();
        this.contactoAlternativo = other.contactoAlternativo == null ? null : other.contactoAlternativo.copy();
        this.estadoCivil = other.estadoCivil == null ? null : other.estadoCivil.copy();
        this.nomeConjugue = other.nomeConjugue == null ? null : other.nomeConjugue.copy();
        this.distritoMunicipal = other.distritoMunicipal == null ? null : other.distritoMunicipal.copy();
        this.bairro = other.bairro == null ? null : other.bairro.copy();
        this.quatreirao = other.quatreirao == null ? null : other.quatreirao.copy();
        this.talhao = other.talhao == null ? null : other.talhao.copy();
        this.celula = other.celula == null ? null : other.celula.copy();
        this.bloco = other.bloco == null ? null : other.bloco.copy();
        this.avenida = other.avenida == null ? null : other.avenida.copy();
        this.numeroPolicia = other.numeroPolicia == null ? null : other.numeroPolicia.copy();
        this.usoActual = other.usoActual == null ? null : other.usoActual.copy();
        this.formaUso = other.formaUso == null ? null : other.formaUso.copy();
        this.formaObtencao = other.formaObtencao == null ? null : other.formaObtencao.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.anoOcupacao = other.anoOcupacao == null ? null : other.anoOcupacao.copy();
        this.tipoAcesso = other.tipoAcesso == null ? null : other.tipoAcesso.copy();
        this.conflito = other.conflito == null ? null : other.conflito.copy();
        this.detalhesConflito = other.detalhesConflito == null ? null : other.detalhesConflito.copy();
        this.construcaoPrecaria = other.construcaoPrecaria == null ? null : other.construcaoPrecaria.copy();
        this.pisosAcimaSoleira = other.pisosAcimaSoleira == null ? null : other.pisosAcimaSoleira.copy();
        this.pisosAbaixoSoleira = other.pisosAbaixoSoleira == null ? null : other.pisosAbaixoSoleira.copy();
        this.materialConstrucaoBarrote = other.materialConstrucaoBarrote == null ? null : other.materialConstrucaoBarrote.copy();
        this.materialConstrucaoIBR = other.materialConstrucaoIBR == null ? null : other.materialConstrucaoIBR.copy();
        this.materialConstrucaoPranchas = other.materialConstrucaoPranchas == null ? null : other.materialConstrucaoPranchas.copy();
        this.materialConstrucaoPau = other.materialConstrucaoPau == null ? null : other.materialConstrucaoPau.copy();
        this.materialConstrucaoCanico = other.materialConstrucaoCanico == null ? null : other.materialConstrucaoCanico.copy();
        this.materialConstrucaoCimento = other.materialConstrucaoCimento == null ? null : other.materialConstrucaoCimento.copy();
        this.ocupacao = other.ocupacao == null ? null : other.ocupacao.copy();
        this.TipoContrucao = other.TipoContrucao == null ? null : other.TipoContrucao.copy();
        this.detalhesTipoContrucao = other.detalhesTipoContrucao == null ? null : other.detalhesTipoContrucao.copy();
        this.infraestruturaExistente = other.infraestruturaExistente == null ? null : other.infraestruturaExistente.copy();
        this.dataLevantamento = other.dataLevantamento == null ? null : other.dataLevantamento.copy();
        this.fichaId = other.fichaId == null ? null : other.fichaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AlfaCriteria copy() {
        return new AlfaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getParcela() {
        return parcela;
    }

    public StringFilter parcela() {
        if (parcela == null) {
            parcela = new StringFilter();
        }
        return parcela;
    }

    public void setParcela(StringFilter parcela) {
        this.parcela = parcela;
    }

    public StringFilter getTipoTitular() {
        return tipoTitular;
    }

    public StringFilter tipoTitular() {
        if (tipoTitular == null) {
            tipoTitular = new StringFilter();
        }
        return tipoTitular;
    }

    public void setTipoTitular(StringFilter tipoTitular) {
        this.tipoTitular = tipoTitular;
    }

    public StringFilter getNomeTitular() {
        return nomeTitular;
    }

    public StringFilter nomeTitular() {
        if (nomeTitular == null) {
            nomeTitular = new StringFilter();
        }
        return nomeTitular;
    }

    public void setNomeTitular(StringFilter nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public StringFilter getEstadoSocial() {
        return estadoSocial;
    }

    public StringFilter estadoSocial() {
        if (estadoSocial == null) {
            estadoSocial = new StringFilter();
        }
        return estadoSocial;
    }

    public void setEstadoSocial(StringFilter estadoSocial) {
        this.estadoSocial = estadoSocial;
    }

    public LocalDateFilter getDataNascimento() {
        return dataNascimento;
    }

    public LocalDateFilter dataNascimento() {
        if (dataNascimento == null) {
            dataNascimento = new LocalDateFilter();
        }
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateFilter dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public StringFilter getSexo() {
        return sexo;
    }

    public StringFilter sexo() {
        if (sexo == null) {
            sexo = new StringFilter();
        }
        return sexo;
    }

    public void setSexo(StringFilter sexo) {
        this.sexo = sexo;
    }

    public StringFilter getDocumento() {
        return documento;
    }

    public StringFilter documento() {
        if (documento == null) {
            documento = new StringFilter();
        }
        return documento;
    }

    public void setDocumento(StringFilter documento) {
        this.documento = documento;
    }

    public StringFilter getNumeroDocumento() {
        return numeroDocumento;
    }

    public StringFilter numeroDocumento() {
        if (numeroDocumento == null) {
            numeroDocumento = new StringFilter();
        }
        return numeroDocumento;
    }

    public void setNumeroDocumento(StringFilter numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public StringFilter getDatEmissao() {
        return datEmissao;
    }

    public StringFilter datEmissao() {
        if (datEmissao == null) {
            datEmissao = new StringFilter();
        }
        return datEmissao;
    }

    public void setDatEmissao(StringFilter datEmissao) {
        this.datEmissao = datEmissao;
    }

    public StringFilter getLocalEmissao() {
        return localEmissao;
    }

    public StringFilter localEmissao() {
        if (localEmissao == null) {
            localEmissao = new StringFilter();
        }
        return localEmissao;
    }

    public void setLocalEmissao(StringFilter localEmissao) {
        this.localEmissao = localEmissao;
    }

    public StringFilter getContactoPrincipal() {
        return contactoPrincipal;
    }

    public StringFilter contactoPrincipal() {
        if (contactoPrincipal == null) {
            contactoPrincipal = new StringFilter();
        }
        return contactoPrincipal;
    }

    public void setContactoPrincipal(StringFilter contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public StringFilter getContactoAlternativo() {
        return contactoAlternativo;
    }

    public StringFilter contactoAlternativo() {
        if (contactoAlternativo == null) {
            contactoAlternativo = new StringFilter();
        }
        return contactoAlternativo;
    }

    public void setContactoAlternativo(StringFilter contactoAlternativo) {
        this.contactoAlternativo = contactoAlternativo;
    }

    public StringFilter getEstadoCivil() {
        return estadoCivil;
    }

    public StringFilter estadoCivil() {
        if (estadoCivil == null) {
            estadoCivil = new StringFilter();
        }
        return estadoCivil;
    }

    public void setEstadoCivil(StringFilter estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public StringFilter getNomeConjugue() {
        return nomeConjugue;
    }

    public StringFilter nomeConjugue() {
        if (nomeConjugue == null) {
            nomeConjugue = new StringFilter();
        }
        return nomeConjugue;
    }

    public void setNomeConjugue(StringFilter nomeConjugue) {
        this.nomeConjugue = nomeConjugue;
    }

    public StringFilter getDistritoMunicipal() {
        return distritoMunicipal;
    }

    public StringFilter distritoMunicipal() {
        if (distritoMunicipal == null) {
            distritoMunicipal = new StringFilter();
        }
        return distritoMunicipal;
    }

    public void setDistritoMunicipal(StringFilter distritoMunicipal) {
        this.distritoMunicipal = distritoMunicipal;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public StringFilter bairro() {
        if (bairro == null) {
            bairro = new StringFilter();
        }
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getQuatreirao() {
        return quatreirao;
    }

    public StringFilter quatreirao() {
        if (quatreirao == null) {
            quatreirao = new StringFilter();
        }
        return quatreirao;
    }

    public void setQuatreirao(StringFilter quatreirao) {
        this.quatreirao = quatreirao;
    }

    public StringFilter getTalhao() {
        return talhao;
    }

    public StringFilter talhao() {
        if (talhao == null) {
            talhao = new StringFilter();
        }
        return talhao;
    }

    public void setTalhao(StringFilter talhao) {
        this.talhao = talhao;
    }

    public StringFilter getCelula() {
        return celula;
    }

    public StringFilter celula() {
        if (celula == null) {
            celula = new StringFilter();
        }
        return celula;
    }

    public void setCelula(StringFilter celula) {
        this.celula = celula;
    }

    public StringFilter getBloco() {
        return bloco;
    }

    public StringFilter bloco() {
        if (bloco == null) {
            bloco = new StringFilter();
        }
        return bloco;
    }

    public void setBloco(StringFilter bloco) {
        this.bloco = bloco;
    }

    public StringFilter getAvenida() {
        return avenida;
    }

    public StringFilter avenida() {
        if (avenida == null) {
            avenida = new StringFilter();
        }
        return avenida;
    }

    public void setAvenida(StringFilter avenida) {
        this.avenida = avenida;
    }

    public StringFilter getNumeroPolicia() {
        return numeroPolicia;
    }

    public StringFilter numeroPolicia() {
        if (numeroPolicia == null) {
            numeroPolicia = new StringFilter();
        }
        return numeroPolicia;
    }

    public void setNumeroPolicia(StringFilter numeroPolicia) {
        this.numeroPolicia = numeroPolicia;
    }

    public StringFilter getUsoActual() {
        return usoActual;
    }

    public StringFilter usoActual() {
        if (usoActual == null) {
            usoActual = new StringFilter();
        }
        return usoActual;
    }

    public void setUsoActual(StringFilter usoActual) {
        this.usoActual = usoActual;
    }

    public StringFilter getFormaUso() {
        return formaUso;
    }

    public StringFilter formaUso() {
        if (formaUso == null) {
            formaUso = new StringFilter();
        }
        return formaUso;
    }

    public void setFormaUso(StringFilter formaUso) {
        this.formaUso = formaUso;
    }

    public StringFilter getFormaObtencao() {
        return formaObtencao;
    }

    public StringFilter formaObtencao() {
        if (formaObtencao == null) {
            formaObtencao = new StringFilter();
        }
        return formaObtencao;
    }

    public void setFormaObtencao(StringFilter formaObtencao) {
        this.formaObtencao = formaObtencao;
    }

    public StringFilter getTipo() {
        return tipo;
    }

    public StringFilter tipo() {
        if (tipo == null) {
            tipo = new StringFilter();
        }
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public IntegerFilter getAnoOcupacao() {
        return anoOcupacao;
    }

    public IntegerFilter anoOcupacao() {
        if (anoOcupacao == null) {
            anoOcupacao = new IntegerFilter();
        }
        return anoOcupacao;
    }

    public void setAnoOcupacao(IntegerFilter anoOcupacao) {
        this.anoOcupacao = anoOcupacao;
    }

    public StringFilter getTipoAcesso() {
        return tipoAcesso;
    }

    public StringFilter tipoAcesso() {
        if (tipoAcesso == null) {
            tipoAcesso = new StringFilter();
        }
        return tipoAcesso;
    }

    public void setTipoAcesso(StringFilter tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public BooleanFilter getConflito() {
        return conflito;
    }

    public BooleanFilter conflito() {
        if (conflito == null) {
            conflito = new BooleanFilter();
        }
        return conflito;
    }

    public void setConflito(BooleanFilter conflito) {
        this.conflito = conflito;
    }

    public BooleanFilter getDetalhesConflito() {
        return detalhesConflito;
    }

    public BooleanFilter detalhesConflito() {
        if (detalhesConflito == null) {
            detalhesConflito = new BooleanFilter();
        }
        return detalhesConflito;
    }

    public void setDetalhesConflito(BooleanFilter detalhesConflito) {
        this.detalhesConflito = detalhesConflito;
    }

    public BooleanFilter getConstrucaoPrecaria() {
        return construcaoPrecaria;
    }

    public BooleanFilter construcaoPrecaria() {
        if (construcaoPrecaria == null) {
            construcaoPrecaria = new BooleanFilter();
        }
        return construcaoPrecaria;
    }

    public void setConstrucaoPrecaria(BooleanFilter construcaoPrecaria) {
        this.construcaoPrecaria = construcaoPrecaria;
    }

    public IntegerFilter getPisosAcimaSoleira() {
        return pisosAcimaSoleira;
    }

    public IntegerFilter pisosAcimaSoleira() {
        if (pisosAcimaSoleira == null) {
            pisosAcimaSoleira = new IntegerFilter();
        }
        return pisosAcimaSoleira;
    }

    public void setPisosAcimaSoleira(IntegerFilter pisosAcimaSoleira) {
        this.pisosAcimaSoleira = pisosAcimaSoleira;
    }

    public IntegerFilter getPisosAbaixoSoleira() {
        return pisosAbaixoSoleira;
    }

    public IntegerFilter pisosAbaixoSoleira() {
        if (pisosAbaixoSoleira == null) {
            pisosAbaixoSoleira = new IntegerFilter();
        }
        return pisosAbaixoSoleira;
    }

    public void setPisosAbaixoSoleira(IntegerFilter pisosAbaixoSoleira) {
        this.pisosAbaixoSoleira = pisosAbaixoSoleira;
    }

    public BooleanFilter getMaterialConstrucaoBarrote() {
        return materialConstrucaoBarrote;
    }

    public BooleanFilter materialConstrucaoBarrote() {
        if (materialConstrucaoBarrote == null) {
            materialConstrucaoBarrote = new BooleanFilter();
        }
        return materialConstrucaoBarrote;
    }

    public void setMaterialConstrucaoBarrote(BooleanFilter materialConstrucaoBarrote) {
        this.materialConstrucaoBarrote = materialConstrucaoBarrote;
    }

    public BooleanFilter getMaterialConstrucaoIBR() {
        return materialConstrucaoIBR;
    }

    public BooleanFilter materialConstrucaoIBR() {
        if (materialConstrucaoIBR == null) {
            materialConstrucaoIBR = new BooleanFilter();
        }
        return materialConstrucaoIBR;
    }

    public void setMaterialConstrucaoIBR(BooleanFilter materialConstrucaoIBR) {
        this.materialConstrucaoIBR = materialConstrucaoIBR;
    }

    public BooleanFilter getMaterialConstrucaoPranchas() {
        return materialConstrucaoPranchas;
    }

    public BooleanFilter materialConstrucaoPranchas() {
        if (materialConstrucaoPranchas == null) {
            materialConstrucaoPranchas = new BooleanFilter();
        }
        return materialConstrucaoPranchas;
    }

    public void setMaterialConstrucaoPranchas(BooleanFilter materialConstrucaoPranchas) {
        this.materialConstrucaoPranchas = materialConstrucaoPranchas;
    }

    public BooleanFilter getMaterialConstrucaoPau() {
        return materialConstrucaoPau;
    }

    public BooleanFilter materialConstrucaoPau() {
        if (materialConstrucaoPau == null) {
            materialConstrucaoPau = new BooleanFilter();
        }
        return materialConstrucaoPau;
    }

    public void setMaterialConstrucaoPau(BooleanFilter materialConstrucaoPau) {
        this.materialConstrucaoPau = materialConstrucaoPau;
    }

    public BooleanFilter getMaterialConstrucaoCanico() {
        return materialConstrucaoCanico;
    }

    public BooleanFilter materialConstrucaoCanico() {
        if (materialConstrucaoCanico == null) {
            materialConstrucaoCanico = new BooleanFilter();
        }
        return materialConstrucaoCanico;
    }

    public void setMaterialConstrucaoCanico(BooleanFilter materialConstrucaoCanico) {
        this.materialConstrucaoCanico = materialConstrucaoCanico;
    }

    public BooleanFilter getMaterialConstrucaoCimento() {
        return materialConstrucaoCimento;
    }

    public BooleanFilter materialConstrucaoCimento() {
        if (materialConstrucaoCimento == null) {
            materialConstrucaoCimento = new BooleanFilter();
        }
        return materialConstrucaoCimento;
    }

    public void setMaterialConstrucaoCimento(BooleanFilter materialConstrucaoCimento) {
        this.materialConstrucaoCimento = materialConstrucaoCimento;
    }

    public StringFilter getOcupacao() {
        return ocupacao;
    }

    public StringFilter ocupacao() {
        if (ocupacao == null) {
            ocupacao = new StringFilter();
        }
        return ocupacao;
    }

    public void setOcupacao(StringFilter ocupacao) {
        this.ocupacao = ocupacao;
    }

    public StringFilter getTipoContrucao() {
        return TipoContrucao;
    }

    public StringFilter TipoContrucao() {
        if (TipoContrucao == null) {
            TipoContrucao = new StringFilter();
        }
        return TipoContrucao;
    }

    public void setTipoContrucao(StringFilter TipoContrucao) {
        this.TipoContrucao = TipoContrucao;
    }

    public StringFilter getDetalhesTipoContrucao() {
        return detalhesTipoContrucao;
    }

    public StringFilter detalhesTipoContrucao() {
        if (detalhesTipoContrucao == null) {
            detalhesTipoContrucao = new StringFilter();
        }
        return detalhesTipoContrucao;
    }

    public void setDetalhesTipoContrucao(StringFilter detalhesTipoContrucao) {
        this.detalhesTipoContrucao = detalhesTipoContrucao;
    }

    public StringFilter getInfraestruturaExistente() {
        return infraestruturaExistente;
    }

    public StringFilter infraestruturaExistente() {
        if (infraestruturaExistente == null) {
            infraestruturaExistente = new StringFilter();
        }
        return infraestruturaExistente;
    }

    public void setInfraestruturaExistente(StringFilter infraestruturaExistente) {
        this.infraestruturaExistente = infraestruturaExistente;
    }

    public LocalDateFilter getDataLevantamento() {
        return dataLevantamento;
    }

    public LocalDateFilter dataLevantamento() {
        if (dataLevantamento == null) {
            dataLevantamento = new LocalDateFilter();
        }
        return dataLevantamento;
    }

    public void setDataLevantamento(LocalDateFilter dataLevantamento) {
        this.dataLevantamento = dataLevantamento;
    }

    public LongFilter getFichaId() {
        return fichaId;
    }

    public LongFilter fichaId() {
        if (fichaId == null) {
            fichaId = new LongFilter();
        }
        return fichaId;
    }

    public void setFichaId(LongFilter fichaId) {
        this.fichaId = fichaId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlfaCriteria that = (AlfaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(parcela, that.parcela) &&
            Objects.equals(tipoTitular, that.tipoTitular) &&
            Objects.equals(nomeTitular, that.nomeTitular) &&
            Objects.equals(estadoSocial, that.estadoSocial) &&
            Objects.equals(dataNascimento, that.dataNascimento) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(documento, that.documento) &&
            Objects.equals(numeroDocumento, that.numeroDocumento) &&
            Objects.equals(datEmissao, that.datEmissao) &&
            Objects.equals(localEmissao, that.localEmissao) &&
            Objects.equals(contactoPrincipal, that.contactoPrincipal) &&
            Objects.equals(contactoAlternativo, that.contactoAlternativo) &&
            Objects.equals(estadoCivil, that.estadoCivil) &&
            Objects.equals(nomeConjugue, that.nomeConjugue) &&
            Objects.equals(distritoMunicipal, that.distritoMunicipal) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(quatreirao, that.quatreirao) &&
            Objects.equals(talhao, that.talhao) &&
            Objects.equals(celula, that.celula) &&
            Objects.equals(bloco, that.bloco) &&
            Objects.equals(avenida, that.avenida) &&
            Objects.equals(numeroPolicia, that.numeroPolicia) &&
            Objects.equals(usoActual, that.usoActual) &&
            Objects.equals(formaUso, that.formaUso) &&
            Objects.equals(formaObtencao, that.formaObtencao) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(anoOcupacao, that.anoOcupacao) &&
            Objects.equals(tipoAcesso, that.tipoAcesso) &&
            Objects.equals(conflito, that.conflito) &&
            Objects.equals(detalhesConflito, that.detalhesConflito) &&
            Objects.equals(construcaoPrecaria, that.construcaoPrecaria) &&
            Objects.equals(pisosAcimaSoleira, that.pisosAcimaSoleira) &&
            Objects.equals(pisosAbaixoSoleira, that.pisosAbaixoSoleira) &&
            Objects.equals(materialConstrucaoBarrote, that.materialConstrucaoBarrote) &&
            Objects.equals(materialConstrucaoIBR, that.materialConstrucaoIBR) &&
            Objects.equals(materialConstrucaoPranchas, that.materialConstrucaoPranchas) &&
            Objects.equals(materialConstrucaoPau, that.materialConstrucaoPau) &&
            Objects.equals(materialConstrucaoCanico, that.materialConstrucaoCanico) &&
            Objects.equals(materialConstrucaoCimento, that.materialConstrucaoCimento) &&
            Objects.equals(ocupacao, that.ocupacao) &&
            Objects.equals(TipoContrucao, that.TipoContrucao) &&
            Objects.equals(detalhesTipoContrucao, that.detalhesTipoContrucao) &&
            Objects.equals(infraestruturaExistente, that.infraestruturaExistente) &&
            Objects.equals(dataLevantamento, that.dataLevantamento) &&
            Objects.equals(fichaId, that.fichaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            parcela,
            tipoTitular,
            nomeTitular,
            estadoSocial,
            dataNascimento,
            sexo,
            documento,
            numeroDocumento,
            datEmissao,
            localEmissao,
            contactoPrincipal,
            contactoAlternativo,
            estadoCivil,
            nomeConjugue,
            distritoMunicipal,
            bairro,
            quatreirao,
            talhao,
            celula,
            bloco,
            avenida,
            numeroPolicia,
            usoActual,
            formaUso,
            formaObtencao,
            tipo,
            anoOcupacao,
            tipoAcesso,
            conflito,
            detalhesConflito,
            construcaoPrecaria,
            pisosAcimaSoleira,
            pisosAbaixoSoleira,
            materialConstrucaoBarrote,
            materialConstrucaoIBR,
            materialConstrucaoPranchas,
            materialConstrucaoPau,
            materialConstrucaoCanico,
            materialConstrucaoCimento,
            ocupacao,
            TipoContrucao,
            detalhesTipoContrucao,
            infraestruturaExistente,
            dataLevantamento,
            fichaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlfaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (parcela != null ? "parcela=" + parcela + ", " : "") +
            (tipoTitular != null ? "tipoTitular=" + tipoTitular + ", " : "") +
            (nomeTitular != null ? "nomeTitular=" + nomeTitular + ", " : "") +
            (estadoSocial != null ? "estadoSocial=" + estadoSocial + ", " : "") +
            (dataNascimento != null ? "dataNascimento=" + dataNascimento + ", " : "") +
            (sexo != null ? "sexo=" + sexo + ", " : "") +
            (documento != null ? "documento=" + documento + ", " : "") +
            (numeroDocumento != null ? "numeroDocumento=" + numeroDocumento + ", " : "") +
            (datEmissao != null ? "datEmissao=" + datEmissao + ", " : "") +
            (localEmissao != null ? "localEmissao=" + localEmissao + ", " : "") +
            (contactoPrincipal != null ? "contactoPrincipal=" + contactoPrincipal + ", " : "") +
            (contactoAlternativo != null ? "contactoAlternativo=" + contactoAlternativo + ", " : "") +
            (estadoCivil != null ? "estadoCivil=" + estadoCivil + ", " : "") +
            (nomeConjugue != null ? "nomeConjugue=" + nomeConjugue + ", " : "") +
            (distritoMunicipal != null ? "distritoMunicipal=" + distritoMunicipal + ", " : "") +
            (bairro != null ? "bairro=" + bairro + ", " : "") +
            (quatreirao != null ? "quatreirao=" + quatreirao + ", " : "") +
            (talhao != null ? "talhao=" + talhao + ", " : "") +
            (celula != null ? "celula=" + celula + ", " : "") +
            (bloco != null ? "bloco=" + bloco + ", " : "") +
            (avenida != null ? "avenida=" + avenida + ", " : "") +
            (numeroPolicia != null ? "numeroPolicia=" + numeroPolicia + ", " : "") +
            (usoActual != null ? "usoActual=" + usoActual + ", " : "") +
            (formaUso != null ? "formaUso=" + formaUso + ", " : "") +
            (formaObtencao != null ? "formaObtencao=" + formaObtencao + ", " : "") +
            (tipo != null ? "tipo=" + tipo + ", " : "") +
            (anoOcupacao != null ? "anoOcupacao=" + anoOcupacao + ", " : "") +
            (tipoAcesso != null ? "tipoAcesso=" + tipoAcesso + ", " : "") +
            (conflito != null ? "conflito=" + conflito + ", " : "") +
            (detalhesConflito != null ? "detalhesConflito=" + detalhesConflito + ", " : "") +
            (construcaoPrecaria != null ? "construcaoPrecaria=" + construcaoPrecaria + ", " : "") +
            (pisosAcimaSoleira != null ? "pisosAcimaSoleira=" + pisosAcimaSoleira + ", " : "") +
            (pisosAbaixoSoleira != null ? "pisosAbaixoSoleira=" + pisosAbaixoSoleira + ", " : "") +
            (materialConstrucaoBarrote != null ? "materialConstrucaoBarrote=" + materialConstrucaoBarrote + ", " : "") +
            (materialConstrucaoIBR != null ? "materialConstrucaoIBR=" + materialConstrucaoIBR + ", " : "") +
            (materialConstrucaoPranchas != null ? "materialConstrucaoPranchas=" + materialConstrucaoPranchas + ", " : "") +
            (materialConstrucaoPau != null ? "materialConstrucaoPau=" + materialConstrucaoPau + ", " : "") +
            (materialConstrucaoCanico != null ? "materialConstrucaoCanico=" + materialConstrucaoCanico + ", " : "") +
            (materialConstrucaoCimento != null ? "materialConstrucaoCimento=" + materialConstrucaoCimento + ", " : "") +
            (ocupacao != null ? "ocupacao=" + ocupacao + ", " : "") +
            (TipoContrucao != null ? "TipoContrucao=" + TipoContrucao + ", " : "") +
            (detalhesTipoContrucao != null ? "detalhesTipoContrucao=" + detalhesTipoContrucao + ", " : "") +
            (infraestruturaExistente != null ? "infraestruturaExistente=" + infraestruturaExistente + ", " : "") +
            (dataLevantamento != null ? "dataLevantamento=" + dataLevantamento + ", " : "") +
            (fichaId != null ? "fichaId=" + fichaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
