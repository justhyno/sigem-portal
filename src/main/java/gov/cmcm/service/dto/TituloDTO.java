package gov.cmcm.service.dto;

import java.util.List;
import lombok.Data;

public class TituloDTO {

    private String nomeCompleto;
    private String documentoIdentificacao;
    private String dataNascimento;
    private String estadoCivil;
    private String distritoMunicipal;
    private String bairro;
    private String numeroParcela;
    private String superficieParcela;
    private String avenida;
    private String finalidade;
    private String dataEmissao;
    private String processo;
    private List<PontosDTO> pontos;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getDocumentoIdentificacao() {
        return documentoIdentificacao;
    }

    public void setDocumentoIdentificacao(String documentoIdentificacao) {
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
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

    public String getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public String getSuperficieParcela() {
        return superficieParcela;
    }

    public void setSuperficieParcela(String superficieParcela) {
        this.superficieParcela = superficieParcela;
    }

    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public List<PontosDTO> getPontos() {
        return pontos;
    }

    public void setPontos(List<PontosDTO> pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {
        return (
            "TituloDTO [nomeCompleto=" +
            nomeCompleto +
            ", documentoIdentificacao=" +
            documentoIdentificacao +
            ", dataNascimento=" +
            dataNascimento +
            ", estadoCivil=" +
            estadoCivil +
            ", distritoMunicipal=" +
            distritoMunicipal +
            ", bairro=" +
            bairro +
            ", numeroParcela=" +
            numeroParcela +
            ", superficieParcela=" +
            superficieParcela +
            ", avenida=" +
            avenida +
            ", finalidade=" +
            finalidade +
            ", dataEmissao=" +
            dataEmissao +
            ", processo=" +
            processo +
            ", pontos=" +
            pontos +
            "]"
        );
    }
}
