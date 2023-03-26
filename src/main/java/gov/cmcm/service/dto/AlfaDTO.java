package gov.cmcm.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gov.cmcm.domain.Alfa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlfaDTO implements Serializable {

    private Long id;

    private String parcela;

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
            ", dataLevantamento='" + getDataLevantamento() + "'" +
            ", ficha=" + getFicha() +
            "}";
    }
}
