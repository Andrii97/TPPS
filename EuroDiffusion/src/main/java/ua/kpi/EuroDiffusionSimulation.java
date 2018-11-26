package ua.kpi;

import java.util.Arrays;

/**
 * @author Andrii Severin
 */
public class EuroDiffusionSimulation {
    public static final String NUMBER_OF_COUNTRIES_ERROR =
            "Number of countries should be not more that max value.";
    public static final int MAX_NUMBER_OF_COUNTRIES = 20;
    public static final int MAX_X = 10;
    public static final int MAX_Y = 10;

    private int numberOfCountries;
    private CountryCoinsDistribution[] countries;
    private boolean[][] matrixOfEUCountries;

    public EuroDiffusionSimulation(CountryCoinsDistribution... countries) throws Exception {
        this.numberOfCountries = countries.length;
        this.countries = countries;
        checkData();
        fillMatrixOfEUCountries();
        setMatrixOfEUCountries();
    }

    private void checkData() throws Exception {
        if (numberOfCountries > MAX_NUMBER_OF_COUNTRIES)
            throw new Exception(NUMBER_OF_COUNTRIES_ERROR);
    }

    private void fillMatrixOfEUCountries() {
        matrixOfEUCountries = new boolean[MAX_X][MAX_Y];
        for (CountryCoinsDistribution country: countries) {
            for (int x = country.getXl(); x <= country.getXh(); x++) {
                for (int y = country.getYl(); y <= country.getYh(); y++) {
                    matrixOfEUCountries[x][y] = true;
                }
            }
        }
    }

    private void setMatrixOfEUCountries() {
        for (CountryCoinsDistribution country: countries) {
            country.setMatrixOfEUCountries(matrixOfEUCountries);
        }
    }


    public void simulate() {
        while (!isEnd()) {
            for (int i = 0; i < numberOfCountries; i++) {
                countries[i].nextDay();
            }
        }
    }

    private boolean isEnd() {
        boolean result = true;
        for (int i = 0; i < numberOfCountries; i++) {
            if (!countries[i].isComplete())
                result &= checkCountryComplete(countries[i]);
        }
        return result;
    }

    private boolean checkCountryComplete(CountryCoinsDistribution country) {
        for (int x = country.getXl(); x <= country.getXh(); x++) {
            for (int y = country.getYl(); y <= country.getYh(); y++) {
                for (int j = 0; j < numberOfCountries; j++) {
                    if (countries[j].getCityCoins(x, y) == 0) {
                            return false;
                        }
                }
            }
        }

        country.setComplete(true);
        return true;
    }

    public String getResults() {
        Arrays.sort(countries);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberOfCountries; i++) {
            stringBuilder.append(countries[i].getName())
                    .append(" ")
                    .append(countries[i].getNumberOfDays())
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    public int getNumberOfCountries() {
        return numberOfCountries;
    }
}
