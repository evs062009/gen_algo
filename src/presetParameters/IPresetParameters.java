package presetParameters;

public interface IPresetParameters {

    /**
     * Returns the number of checkpoints.
     * @return the number of checkpoints
     */
    int getCheckPointsNumber();

    /**
     * Returns the value of the least coin.
     * @return the value of the least coin
     */
    int getMinCoin();

    /**
     * Returns the array of coins values.
     * @return the array of coins values
     */
    int[] getCoinsArr();

    /**
     * Returns the size of a population.
     * @return the size of a population
     */
    int getPopulationSize();

    /**
     * Returns the number of parents pairs for breeding.
     * @return the number of parents pairs for breeding
     */
    int getNumberOfPairs();

    /**
     * Returns the value of offspring`s chance of mutation (in range 0.0 - 0.99).
     * @return the value of offspring`s chance of mutation
     */
    double getMutateChance();
}
