package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("/authors")
    public String authorsList(Model model) {
        List<Author> authors = authorService.findAll();
        AuthorDto authorDto = new AuthorDto();
        model.addAttribute("authors", authors);
        model.addAttribute("authorDto", authorDto);
        return "authors";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id, Model model) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

    @PostMapping("/authors")
    public String createAuthor(@ModelAttribute("authorDto") @Valid AuthorDto author,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Author> authors = authorService.findAll();
            model.addAttribute("authors", authors);
            return "authors";
        }
        authorService.insert(author.getFullName());
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit/{id}")
    public String editAuthor(@PathVariable("id") long id, Model model) {
        Author author = authorService.findById(id).orElseThrow(() -> new NotFoundException("Author not found"));
        model.addAttribute("author", AuthorDto.fromDomainObject(author));
        return "edit-author";
    }

    @PostMapping("/authors/edit/{id}")
    public String updateAuthor(@ModelAttribute("author") @Valid AuthorDto authorDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-author";
        }

        authorService.update(authorDto.getId(), authorDto.getFullName());
        return "redirect:/authors";
    }
}
