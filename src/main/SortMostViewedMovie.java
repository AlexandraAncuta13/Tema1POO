package main;

import fileio.MovieInputData;

import java.util.Comparator;

public class SortMostViewedMovie implements Comparator<MovieInputData> {

    /**
     * Sortez filmele in functie de numarul de vizualizari,
     * al doilea criteriu fiind numele
     */

    public final int compare(final MovieInputData movie1, final MovieInputData movie2) {
        if (movie1.getViews() > movie2.getViews()) {
            return 1;
        }
        if (movie1.getViews() < movie2.getViews()) {
            return -1;
        }
        return movie1.getTitle().compareTo(movie2.getTitle());
    }
}
