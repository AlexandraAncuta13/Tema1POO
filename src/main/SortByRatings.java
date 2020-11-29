package main;

import fileio.ActorInputData;

import java.util.Comparator;

public class SortByRatings implements Comparator<ActorInputData> {

    /**
     * Sortez actorii dupa rating-urile filmelor si serialelor in care
     * au jucat, al doilea criteriu fiind numele
     */

    public final int compare(final ActorInputData actor1, final ActorInputData actor2) {
        if (actor1.getRating() > actor2.getRating()) {
            return 1;
        }
        if (actor1.getRating() < actor2.getRating()) {
            return -1;
        }
        return actor1.getName().compareTo(actor2.getName());
    }
}
