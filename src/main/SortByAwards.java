package main;

import fileio.ActorInputData;

import java.util.Comparator;

public class SortByAwards implements Comparator<ActorInputData> {

    /**
     * Sortez actorii dupa numarul de premii,
     * al doilea criteriu fiind numele
     */

    public final int compare(final ActorInputData actor1, final ActorInputData actor2) {
        if (actor1.getNrAwards() > actor2.getNrAwards()) {
            return 1;
        }
        if (actor1.getNrAwards() < actor2.getNrAwards()) {
            return -1;
        }
        return actor1.getName().compareTo(actor2.getName());
    }

}
