package algorithms;

public interface IAlgo {

    int[] executeAlgo(int[] model, int[] input);

    int getPairsNumber();

    int getMutateChance();

    int getPopulationSize();
}
