package com.cursospringalura.ScreenMatch;

import com.cursospringalura.ScreenMatch.principal.Menu;
import com.cursospringalura.ScreenMatch.repository.DatosSerieRepository;
import com.cursospringalura.ScreenMatch.repository.EpisodiosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	// En este caso es usada para inyectar dependencias de la interfaz "DatosSerieRepository"
	@Autowired // Indica inyeccion de dependencias automatica. Debe usarse en una clase creada por Spring.
	private DatosSerieRepository datosSerieRepository;
	private EpisodiosRepository episodiosRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu menu = new Menu(datosSerieRepository, episodiosRepository);
		while (true) {
			menu.mostrarMenu();
		}
	}
}
