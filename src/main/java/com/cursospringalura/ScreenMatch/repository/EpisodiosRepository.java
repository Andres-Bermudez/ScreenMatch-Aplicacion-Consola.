package com.cursospringalura.ScreenMatch.repository;

import com.cursospringalura.ScreenMatch.modelos.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodiosRepository extends JpaRepository<Episodio, Long> {

}
