package com.challenge.literatura.model;

import jakarta.persistence.*;

import java.util.stream.Collectors;

@Entity
@Table(name = "libross")
public class libros {
    @Id
    private Long id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private lenguajes lenguaje;
    private String copyright;
    private Integer downloadCount;
    @ManyToOne
    private autores autor;

    public libros(datosLibros DatosLibros){
        this.id = DatosLibros.id();
        this.titulo = DatosLibros.titulo();
        this.lenguaje = lenguajes.fromString(DatosLibros.lenguajes().stream()
                .limit(1).collect(Collectors.joining()));
        this.copyright = DatosLibros.copyright();
        this.downloadCount = DatosLibros.downloadCount();
    }

    @Override
    public String toString() {
        return "libros{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", lenguaje=" + lenguaje +
                ", copyright='" + copyright + '\'' +
                ", downloadCount=" + downloadCount +
                ", autor=" + autor +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public lenguajes getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(lenguajes lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public autores getAutor() {
        return autor;
    }

    public void setAutor(autores autor) {
        this.autor = autor;
    }
}
