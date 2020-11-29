package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        ActionInputData command;
        String message = " ";
        for (int i = 0; i < input.getCommands().size(); i++) {
            command = input.getCommands().get(i);
            if (command.getActionType().equals("command")) {
                Comands comands = new Comands();
                if (command.getType().equals("favorite")) {
                    message = comands.favourite(input, command.getUsername(), command.getTitle());
                }
                if (command.getType().equals("view")) {
                    message = comands.view(input, command.getUsername(), command.getTitle());
                }
                if (command.getType().equals("rating")) {
                    if (command.getSeasonNumber() == 0) {
                        message = comands.movieRating(input, command.getUsername(),
                                command.getTitle(), command.getGrade());
                    } else {
                        message = comands.showRating(input, command.getUsername(),
                                command.getTitle(), command.getGrade(), command.getSeasonNumber());
                    }
                }
            }
            if (command.getActionType().equals("query")) {
                Querys querys = new Querys();
                if (command.getObjectType().equals("actors")) {
                    if (command.getCriteria().equals("awards")) {
                        message = querys.ascAwards(input, command.getFilters().get(3),
                                command.getSortType());
                    }
                    if (command.getCriteria().equals("average")) {
                        message = querys.average(input, command.getNumber(), command.getSortType());
                    }
                    if (command.getCriteria().equals("filter_description")) {
                        message = querys.filterDescription(input, command.getFilters().get(2),
                                command.getSortType());
                    }
                }
                if (command.getCriteria().equals("ratings")) {
                    if (command.getObjectType().equals("movies")) {
                        message = querys.ascmovieRatings(input, command.getNumber(),
                                command.getFilters().get(0).get(0), command.getFilters()
                                        .get(1).get(0), command.getSortType());
                    }
                    if (command.getObjectType().equals("shows")) {
                        message = querys.ascShowRatings(input, command.getNumber(),
                                command.getFilters().get(0).get(0), command.getFilters()
                                        .get(1).get(0), command.getSortType());
                    }
                }
                if (command.getCriteria().equals("favorite")) {
                    if (command.getObjectType().equals("movies")) {
                        message = querys.ascFavoriteMovie(input, command.getNumber(),
                                command.getFilters().get(0).get(0), command.getFilters()
                                        .get(1).get(0), command.getSortType());
                    }
                    if (command.getObjectType().equals("shows")) {
                        message = querys.ascFavoriteShow(input, command.getNumber(),
                                command.getFilters().get(0).get(0), command.getFilters()
                                        .get(1).get(0), command.getSortType());
                    }
                }
                if (command.getCriteria().equals("longest")) {
                    if (command.getObjectType().equals("movies")) {
                        message = querys.ascLongestMovie(input, command.getNumber(),
                                command.getFilters().get(0).get(0), command.getFilters()
                                        .get(1).get(0), command.getSortType());
                    }
                    if (command.getObjectType().equals("shows")) {
                        message = querys.ascLongestShow(input, command.getNumber(),
                                command.getFilters().get(0).get(0), command.getFilters()
                                        .get(1).get(0), command.getSortType());
                    }
                }
                if (command.getCriteria().equals("most_viewed")) {
                    if (command.getObjectType().equals("movies")) {
                        message = querys.mostViewedMovie(input, command.getNumber(),
                                command.getFilters().get(0).get(0),
                                command.getFilters().get(1).get(0), command.getSortType());

                    }
                    if (command.getObjectType().equals("shows")) {
                        message = querys.mostViewedShow(input, command.getNumber(),
                                command.getFilters().get(0).get(0),
                                command.getFilters().get(1).get(0), command.getSortType());
                    }
                }
                if (command.getCriteria().equals("num_ratings")) {
                    message = querys.usersRatings(input, command.getNumber(), command.getSortType());
                }
            }
            if (command.getActionType().equals("recommendation")) {
                Recommendations recommendations = new Recommendations();
                if (command.getType().equals("standard")) {
                    message = recommendations.standard(input, command.getUsername());
                }
                if (command.getType().equals("best_unseen")) {
                    message = recommendations.bestUnseen(input, command.getUsername());
                }
                if (command.getType().equals("favorite")) {
                    message = recommendations.favorite(input, command.getUsername());
                }
                if (command.getType().equals("search")) {
                    message = recommendations.search(input, command.getUsername(),
                            command.getGenre());
                }
                if (command.getType().equals("popular")) {
                    message = recommendations.popular(input, command.getUsername());
                }
            }
            JSONObject jsonObject = fileWriter.writeFile(command.getActionId(), " ", message);
            arrayResult.add(jsonObject);
        }
        fileWriter.closeJSON(arrayResult);
    }
}
