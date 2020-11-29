package main;

import fileio.SerialInputData;

import java.util.Comparator;

public class SortShowsByRatings implements Comparator<SerialInputData> {

    /**
     * Sortez serialele in functie de rating-urile utilizatorilor,
     * al doilea criteriu fiind numele
     */

    public final int compare(final SerialInputData serial1, final SerialInputData serial2) {
        if (serial1.getSerialRatingsMean() > serial2.getSerialRatingsMean()) {
            return 1;
        }
        if (serial1.getSerialRatingsMean() < serial2.getSerialRatingsMean()) {
            return -1;
        }
        return serial1.getTitle().compareTo(serial2.getTitle());
    }
}
