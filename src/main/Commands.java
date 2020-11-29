package main;
import fileio.Input;

class Comands {

    public String favourite(final Input input, final String user, final String video) {
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(user)) {
                if (input.getUsers().get(i).getHistory().containsKey(video)) {
                    if (input.getUsers().get(i).getFavoriteMovies().contains(video)) {
                        return "error -> " + video + " is already in favourite list";
                    }
                    input.getUsers().get(i).getFavoriteMovies().add(video);
                    return "success -> " + video + " was added as favourite";
                }
                return "error -> " + video + " is not seen";
            }
        }
        return null;
    }

    public String view(final Input input, final String user, final String title) {
        int numberOfViews = 1;
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(user)) {
                if (input.getUsers().get(i).getHistory().containsKey(title)) {
                    numberOfViews = input.getUsers().get(i).getHistory().get(title);
                    numberOfViews++;
                    input.getUsers().get(i).getHistory().replace(title, numberOfViews);
                    return "success -> " + title + " was viewed with total views of "
                            + numberOfViews;
                }
                input.getUsers().get(i).getHistory().put(title, numberOfViews);
                return "success -> " + title + " was viewed with total views of " + numberOfViews;
            }
        }
        return null;
    }

    public String movieRating(final Input input, final String user,
                              final String title, final double rating) {
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(user)) {
                if (input.getUsers().get(i).getHistory().containsKey(title)) {
                    if (input.getUsers().get(i).getRatings().containsKey(title)) {
                        return "error -> " + title + " has been already rated";
                    }
                    for (int j = 0; j < input.getMovies().size(); j++) {
                        if (input.getMovies().get(j).getTitle().equals(title)) {
                            input.getMovies().get(j).getRatings().add(rating);
                            input.getUsers().get(i).getRatings().put(title, 0);
                            return "success -> " + title + " was rated with "
                                    + rating + " by " + user;
                        }
                    }
                }
                return "error -> " + title + " is not seen";
            }
        }
        return null;
    }

    public String showRating(final Input input, final String user, final String title,
                             final double rating, final int seasonNumber) {
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getUsername().equals(user)) {
                if (input.getUsers().get(i).getHistory().containsKey(title)) {
                    if (input.getUsers().get(i).getRatings().containsKey(title)) {
                        if (input.getUsers().get(i).getRatings()
                                .get(title) == seasonNumber) {
                            return "error -> " + title + " has been already rated";
                        }
                    }
                    for (int j = 0; j < input.getSerials().size(); j++) {
                        if (input.getSerials().get(j).getTitle().equals(title)) {
                            input.getSerials().get(j).getSeasons().get(seasonNumber - 1)
                                    .getRatings().add(rating);
                            input.getUsers().get(i).getRatings().put(title, seasonNumber);
                            return "success -> " + title + " was rated with "
                                    + rating + " by " + user;
                        }
                    }
                }
                return "error -> " + title + " is not seen";
            }
        }
        return null;
    }
}
