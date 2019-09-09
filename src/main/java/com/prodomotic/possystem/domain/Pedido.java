package com.prodomotic.possystem.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "descuento")
    private Double descuento;

    @Column(name = "impuestos")
    private Double impuestos;

    @Column(name = "total")
    private Double total;

    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Empleado empleado;

    @ManyToMany
    @JoinTable(name = "pedido_producto",
               joinColumns = @JoinColumn(name = "pedido_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"))
    private Set<Producto> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Pedido codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Pedido subtotal(Double subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDescuento() {
        return descuento;
    }

    public Pedido descuento(Double descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public Pedido impuestos(Double impuestos) {
        this.impuestos = impuestos;
        return this;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }

    public Double getTotal() {
        return total;
    }

    public Pedido total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Pedido fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Pedido empleado(Empleado empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Pedido productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Pedido addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getPedidos().add(this);
        return this;
    }

    public Pedido removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getPedidos().remove(this);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", subtotal=" + getSubtotal() +
            ", descuento=" + getDescuento() +
            ", impuestos=" + getImpuestos() +
            ", total=" + getTotal() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
