package main;

import fileio.SerialInputData;

import java.util.Comparator;

public class SortLongestShow implements Comparator<SerialInputData> {

    /**
     * Sortez serialele in functie de durata, al doilea criteriu fiind numele
     */

    public final int compare(final SerialInputData serial1, final SerialInputData serial2) {
        if (serial1.getDuration() > serial2.getDuration()) {
            return 1;
        }
        if (serial1.getDuration() < serial2.getDuration()) {
            return -1;
        }
        return serial1.getTitle().compareTo(serial2.getTitle());
    }
}
