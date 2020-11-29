package main;

import fileio.SerialInputData;

import java.util.Comparator;

public class SortMostViewedShow implements Comparator<SerialInputData> {

    /**
     * Sortez serialele dupa numarul de vizualizari,
     * al doilea criteriu fiind numele
     */

    public final int compare(final SerialInputData serial1, final SerialInputData serial2) {
        if (serial1.getViews() > serial2.getViews()) {
            return 1;
        }
        if (serial1.getViews() < serial2.getViews()) {
            return -1;
        }
        return serial1.getTitle().compareTo(serial2.getTitle());
    }
}
