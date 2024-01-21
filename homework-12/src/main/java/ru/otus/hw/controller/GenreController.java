package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public String genresList(Model model) {
        List<Genre> genres = genreService.findAll();
        System.out.println("gebres: " + genres);
        GenreDto genreDto = new GenreDto();
        model.addAttribute("genres", genres);
        model.addAttribute("genreDto", genreDto);
        return "genres";
    }

    @PostMapping("/genres")
    public String createGenre(@ModelAttribute("genreDto") @Valid GenreDto genre,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Genre> genres = genreService.findAll();
            model.addAttribute("genres", genres);
            return "genres";
        }
        genreService.insert(genre.getName());
        return "redirect:/genres";
    }

    @GetMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable("id") long id, Model model) {
        genreService.deleteById(id);
        return "redirect:/genres";
    }
}
