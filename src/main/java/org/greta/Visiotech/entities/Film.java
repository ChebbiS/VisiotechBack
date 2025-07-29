package org.greta.Visiotech.entities;

public class Film {
    private Long filmId;
    private String title;
    private String synopsis;
    private String date;
    private String url;

    public Film() {}
    public Film(Long filmId, String title, String synopsis, String date, String url) {
        this.filmId = filmId;
        this.title = title;
        this.synopsis = synopsis;
        this.date = date;
        this.url = url;
    }



    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}