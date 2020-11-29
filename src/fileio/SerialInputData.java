package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    private double serialRatingsMean;

    private int nrFavorites = 0;

    private int duration;

    private int views;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        for (int i = 0; i < seasons.size(); i++) {
            this.duration += seasons.get(i).getDuration();
        }
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * Calculez media rating-urilor pentru intreg serialul
     */

    public double getSerialRatingsMean() {
        double sum = 0;
        int ok = 0;
        for (int i = 0; i < this.seasons.size(); i++) {
            if (this.seasons.get(i).getRatingsMean() != 0) {
                ok = 1;
            }
            sum += this.seasons.get(i).getRatingsMean();
        }
        if (ok == 0) {
            return 0;
        }
        return sum / this.seasons.size();
    }

    public int getNrFavorites() {
        return nrFavorites;
    }

    public void setNrFavorites(int nrFavorites) {
        this.nrFavorites = nrFavorites;
    }

    public int getDuration() {
        return duration;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
