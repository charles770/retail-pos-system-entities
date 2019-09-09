package com.prodomotic.possystem.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "abreviatura")
    private String abreviatura;

    @Column(name = "foto")
    private String foto;

    @Column(name = "color_fondo")
    private Integer colorFondo;

    @ManyToOne
    @JsonIgnoreProperties("categorias")
    private Categoria categoria;

    @OneToMany(mappedBy = "categoria")
    private Set<Categoria> categorias = new HashSet<>();

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

    public Categoria nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public Categoria abreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
        return this;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getFoto() {
        return foto;
    }

    public Categoria foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getColorFondo() {
        return colorFondo;
    }

    public Categoria colorFondo(Integer colorFondo) {
        this.colorFondo = colorFondo;
        return this;
    }

    public void setColorFondo(Integer colorFondo) {
        this.colorFondo = colorFondo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Categoria categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public Categoria categorias(Set<Categoria> categorias) {
        this.categorias = categorias;
        return this;
    }

    public Categoria addCategoria(Categoria categoria) {
        this.categorias.add(categoria);
        categoria.setCategoria(this);
        return this;
    }

    public Categoria removeCategoria(Categoria categoria) {
        this.categorias.remove(categoria);
        categoria.setCategoria(null);
        return this;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categoria)) {
            return false;
        }
        return id != null && id.equals(((Categoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Categoria{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", abreviatura='" + getAbreviatura() + "'" +
            ", foto='" + getFoto() + "'" +
            ", colorFondo=" + getColorFondo() +
            "}";
    }
}
