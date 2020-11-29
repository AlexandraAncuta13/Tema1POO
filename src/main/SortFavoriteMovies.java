package main;

import fileio.MovieInputData;

import java.util.Comparator;

public class SortFavoriteMovies implements Comparator<MovieInputData> {

    /**
     * Sortez filmele dupa numarul de aparitii in listele de
     * favorite ale utilizatorilor, al doilea criteriu fiind numele
     */

    public final int compare(final MovieInputData movie1, final MovieInputData movie2) {
        if (movie1.getNrFavorites() > movie2.getNrFavorites()) {
            return 1;
        }
        if (movie1.getNrFavorites() < movie2.getNrFavorites()) {
            return -1;
        }
        return movie1.getTitle().compareTo(movie2.getTitle());
    }
}
