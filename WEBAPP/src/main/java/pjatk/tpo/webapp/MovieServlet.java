package pjatk.tpo.webapp;

import java.io.*;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pjatk.tpo.webapp.jdbc.MovieDAO;
import pjatk.tpo.webapp.models.Movie;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {
    private MovieDAO database;

    @Override
    public void init() throws ServletException {
        super.init();
        database = new MovieDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String keywords = request.getParameter("keywords");
        String country = request.getParameter("country");
        String genre = request.getParameter("genre");
        String ages = request.getParameter("ages");
        String sortBy = request.getParameter("sort-by");

        //System.out.println(country + " " + genre + " " + ages + " " + sortBy);
        List<Movie> movies;

        if (keywords!= null || country != null || genre != null || ages != null || sortBy != null) {
            movies = database.searchMovies(keywords, country, genre, (ages.isEmpty() ? 0 : Integer.parseInt(ages)), sortBy);
        } else {
            movies = database.getAllMovies();
        }

        /*JSONArray jsonArray = new JSONArray();

        for (Movie movie : movies) {
            JSONObject jsonMovie = new JSONObject();
            jsonMovie.put("id", movie.getId());
            jsonMovie.put("title", movie.getTitle());
            jsonMovie.put("country", movie.getCountry());
            jsonMovie.put("releaseYear", movie.getReleaseYear());
            jsonMovie.put("genre", movie.getGenre());
            jsonMovie.put("rating", movie.getRating());
            jsonArray.add(jsonMovie);
        }*/

        request.setAttribute("keywords", keywords);
        request.setAttribute("country", country);
        request.setAttribute("genre", genre);
        request.setAttribute("ages", ages);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("movies", movies);
        request.getRequestDispatcher("/index.jsp").forward(request, response);

        /*response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(movies);
        out.flush();*/
    }
}
