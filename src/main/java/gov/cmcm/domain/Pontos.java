package gov.cmcm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Pontos.
 */
@Entity
@Table(name = "pontos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pontos extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "parcela")
    private String parcela;

    @Column(name = "point_x")
    private Double pointX;

    @Column(name = "point_y")
    private Double pointY;

    @Column(name = "marco")
    private String marco;

    @Column(name = "zona")
    private String zona;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ficha" }, allowSetters = true)
    private SpatialUnit spatialUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pontos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcela() {
        return this.parcela;
    }

    public Pontos parcela(String parcela) {
        this.setParcela(parcela);
        return this;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public Double getPointX() {
        return this.pointX;
    }

    public Pontos pointX(Double pointX) {
        this.setPointX(pointX);
        return this;
    }

    public void setPointX(Double pointX) {
        this.pointX = pointX;
    }

    public Double getPointY() {
        return this.pointY;
    }

    public Pontos pointY(Double pointY) {
        this.setPointY(pointY);
        return this;
    }

    public void setPointY(Double pointY) {
        this.pointY = pointY;
    }

    public String getMarco() {
        return this.marco;
    }

    public Pontos marco(String marco) {
        this.setMarco(marco);
        return this;
    }

    public void setMarco(String marco) {
        this.marco = marco;
    }

    public String getZona() {
        return this.zona;
    }

    public Pontos zona(String zona) {
        this.setZona(zona);
        return this;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public SpatialUnit getSpatialUnit() {
        return this.spatialUnit;
    }

    public void setSpatialUnit(SpatialUnit spatialUnit) {
        this.spatialUnit = spatialUnit;
    }

    public Pontos spatialUnit(SpatialUnit spatialUnit) {
        this.setSpatialUnit(spatialUnit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pontos)) {
            return false;
        }
        return id != null && id.equals(((Pontos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pontos{" +
            "id=" + getId() +
            ", parcela='" + getParcela() + "'" +
            ", pointX=" + getPointX() +
            ", pointY=" + getPointY() +
            ", marco='" + getMarco() + "'" +
            ", zona='" + getZona() + "'" +
            "}";
    }
}
