package com.cursospringalura.ScreenMatch.menu;

import com.cursospringalura.ScreenMatch.autenticacion.DatosAutenticacion;
import com.cursospringalura.ScreenMatch.model.DatosEpisodio;
import com.cursospringalura.ScreenMatch.model.DatosSerie;
import com.cursospringalura.ScreenMatch.model.DatosTemporada;
import com.cursospringalura.ScreenMatch.model.Episodio;
import com.cursospringalura.ScreenMatch.service.ConsumoAPI;
import com.cursospringalura.ScreenMatch.service.ConvertirDatos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu extends DatosAutenticacion {

    public static void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        ConvertirDatos convertirDatos = new ConvertirDatos();
        ConsumoAPI consumoAPI = new ConsumoAPI();
        DatosAutenticacion datosAutenticacion = new DatosAutenticacion();

        String urlBase = "https://www.omdbapi.com/?t=";
        String apiKey = datosAutenticacion.getApiKey();

        System.out.println(":::::::::::::::::::::: Inicio ::::::::::::::::::::::");
        System.out.print("\nEscribe el nombre de la serie que estas buscando: ");
        String nombreSerie = sc.nextLine().trim().replace(" ", "+");

        System.out.println("\nBuscando Serie.....\n");

        String url = urlBase + nombreSerie + "&apikey=" + apiKey;

        // Busqueda de los datos generales de la serie
        String json = consumoAPI.obtenerDatos(url);
        DatosSerie datosSerie = convertirDatos.obtenerDatos(json, DatosSerie.class);

        System.out.println(datosSerie + "\n");

        // Obtener datos de la serie por temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();

        try {
            for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
                url = consumoAPI.obtenerDatos(urlBase + nombreSerie + "&Season=" + i + "&apikey=" + apiKey);
                var datosTemporadas = convertirDatos.obtenerDatos(url, DatosTemporada.class);
                temporadas.add(datosTemporadas);
            }
            System.out.println("DATOS POR TEMPORADA:");
            temporadas.forEach(System.out::println);

        } catch (NullPointerException e) {
            System.out.println("\nEsta serie contiene datos incompletos.");
            System.exit(0);
        }
        System.out.println();

        // Imprimir todos los titulos de todas las temporadas
        /*
        for (int i = 0; i < datosSerie.totalTemporadas(); i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();

            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }
        */

        // Usando una expresion lambda para mostrar los titulos de los episodios
        // temporadas.forEach(T -> T.episodios().forEach(E -> System.out.println(E.titulo())));

        // Convertir toda la informacion a una lista de tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        // Top 5 episodios
        System.out.println("TOP 5 EPISODIOS");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);

        // Convirtiendo los datos a una lista de tipo Episodio
        System.out.println("DATOS POR EPISODIO");
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }
}
