package gov.cmcm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link gov.cmcm.domain.Pontos} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PontosDTO implements Serializable {

    private Long id;

    private String parcela;

    private Double pointX;

    private Double pointY;

    private String marco;

    private String zona;

    private SpatialUnitDTO spatialUnit;

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

    public Double getPointX() {
        return pointX;
    }

    public void setPointX(Double pointX) {
        this.pointX = pointX;
    }

    public Double getPointY() {
        return pointY;
    }

    public void setPointY(Double pointY) {
        this.pointY = pointY;
    }

    public String getMarco() {
        return marco;
    }

    public void setMarco(String marco) {
        this.marco = marco;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public SpatialUnitDTO getSpatialUnit() {
        return spatialUnit;
    }

    public void setSpatialUnit(SpatialUnitDTO spatialUnit) {
        this.spatialUnit = spatialUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PontosDTO)) {
            return false;
        }

        PontosDTO pontosDTO = (PontosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pontosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PontosDTO{" +
            "id=" + getId() +
            ", parcela='" + getParcela() + "'" +
            ", pointX=" + getPointX() +
            ", pointY=" + getPointY() +
            ", marco='" + getMarco() + "'" +
            ", zona='" + getZona() + "'" +
            ", spatialUnit=" + getSpatialUnit() +
            "}";
    }
}
