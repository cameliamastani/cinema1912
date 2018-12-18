package fr.camelia.cda.cinema.web;

import fr.camelia.cda.cinema.dao.FilmDao;
import fr.camelia.cda.cinema.model.Film;
import fr.camelia.cda.cinema.service.ImageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/film")
public class FilmController {
    @Autowired
    FilmDao filmDao;

    @Autowired
    ImageManager imm;

    @GetMapping("/list")
    public String list(Model model){
        Iterable<Film> films = filmDao.findAll();
        model.addAttribute("films", films);
        return "film/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id")long id, Model model){
        model.addAttribute("film", filmDao.findById(id).get());
        return "film/detail";
    }

    @GetMapping("/mod/{id}")
    public String mod(@PathVariable("id")long id, Model model){
        model.addAttribute("film", filmDao.findById(id).get());
        return "film/form";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("film", new Film());
        return "film/form";
    }

    @PostMapping("/add")
    public String submit(@RequestParam("poster")MultipartFile file, @ModelAttribute Film film){
        if(file.getContentType().equalsIgnoreCase("image/jpeg")){
            try {
                imm.savePoster(film, file.getInputStream());
            } catch (IOException ioe){
                System.out.println("Erreur lecture : "+ioe.getMessage());
            }
        }
        filmDao.save(film);

        return "redirect:index";
    }

}

