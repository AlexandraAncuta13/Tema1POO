package main;

import fileio.MovieInputData;

import java.util.Comparator;

public class SortMoviesByRatings implements Comparator<MovieInputData> {

    /**
     * Sortez filmele in functie de rating-urile utilozatorilor,
     * al doilea criteriu fiind numele
     * @param movie1
     * @param movie2
     * @return
     */

    public final int compare(final MovieInputData movie1, final MovieInputData movie2) {
        if (movie1.getRatingsMean() > movie2.getRatingsMean()) {
            return 1;
        }
        if (movie1.getRatingsMean() < movie2.getRatingsMean()) {
            return -1;
        }
        return movie1.getTitle().compareTo(movie2.getTitle());
    }
}
