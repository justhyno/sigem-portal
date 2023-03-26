package gov.cmcm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Alfa.
 */
@Entity
@Table(name = "alfa")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Alfa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "parcela")
    private String parcela;

    @Column(name = "tipo_titular")
    private String tipoTitular;

    @Column(name = "nome_titular")
    private String nomeTitular;

    @Column(name = "estado_social")
    private String estadoSocial;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "documento")
    private String documento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "dat_emissao")
    private String datEmissao;

    @Column(name = "local_emissao")
    private String localEmissao;

    @Column(name = "contacto_principal")
    private String contactoPrincipal;

    @Column(name = "contacto_alternativo")
    private String contactoAlternativo;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "nome_conjugue")
    private String nomeConjugue;

    @Column(name = "distrito_municipal")
    private String distritoMunicipal;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "quatreirao")
    private String quatreirao;

    @Column(name = "talhao")
    private String talhao;

    @Column(name = "celula")
    private String celula;

    @Column(name = "bloco")
    private String bloco;

    @Column(name = "avenida")
    private String avenida;

    @Column(name = "numero_policia")
    private String numeroPolicia;

    @Column(name = "uso_actual")
    private String usoActual;

    @Column(name = "forma_uso")
    private String formaUso;

    @Column(name = "forma_obtencao")
    private String formaObtencao;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "ano_ocupacao")
    private Integer anoOcupacao;

    @Column(name = "tipo_acesso")
    private String tipoAcesso;

    @Column(name = "conflito")
    private Boolean conflito;

    @Column(name = "detalhes_conflito")
    private Boolean detalhesConflito;

    @Column(name = "construcao_precaria")
    private Boolean construcaoPrecaria;

    @Column(name = "pisos_acima_soleira")
    private Integer pisosAcimaSoleira;

    @Column(name = "pisos_abaixo_soleira")
    private Integer pisosAbaixoSoleira;

    @Column(name = "material_construcao_barrote")
    private Boolean materialConstrucaoBarrote;

    @Column(name = "material_construcao_ibr")
    private Boolean materialConstrucaoIBR;

    @Column(name = "material_construcao_pranchas")
    private Boolean materialConstrucaoPranchas;

    @Column(name = "material_construcao_pau")
    private Boolean materialConstrucaoPau;

    @Column(name = "material_construcao_canico")
    private Boolean materialConstrucaoCanico;

    @Column(name = "material_construcao_cimento")
    private Boolean materialConstrucaoCimento;

    @Column(name = "ocupacao")
    private String ocupacao;

    @Column(name = "tipo_contrucao")
    private String TipoContrucao;

    @Column(name = "detalhes_tipo_contrucao")
    private String detalhesTipoContrucao;

    @Column(name = "infraestrutura_existente")
    private String infraestruturaExistente;

    @Column(name = "data_levantamento")
    private LocalDate dataLevantamento;

    @ManyToOne
    @JsonIgnoreProperties(value = { "projecto" }, allowSetters = true)
    private Ficha ficha;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Alfa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcela() {
        return this.parcela;
    }

    public Alfa parcela(String parcela) {
        this.setParcela(parcela);
        return this;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getTipoTitular() {
        return this.tipoTitular;
    }

    public Alfa tipoTitular(String tipoTitular) {
        this.setTipoTitular(tipoTitular);
        return this;
    }

    public void setTipoTitular(String tipoTitular) {
        this.tipoTitular = tipoTitular;
    }

    public String getNomeTitular() {
        return this.nomeTitular;
    }

    public Alfa nomeTitular(String nomeTitular) {
        this.setNomeTitular(nomeTitular);
        return this;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getEstadoSocial() {
        return this.estadoSocial;
    }

    public Alfa estadoSocial(String estadoSocial) {
        this.setEstadoSocial(estadoSocial);
        return this;
    }

    public void setEstadoSocial(String estadoSocial) {
        this.estadoSocial = estadoSocial;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Alfa dataNascimento(LocalDate dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return this.sexo;
    }

    public Alfa sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDocumento() {
        return this.documento;
    }

    public Alfa documento(String documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public Alfa numeroDocumento(String numeroDocumento) {
        this.setNumeroDocumento(numeroDocumento);
        return this;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDatEmissao() {
        return this.datEmissao;
    }

    public Alfa datEmissao(String datEmissao) {
        this.setDatEmissao(datEmissao);
        return this;
    }

    public void setDatEmissao(String datEmissao) {
        this.datEmissao = datEmissao;
    }

    public String getLocalEmissao() {
        return this.localEmissao;
    }

    public Alfa localEmissao(String localEmissao) {
        this.setLocalEmissao(localEmissao);
        return this;
    }

    public void setLocalEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
    }

    public String getContactoPrincipal() {
        return this.contactoPrincipal;
    }

    public Alfa contactoPrincipal(String contactoPrincipal) {
        this.setContactoPrincipal(contactoPrincipal);
        return this;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getContactoAlternativo() {
        return this.contactoAlternativo;
    }

    public Alfa contactoAlternativo(String contactoAlternativo) {
        this.setContactoAlternativo(contactoAlternativo);
        return this;
    }

    public void setContactoAlternativo(String contactoAlternativo) {
        this.contactoAlternativo = contactoAlternativo;
    }

    public String getEstadoCivil() {
        return this.estadoCivil;
    }

    public Alfa estadoCivil(String estadoCivil) {
        this.setEstadoCivil(estadoCivil);
        return this;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNomeConjugue() {
        return this.nomeConjugue;
    }

    public Alfa nomeConjugue(String nomeConjugue) {
        this.setNomeConjugue(nomeConjugue);
        return this;
    }

    public void setNomeConjugue(String nomeConjugue) {
        this.nomeConjugue = nomeConjugue;
    }

    public String getDistritoMunicipal() {
        return this.distritoMunicipal;
    }

    public Alfa distritoMunicipal(String distritoMunicipal) {
        this.setDistritoMunicipal(distritoMunicipal);
        return this;
    }

    public void setDistritoMunicipal(String distritoMunicipal) {
        this.distritoMunicipal = distritoMunicipal;
    }

    public String getBairro() {
        return this.bairro;
    }

    public Alfa bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getQuatreirao() {
        return this.quatreirao;
    }

    public Alfa quatreirao(String quatreirao) {
        this.setQuatreirao(quatreirao);
        return this;
    }

    public void setQuatreirao(String quatreirao) {
        this.quatreirao = quatreirao;
    }

    public String getTalhao() {
        return this.talhao;
    }

    public Alfa talhao(String talhao) {
        this.setTalhao(talhao);
        return this;
    }

    public void setTalhao(String talhao) {
        this.talhao = talhao;
    }

    public String getCelula() {
        return this.celula;
    }

    public Alfa celula(String celula) {
        this.setCelula(celula);
        return this;
    }

    public void setCelula(String celula) {
        this.celula = celula;
    }

    public String getBloco() {
        return this.bloco;
    }

    public Alfa bloco(String bloco) {
        this.setBloco(bloco);
        return this;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public String getAvenida() {
        return this.avenida;
    }

    public Alfa avenida(String avenida) {
        this.setAvenida(avenida);
        return this;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public String getNumeroPolicia() {
        return this.numeroPolicia;
    }

    public Alfa numeroPolicia(String numeroPolicia) {
        this.setNumeroPolicia(numeroPolicia);
        return this;
    }

    public void setNumeroPolicia(String numeroPolicia) {
        this.numeroPolicia = numeroPolicia;
    }

    public String getUsoActual() {
        return this.usoActual;
    }

    public Alfa usoActual(String usoActual) {
        this.setUsoActual(usoActual);
        return this;
    }

    public void setUsoActual(String usoActual) {
        this.usoActual = usoActual;
    }

    public String getFormaUso() {
        return this.formaUso;
    }

    public Alfa formaUso(String formaUso) {
        this.setFormaUso(formaUso);
        return this;
    }

    public void setFormaUso(String formaUso) {
        this.formaUso = formaUso;
    }

    public String getFormaObtencao() {
        return this.formaObtencao;
    }

    public Alfa formaObtencao(String formaObtencao) {
        this.setFormaObtencao(formaObtencao);
        return this;
    }

    public void setFormaObtencao(String formaObtencao) {
        this.formaObtencao = formaObtencao;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Alfa tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getAnoOcupacao() {
        return this.anoOcupacao;
    }

    public Alfa anoOcupacao(Integer anoOcupacao) {
        this.setAnoOcupacao(anoOcupacao);
        return this;
    }

    public void setAnoOcupacao(Integer anoOcupacao) {
        this.anoOcupacao = anoOcupacao;
    }

    public String getTipoAcesso() {
        return this.tipoAcesso;
    }

    public Alfa tipoAcesso(String tipoAcesso) {
        this.setTipoAcesso(tipoAcesso);
        return this;
    }

    public void setTipoAcesso(String tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public Boolean getConflito() {
        return this.conflito;
    }

    public Alfa conflito(Boolean conflito) {
        this.setConflito(conflito);
        return this;
    }

    public void setConflito(Boolean conflito) {
        this.conflito = conflito;
    }

    public Boolean getDetalhesConflito() {
        return this.detalhesConflito;
    }

    public Alfa detalhesConflito(Boolean detalhesConflito) {
        this.setDetalhesConflito(detalhesConflito);
        return this;
    }

    public void setDetalhesConflito(Boolean detalhesConflito) {
        this.detalhesConflito = detalhesConflito;
    }

    public Boolean getConstrucaoPrecaria() {
        return this.construcaoPrecaria;
    }

    public Alfa construcaoPrecaria(Boolean construcaoPrecaria) {
        this.setConstrucaoPrecaria(construcaoPrecaria);
        return this;
    }

    public void setConstrucaoPrecaria(Boolean construcaoPrecaria) {
        this.construcaoPrecaria = construcaoPrecaria;
    }

    public Integer getPisosAcimaSoleira() {
        return this.pisosAcimaSoleira;
    }

    public Alfa pisosAcimaSoleira(Integer pisosAcimaSoleira) {
        this.setPisosAcimaSoleira(pisosAcimaSoleira);
        return this;
    }

    public void setPisosAcimaSoleira(Integer pisosAcimaSoleira) {
        this.pisosAcimaSoleira = pisosAcimaSoleira;
    }

    public Integer getPisosAbaixoSoleira() {
        return this.pisosAbaixoSoleira;
    }

    public Alfa pisosAbaixoSoleira(Integer pisosAbaixoSoleira) {
        this.setPisosAbaixoSoleira(pisosAbaixoSoleira);
        return this;
    }

    public void setPisosAbaixoSoleira(Integer pisosAbaixoSoleira) {
        this.pisosAbaixoSoleira = pisosAbaixoSoleira;
    }

    public Boolean getMaterialConstrucaoBarrote() {
        return this.materialConstrucaoBarrote;
    }

    public Alfa materialConstrucaoBarrote(Boolean materialConstrucaoBarrote) {
        this.setMaterialConstrucaoBarrote(materialConstrucaoBarrote);
        return this;
    }

    public void setMaterialConstrucaoBarrote(Boolean materialConstrucaoBarrote) {
        this.materialConstrucaoBarrote = materialConstrucaoBarrote;
    }

    public Boolean getMaterialConstrucaoIBR() {
        return this.materialConstrucaoIBR;
    }

    public Alfa materialConstrucaoIBR(Boolean materialConstrucaoIBR) {
        this.setMaterialConstrucaoIBR(materialConstrucaoIBR);
        return this;
    }

    public void setMaterialConstrucaoIBR(Boolean materialConstrucaoIBR) {
        this.materialConstrucaoIBR = materialConstrucaoIBR;
    }

    public Boolean getMaterialConstrucaoPranchas() {
        return this.materialConstrucaoPranchas;
    }

    public Alfa materialConstrucaoPranchas(Boolean materialConstrucaoPranchas) {
        this.setMaterialConstrucaoPranchas(materialConstrucaoPranchas);
        return this;
    }

    public void setMaterialConstrucaoPranchas(Boolean materialConstrucaoPranchas) {
        this.materialConstrucaoPranchas = materialConstrucaoPranchas;
    }

    public Boolean getMaterialConstrucaoPau() {
        return this.materialConstrucaoPau;
    }

    public Alfa materialConstrucaoPau(Boolean materialConstrucaoPau) {
        this.setMaterialConstrucaoPau(materialConstrucaoPau);
        return this;
    }

    public void setMaterialConstrucaoPau(Boolean materialConstrucaoPau) {
        this.materialConstrucaoPau = materialConstrucaoPau;
    }

    public Boolean getMaterialConstrucaoCanico() {
        return this.materialConstrucaoCanico;
    }

    public Alfa materialConstrucaoCanico(Boolean materialConstrucaoCanico) {
        this.setMaterialConstrucaoCanico(materialConstrucaoCanico);
        return this;
    }

    public void setMaterialConstrucaoCanico(Boolean materialConstrucaoCanico) {
        this.materialConstrucaoCanico = materialConstrucaoCanico;
    }

    public Boolean getMaterialConstrucaoCimento() {
        return this.materialConstrucaoCimento;
    }

    public Alfa materialConstrucaoCimento(Boolean materialConstrucaoCimento) {
        this.setMaterialConstrucaoCimento(materialConstrucaoCimento);
        return this;
    }

    public void setMaterialConstrucaoCimento(Boolean materialConstrucaoCimento) {
        this.materialConstrucaoCimento = materialConstrucaoCimento;
    }

    public String getOcupacao() {
        return this.ocupacao;
    }

    public Alfa ocupacao(String ocupacao) {
        this.setOcupacao(ocupacao);
        return this;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getTipoContrucao() {
        return this.TipoContrucao;
    }

    public Alfa TipoContrucao(String TipoContrucao) {
        this.setTipoContrucao(TipoContrucao);
        return this;
    }

    public void setTipoContrucao(String TipoContrucao) {
        this.TipoContrucao = TipoContrucao;
    }

    public String getDetalhesTipoContrucao() {
        return this.detalhesTipoContrucao;
    }

    public Alfa detalhesTipoContrucao(String detalhesTipoContrucao) {
        this.setDetalhesTipoContrucao(detalhesTipoContrucao);
        return this;
    }

    public void setDetalhesTipoContrucao(String detalhesTipoContrucao) {
        this.detalhesTipoContrucao = detalhesTipoContrucao;
    }

    public String getInfraestruturaExistente() {
        return this.infraestruturaExistente;
    }

    public Alfa infraestruturaExistente(String infraestruturaExistente) {
        this.setInfraestruturaExistente(infraestruturaExistente);
        return this;
    }

    public void setInfraestruturaExistente(String infraestruturaExistente) {
        this.infraestruturaExistente = infraestruturaExistente;
    }

    public LocalDate getDataLevantamento() {
        return this.dataLevantamento;
    }

    public Alfa dataLevantamento(LocalDate dataLevantamento) {
        this.setDataLevantamento(dataLevantamento);
        return this;
    }

    public void setDataLevantamento(LocalDate dataLevantamento) {
        this.dataLevantamento = dataLevantamento;
    }

    public Ficha getFicha() {
        return this.ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public Alfa ficha(Ficha ficha) {
        this.setFicha(ficha);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alfa)) {
            return false;
        }
        return id != null && id.equals(((Alfa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alfa{" +
            "id=" + getId() +
            ", parcela='" + getParcela() + "'" +
            ", tipoTitular='" + getTipoTitular() + "'" +
            ", nomeTitular='" + getNomeTitular() + "'" +
            ", estadoSocial='" + getEstadoSocial() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", datEmissao='" + getDatEmissao() + "'" +
            ", localEmissao='" + getLocalEmissao() + "'" +
            ", contactoPrincipal='" + getContactoPrincipal() + "'" +
            ", contactoAlternativo='" + getContactoAlternativo() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", nomeConjugue='" + getNomeConjugue() + "'" +
            ", distritoMunicipal='" + getDistritoMunicipal() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", quatreirao='" + getQuatreirao() + "'" +
            ", talhao='" + getTalhao() + "'" +
            ", celula='" + getCelula() + "'" +
            ", bloco='" + getBloco() + "'" +
            ", avenida='" + getAvenida() + "'" +
            ", numeroPolicia='" + getNumeroPolicia() + "'" +
            ", usoActual='" + getUsoActual() + "'" +
            ", formaUso='" + getFormaUso() + "'" +
            ", formaObtencao='" + getFormaObtencao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", anoOcupacao=" + getAnoOcupacao() +
            ", tipoAcesso='" + getTipoAcesso() + "'" +
            ", conflito='" + getConflito() + "'" +
            ", detalhesConflito='" + getDetalhesConflito() + "'" +
            ", construcaoPrecaria='" + getConstrucaoPrecaria() + "'" +
            ", pisosAcimaSoleira=" + getPisosAcimaSoleira() +
            ", pisosAbaixoSoleira=" + getPisosAbaixoSoleira() +
            ", materialConstrucaoBarrote='" + getMaterialConstrucaoBarrote() + "'" +
            ", materialConstrucaoIBR='" + getMaterialConstrucaoIBR() + "'" +
            ", materialConstrucaoPranchas='" + getMaterialConstrucaoPranchas() + "'" +
            ", materialConstrucaoPau='" + getMaterialConstrucaoPau() + "'" +
            ", materialConstrucaoCanico='" + getMaterialConstrucaoCanico() + "'" +
            ", materialConstrucaoCimento='" + getMaterialConstrucaoCimento() + "'" +
            ", ocupacao='" + getOcupacao() + "'" +
            ", TipoContrucao='" + getTipoContrucao() + "'" +
            ", detalhesTipoContrucao='" + getDetalhesTipoContrucao() + "'" +
            ", infraestruturaExistente='" + getInfraestruturaExistente() + "'" +
            ", dataLevantamento='" + getDataLevantamento() + "'" +
            "}";
    }
}
