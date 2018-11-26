package ua.kpi;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Andrii Severin
 */
public class App 
{
    public static final String FILE_NOT_FOUND = "File not found.";
    public static final String INCORRECT_FILE_FORMAT = "Incorrect file format.";
    public static final String CASE_NUMBER = "Case Number";
    public static final String NUMBER_OF_COUNTRY_SHOULD_BE_POSITIVE = "Number of country should be positive";

    public static void main( String[] args ) throws Exception {
        Scanner input = null;
        try {
            input = new Scanner(Paths.get("in.txt"));
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        if (input == null) {
            return;
        }

        List<EuroDiffusionSimulation> euroDiffusionSimulations = parseInputFile(input);

        for (EuroDiffusionSimulation euroDiffusionSimulation : euroDiffusionSimulations) {
            euroDiffusionSimulation.simulate();
        }

        try (PrintWriter out = new PrintWriter("out.txt")) {
            out.println(buildResult(euroDiffusionSimulations));
        }
    }

    private static List<EuroDiffusionSimulation> parseInputFile(Scanner input) throws Exception {
        List<EuroDiffusionSimulation> result = new ArrayList<>();

        while (input.hasNextLine()) {
            Scanner line = new Scanner(input.nextLine());
            int numberOfCountry = getNextInt(line);
            if (numberOfCountry < 0)
                throw new Exception(NUMBER_OF_COUNTRY_SHOULD_BE_POSITIVE);
            CountryCoinsDistribution[] countries = new CountryCoinsDistribution[numberOfCountry];

            for (int i = 0; i < numberOfCountry; i++) {
                if (!input.hasNextLine()) {
                    throw new Exception(INCORRECT_FILE_FORMAT);
                } else {
                    line = new Scanner(input.nextLine());
                    line.useDelimiter(" ");
                    String countryName = line.next();

                    int xl = getNextInt(line);
                    int yl = getNextInt(line);
                    int xh = getNextInt(line);
                    int yh = getNextInt(line);
                    countries[i] = new CountryCoinsDistribution(countryName, xl, yl, xh, yh);
                }
            }

            EuroDiffusionSimulation euroDiffusionSimulation =
                    new EuroDiffusionSimulation(countries);
            result.add(euroDiffusionSimulation);
        }

        return result;
    }

    private static int getNextInt(Scanner line) throws Exception {
        if (!line.hasNextInt()) {
            throw new Exception(INCORRECT_FILE_FORMAT);
        }
        return line.nextInt();
    }

    private static String buildResult(List<EuroDiffusionSimulation> euroDiffusionSimulations) {
        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        for (EuroDiffusionSimulation euroDiffusionSimulation : euroDiffusionSimulations) {
            i++;
            if (euroDiffusionSimulation.getNumberOfCountries() == 0)
                continue;
            stringBuilder.append(CASE_NUMBER)
                    .append(" ")
                    .append(i)
                    .append("\n")
                    .append(euroDiffusionSimulation.getResults());
        }

        return stringBuilder.toString();
    }
}
