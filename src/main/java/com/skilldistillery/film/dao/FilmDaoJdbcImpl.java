package com.skilldistillery.film.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;


public class FilmDaoJdbcImpl implements FilmDAO {
	
		private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
		private static final String USERNAME = "student";
		private static final String PASSWORD = "student";
	
		static {
			  try {
			   Class.forName("com.mysql.cj.jdbc.Driver");
			  } catch (ClassNotFoundException e) {
			   e.printStackTrace();
			   System.err.println("Error loading MySQL Driver");
			   throw new RuntimeException("Unable to load MySQL Driver class");
			  }
			 }

		// return null when results were empty
		@Override
		public Film findFilmById(int filmId)  {
			Film film = new Film();
			try {

				Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				
				String sql = "SELECT * FROM film JOIN language ON film.language_id = language.id "
						   + "WHERE film.id = ?";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setInt(1, filmId);
				ResultSet filmResult = stmt.executeQuery();
				
				if (!filmResult.next()) {
					return null; // SIGNAL NO MATCH
				}
				
				film = resultToFilm(filmResult, conn);
				
			    filmResult.close();
			    stmt.close();
			    conn.close();
			} catch(SQLException e) {
				System.out.println(e); // for debug
			}
			
			
			return film;

		}
		

		// the empty list signals no results match
		public List<Film> findFilmBySearchTerm(String searchTerm) {

			
			List<Film> films = new ArrayList<>();
			try {
				Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
				String sql = "SELECT * FROM film JOIN language ON film.language_id = language.id "
				           + "WHERE film.title LIKE ? OR film.description LIKE ?";		
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%" + searchTerm + "%");
				stmt.setString(2, "%" + searchTerm + "%");
				
				ResultSet filmsResult = stmt.executeQuery();
				
				while (filmsResult.next()) {
					films.add(resultToFilm(filmsResult, conn));
				}
				
			    filmsResult.close();
			    stmt.close();
			    conn.close();
				
			} catch(SQLException e) {
				System.out.println(e);  // for debug
			}
			
			return films;
		}

		private Film resultToFilm(ResultSet filmResult, Connection conn) {
			Film film = new Film();
			try {
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getString("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_rate"));
				film.setRentalRate(filmResult.getInt("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getInt("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				
				// get language as string
				film.setReadableLanguage(filmResult.getString("name"));
				

				// populate a list of actors within the film object based on the following query:
				String sql = "SELECT actor.first_name, actor.last_name "
				           + "FROM film_actor JOIN actor ON actor.id = film_actor.actor_id "
						   + "JOIN film ON film.id = film_actor.film_id WHERE film.id = ? ";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1, filmResult.getString("id"));
				
				ResultSet actorsResult = stmt.executeQuery();
				
				List<Actor> actors = new ArrayList<>();
				
				while (actorsResult.next()) {
					Actor actor = new Actor();
					actor.setFirstName(actorsResult.getString("first_name"));
					actor.setLastName(actorsResult.getString("last_name"));
					
					actors.add(actor);
				}
				
				actorsResult.close();
				stmt.close();
				
				film.setActors(actors);
				
				// now we add the category
				String sqlCat = "SELECT category.name\n"
						+ "FROM film \n"
						+ "JOIN film_category ON film.id = film_category.film_id \n"
						+ "JOIN category ON film_category.film_id = category.id \n"
						+ "WHERE film.id = ? ";

				PreparedStatement stmtCat = conn.prepareStatement(sqlCat);
				
				stmtCat.setInt(1, film.getId());
				
				ResultSet category = stmtCat.executeQuery();
				
				if (category.next()) {
					film.setCategory(category.getString("name"));
				}
				
				category.close();
				stmtCat.close();

			} catch (SQLException e) {
				System.out.println(e);
			}
			return film;
		}
		
		// new DB stuff ....
		// this is the guideline / template for h.w. 
		// notice it takes an actor as a parameter
		public Actor createActor(Actor actor) {
			  Connection conn = null;
			  try {
			    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			    conn.setAutoCommit(false); // START TRANSACTION
			    String sql = "INSERT INTO actor (first_name, last_name) "
			                     + " VALUES (?,?)";
			    
			    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			    stmt.setString(1, actor.getFirstName());
			    stmt.setString(2, actor.getLastName());
			    
			    int updateCount = stmt.executeUpdate();
			    
			    if (updateCount == 1) {
			    	
			      ResultSet keys = stmt.getGeneratedKeys();
			      if (keys.next()) {
			    	  
			        int newActorId = keys.getInt(1);
			        
			        actor.setId(newActorId);
			        
			        if (actor.getFilms() != null && actor.getFilms().size() > 0) {
			          sql = "INSERT INTO film_actor (film_id, actor_id) VALUES (?,?)";
			          stmt = conn.prepareStatement(sql);
			          
			          // loop thru films in actor object, 
			          // grab just id, 
			          for (Film film : actor.getFilms()) {
			            stmt.setInt(1, film.getId());
			            stmt.setInt(2, newActorId);
			            updateCount = stmt.executeUpdate();
			          }
			        }
			      }
			    } else {
			      actor = null;  // signal to caller that things went sideways
			    }
			    conn.commit(); // COMMIT TRANSACTION
			  } catch (SQLException sqle) {
			    sqle.printStackTrace();
			    if (conn != null) {
			      try { conn.rollback(); }
			      catch (SQLException sqle2) {
			        System.err.println("Error trying to rollback");
			      }
			    }
			    throw new RuntimeException("Error inserting actor " + actor);
			  }
			  return actor;
			}
		
		public boolean saveActor(Actor actor) {
			  Connection conn = null;
			  try {
			    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			    conn.setAutoCommit(false); // START TRANSACTION
			    
			    String sql = "UPDATE actor SET first_name=?, last_name=? "
			               + " WHERE id=?";
			    
			    PreparedStatement stmt = conn.prepareStatement(sql);
			    
			    stmt.setString(1, actor.getFirstName());
			    stmt.setString(2, actor.getLastName());
			    stmt.setInt(3, actor.getId());
			    
			    int updateCount = stmt.executeUpdate();
			    
			    if (updateCount == 1) {
			      // Replace actor's film list
			      sql = "DELETE FROM film_actor WHERE actor_id = ?";
			      stmt = conn.prepareStatement(sql);
			      stmt.setInt(1, actor.getId());
			      updateCount = stmt.executeUpdate();
			      
			      sql = "INSERT INTO film_actor (film_id, actor_id) VALUES (?,?)";
			      stmt = conn.prepareStatement(sql);
			      
			      for (Film film : actor.getFilms()) {
			        stmt.setInt(1, film.getId());
			        stmt.setInt(2, actor.getId());
			        updateCount = stmt.executeUpdate();
			      }
			      conn.commit();           // COMMIT TRANSACTION
			    }
			  } catch (SQLException sqle) {
			    sqle.printStackTrace();
			    if (conn != null) {
			      try { conn.rollback(); } // ROLLBACK TRANSACTION ON ERROR
			      catch (SQLException sqle2) {
			        System.err.println("Error trying to rollback");
			      }
			    }
			    return false;
			  }
			  return true;
			}
		
		
		public boolean deleteActor(Actor actor) {
			  Connection conn = null;
			  try {
			    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			    
			    conn.setAutoCommit(false); // START TRANSACTION
			    
			    String sql = "DELETE FROM film_actor WHERE actor_id = ?";
			    
			    PreparedStatement stmt = conn.prepareStatement(sql);
			    
			    stmt.setInt(1, actor.getId());
			    
			    // we don't need this? 
			    int updateCount = stmt.executeUpdate();
			    
			    sql = "DELETE FROM actor WHERE id = ?";
			    stmt = conn.prepareStatement(sql);
			    stmt.setInt(1, actor.getId());
			    updateCount = stmt.executeUpdate();
			    
			    conn.commit();             // COMMIT TRANSACTION
			  }
			  
			  catch (SQLException sqle) {
			    sqle.printStackTrace();
			    if (conn != null) {
			      try { conn.rollback(); }
			      catch (SQLException sqle2) {
			        System.err.println("Error trying to rollback");
			      }
			    }
			    return false;
			  }
			  return true;
			}
		
		
		/*
		 * This method currently throws an error if someone tries to add a film
		 * whose title is the same as one already in the database. 
		 * Might want to fix that as a TODO item. 
		 */
		
		public Film createFilm(Film film) {
			  Connection conn = null;
			  try {
			    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			    
			    conn.setAutoCommit(false); // START TRANSACTION
			    
			    
			    String sql = "INSERT INTO film (title, language_id) VALUES (?, ?)";
			    
			    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			    stmt.setString(1, film.getTitle());
			    stmt.setInt(2, film.getLanguageId());
			    
			    int updateCount = stmt.executeUpdate();
			    
			    if (updateCount == 1) {
			    	
			      ResultSet keys = stmt.getGeneratedKeys();
			      
			      if (keys.next()) {
			    	  
			        int newFilmID = keys.getInt(1);
			        
			        film.setId(newFilmID);
			        
			        // if a film needed actors, you would add that here as in createActor method
			      }
			    } else {
			      film = null;  // signal to caller that things went sideways
			    }
			    conn.commit(); // COMMIT TRANSACTION
			  } catch (SQLException sqle) {
			    sqle.printStackTrace();
			    if (conn != null) {
			      try { conn.rollback(); }
			      catch (SQLException sqle2) {
			        System.err.println("Error trying to rollback");
			      }
			    }
			    throw new RuntimeException("Error inserting film " + film);
			  }
			  return film;
			}
		
		public boolean deleteFilm(Film film) {
			  Connection conn = null;
			  try {
			    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			    
			    conn.setAutoCommit(false); // START TRANSACTION
			    
			    String sql = "DELETE FROM film WHERE id = ?";
			    
			    PreparedStatement stmt = conn.prepareStatement(sql);
			    
			    stmt.setInt(1, film.getId());
			    
			    // we don't need this? 
			    int updateCount = stmt.executeUpdate();
			    
			    
			    updateCount = stmt.executeUpdate();
			    
			    conn.commit();             // COMMIT TRANSACTION
			  }
			  
			  catch (SQLException sqle) {
			    sqle.printStackTrace();
			    if (conn != null) {
			      try { conn.rollback(); }
			      catch (SQLException sqle2) {
			        System.err.println("Error trying to rollback");
			      }
			    }
			    return false;
			  }
			  return true;
			}
		
		// this just needs to change over from actor info into film relevant info
		public boolean updateFilm(Film film) {
			  Connection conn = null;
			  try {
			    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			    conn.setAutoCommit(false); // START TRANSACTION
			    
			    String sql = "UPDATE film SET title=?, language_id=? "
			               + " WHERE id=?";
			    
			    PreparedStatement stmt = conn.prepareStatement(sql);
			    
			    stmt.setString(1, film.getTitle());
			    stmt.setInt(2, film.getLanguageId());
			    stmt.setInt(3, film.getId());
			    
			    int updateCount = stmt.executeUpdate();
			    
			    if (updateCount == 1) {

			      conn.commit();           // COMMIT TRANSACTION
			    }
			  } catch (SQLException sqle) {
			    sqle.printStackTrace();
			    if (conn != null) {
			      try { conn.rollback(); } // ROLLBACK TRANSACTION ON ERROR
			      catch (SQLException sqle2) {
			        System.err.println("Error trying to rollback");
			      }
			    }
			    return false;
			  }
			  return true;
			}
		
		// end of class........
	}
		

