package algorithms;

import domains.AlgoResult;

public interface IAlgo {

    AlgoResult executeAlgo(int[] model, int[] input, int populationSize, int numberOfPairs, double mutateChance);
}
