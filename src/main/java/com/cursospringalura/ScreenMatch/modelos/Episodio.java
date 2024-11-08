package com.cursospringalura.ScreenMatch.modelos;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity // Indica que esta clase es una entidad(Tabla) en nuestra base de datos.
@Table(name = "episodios") // Indica que esta tabla en nuetsra base de datos se llamara "episodios".
public class Episodio {

    @Id // Indica que este atributo corresponde a la primary key de nuestra entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que autoincrementara con cada registro almacenado.
    private Long id;

    @Column// Indica que este atributo correspondera a una columna dentro de nuestra tabla en nuestra base de datos.
    private Integer temporada;

    @Column(unique = true, nullable = false) //Indica que sera una columna de la tabla y le agregamos algunas constraints.
    private String titulo;

    @Column // Indica que este atributo correspondera a una columna dentro de nuestra tabla en nuestra base de datos.
    private Integer numeroEpisodio;

    @Column // Indica que este atributo correspondera a una columna dentro de nuestra tabla en nuestra base de datos.
    private Double evaluacion;

    @Column // Indica que este atributo correspondera a una columna dentro de nuestra tabla en nuestra base de datos.
    private LocalDate fechaLanzamiento;

    // Este atributo es la relacion que existe entre las serie y sus episodios en la base de datos.
    // Relacion uno a muchos. Una serie, muchos episodios.
    @ManyToOne // Indica una relacion de muchos a uno con la tabla "series" en nuestra base de datos .
    private DatosSerie serie;

    public Episodio(Integer numero, DatosEpisodio episodio) {
        this.temporada = numero;
        this.titulo = episodio.titulo();
        this.numeroEpisodio = episodio.numeroEpisodio();

        try {
            this.evaluacion = Double.valueOf(episodio.evaluacion());
        } catch (NumberFormatException e) {
            this.evaluacion = 0.0;
        }

        try {
            this.fechaLanzamiento = LocalDate.parse(episodio.fechaLanzamiento());
        } catch (DateTimeParseException e) {
            this.fechaLanzamiento = null;
        }
    }

    // Constructor por defecto
    public Episodio() {

    }

    public Integer getTemporada() {
        return temporada;
    }

    @Override
    public String toString() {
        return "\nTemporada: " + temporada +
                "\nTitulo: " + titulo +
                "\nNumero Episodio: " + numeroEpisodio +
                "\nEvaluacion: " + evaluacion +
                "\nFecha de lanzamiento: " + fechaLanzamiento;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public DatosSerie getSerie() {
        return serie;
    }

    public void setSerie(DatosSerie serie) {
        this.serie = serie;
    }
}
