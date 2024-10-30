package com.cursospringalura.ScreenMatch.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double evaluacion;
    private LocalDate fechaLanzamiento;

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
}
