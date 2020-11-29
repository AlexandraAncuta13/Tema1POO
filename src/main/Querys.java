package main;

import actor.ActorsAwards;
import fileio.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Querys {

    /**
     * Returnez topul actorilor in sortati in functie de rating-ul
     * filmelor si serialelor in care au jucat
     */

    public String average(final Input input, final int number, final String sortType) {
        ArrayList<ActorInputData> actors = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        String title;
        double totalRating;
        int countRatings;
        for (int i = 0; i < input.getActors().size(); i++) {
            totalRating = 0;
            countRatings = 0;
            for (int j = 0; j < input.getActors().get(i).getFilmography().size(); j++) {
                title = input.getActors().get(i).getFilmography().get(j);
                for (int k = 0; k < input.getMovies().size(); k++) {
                    if (input.getMovies().get(k).getTitle().equals(title)) {
                        if (input.getMovies().get(k).getRatingsMean() != 0) {
                            totalRating += input.getMovies().get(k).getRatingsMean();
                            countRatings++;
                        }
                    }
                }
                for (int k = 0; k < input.getSerials().size(); k++) {
                    if (input.getSerials().get(k).getTitle().equals(title)) {
                        if (input.getSerials().get(k).getSerialRatingsMean() != 0) {
                            totalRating += input.getSerials().get(k).getSerialRatingsMean();
                            countRatings++;
                        }
                    }
                }
            }
            if (countRatings != 0) {
                input.getActors().get(i).setRating(totalRating / countRatings);
            }
            actors.add(input.getActors().get(i));
        }

        actors.sort(new SortByRatings());

        if (sortType.equals("desc")) {
            Collections.reverse(actors);
        }
        int j = 0;
        while (result.size() < number && j < actors.size()) {
            if (actors.get(j).getRating() > 0) {
                result.add(actors.get(j).getName());
            }
            j++;
        }
        return "Query result: " + result;
    }

    /**
     * Returnez toul actorilor in functie de numaul total de premii obtinute in cariera
     */

    public String ascAwards(final Input input, final List<String> awardsList,
                            final String sortType) {
        ActorsAwards award;
        ArrayList<ActorInputData> actors = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < input.getActors().size(); i++) {
            int ok = 0;
            for (String s : awardsList) {
                award = Utils.stringToAwards(s);
                if (input.getActors().get(i).getAwards().containsKey(award)) {
                    ok++;
                } else {
                    break;
                }
            }
            if (ok == awardsList.size()) {
                actors.add(input.getActors().get(i));
            }
        }
        actors.sort(new SortByAwards());

        if (sortType.equals("desc")) {
            Collections.reverse(actors);
        }

        for (ActorInputData actor : actors) {
            result.add(actor.getName());
        }
        return "Query result: " + result;
    }

    /**
     * Returnez toti actorii in a caror descriere se regasesc toate cuvintele cerute
     */

    public String filterDescription(final Input input, final List<String> keywords,
                                    final String sortType) {
        ArrayList<String> actors = new ArrayList<>();
        int ok;
        for (int i = 0; i < input.getActors().size(); i++) {
            ok = 0;
            for (String keyword : keywords) {
                if (input.getActors().get(i).getCareerDescription().toLowerCase()
                        .contains(" " + keyword.toLowerCase() + " ")
                        || input.getActors().get(i).getCareerDescription().toLowerCase()
                        .contains("-" + keyword.toLowerCase() + " ")
                        || input.getActors().get(i).getCareerDescription().toLowerCase()
                        .contains(" " + keyword.toLowerCase() + "-")
                        || input.getActors().get(i).getCareerDescription().toLowerCase()
                        .contains(" " + keyword.toLowerCase() + ",")
                        || input.getActors().get(i).getCareerDescription().toLowerCase()
                        .contains(" " + keyword.toLowerCase() + ".")) {
                    ok++;
                } else {
                    break;
                }
            }
            if (ok == keywords.size()) {
                actors.add(input.getActors().get(i).getName());
            }
        }
        Collections.sort(actors);
        if (sortType.equals("desc")) {
            Collections.reverse(actors);
        }
        return "Query result: " + actors;
    }

    /**
     * Topul filmelor sortate crescator sau descrescator in functie de
     * rating-urile oferite de utilizatori
     */

    public String ascmovieRatings(final Input input, final int number, final String s,
                                  final String genre, final String sortType) {
        int ok = 1, ok2 = 1, year = 0;
        ArrayList<String> result = new ArrayList<>();
        ArrayList<MovieInputData> movies = new ArrayList<>();
        if (s == null) {
            ok = 0;
        } else {
            year = Integer.parseInt(s);
        }
        if (genre == null) {
            ok2 = 0;
        }
        for (int i = 0; i < input.getMovies().size(); i++) {
            if (ok == 1) {
                if (input.getMovies().get(i).getYear() == year) {
                    if (ok2 == 1) {
                        if (input.getMovies().get(i).getGenres().contains(genre)) {
                            movies.add(input.getMovies().get(i));
                        }
                    } else {
                        movies.add(input.getMovies().get(i));
                    }
                }
            } else {
                if (ok2 == 1) {
                    if (input.getMovies().get(i).getGenres().contains(genre)) {
                        movies.add(input.getMovies().get(i));
                    }
                } else {
                    movies.add(input.getMovies().get(i));
                }
            }
        }
        movies.sort(new SortMoviesByRatings());

        if (sortType.equals("desc")) {
            Collections.reverse(movies);
        }

        int j = 0;
        while (result.size() < number && j < movies.size()) {
            if (movies.get(j).getRatingsMean() > 0) {
                result.add(movies.get(j).getTitle());
            }
            j++;
        }
        return "Query result: " + result;
    }

    /**
     *Topul serialelor sortate crescator sau descrescator in functie de
     * rating-urile oferite de utilizatori
     */

    public String ascShowRatings(final Input input, final int number, final String s, final String genre, final String sortType) {
        int ok = 1, ok2 = 1, year = 0;
        if (s == null) {
            ok = 0;
        } else {
            year = Integer.parseInt(s);
        }
        if (genre == null) {
            ok2 = 0;
        }
        ArrayList<String> result = new ArrayList<>();
        ArrayList<SerialInputData> serials = new ArrayList<>();
        for (int i = 0; i < input.getSerials().size(); i++) {
            if (ok == 1) {
                if (input.getSerials().get(i).getYear() == year) {
                    if (ok2 == 1) {
                        if (input.getSerials().get(i).getGenres().contains(genre)) {
                            serials.add(input.getSerials().get(i));
                        }
                    } else {
                        serials.add(input.getSerials().get(i));
                    }
                }
            } else {
                if (ok2 == 1) {
                    if (input.getSerials().get(i).getGenres().contains(genre)) {
                        serials.add(input.getSerials().get(i));
                    }
                } else {
                    serials.add(input.getSerials().get(i));
                }
            }
        }
        serials.sort(new SortShowsByRatings());

        if (sortType.equals("desc")) {
            Collections.reverse(serials);
        }

        int j = 0;
        while (result.size() < number && j < serials.size()) {
            if (serials.get(j).getSerialRatingsMean() > 0) {
                result.add(serials.get(j).getTitle());
            }
            j++;
        }
        return "Query result: " + result;
    }

    /**
     *
     */

    public String ascFavoriteMovie(final Input input, final int number, final String s, final String genre, final String sortType) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<MovieInputData> movies = new ArrayList<>();
        int ok = 1, ok2 = 1, year = 0;
        if (s == null) {
            ok = 0;
        } else {
            year = Integer.parseInt(s);
        }
        if (genre == null) {
            ok2 = 0;
        }
        for (int i = 0; i < input.getUsers().size(); i++) {
            for (int j = 0; j < input.getMovies().size(); j++) {
                if (input.getUsers().get(i).getFavoriteMovies().contains(input.getMovies().get(j).getTitle())) {
                    input.getMovies().get(j).setNrFavorites(input.getMovies().get(j).getNrFavorites() + 1);
                }
            }
        }
        for (int i = 0; i < input.getMovies().size(); i++) {
            if (ok == 1) {
                if (input.getMovies().get(i).getYear() == year) {
                    if (ok2 == 1) {
                        if (input.getMovies().get(i).getGenres().contains(genre)) {
                            movies.add(input.getMovies().get(i));
                        }
                    } else {
                        movies.add(input.getMovies().get(i));
                    }
                }
            } else {
                if (ok2 == 1) {
                    if (input.getMovies().get(i).getGenres().contains(genre)) {
                        movies.add(input.getMovies().get(i));
                    }
                } else {
                    movies.add(input.getMovies().get(i));
                }
            }
        }
        movies.sort(new SortFavoriteMovies());

        if (sortType.equals("desc")) {
            Collections.reverse(movies);
        }
        int j = 0;
        while (result.size() < number && j < movies.size()) {
            if (movies.get(j).getNrFavorites() > 0) {
                result.add(movies.get(j).getTitle());
            }
            j++;
        }
        return "Query result: " + result;
    }

    /**
     *
     */

    public String ascFavoriteShow(final Input input, final int number, final String s, final String genre, final String sortType) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<SerialInputData> serials = new ArrayList<>();
        int ok = 1, ok2 = 1, year = 0;
        if (s == null) {
            ok = 0;
        } else {
            year = Integer.parseInt(s);
        }
        if (genre == null) {
            ok2 = 0;
        }
        for (int i = 0; i < input.getUsers().size(); i++) {
            for (int j = 0; j < input.getSerials().size(); j++) {
                if (input.getUsers().get(i).getFavoriteMovies().contains(input.getSerials().get(j).getTitle())) {
                    input.getSerials().get(j).setNrFavorites(input.getSerials().get(j).getNrFavorites() + 1);
                }
            }
        }
        for (int i = 0; i < input.getSerials().size(); i++) {
            if (ok == 1) {
                if (input.getSerials().get(i).getYear() == year) {
                    if (ok2 == 1) {
                        if (input.getSerials().get(i).getGenres().contains(genre)) {
                            serials.add(input.getSerials().get(i));
                        }
                    } else {
                        serials.add(input.getSerials().get(i));
                    }
                }
            } else {
                if (ok2 == 1) {
                    if (input.getSerials().get(i).getGenres().contains(genre)) {
                        serials.add(input.getSerials().get(i));
                    }
                } else {
                    serials.add(input.getSerials().get(i));
                }
            }
        }
        serials.sort(new SortFavoriteShows());

        if (sortType.equals("desc")) {
            Collections.reverse(serials);
        }
        int j = 0;
        while (result.size() < number && j < serials.size()) {
            if (serials.get(j).getNrFavorites() > 0) {
                result.add(serials.get(j).getTitle());
            }
            j++;
        }
        return "Query result: " + result;
    }

    /**
     *
     */

    public String ascLongestMovie(final Input input, final int number, final String s, final String genre, final String sortType) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<MovieInputData> movies = new ArrayList<>();
        int ok = 1, ok2 = 1, year = 0;
        if (s == null) {
            ok = 0;
        } else {
            year = Integer.parseInt(s);
        }
        if (genre == null) {
            ok2 = 0;
        }
        for (int i = 0; i < input.getMovies().size(); i++) {
            if (ok == 1) {
                if (input.getMovies().get(i).getYear() == year) {
                    if (ok2 == 1) {
                        if (input.getMovies().get(i).getGenres().contains(genre)) {
                            movies.add(input.getMovies().get(i));
                        }
                    } else {
                        movies.add(input.getMovies().get(i));
                    }
                }
            } else {
                if (ok2 == 1) {
                    if (input.getMovies().get(i).getGenres().contains(genre)) {
                        movies.add(input.getMovies().get(i));
                    }
                } else {
                    movies.add(input.getMovies().get(i));
                }
            }
        }
        movies.sort(new SortLongestMovie());

        if (sortType.equals("desc")) {
            Collections.reverse(movies);
        }
        int j = 0;
        while (result.size() < number && j < movies.size()) {
            if (movies.get(j).getDuration() > 0) {
                result.add(movies.get(j).getTitle());
            }
            j++;
        }
        return "Query result: " + result;
    }

    /**
     *
     */

    public String ascLongestShow(final Input input, final int number, final String s, final String genre, final String sortType) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<SerialInputData> serials = new ArrayList<>();
        int ok = 1, ok2 = 1, year = 0;
        if (s == null) {
            ok = 0;
        } else {
            year = Integer.parseInt(s);
        }
        if (genre == null) {
            ok2 = 0;
        }
        for (int i = 0; i < input.getSerials().size(); i++) {
            if (ok == 1) {
                if (input.getSerials().get(i).getYear() == year) {
                    if (ok2 == 1) {
                        if (input.getSerials().get(i).getGenres().contains(genre)) {
                            serials.add(input.getSerials().get(i));
                        }
                    } else {
                        serials.add(input.getSerials().get(i));
                    }
                }
            } else {
                if (ok2 == 1) {
                    if (input.getSerials().get(i).getGenres().contains(genre)) {
                        serials.add(input.getSerials().get(i));
                    }
                } else {
                    serials.add(input.getSerials().get(i));
                }
            }
        }
        serials.sort(new SortLongestShow());

        if (sortType.equals("desc")) {
            Collections.reverse(serials);
        }
        int j = 0;
        while (result.size() < number && j < serials.size()) {
            if (serials.get(j).getDuration() > 0) {
                result.add(serials.get(j).getTitle());
            }
            j++;
        }
        return "Query result: " + result;
    }

    /**
     * Returnez topul celor mai vizionate filme, in ordine crescatoare sau descrescatoare
     */

    public String mostViewedMovie(final Input input, final int number,
                                  final String s, final String genre, final String sortType) {
        ArrayList<MovieInputData> movies = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        int ok = 1, ok2 = 1, year = 0;
        if (s == null) {
            ok = 0;
        } else {
            year = Integer.parseInt(s);
        }
        if (genre == null) {
            ok2 = 0;
        }
        for (int i = 0; i < input.getUsers().size(); i++) {
            for (int j = 0; j < input.getMovies().size(); j++) {
                if (input.getUsers().get(i).getHistory().containsKey(input.getMovies().get(j).getTitle())) {
                    input.getMovies().get(j).setViews(input.getMovies().get(j).getViews() + input.getUsers().get(i).getHistory().get(input.getMovies().get(j).getTitle()));
                }
            }
        }
        for (int i = 0; i < input.getMovies().size(); i++) {
            if (ok == 1) {
                if (input.getMovies().get(i).getYear() == year) {
                    if (ok2 == 1) {
                        if (input.getMovies().get(i).getGenres().contains(genre)) {
                            movies.add(input.getMovies().get(i));
                        }
                    } else {
                        movies.add(input.getMovies().get(i));
                    }
                }
            } else {
                if (ok2 == 1) {
                    if (input.getMovies().get(i).getGenres().contains(genre)) {
                        movies.add(input.getMovies().get(i));
                    }
                } else {
                    movies.add(input.getMovies().get(i));
                }
            }
        }
        movies.sort(new SortMostViewedMovie());
        if (sortType.equals("desc")) {
            Collections.reverse(movies);
        }
        int j = 0;
        while (result.size() < number && j < movies.size()) {
            if (movies.get(j).getViews() > 0) {
                result.add(movies.get(j).getTitle());
            }
            j++;
        }
        return "Query result: " + result;
    }

    /**
     * Returnez topul celor mai vizionate seriale, in ordine crescatoare sau descrescatoare
     */

    public String mostViewedShow(final Input input, final int number, final String s, final String genre, final String sortType) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        int ok = 1, ok2 = 1, year = 0;
        if (s == null) {
            ok = 0;
        } else {
            year = Integer.parseInt(s);
        }
        if (genre == null) {
            ok2 = 0;
        }
        for (int i = 0; i < input.getUsers().size(); i++) {
            for (int j = 0; j < input.getSerials().size(); j++) {
                if (input.getUsers().get(i).getHistory().containsKey(input.getSerials().get(j).getTitle())) {
                    input.getSerials().get(j).setViews(input.getSerials().get(j).getViews() + input.getUsers().get(i).getHistory().get(input.getSerials().get(j).getTitle()));
                }
            }
        }
        for (int i = 0; i < input.getSerials().size(); i++) {
            if (ok == 1) {
                if (input.getSerials().get(i).getYear() == year) {
                    if (ok2 == 1) {
                        if (input.getSerials().get(i).getGenres().contains(genre)) {
                            serials.add(input.getSerials().get(i));
                        }
                    } else {
                        serials.add(input.getSerials().get(i));
                    }
                }
            } else {
                if (ok2 == 1) {
                    if (input.getSerials().get(i).getGenres().contains(genre)) {
                        serials.add(input.getSerials().get(i));
                    }
                } else {
                    serials.add(input.getSerials().get(i));
                }
            }
        }
        serials.sort(new SortMostViewedShow());
        if (sortType.equals("desc")) {
            Collections.reverse(serials);
        }
        int i = 0;
        while (result.size() < number && i < serials.size()) {
            if (serials.get(i).getViews() > 0) {
                result.add(serials.get(i).getTitle());
            }
            i++;
        }
        return "Query result: " + result;
    }

    /**
     * Returnez topul userilor sortat crescator sau descrescator
     * in functie de numarul de ranting-uri pe care le au dat
     */

    public String usersRatings(final Input input, final int number, final String sortType) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<UserInputData> users = new ArrayList<>(input.getUsers());
        users.sort(new SortUsers());
        if (sortType.equals("desc")) {
            Collections.reverse(users);
        }
        int i = 0;
        while (result.size() < number && i < users.size()) {
            if (users.get(i).getRatings().size() > 0) {
                result.add(users.get(i).getUsername());
            }
            i++;
        }
        return "Query result: " + result;
    }
}
