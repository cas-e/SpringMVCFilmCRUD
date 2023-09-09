package com.skilldistillery.film.dao;

import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;


public interface FilmDAO {
	  public Film findFilmById(int filmId);
	  public List<Film> findFilmBySearchTerm(String searchTerm);
	 
	  public Actor createActor(Actor actor);
	  public boolean saveActor(Actor actor);
	  public boolean deleteActor(Actor actor);
	  
	  public Film createFilm(Film film);
	  public boolean deleteFilm(Film film);
	  public boolean updateFilm(Film film);
	  
}
