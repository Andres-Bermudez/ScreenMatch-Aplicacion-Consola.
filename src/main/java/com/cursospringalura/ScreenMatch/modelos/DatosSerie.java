package com.cursospringalura.ScreenMatch.modelos;

import com.cursospringalura.ScreenMatch.servicios.gemini.ConsultaGemini;
import com.cursospringalura.ScreenMatch.servicios.gemini.modelos.ResponseData;
import jakarta.persistence.*;
import java.util.List;
import java.util.OptionalDouble;

@Entity // Indica que esta clase sera una entidad(Tabla) en nuestra base de datos.
@Table(name = "series") // Indica que esta tabla en la base de datos se llamara "series".
public class DatosSerie {

    @Id // Indica que este atributo sera la PRIMARY KEY de nuestra tabla.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que JPA se encargara de que esta columna autoincrementara con cada registro.
    private Long id;

    @Column(unique = true) // Indica que este atributo sera una columna de nuestra tabla y se le agregan las constraints.
    private String titulo;

    @Column(nullable = false) // Indica que este atributo sera una columna de nuestra tabla y se le agregan las constraints.
    private String año;

    @Column // Indica que este atributo sera una columna en la tabla con este mismo nombre.
    private String calificada;

    @Column(name = "fecha_publicacion", nullable = false) // Indica que este atributo sera una columna de nuestra tabla y se le agregan las constraints.
    private String fechaPublicacion;

    @Column(nullable = false)
    private String duracion;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String escritor;

    @Column(nullable = false)
    private String actores;

    @Column(nullable = false)
    private String sinopsis;

    @Column(nullable = false)
    private String idioma;

    @Column(nullable = false)
    private String pais;

    @Column
    private String premios;

    @Column(nullable = false)
    private String imagen;

    @Column(name = "total_temporadas", nullable = false)
    private Integer totalTemporadas;

    @Column(nullable = false)
    private Double evaluacion;

    @Column(name = "total_votos", nullable = false)
    private String totalVotos;

    @Enumerated(EnumType.STRING) // Indica que estamos trabajando con tipos de datos ENUM.
    private Categoria genero;

    @OneToMany(mappedBy = "serie") // Indica que este atributo tiene relacion de uno a muchos con la tabla "episodios".
    private List<Episodio> episodios;

    // Constructor por defecto
    public DatosSerie() {

    }

    public DatosSerie(Serie serie) {
        this.año = serie.año();
        this.titulo = serie.titulo();
        this.calificada = serie.calificada();
        this.fechaPublicacion = serie.fechaPublicacion();
        this.duracion = serie.duracion();
        this.director = serie.director();
        this.escritor = serie.escritor();
        this.actores = serie.actores();

        // Sinopsis traducida
        this.sinopsis = traducirSinopsis(serie.sinopsis()); // Llama al metodo que traduce la sinopsis a español.

        this.idioma = serie.idioma();
        this.pais = serie.pais();
        this.premios = serie.premios();
        this.imagen = serie.imagen();
        this.totalTemporadas = serie.totalTemporadas();

        // Parseando evaluacion a un tipo de dato Double
        this.evaluacion = OptionalDouble.of(Double.parseDouble(serie.evaluacion())).orElse(0);
        this.totalVotos = serie.totalVotos();

        // Parseando atributo genero, de String a un tipo de dato Categoria.
        this.genero = Categoria.fromString(serie.genero().split(",")[0].trim());
    }

    @Override // Indica que estamos sobreescribiendo este metodo.
    public String toString() {
        return "\nDATOS SERIE: " +
                "\nTitulo: " + titulo +
                "\nYear: " + año +
                "\nCalificada: " + calificada +
                "\nFecha de publicacion: " + fechaPublicacion +
                "\nDuracion: " + duracion +
                "\nDirector: " + director +
                "\nEscritor: " + escritor +
                "\nActores: " + actores +
                "\nSinopsis: " + sinopsis +
                "Idioma: " + idioma +
                "\nPais: " + pais +
                "\nPremios: " + premios +
                "\nImagen: " + imagen +
                "\nTotal de temporadas: " + totalTemporadas +
                "\nEvaluacion: " + evaluacion +
                "\nTotal de votos: " + totalVotos +
                "\nGenero: " + genero;
    }

    // Metodo que traduce la sinopsis de cada serie a español por medio de inteligencia artificial (Gemini)
    private String traducirSinopsis(String sinopsisSinTraducir) {
        ConsultaGemini consultaGemini = new ConsultaGemini();
        ResponseData responseData = consultaGemini.enviarSolicitud("Traduce el siguiente " +
                "texto a español: " + sinopsisSinTraducir);
        return responseData.getCandidates().get(0).getContent().getParts().get(0).getText();
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

    public String getYear() {
        return año;
    }

    public void setYear(String año) {
        this.año = año;
    }

    public String getcalificada() {
        return calificada;
    }

    public void setcalificada(String calificada) {
        calificada = calificada;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getEscritor() {
        return escritor;
    }

    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getLenguaje() {
        return idioma;
    }

    public void setLenguaje(String idioma) {
        this.idioma = idioma;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPremios() {
        return premios;
    }

    public void setPremios(String premios) {
        this.premios = premios;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(String totalVotos) {
        this.totalVotos = totalVotos;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }
}
