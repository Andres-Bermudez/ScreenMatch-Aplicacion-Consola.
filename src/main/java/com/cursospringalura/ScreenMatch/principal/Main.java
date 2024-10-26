package com.cursospringalura.ScreenMatch.principal;

import com.cursospringalura.ScreenMatch.autenticacion.DatosAutenticacion;
import com.cursospringalura.ScreenMatch.model.DatosEpisodio;
import com.cursospringalura.ScreenMatch.model.DatosSerie;
import com.cursospringalura.ScreenMatch.model.DatosTemporada;
import com.cursospringalura.ScreenMatch.model.Episodio;
import com.cursospringalura.ScreenMatch.service.ConsumoAPI;
import com.cursospringalura.ScreenMatch.service.ConvertirDatos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main extends DatosAutenticacion {
    private final ConvertirDatos convertirDatos = new ConvertirDatos();
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final DatosAutenticacion datosAutenticacion = new DatosAutenticacion();
    private final String urlBase = "https://www.omdbapi.com/?t=";
    private final String apiKey = datosAutenticacion.getApiKey();

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int seleccionUsuario = - 1;
        String menuPrincipal = """
                               \n:::::::::::::::::::::: Inicio ::::::::::::::::::::::
                                    1. Buscar los datos generales de una serie.
                                    2. Buscar los datos de todas las temporadas de una serie.
                                    3. Buscar Top 5 de los episodios de una serie.
                                    4. Buscar los datos de todos los episodios de todas las temporadas de una serie.
                                    5. Buscar episodios de una serie por fecha.
                                    6. Buscar un episodio de una serie por la primera coincidencia encontrada.
                                    0. Salir
                               """;
        while (seleccionUsuario < 0 || seleccionUsuario > 6) {
            System.out.print(menuPrincipal + "\nTu eleccion: ");
            try {
                 seleccionUsuario = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nEsta opcion no esta disponible.");
            }
        }

        switch (seleccionUsuario) {
            case 0:
                System.exit(0);
                break;
            case 1:
                System.out.println(buscarDatosGeneralesSerie(tomarNombreSerie()));
                break;
            case 2:
                List<DatosTemporada> temporadas = buscarDatosSeriePorTemporada(tomarNombreSerie());
                temporadas.forEach(System.out::println);
                break;
            case 3:
                verTop5Episodios(tomarNombreSerie());
                break;
            case 4:
                List<Episodio> episodios = verDatosDeTodosLosEpisodios(tomarNombreSerie());
                episodios.forEach(System.out::println);
                break;
            case 5:
                buscarEpisodiosDeUnaSeriePorFecha();
                break;
            case 6:
                busquedaEpisodioPorFraccionTitulo();
                break;
            default:
                System.out.println("\nLa opcion que elegiste no existe");
                break;
        }
    }

    private String tomarNombreSerie() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEscribe el nombre de la serie que estas buscando: ");
        String nombreSerie = sc.nextLine().trim().replace(" ", "+");
        System.out.println("\nBuscando Serie.....");

        return nombreSerie;
    }

    private DatosSerie buscarDatosGeneralesSerie(String nombreSerie) {
        String url = urlBase + nombreSerie + "&apikey=" + apiKey;

        // Usando una expresion lambda para mostrar los titulos de los episodios
        // temporadas.forEach(T -> T.episodios().forEach(E -> System.out.println(E.titulo())));

        // Busqueda de los datos generales de la serie
        String json = consumoAPI.obtenerDatos(url);
        DatosSerie datosSerie = convertirDatos.obtenerDatos(json, DatosSerie.class);

        return datosSerie;
    }

    private List<DatosTemporada> buscarDatosSeriePorTemporada(String nombreSerie) {
        List<DatosTemporada> temporadas = new ArrayList<>();

        DatosSerie datosSerie = buscarDatosGeneralesSerie(nombreSerie);
        try {
            for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
                String url = consumoAPI.obtenerDatos(urlBase + nombreSerie + "&Season=" + i + "&apikey=" + apiKey);
                var datosTemporadas = convertirDatos.obtenerDatos(url, DatosTemporada.class);
                temporadas.add(datosTemporadas);
            }

        } catch (NullPointerException e) {
            System.out.println("\nEsta serie contiene datos incompletos.");
            System.exit(0);
        }
        return temporadas;
    }

    private void verTop5Episodios(String nombreSerie) {
        List<DatosTemporada> temporadas = buscarDatosSeriePorTemporada(nombreSerie);

        System.out.println("\nTOP 5 EPISODIOS CON MEJOR CALIFICACION:");
        // Convertir toda la informacion a una lista de tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        // Mostrar Top 5 episodios de la serie
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    private List<Episodio> verDatosDeTodosLosEpisodios(String nombreSerie) {
        System.out.println("\nBUSCANDO DATOS DE LOS EPISODIOS DE LA SERIE:");

        List<DatosTemporada> temporadas = buscarDatosSeriePorTemporada(nombreSerie);

        // Convirtiendo los datos a una lista de tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d)))
                .collect(Collectors.toList());

        return episodios;
    }

    private void buscarEpisodiosDeUnaSeriePorFecha() {
        Scanner sc = new Scanner(System.in);
        int año = 0;

        System.out.println("\nBUSCANDO DATOS DE LA SERIE POR FECHA...");
        System.out.print("\nIndica el año a partir del que quieres buscar los episodios: ");
        try {
            año = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nEl año que ingresaste no es valido.");
        }
        List<Episodio> episodios = verDatosDeTodosLosEpisodios(tomarNombreSerie());
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

    private void busquedaEpisodioPorFraccionTitulo() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEscribe el episodio que estas buscando: ");
        String episodioBuscado = sc.nextLine();

        List<Episodio> episodios = verDatosDeTodosLosEpisodios(tomarNombreSerie());

        // Filtrando la primera coincidencia del titulo encontrado
        Optional<Episodio> episodioEncontrado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(episodioBuscado.toUpperCase()))
                .findFirst();

        if (episodioEncontrado.isPresent()) {
            System.out.println("\nEpisodio Encontrado: \n" + episodioEncontrado.get());
        } else {
            System.out.println("\nEl episodio NO fue encontrado.");
        }
    }
}
