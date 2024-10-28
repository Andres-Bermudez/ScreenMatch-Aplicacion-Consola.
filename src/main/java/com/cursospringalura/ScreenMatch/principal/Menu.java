package com.cursospringalura.ScreenMatch.principal;

import com.cursospringalura.ScreenMatch.autenticacion.DatosAutenticacion;
import com.cursospringalura.ScreenMatch.model.DatosTemporada;
import com.cursospringalura.ScreenMatch.model.Episodio;
import java.util.*;

public class Menu extends DatosAutenticacion {

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
                                    7. Ver calificaciones de las temporadas de una serie.
                                    8. Ver estadisticas de una serie.
                                    0. Salir
                               """;
        while (seleccionUsuario < 0 || seleccionUsuario > 8) {
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
                System.out.println("\nBuscando serie.....");
                System.out.println(MetodosBusqueda.buscarDatosGeneralesSerie(tomarNombreSerie()));
                break;
            case 2:
                System.out.println("\nBuscando datos de las temporadas de la serie.....");
                List<DatosTemporada> temporadas = MetodosBusqueda.buscarDatosSeriePorTemporada(tomarNombreSerie());
                temporadas.forEach(System.out::println);
                break;
            case 3:
                System.out.println("\nBuscando top 5 episodios de una serie.....");
                MetodosBusqueda.verTop5Episodios(tomarNombreSerie());
                break;
            case 4:
                System.out.println("\nBuscando datos de todos los episodios de la serie.....");
                List<Episodio> episodios = MetodosBusqueda.verDatosDeTodosLosEpisodios(tomarNombreSerie());
                episodios.forEach(System.out::println);
                break;
            case 5:
                System.out.println("\nBuscando datos de una serie por fecha.....");
                MetodosBusqueda.buscarEpisodiosDeUnaSeriePorFecha(tomarNombreSerie());
                break;
            case 6:
                System.out.println("\nBuscando primera coincidencia en la serie.....");
                MetodosBusqueda.busquedaEpisodioPorFraccionTitulo(tomarNombreSerie());
                break;
            case 7:
                System.out.println("\nBuscando calificacion de la serie.....");
                MetodosBusqueda.evaluacionesPorTemporada(tomarNombreSerie());
                break;
            case 8:
                System.out.println("\nCalculando datos de la serie.....");
                MetodosBusqueda.verEstadisticasSerie(tomarNombreSerie());
                break;
            default:
                System.out.println("\nLa opcion que elegiste no existe");
                break;
        }
    }

    private String tomarNombreSerie() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Escribe el nombre de la serie que estas buscando: ");
        return sc.nextLine().trim().replace(" ", "+");
    }
}
