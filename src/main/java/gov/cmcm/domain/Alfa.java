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
            ", dataLevantamento='" + getDataLevantamento() + "'" +
            "}";
    }
}
