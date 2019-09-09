package com.prodomotic.possystem.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "foto")
    private String foto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_compra")
    private Double precioCompra;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "minimas_unidades")
    private Integer minimasUnidades;

    @Column(name = "unidades_pedido")
    private Integer unidadesPedido;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private UnidadMedida codum;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Proveedor proveedor;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Categoria codcategoria;

    @ManyToMany
    @JoinTable(name = "producto_impuesto",
               joinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "impuesto_id", referencedColumnName = "id"))
    private Set<Impuesto> impuestos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public Producto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFoto() {
        return foto;
    }

    public Producto foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Producto cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public Producto precioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
        return this;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public Producto precioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
        return this;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getMinimasUnidades() {
        return minimasUnidades;
    }

    public Producto minimasUnidades(Integer minimasUnidades) {
        this.minimasUnidades = minimasUnidades;
        return this;
    }

    public void setMinimasUnidades(Integer minimasUnidades) {
        this.minimasUnidades = minimasUnidades;
    }

    public Integer getUnidadesPedido() {
        return unidadesPedido;
    }

    public Producto unidadesPedido(Integer unidadesPedido) {
        this.unidadesPedido = unidadesPedido;
        return this;
    }

    public void setUnidadesPedido(Integer unidadesPedido) {
        this.unidadesPedido = unidadesPedido;
    }

    public UnidadMedida getCodum() {
        return codum;
    }

    public Producto codum(UnidadMedida unidadMedida) {
        this.codum = unidadMedida;
        return this;
    }

    public void setCodum(UnidadMedida unidadMedida) {
        this.codum = unidadMedida;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Producto proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Categoria getCodcategoria() {
        return codcategoria;
    }

    public Producto codcategoria(Categoria categoria) {
        this.codcategoria = categoria;
        return this;
    }

    public void setCodcategoria(Categoria categoria) {
        this.codcategoria = categoria;
    }

    public Set<Impuesto> getImpuestos() {
        return impuestos;
    }

    public Producto impuestos(Set<Impuesto> impuestos) {
        this.impuestos = impuestos;
        return this;
    }

    public Producto addImpuesto(Impuesto impuesto) {
        this.impuestos.add(impuesto);
        impuesto.getProductos().add(this);
        return this;
    }

    public Producto removeImpuesto(Impuesto impuesto) {
        this.impuestos.remove(impuesto);
        impuesto.getProductos().remove(this);
        return this;
    }

    public void setImpuestos(Set<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", foto='" + getFoto() + "'" +
            ", cantidad=" + getCantidad() +
            ", precioCompra=" + getPrecioCompra() +
            ", precioVenta=" + getPrecioVenta() +
            ", minimasUnidades=" + getMinimasUnidades() +
            ", unidadesPedido=" + getUnidadesPedido() +
            "}";
    }
}
