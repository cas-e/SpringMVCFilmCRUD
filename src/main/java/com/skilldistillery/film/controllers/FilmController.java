package com.skilldistillery.film.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.dao.FilmDAO;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {

	@Autowired
	private FilmDAO filmDao;


	@RequestMapping(path = "getFilm.do", params = "filmId", method = RequestMethod.GET)
	public ModelAndView getFilm(String filmId) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/film.jsp");

		Film film = null;
		String notFound = "Sorry, the film with the ID: " + filmId + " does not exist in our records.";

		try {
			int id = Integer.parseInt(filmId);
			film = filmDao.findFilmById(id);

		} catch (Exception e) {
			// if we can't parse the filmId, film remains null.
		}

		if (film != null) {
			mv.addObject("filmId", film);
		} else {
			mv.addObject("errorMessage", notFound);
		}

		return mv;
	}

	@RequestMapping(path = "createFilm.do", method = RequestMethod.GET, 
			params = {"filmTitle", "filmDesc", "filmYear", "rentalDuration", "filmLength"})
	public ModelAndView createFilm(String filmTitle,
			String filmDesc,
			String filmYear,
			int rentalDuration,
			int filmLength) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/createSuccess.jsp");

		Film film = new Film();
		film.setLanguageId(1);  // hard code language
		
		film.setTitle(filmTitle);
		
		film.setDescription(filmDesc);
		
		film.setReleaseYear(filmYear);
		
		film.setRentalDuration(rentalDuration);
		
		film.setLength(filmLength);
		// okay, all there is to do now is do this for every single field.
		// it's just typing. I can do it. 
		
		filmDao.createFilm(film);
		
		return mv;
	}

	@RequestMapping(path = "createFilmForm.do")
	public ModelAndView createFilmForm() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/createFilm.jsp");
		


		return mv;
	}
	
	@RequestMapping(path = "editFilmForm.do", params="filmId")
	public ModelAndView editFilmForm(int filmId) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/editFilm.jsp");

		// select film to edit
		Film film = filmDao.findFilmById(filmId);
		
		mv.addObject("filmObj", film);
		
		return mv;
	}
	
	// called from editFilmForm page to actually update the film
	@RequestMapping(path = "changeFilm.do", params={"filmId", "filmTitle", "filmDesc", "filmYear",
			"filmLength", "filmReplacementCost"})
	public ModelAndView changeFilm(int filmId, String filmTitle, String filmDesc,
			String filmYear, int filmLength, int filmReplacementCost) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/editSuccess.jsp");

		
		
		// okay, now we create a new film object...
		Film film = new Film();
		film.setId(filmId);
		film.setTitle(filmTitle);
		film.setLanguageId(1); // hard code for now
		film.setDescription(filmDesc);
		film.setReleaseYear(filmYear);
		film.setLength(filmLength);
		film.setReplacementCost(filmReplacementCost);
		
		// do the update
		
		boolean updated = filmDao.updateFilm(film);
		
		if (!updated) {
			System.out.println(filmDesc);
		} else {
			System.out.println(filmReplacementCost);
		}
		return mv;
	}
	
	@RequestMapping(path = "deleteFilm.do", params = "filmId", method = RequestMethod.GET)
	public ModelAndView deleteFilm(int filmId) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/deleteFilm.jsp");

		Film film = new Film();
		

		film.setId(filmId);
		
		filmDao.deleteFilm(film);
		
		mv.addObject("filmId", filmId);

		return mv;
	}
	
	@RequestMapping(path = "searchFilms.do", params ="searchTerm", method = RequestMethod.GET)
	public ModelAndView searchFilmForm(String searchTerm) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/searchFilm.jsp");
		List<Film> films = filmDao.findFilmBySearchTerm(searchTerm);
		
		mv.addObject("searchResults", films);
		return mv;
	}
	
}
