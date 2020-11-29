package main;

import fileio.UserInputData;

import java.util.Comparator;

public class SortUsers implements Comparator<UserInputData> {

    /**
     * Sortez userii in functie de numarul de rating-uri pe care le-au dat,
     * al doilea criteriu fiind username-ul
     */

    public final int compare(final UserInputData user1, final UserInputData user2) {
        if (user1.getRatings().size() > user2.getRatings().size()) {
            return 1;
        }
        if (user1.getRatings().size() < user2.getRatings().size()) {
            return -1;
        }
        return user1.getUsername().compareTo(user2.getUsername());
    }
}
