package org.greta.Visiotech.controllers;

import jakarta.validation.Valid;
import org.greta.Visiotech.daos.FilmDAO;
import org.greta.Visiotech.entities.Film;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmDAO filmDAO;

    public FilmController(FilmDAO filmDAO){
        this.filmDAO = filmDAO;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(filmDAO.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        return ResponseEntity.ok(filmDAO.findById(id));
    }
    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        Film createdFilm = filmDAO.save(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @RequestBody Film film) {
        Film updatedFilm = filmDAO.update(id, film);
        return ResponseEntity.ok(updatedFilm);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        if (filmDAO.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
