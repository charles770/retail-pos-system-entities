package com.prodomotic.possystem.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UnidadMedida.
 */
@Entity
@Table(name = "unidad_medida")
public class UnidadMedida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_unidad_medida")
    private String nombreUnidadMedida;

    @Column(name = "abreviatura")
    private String abreviatura;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public UnidadMedida nombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
        return this;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public UnidadMedida abreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
        return this;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadMedida)) {
            return false;
        }
        return id != null && id.equals(((UnidadMedida) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UnidadMedida{" +
            "id=" + getId() +
            ", nombreUnidadMedida='" + getNombreUnidadMedida() + "'" +
            ", abreviatura='" + getAbreviatura() + "'" +
            "}";
    }
}
