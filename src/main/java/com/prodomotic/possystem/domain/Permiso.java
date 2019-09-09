package com.prodomotic.possystem.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Permiso.
 */
@Entity
@Table(name = "permiso")
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_permiso")
    private String nombrePermiso;

    @Column(name = "activo")
    private Boolean activo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePermiso() {
        return nombrePermiso;
    }

    public Permiso nombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
        return this;
    }

    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Permiso activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Permiso)) {
            return false;
        }
        return id != null && id.equals(((Permiso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Permiso{" +
            "id=" + getId() +
            ", nombrePermiso='" + getNombrePermiso() + "'" +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
