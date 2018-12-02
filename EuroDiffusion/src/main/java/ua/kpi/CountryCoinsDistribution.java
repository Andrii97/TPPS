package ua.kpi;

/**
 * @author Andrii Severin
 */
public class CountryCoinsDistribution implements Comparable {
    public static final String NAME_LENGTH_ERROR =
            "Name should contains not more than 25 characters.";
    public static final String COORDINATES_SHOULD_BE_POSITIVE =
            "Coordinates should be positive.";
    public static final String COORDINATES_SHOULD_BE_LESS_THAN_MAX =
            "Coordinates should be less than max value.";
    public static final int AMOUNT_OF_INITIAL_CITY_MOTIF = 1000000;
    public static final int PER_AMOUNT = 1000;
    public static final int MAX_X = 10;
    public static final int MAX_Y = 10;

    private String name;
    private int[][] currentMatrix;
    private boolean[][] matrixOfEUCountries;
    private int xl;
    private int yl;
    private int xh;
    private int yh;
    private int numberOfDays = 0;
    private boolean isComplete;

    public CountryCoinsDistribution(String name, int xl, int yl, int xh, int yh) throws Exception {
        this.name = name;
        this.xl = xl - 1;
        this.yl = yl - 1;
        this.xh = xh - 1;
        this.yh = yh - 1;
        checkData();
        currentMatrix = initMatrix();
    }

    private void checkData() throws Exception {
        if (name.length() > 25) {
            throw new Exception(NAME_LENGTH_ERROR);
        }
        if (xl < 0 || yl < 0 || xh < 0 || yh < 0) {
            throw new Exception(COORDINATES_SHOULD_BE_POSITIVE);
        }
        if (xl >= MAX_X || yl >= MAX_Y || xh >= MAX_X || yh >= MAX_Y) {
            throw new Exception(COORDINATES_SHOULD_BE_LESS_THAN_MAX);
        }
    }

    public void nextDay() {
        int[][] result = new int[MAX_X][MAX_Y];

        for(int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                int amountToTransport = currentMatrix[x][y] / PER_AMOUNT;
                int numberOfSuccessfulTransportation =
                        transportToNeighbors(result, x, y, amountToTransport);
                result[x][y] += currentMatrix[x][y] - numberOfSuccessfulTransportation * amountToTransport;
            }
        }

        if (!isComplete()) {
            numberOfDays++;
        }
        currentMatrix = result;
    }

    private int transportToNeighbors(int[][] matrix, int x, int y, int amountToTransport) {
        int numberOfSuccessfulTransportation = 0;

        if (amountToTransport <= 0)
            return numberOfSuccessfulTransportation;

        if (updateNeighborCoins(matrix, x - 1, y, amountToTransport))
            numberOfSuccessfulTransportation++;

        if (updateNeighborCoins(matrix, x, y - 1, amountToTransport))
            numberOfSuccessfulTransportation++;

        if (updateNeighborCoins(matrix, x + 1, y, amountToTransport))
            numberOfSuccessfulTransportation++;

        if (updateNeighborCoins(matrix, x, y + 1, amountToTransport))
            numberOfSuccessfulTransportation++;

        return numberOfSuccessfulTransportation;
    }

    private boolean updateNeighborCoins(int[][] matrix, int x, int y, int amountToTransport) {
        if (!checkIsCityAvailable(x, y)) {
            return false;
        }

        matrix[x][y] += amountToTransport;
        return true;
    }

    private int[][] initMatrix() {
        int[][] result = new int[MAX_X][MAX_Y];

        for (int x = xl; x <= xh; x++) {
            for (int y = yl; y <= yh; y++) {
                result[x][y] = AMOUNT_OF_INITIAL_CITY_MOTIF;
            }
        }

        return result;
    }

    private boolean checkIsCityAvailable(int x, int y) {
        if (x < 0 || y < 0 || x >= MAX_X || y >= MAX_Y) {
            return false;
        }

        return matrixOfEUCountries[x][y];
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof CountryCoinsDistribution)
        {
            CountryCoinsDistribution country = (CountryCoinsDistribution)o;

            if(this.numberOfDays > country.numberOfDays)
                return 1;
            if(this.numberOfDays < country.numberOfDays)
                return -1;

            return (this.name.compareTo(country.name));
        }
        else
            return -1;
    }

    public int getCityCoins(int x, int y) {
        return currentMatrix[x][y];
    }

    public String getName() {
        return name;
    }

    public int getXl() {
        return xl;
    }

    public int getYl() {
        return yl;
    }

    public int getXh() {
        return xh;
    }

    public int getYh() {
        return yh;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setMatrixOfEUCountries(boolean[][] matrixOfEUCountries) {
        this.matrixOfEUCountries = matrixOfEUCountries;
    }
}
