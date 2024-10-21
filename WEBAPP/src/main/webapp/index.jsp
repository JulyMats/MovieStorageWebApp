<%@ page import="pjatk.tpo.webapp.models.Movie" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Movie search app</title>
    <link rel="stylesheet" href="./css/style.css"/>
</head>
<body>
    <header>
        <form id="form" method="get" action="movies">
            <input
                    name="keywords"
                    type="text"
                    id="search"
                    placeholder="Search"
                    class="search"
                    value="<%= request.getAttribute("keywords") != null ? request.getAttribute("keywords") : "" %>"
            />

            <select data-trigger name="country" class="select" id="country-select">
                <option class="initial-select" placeholder value>Сountry</option>
                <option <%= "USA".equals(request.getAttribute("country")) ? "selected" : "" %>>USA</option>
                <option <%= "UK".equals(request.getAttribute("country")) ? "selected" : "" %>>UK</option>
                <option <%= "France".equals(request.getAttribute("country")) ? "selected" : "" %>>France</option>
                <option <%= "Japan".equals(request.getAttribute("country")) ? "selected" : "" %>>Japan</option>
                <option <%= "India".equals(request.getAttribute("country")) ? "selected" : "" %>>India</option>
            </select>

            <select data-trigger name="genre" class="select" id="genre-select">
                <option class="initial-select" placeholder value>Genre</option>
                <option <%= "Action".equals(request.getAttribute("genre")) ? "selected" : "" %>>Action</option>
                <option <%= "Comedy".equals(request.getAttribute("genre")) ? "selected" : "" %>>Comedy</option>
                <option <%= "Drama".equals(request.getAttribute("genre")) ? "selected" : "" %>>Drama </option>
                <option <%= "Thriller".equals(request.getAttribute("genre")) ? "selected" : "" %>>Thriller</option>
                <option <%= "Science Fiction".equals(request.getAttribute("genre")) ? "selected" : "" %>>Science Fiction</option>
            </select>

            <select data-trigger name="ages" class="select" id="ages-select">
                <option class="initial-select" placeholder value>Ages</option>
                <option <%= "2024".equals(request.getAttribute("ages")) ? "selected" : "" %>>2024</option>
                <option <%= "2023".equals(request.getAttribute("ages")) ? "selected" : "" %>>2023</option>
                <option <%= "2022".equals(request.getAttribute("ages")) ? "selected" : "" %>>2022</option>
                <option <%= "2021".equals(request.getAttribute("ages")) ? "selected" : "" %>>2021</option>
                <option <%= "2010".equals(request.getAttribute("ages")) ? "selected" : "" %>>2010</option>
            </select>

            <select data-trigger name="sort-by" class="select" id="sort-by-select">
                <option class="initial-select" placeholder value>Sort by</option>
                <option <%= "Release year".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Release year</option>
                <option <%= "Rating".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Rating</option>
            </select>
        </form>
    </header>
    <main id="main">
        <%
            List<Movie> movies = (List<Movie>) request.getAttribute("movies");
            if (movies != null && !movies.isEmpty()) {
        %>
            <%
                for (Movie movie : movies) {
            %>
                <div class="movie">
                    <img
                        src=""
                        alt="<%= movie.getTitle() + "&y=" + movie.getReleaseYear()%>"
                    />
                    <div class="movie-info">
                        <h3><%= movie.getTitle() %></h3>
                        <span class=""><%= movie.getRating() %></span>
                    </div>
                    <div class="overview">
                        <h3>Overview:</h3>
                        <p class="overview-info"></p>
                        <h4><%= movie.getCountry() %>, <%= movie.getReleaseYear() %></h4>
                        <h4>genre: <%= movie.getGenre() %></h4>
                    </div>
                </div>
            <%
                }
            %>
        <%
            } else {
        %>
                <p>No results found.</p>
        <%
            }
        %>
    </main>
    <script src="js/main.js"></script>
</body>
</html>
