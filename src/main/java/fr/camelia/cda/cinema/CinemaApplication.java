package fr.camelia.cda.cinema;



import fr.camelia.cda.cinema.dao.FilmDao;
import fr.camelia.cda.cinema.model.Film;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean

    public CommandLineRunner runner(FilmDao dao){
        return new CommandLineRunner() {
            @Override
            @Transactional
            public void run(String... args) throws Exception {
                Iterable<Film> films = dao.findAll();
                for (Film film: films
                ) {
                    System.out.println(film.getTitle()+ " réalisé par "+film.getRealisateur().getSurname());
                }
                Optional<Film> film = dao.findById(1L);
                System.out.println(film.get().getTitle());
            }
        };
    }

}


