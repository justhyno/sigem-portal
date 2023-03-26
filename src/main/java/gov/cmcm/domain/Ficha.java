package gov.cmcm.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Ficha.
 */
@Entity
@Table(name = "ficha")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ficha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "parcela")
    private String parcela;

    @Column(name = "processo")
    private String processo;

    @ManyToOne(optional = false)
    @NotNull
    private Projecto projecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ficha id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcela() {
        return this.parcela;
    }

    public Ficha parcela(String parcela) {
        this.setParcela(parcela);
        return this;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getProcesso() {
        return this.processo;
    }

    public Ficha processo(String processo) {
        this.setProcesso(processo);
        return this;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public Projecto getProjecto() {
        return this.projecto;
    }

    public void setProjecto(Projecto projecto) {
        this.projecto = projecto;
    }

    public Ficha projecto(Projecto projecto) {
        this.setProjecto(projecto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ficha)) {
            return false;
        }
        return id != null && id.equals(((Ficha) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ficha{" +
            "id=" + getId() +
            ", parcela='" + getParcela() + "'" +
            ", processo='" + getProcesso() + "'" +
            "}";
    }
}
