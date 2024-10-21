package pjatk.tpo.webapp.jdbc;

import pjatk.tpo.webapp.models.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private static final String URL = "jdbc:oracle:thin:@//db-oracle02.pjwstk.edu.pl:1521/baza.pjwstk.edu.pl";
    private static final String USER = "s28610";
    private static final String PASSWORD = "oracle12";

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            String query = "SELECT m.idMovie, m.title, m.release_year, m.rating, g.name as genre, c.name as country FROM Movie m " +
                    "INNER JOIN Country c ON m.idCountry = c.idCountry " +
                    "INNER JOIN Movie_Genre mg ON m.idMovie = mg.idMovie " +
                    "INNER JOIN Genre g ON mg.idGenre = g.idGenre ";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("idMovie");
                String title = resultSet.getString("title");
                String country = resultSet.getString("country");
                int releaseYear = resultSet.getInt("release_year");
                String genre = resultSet.getString("genre");
                double rating = resultSet.getDouble("rating");
                //System.out.println(id + " " + title + " " + country + " " + releaseYear + " " + genre + " " + rating + " ");
                boolean isExist = false;
                for (Movie movie : movies) {
                    if (movie.getId() == id) {
                        isExist = true;
                    }
                }
                if (!isExist)
                    movies.add(new Movie(id, title, releaseYear, rating, genre, country));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> searchMovies(String keyword, String country, String genre, Integer releaseYear, String sortBy) {
        List<Movie> movies = new ArrayList<>();
        StringBuilder query = new StringBuilder( "SELECT m.idMovie, m.title, m.release_year, m.rating, g.name as genre, c.name as country FROM Movie m " +
                "INNER JOIN Country c ON m.idCountry = c.idCountry " +
                "INNER JOIN Movie_Genre mg ON m.idMovie = mg.idMovie " +
                "INNER JOIN Genre g ON mg.idGenre = g.idGenre " +
                "WHERE 1=1");

        List<Object> parameters = new ArrayList<>();

        if(!keyword.isEmpty()) {
            query.append(" AND (m.title LIKE '" + keyword + "%' OR m.title LIKE '" + keyword.toUpperCase() + "%')" );
        }

        if (!country.isEmpty()) {
            query.append(" AND c.name LIKE ?");
            parameters.add("%" + country + "%");
        }

        if (!genre.isEmpty()) {
            query.append(" AND g.name LIKE ?");
            parameters.add("%" + genre + "%");
        }

        if (releaseYear != 0) {
            query.append(" AND m.release_year = ?");
            parameters.add(releaseYear);
        }

        if (!sortBy.isEmpty()) {
            System.out.println(sortBy);
            sortBy = (sortBy.equals("Rating") ? "rating" : "release_year");
            query.append(" ORDER BY ").append(sortBy).append(" DESC");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("idMovie");
                    String title = resultSet.getString("title");
                    String countryResult = resultSet.getString("country");
                    int releaseYearResult = resultSet.getInt("release_year");
                    String genreResult = resultSet.getString("genre");
                    double rating = resultSet.getDouble("rating");
                    //System.out.println(id + " " + title + " " + countryResult + " " + releaseYearResult + " " + genreResult + " " + rating);
                    boolean isExist = false;
                    for (Movie movie : movies) {
                        if (movie.getId() == id) {
                            isExist = true;
                        }
                    }
                    if (!isExist)
                        movies.add(new Movie(id, title, releaseYearResult, rating, genreResult, countryResult));                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}
