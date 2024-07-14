package com.challenge.literatura.model;

import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "autores")
public class autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<libros> Libros;

    public autores(){

    }
    public autores(datosAutores datosAutor){
        this.nombre=datosAutor.nombre();
        this.fechaDeNacimiento=datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento=datosAutor.fechaDeFallecimiento();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<libros> getLibros() {
        return Libros;
    }

    public void setLibros(List<libros> libros) {
        Libros = libros;
    }


    @Override
    public String toString() {
        return "autores{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha De Nacimiento=" + fechaDeNacimiento +
                ", fecha De Fallecimiento=" + fechaDeFallecimiento +
                ", Libros=" + Libros +
                '}';
    }
}
