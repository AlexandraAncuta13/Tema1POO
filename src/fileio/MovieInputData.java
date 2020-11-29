package fileio;

import java.util.ArrayList;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    private ArrayList<Double> ratings = new ArrayList<>();

    private int nrFavorites = 0;

    private int views;

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    /**
     * Calculez media rating-urilor filmului
     */

    public double getRatingsMean() {
        double sum = 0;
        if (this.ratings.size() == 0) {
            return 0;
        }
        for (int i = 0; i < this.ratings.size(); i++) {
            sum += this.ratings.get(i);
        }
        return sum / this.ratings.size();
    }

    public int getNrFavorites() {
        return nrFavorites;
    }

    public void setNrFavorites(int nrFavorites) {
        this.nrFavorites = nrFavorites;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
