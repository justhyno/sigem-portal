package gov.cmcm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A SpatialUnit.
 */
@Entity
@Table(name = "spatial_unit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SpatialUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "parcela")
    private String parcela;

    @ManyToOne
    @JsonIgnoreProperties(value = { "projecto" }, allowSetters = true)
    private Ficha ficha;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SpatialUnit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcela() {
        return this.parcela;
    }

    public SpatialUnit parcela(String parcela) {
        this.setParcela(parcela);
        return this;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public Ficha getFicha() {
        return this.ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public SpatialUnit ficha(Ficha ficha) {
        this.setFicha(ficha);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpatialUnit)) {
            return false;
        }
        return id != null && id.equals(((SpatialUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpatialUnit{" +
            "id=" + getId() +
            ", parcela='" + getParcela() + "'" +
            "}";
    }
}
