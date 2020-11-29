package main;

import fileio.Input;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.LinkedHashMap;

import java.util.Map;

import java.util.Collections;

import java.util.stream.Collectors;

public final class Recommendations {

    /**
     * Returnez primul film sau serial din baza de date nevizualizat de user
     */

    public String standard(final Input input, final String username) {
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(username)) {
                for (int j = 0; j < input.getMovies().size(); j++) {
                    if (!input.getUsers().get(i).getHistory()
                            .containsKey(input.getMovies().get(j).getTitle())) {
                        return "StandardRecommendation result: "
                                + input.getMovies().get(j).getTitle();
                    }
                }
                for (int j = 0; j < input.getSerials().size(); j++) {
                    if (!input.getUsers().get(i).getHistory()
                            .containsKey(input.getSerials().get(j).getTitle())) {
                        return "StandardRecommendation result: "
                                + input.getSerials().get(j).getTitle();
                    }
                }
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * Returnez primul film sau serial nevizualizat de user,
     */

    public String bestUnseen(final Input input, final String username) {
        String movie = null, serial = null;
        double max1 = -1, max2 = -1;
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(username)) {
                for (int j = 0; j < input.getMovies().size(); j++) {
                    if (!input.getUsers().get(i).getHistory().containsKey(input
                            .getMovies().get(j).getTitle())) {
                        if (input.getMovies().get(j).getRatingsMean() > max1) {
                            max1 = input.getMovies().get(j).getRatingsMean();
                            movie = input.getMovies().get(j).getTitle();
                        }
                    }
                }
                for (int j = 0; j < input.getSerials().size(); j++) {
                    if (!input.getUsers().get(i).getHistory().containsKey(input
                            .getSerials().get(j).getTitle())) {
                        if (input.getSerials().get(j).getSerialRatingsMean() > max2) {
                            max2 = input.getSerials().get(j).getSerialRatingsMean();
                            serial = input.getSerials().get(j).getTitle();
                        }
                    }
                }
            }
        }
        if (movie == null && serial == null) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }
        if (max1 > max2) {
            return "BestRatedUnseenRecommendation result: " + movie;
        }
        if (max2 > max1) {
            return "BestRatedUnseenRecommendation result: " + serial;
        }
        if (max1 == max2) {
            return "BestRatedUnseenRecommendation result: " + movie;
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * Returnez primul film sau serial neizualizat de user,
     * cel mai des intalnit in lisetele de favorite alea elorlalti useri.
     */

    public String favorite(final Input input, final String username) {
        String movie = null, serial = null;
        int max1 = -1, max2 = -1;

        for (int i = 0; i < input.getUsers().size(); i++) {
            // Calculez de cate ori apare fiecare film in listele de favorite
            for (int j = 0; j < input.getMovies().size(); j++) {
                if (input.getUsers().get(i).getFavoriteMovies()
                        .contains(input.getMovies().get(j).getTitle())) {
                    input.getMovies().get(j).setNrFavorites(input.getMovies()
                            .get(j).getNrFavorites() + 1);
                }
            }

            // Calculez de cate ori apare fiecare serial in listele de favorite
            for (int j = 0; j < input.getSerials().size(); j++) {
                if (input.getUsers().get(i).getFavoriteMovies()
                        .contains(input.getSerials().get(j).getTitle())) {
                    input.getSerials().get(j).setNrFavorites(input.getSerials()
                            .get(j).getNrFavorites() + 1);
                }
            }
        }
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(username)) {
                for (int j = 0; j < input.getMovies().size(); j++) {
                    if (!input.getUsers().get(i).getHistory().containsKey(input
                            .getMovies().get(j).getTitle())) {
                        if (input.getMovies().get(j).getNrFavorites() > max1) {
                            max1 = input.getMovies().get(j).getNrFavorites();
                            movie = input.getMovies().get(j).getTitle();
                        }
                    }
                }
                for (int j = 0; j < input.getSerials().size(); j++) {
                    if (!input.getUsers().get(i).getHistory().containsKey(input
                            .getSerials().get(j).getTitle())) {
                        if (input.getSerials().get(j).getNrFavorites() > max2) {
                            serial = input.getSerials().get(j).getTitle();
                            max2 = input.getSerials().get(j).getNrFavorites();
                        }
                    }
                }
            }
        }
        if (movie == null && serial == null) {
            return "FavoriteRecommendation cannot be applied!";
        }
        if (max1 > max2) {
            return "FavoriteRecommendation result: " + movie;
        }
        if (max2 > max1) {
            return "FavoriteRecommendation result: " + serial;
        }
        return "FavoriteRecommendation result: " + movie;
    }

    /**
     * Returnez cele mai bune filme sau seriale din genul cerut.
     */

    public String search(final Input input, final String username, final String genre) {
        ArrayList<String> result = new ArrayList<>();
        Map<String, Double> video = new HashMap<>();
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(username)) {
                // Stochez toate filmele care se incadreaza la genul specificat intr -un Map.
                for (int j = 0; j < input.getMovies().size(); j++) {
                    if (!input.getUsers().get(i).getHistory()
                            .containsKey(input.getMovies().get(j).getTitle())
                            && input.getMovies().get(j).getGenres().contains(genre)) {

                        video.put(input.getMovies().get(j).getTitle(),
                                input.getMovies().get(j).getRatingsMean());
                    }
                }
                // Stochez toate serialele care se incadreaza la genul specificat intr -un Map.
                for (int j = 0; j < input.getSerials().size(); j++) {
                    if (!input.getUsers().get(i).getHistory()
                            .containsKey(input.getSerials().get(j).getTitle())
                            && input.getSerials().get(j).getGenres().contains(genre)) {

                        video.put(input.getSerials().get(j).getTitle(),
                                input.getSerials().get(j).getSerialRatingsMean());
                    }
                }
            }
        }
        // Sortez Map-ul dupa rating-ul video-urilor, al doilea criteriu fiind numele
        Map<String, Double> sorted = video.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for (Map.Entry<String, Double> entry : sorted.entrySet()) {
            result.add(entry.getKey());
        }

        if (result.size() == 0) {
            return "SearchRecommendation cannot be applied!";
        }
        return "SearchRecommendation result: " + result;
    }

    /**
     * Retrneaza filmele sau serialele care se incadreaza in cele mai populare genuri.
     */

    public String popular(final Input input, final String username) {
        Map<String, Integer> generes = new HashMap<>();
        int index = 0;
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(username)) {
                index = i;
            }
        }
        if (input.getUsers().get(index).getSubscriptionType().equals("BASIC")) {
            return "PopularRecommendation cannot be applied!";
        }
        for (int i = 0; i < input.getMovies().size(); i++) {
            // Stochez intr-un map toate genurile filmelor, iar ca valoare numarl de aparitii
            // intre genrurile filmelor parcurse.
            for (int j = 0; j < input.getMovies().get(i).getGenres().size(); j++) {
                if (generes.containsKey(input.getMovies().get(i).getGenres().get(j))) {
                    generes.put(input.getMovies().get(i).getGenres().get(j),
                            generes.get(input.getMovies().get(i).getGenres().get(j)) + 1);
                } else {
                    generes.put(input.getMovies().get(i).getGenres().get(j), 1);
                }
            }
        }
        // Stochez intr-un map toate genurile serialelor, iar ca valoare numarl de aparitii
        // intre genrurile serialelor parcurse.
        for (int i = 0; i < input.getSerials().size(); i++) {
            for (int j = 0; j < input.getSerials().get(i).getGenres().size(); j++) {
                if (generes.containsKey(input.getSerials().get(i).getGenres().get(j))) {
                    generes.put(input.getSerials().get(i).getGenres().get(j),
                            generes.get(input.getSerials().get(i).getGenres().get(j)) + 1);
                } else {
                    generes.put(input.getSerials().get(i).getGenres().get(j), 1);
                }
            }
        }
        Map<String, Integer> sorted = generes.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            for (int j = 0; j < input.getMovies().size(); j++) {
                if (!input.getUsers().get(index).getHistory()
                        .containsKey(input.getMovies().get(j).getTitle())) {
                    if (input.getMovies().get(j).getGenres().contains(entry.getKey())) {
                        return "PopularRecommendation result: "
                                + input.getMovies().get(j).getTitle();
                    }
                }
            }
            for (int j = 0; j < input.getSerials().size(); j++) {
                if (!input.getUsers().get(index).getHistory().
                        containsKey(input.getSerials().get(j).getTitle())) {
                    if (input.getSerials().get(j).getGenres().contains(entry.getKey())) {
                        return "PopularRecommendation result: "
                                + input.getSerials().get(j).getTitle();
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }
}
