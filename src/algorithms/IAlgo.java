package algorithms;

import domains.AlgoResult;

public interface IAlgo {

    /**
     * Using a certain algorithm determines the set (the array) of values that is optimal for the given model in terms
     * of the fitness function.
     * the function is determine in algorithm.
     * @param model             the array of numbers, that is a model for the fitness function.
     * @param input             start set (the array) of numbers for optimisation
     * @param populationSize    the number of variants of input array (individuals) in every generation
     * @param numberOfBreeding     the number of breeding for generation
     * @param mutateChance      the chance of offsprings mutation (in range 0.0 - 0.99)
     * @return                  the {@code AlgoResult} object, what contains optimal solution - {@code Individ} object,
     *                              and number of generation for this solution calculation.
     *                              The {@code Individ} object contains array of values, which is optimal, and the
     *                              value of fit deviation for this array.
     */
    AlgoResult executeAlgo(int[] model, int[] input, int populationSize, int numberOfBreeding, double mutateChance);
}
