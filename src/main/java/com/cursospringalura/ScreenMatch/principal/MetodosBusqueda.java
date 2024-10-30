package com.cursospringalura.ScreenMatch.principal;

import com.cursospringalura.ScreenMatch.autenticacion.DatosAutenticacion;
import com.cursospringalura.ScreenMatch.modelos.*;
import com.cursospringalura.ScreenMatch.servicios.ConsultaOMDb;
import com.cursospringalura.ScreenMatch.conversiondatos.ConvertirDatos;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MetodosBusqueda extends DatosAutenticacion {

    private final static ConvertirDatos convertirDatos = new ConvertirDatos();
    private final static ConsultaOMDb consultaOmdb = new ConsultaOMDb();
    private final static DatosAutenticacion datosAutenticacion = new DatosAutenticacion();
    private final static String urlBase = "https://www.omdbapi.com/?t=";
    private final static String apiKey = datosAutenticacion.getApiKey();
    private final static List<DatosSerie> seriesBuscadas = new ArrayList<>();

    public static Serie buscarDatosGeneralesSerie(String nombreSerie) {
        String url = urlBase + nombreSerie + "&apikey=" + apiKey;

        // Busqueda de los datos generales de la serie
        String json = consultaOmdb.obtenerDatos(url);

        // Procedimiento para convertir el JSON en un objeto Java
        ObjectMapper objectMapper = new ObjectMapper();
        Serie serie = null;
        try {
            // Conversion del JSON a un objeto Java
            serie = objectMapper.readValue(json, Serie.class);

            // Conversion del objeto para almacenarlo con algunas cambios como traduccion de sus datos.
            DatosSerie datosSerie = new DatosSerie(serie);
            seriesBuscadas.add(datosSerie); // Añadir el objeto a una ArrayList.

        } catch (Exception e) {
            System.out.println("\nNo se pudo convertir el JSON de la serie en el objeto solicitado.");
            System.out.println(e.getMessage());
        }
        return convertirDatos.obtenerDatos(json, Serie.class);
    }

    public static List<Temporada> buscarDatosSeriePorTemporada(String nombreSerie) {
        Serie serie = buscarDatosGeneralesSerie(nombreSerie);

        List<Temporada> temporadas = new ArrayList<>();

        try {
            for (int i = 1; i <= serie.totalTemporadas(); i++) {
                String url = consultaOmdb.obtenerDatos(urlBase + nombreSerie + "&Season=" + i + "&apikey=" + apiKey);
                var datosTemporadas = convertirDatos.obtenerDatos(url, Temporada.class);
                temporadas.add(datosTemporadas);
            }
        } catch (NullPointerException e) {
            System.out.println("\nEsta serie contiene datos incompletos.");
            System.exit(0);
        }
        return temporadas;
    }

    public static void verTop5Episodios(String nombreSerie) {
        // Convertir toda la informacion a una lista de tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = buscarDatosSeriePorTemporada(nombreSerie).stream()
                .flatMap(t -> t.episodios().stream()).toList();

        // Mostrar Top 5 episodios de la serie
        System.out.println("\nTOP 5 EPISODIOS CON MEJOR CALIFICACION:");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    public static List<Episodio> verDatosDeTodosLosEpisodios(String nombreSerie) {
        // Convirtiendo los datos a una lista de tipo Episodio
        List<Episodio> episodios = buscarDatosSeriePorTemporada(nombreSerie).stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d)))
                .collect(Collectors.toList());

        return episodios;
    }

    public static void buscarEpisodiosDeUnaSeriePorFecha(String nombreSerie) {
        Scanner sc = new Scanner(System.in);
        int año = 0;

        System.out.print("Indica el año a partir del que quieres buscar los episodios: ");
        try {
            año = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nEl año que ingresaste no es valido.");
        }
        List<Episodio> episodios = verDatosDeTodosLosEpisodios(nombreSerie);
        LocalDate fechaBusqueda = LocalDate.of(año, 1, 1);

        // Formatear la fecha para hacerla apta para latinoamerica en el formato dia/mes/año
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                        "\nTemporada: " + e.getTemporada() +
                                "\nEpisodio: " + e.getNumeroEpisodio() +
                                "\nFecha de lanzamiento: " + e.getFechaLanzamiento().format(dtf)
                ));
    }

    public static void busquedaEpisodioPorFraccionTitulo(String nombreSerie) {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEscribe el episodio que estas buscando: ");
        String episodioBuscado = sc.nextLine();

        // Filtrando la primera coincidencia del titulo encontrado
        Optional<Episodio> episodioEncontrado = verDatosDeTodosLosEpisodios(nombreSerie).stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(episodioBuscado.toUpperCase()))
                .findFirst();

        if (episodioEncontrado.isPresent()) {
            System.out.println("\nEpisodio Encontrado: \n" + episodioEncontrado.get());
        } else {
            System.out.println("\nEl episodio NO fue encontrado.");
        }
    }

    public static void evaluacionesPorTemporada(String nombreSerie) {
        Map<Integer, Double> evaluaciones = verDatosDeTodosLosEpisodios(nombreSerie).stream()
                .filter(e -> e.getEvaluacion() > 0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));

        for (Map.Entry<Integer, Double> entry : evaluaciones.entrySet()) {
            Integer clave = entry.getKey();
            Double valor = entry.getValue();

            System.out.println("\nTemporada: " + clave
                    + "\nCalificacion: " + valor);
        }
    }

    public static void verEstadisticasSerie(String nombreSerie) {
        DoubleSummaryStatistics statistics = verDatosDeTodosLosEpisodios(nombreSerie).stream()
                .filter(e -> e.getEvaluacion() > 0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));

        System.out.println("\nPromedio evaluaciones: " + statistics.getAverage()
                + "\nMaxima calificacion obtenida de un episodio: " + statistics.getMax()
                + "\nCalificacion mas baja obtenida de un episodio: " + statistics.getMin());
    }

    public static void consultasAlmacenadas() {
        if (seriesBuscadas.isEmpty()) {
            System.out.println("\nLa lista de datos esta vacia.");
        } else {
            System.out.println("\nTOTAL DE SERIES ALMACENADAS: " + seriesBuscadas.size());
            seriesBuscadas.forEach(System.out::println);
        }
    }
}
