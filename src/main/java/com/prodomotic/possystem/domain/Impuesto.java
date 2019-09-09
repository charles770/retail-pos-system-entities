package com.prodomotic.possystem.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Impuesto.
 */
@Entity
@Table(name = "impuesto")
public class Impuesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "description")
    private String description;

    @Column(name = "porcentaje")
    private Double porcentaje;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Impuesto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public Impuesto description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public Impuesto porcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
        return this;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Impuesto)) {
            return false;
        }
        return id != null && id.equals(((Impuesto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Impuesto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", description='" + getDescription() + "'" +
            ", porcentaje=" + getPorcentaje() +
            "}";
    }
}
