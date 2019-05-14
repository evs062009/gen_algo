package algorithms;

public interface IAlgo {

    int[] executeAlgo(int[] model, int[] input);

    int getNumberOfPairs();

    double getMutateChance();

    int getPopulationSize();
}
