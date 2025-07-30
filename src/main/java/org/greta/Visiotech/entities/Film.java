package org.greta.Visiotech.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Film {
    private Long id;
    @NotBlank(message = "Le nom du film ne peut pas être vide")
    private String title;
    @NotBlank(message = "La description du film ne peut pas être vide")
    private String description;
    @NotNull
    private Integer releaseYear;
    @NotNull
    @Min(value = 1, message ="Le film doit faire au moins 1minute")
    private Integer durationMinutes;
    @NotNull
    private String language;
    @NotNull
    private String posterUrl;

    public Film() {}

    public Film(Long id, String title, String description, Integer releaseYear,
                Integer durationMinutes, String language, String posterUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.durationMinutes = durationMinutes;
        this.language = language;
        this.posterUrl = posterUrl;
    }

    // getters / setters pour tous les champs

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
}
