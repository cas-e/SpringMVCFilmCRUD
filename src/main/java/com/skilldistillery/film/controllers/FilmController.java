package com.skilldistillery.film.controllers;

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

	/*
	 * I could not get this method to work... I don't understand the
	 * Model.addAtrribute()
	 */
//	@RequestMapping(path = "getFilm.do", method=RequestMethod.GET)
//	public String getFilm(@RequestParam Integer filmId, Model model) {
//		System.out.println("filmId:" + filmId);
//		
//		model.addAttribute("filmId:" + filmId);	
//		return "WEB-INF/film.jsp";
//
	/*
	 * This method is like the one we used in USstates, it seems to be working.
	 */
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
			mv.addObject("filmId", notFound);
		}

		return mv;
	}

	@RequestMapping(path = "createFilm.do", params = "filmTitle", method = RequestMethod.GET)
	public ModelAndView createFilm(String filmTitle) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/createFilm.jsp");

		Film film = new Film();
		String notFound = "Sorry, the film with the ID: " + filmTitle + " does not exist in our records.";

		film.setTitle(filmTitle);
		film.setLanguageId(1);
		
		
		filmDao.createFilm(film);

		mv.addObject("film", film);
		
		System.out.println("I RAN");
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
}
