package main;

import fileio.SerialInputData;

import java.util.Comparator;

public class SortFavoriteShows implements Comparator<SerialInputData> {

    /**
     * Sortez serialele dupa numarul de aparitii in listele de favorite
     */

    public final int compare(final SerialInputData serial1, final SerialInputData serial2) {
        return serial1.getNrFavorites() - serial2.getNrFavorites();
    }
}
