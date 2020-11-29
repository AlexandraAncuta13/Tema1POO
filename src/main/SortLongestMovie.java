package main;

import fileio.MovieInputData;

import java.util.Comparator;

public class SortLongestMovie implements Comparator<MovieInputData> {

    /**
     * Sortez filmele dupa durata, al doilea criteriu fiind numele
     */

    public final int compare(final MovieInputData movie1, final MovieInputData movie2) {
        if (movie1.getDuration() > movie2.getDuration()) {
            return 1;
        }
        if (movie1.getDuration() < movie2.getDuration()) {
            return -1;
        }
        return movie1.getTitle().compareTo(movie2.getTitle());
    }
}
