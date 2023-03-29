package gov.cmcm.service.dto;

import com.poiji.annotation.ExcelCellName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gov.cmcm.domain.Alfa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlfaDTO implements Serializable {

    private Long id;

    @ExcelCellName("Codigo da Parcela")
    private String parcela;

    @ExcelCellName("Tipo de Titular")
    private String tipoTitular;

    @ExcelCellName("Nome do Titular")
    private String nomeTitular;

    @ExcelCellName("Estado Social /Vulnerabilidade")
    private String estadoSocial;

    @ExcelCellName("Data de Nascimento")
    private LocalDate dataNascimento;

    @ExcelCellName("Sexo")
    private String sexo;

    @ExcelCellName("Documento")
    private String documento;

    @ExcelCellName("Numero do Documento de Identificacao")
    private String numeroDocumento;

    @ExcelCellName("Data de Emissao")
    private String datEmissao;

    @ExcelCellName("Local de Emissao")
    private String localEmissao;

    @ExcelCellName("Contacto 1")
    private String contactoPrincipal;

    @ExcelCellName("Contacto 2")
    private String contactoAlternativo;

    @ExcelCellName("Estado Civil")
    private String estadoCivil;

    @ExcelCellName("Nome do Conjugue")
    private String nomeConjugue;

    @ExcelCellName("Distrito Municipal")
    private String distritoMunicipal;

    @ExcelCellName("Bairro")
    private String bairro;

    @ExcelCellName("Quarteirao")
    private String quatreirao;

    @ExcelCellName("Talhao")
    private String talhao;

    @ExcelCellName("Celula")
    private String celula;

    @ExcelCellName("Zona/Bloco")
    private String bloco;

    @ExcelCellName("Avenida/Rua")
    private String avenida;

    @ExcelCellName("Numero de policia")
    private String numeroPolicia;

    @ExcelCellName("Uso Actual do Solo")
    private String usoActual;

    @ExcelCellName("Forma  de Uso")
    private String formaUso;

    @ExcelCellName("Forma de Obtenção")
    private String formaObtencao;

    @ExcelCellName("Tipo:")
    private String tipo;

    @ExcelCellName("Ano de Ocupação")
    private Integer anoOcupacao;

    @ExcelCellName("Tipo de Acesso:")
    private String tipoAcesso;

    //@ExcelCellName("Bloco Cadastral")
    private Boolean conflito;

    //@ExcelCellName("Bloco Cadastral")

    private Boolean detalhesConflito;

    //@ExcelCellName("Bloco Cadastral")

    private Boolean construcaoPrecaria;

    @ExcelCellName("Numero de pisos acima da soleira")
    private Integer pisosAcimaSoleira;

    @ExcelCellName("Numero de pisos abaixo da soleira")
    private Integer pisosAbaixoSoleira;

    @ExcelCellName("Bloco Cadastral")
    private Boolean materialConstrucaoBarrote;

    //   @ExcelCellName("Bloco Cadastral")

    private Boolean materialConstrucaoIBR;
    // @ExcelCellName("Bloco Cadastral")

    private Boolean materialConstrucaoPranchas;
    //@ExcelCellName("Bloco Cadastral")

    private Boolean materialConstrucaoPau;
    // @ExcelCellName("Bloco Cadastral")

    private Boolean materialConstrucaoCanico;
    //@ExcelCellName("Bloco Cadastral")

    private Boolean materialConstrucaoCimento;

    @ExcelCellName("Ocupacao")
    private String ocupacao;

    @ExcelCellName("Tipos de Construcao")
    private String TipoContrucao;

    @ExcelCellName("Especifique")
    private String detalhesTipoContrucao;

    @ExcelCellName("Infraestruturas existentes no talhao")
    private String infraestruturaExistente;

    private LocalDate dataLevantamento;

    private FichaDTO ficha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getTipoTitular() {
        return tipoTitular;
    }

    public void setTipoTitular(String tipoTitular) {
        this.tipoTitular = tipoTitular;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getEstadoSocial() {
        return estadoSocial;
    }

    public void setEstadoSocial(String estadoSocial) {
        this.estadoSocial = estadoSocial;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDatEmissao() {
        return datEmissao;
    }

    public void setDatEmissao(String datEmissao) {
        this.datEmissao = datEmissao;
    }

    public String getLocalEmissao() {
        return localEmissao;
    }

    public void setLocalEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getContactoAlternativo() {
        return contactoAlternativo;
    }

    public void setContactoAlternativo(String contactoAlternativo) {
        this.contactoAlternativo = contactoAlternativo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNomeConjugue() {
        return nomeConjugue;
    }

    public void setNomeConjugue(String nomeConjugue) {
        this.nomeConjugue = nomeConjugue;
    }

    public String getDistritoMunicipal() {
        return distritoMunicipal;
    }

    public void setDistritoMunicipal(String distritoMunicipal) {
        this.distritoMunicipal = distritoMunicipal;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getQuatreirao() {
        return quatreirao;
    }

    public void setQuatreirao(String quatreirao) {
        this.quatreirao = quatreirao;
    }

    public String getTalhao() {
        return talhao;
    }

    public void setTalhao(String talhao) {
        this.talhao = talhao;
    }

    public String getCelula() {
        return celula;
    }

    public void setCelula(String celula) {
        this.celula = celula;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public String getNumeroPolicia() {
        return numeroPolicia;
    }

    public void setNumeroPolicia(String numeroPolicia) {
        this.numeroPolicia = numeroPolicia;
    }

    public String getUsoActual() {
        return usoActual;
    }

    public void setUsoActual(String usoActual) {
        this.usoActual = usoActual;
    }

    public String getFormaUso() {
        return formaUso;
    }

    public void setFormaUso(String formaUso) {
        this.formaUso = formaUso;
    }

    public String getFormaObtencao() {
        return formaObtencao;
    }

    public void setFormaObtencao(String formaObtencao) {
        this.formaObtencao = formaObtencao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getAnoOcupacao() {
        return anoOcupacao;
    }

    public void setAnoOcupacao(Integer anoOcupacao) {
        this.anoOcupacao = anoOcupacao;
    }

    public String getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(String tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public Boolean getConflito() {
        return conflito;
    }

    public void setConflito(Boolean conflito) {
        this.conflito = conflito;
    }

    public Boolean getDetalhesConflito() {
        return detalhesConflito;
    }

    public void setDetalhesConflito(Boolean detalhesConflito) {
        this.detalhesConflito = detalhesConflito;
    }

    public Boolean getConstrucaoPrecaria() {
        return construcaoPrecaria;
    }

    public void setConstrucaoPrecaria(Boolean construcaoPrecaria) {
        this.construcaoPrecaria = construcaoPrecaria;
    }

    public Integer getPisosAcimaSoleira() {
        return pisosAcimaSoleira;
    }

    public void setPisosAcimaSoleira(Integer pisosAcimaSoleira) {
        this.pisosAcimaSoleira = pisosAcimaSoleira;
    }

    public Integer getPisosAbaixoSoleira() {
        return pisosAbaixoSoleira;
    }

    public void setPisosAbaixoSoleira(Integer pisosAbaixoSoleira) {
        this.pisosAbaixoSoleira = pisosAbaixoSoleira;
    }

    public Boolean getMaterialConstrucaoBarrote() {
        return materialConstrucaoBarrote;
    }

    public void setMaterialConstrucaoBarrote(Boolean materialConstrucaoBarrote) {
        this.materialConstrucaoBarrote = materialConstrucaoBarrote;
    }

    public Boolean getMaterialConstrucaoIBR() {
        return materialConstrucaoIBR;
    }

    public void setMaterialConstrucaoIBR(Boolean materialConstrucaoIBR) {
        this.materialConstrucaoIBR = materialConstrucaoIBR;
    }

    public Boolean getMaterialConstrucaoPranchas() {
        return materialConstrucaoPranchas;
    }

    public void setMaterialConstrucaoPranchas(Boolean materialConstrucaoPranchas) {
        this.materialConstrucaoPranchas = materialConstrucaoPranchas;
    }

    public Boolean getMaterialConstrucaoPau() {
        return materialConstrucaoPau;
    }

    public void setMaterialConstrucaoPau(Boolean materialConstrucaoPau) {
        this.materialConstrucaoPau = materialConstrucaoPau;
    }

    public Boolean getMaterialConstrucaoCanico() {
        return materialConstrucaoCanico;
    }

    public void setMaterialConstrucaoCanico(Boolean materialConstrucaoCanico) {
        this.materialConstrucaoCanico = materialConstrucaoCanico;
    }

    public Boolean getMaterialConstrucaoCimento() {
        return materialConstrucaoCimento;
    }

    public void setMaterialConstrucaoCimento(Boolean materialConstrucaoCimento) {
        this.materialConstrucaoCimento = materialConstrucaoCimento;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getTipoContrucao() {
        return TipoContrucao;
    }

    public void setTipoContrucao(String TipoContrucao) {
        this.TipoContrucao = TipoContrucao;
    }

    public String getDetalhesTipoContrucao() {
        return detalhesTipoContrucao;
    }

    public void setDetalhesTipoContrucao(String detalhesTipoContrucao) {
        this.detalhesTipoContrucao = detalhesTipoContrucao;
    }

    public String getInfraestruturaExistente() {
        return infraestruturaExistente;
    }

    public void setInfraestruturaExistente(String infraestruturaExistente) {
        this.infraestruturaExistente = infraestruturaExistente;
    }

    public LocalDate getDataLevantamento() {
        return dataLevantamento;
    }

    public void setDataLevantamento(LocalDate dataLevantamento) {
        this.dataLevantamento = dataLevantamento;
    }

    public FichaDTO getFicha() {
        return ficha;
    }

    public void setFicha(FichaDTO ficha) {
        this.ficha = ficha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlfaDTO)) {
            return false;
        }

        AlfaDTO alfaDTO = (AlfaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alfaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlfaDTO{" +
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
            ", ficha=" + getFicha() +
            "}";
    }
}
