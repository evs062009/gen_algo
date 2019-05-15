package presetParameters;

public interface IPresetParameters {

    int getCheckPointsNumber();

    int getMinCoin();

    int getMaxCoin();

    int totalCostMoreThan();

    int[] getCostsArr();

    int[] getCoinsArr();

    int getNumberOfPairs();

    double getMutateChance();

    int getPopulationSize();
}
