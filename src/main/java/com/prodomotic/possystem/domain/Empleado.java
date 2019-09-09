package com.prodomotic.possystem.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_empleado")
    private String nombreEmpleado;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "telefono")
    private String telefono;

    @OneToMany(mappedBy = "empleado")
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("empleados")
    private Perfil perfil;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public Empleado nombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
        return this;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public Empleado usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Empleado contrasena(String contrasena) {
        this.contrasena = contrasena;
        return this;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public Empleado telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public Empleado pedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        return this;
    }

    public Empleado addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setEmpleado(this);
        return this;
    }

    public Empleado removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setEmpleado(null);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public Empleado perfil(Perfil perfil) {
        this.perfil = perfil;
        return this;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empleado)) {
            return false;
        }
        return id != null && id.equals(((Empleado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Empleado{" +
            "id=" + getId() +
            ", nombreEmpleado='" + getNombreEmpleado() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", contrasena='" + getContrasena() + "'" +
            ", telefono='" + getTelefono() + "'" +
            "}";
    }
}
