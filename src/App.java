import algorithms.IAlgo;
import domains.AlgoResult;
import inputParameters.IInputParameters;
import presetParameters.IPresetParameters;

import java.util.Arrays;

/**
 * Class calculates and shows two results of one problem solving. The results are created by two genetic algorithms
 * (with classic breeding ang breeding by only mutation), using ranges of algorithms parameters (like population size
 * etc.). The best result for each algorithm is selected for showing.
 *
 * @author eshevtsov
 */
class App {
    private IPresetParameters presetParameters;
    private IInputParameters inputParameters;
    private IAlgo classicAlgo;
    private IAlgo onlyMutateAlgo;

    App(IPresetParameters presetParameters, IInputParameters inputParameters, IAlgo classicAlgo,
               IAlgo onlyMutateAlgo) {
        this.presetParameters = presetParameters;
        this.inputParameters = inputParameters;
        this.classicAlgo = classicAlgo;
        this.onlyMutateAlgo = onlyMutateAlgo;
    }

    void showResults() {
        int[] model = inputParameters.getCostsArr();
        int[] input = presetParameters.getCoinsArr();
        int[] populationSizeRange = {5, 20};
        int[] numberOfBreedingRange = {1, 5};
        int[] mutateChanceRange = {1, 5};

        AlgoResult classicRes = getBestResult(model, input, classicAlgo, populationSizeRange, numberOfBreedingRange,
                mutateChanceRange);
        AlgoResult mutateRes = getBestResult(model, input, onlyMutateAlgo, populationSizeRange, numberOfBreedingRange,
                mutateChanceRange);
        System.out.println("Array of costs: " + Arrays.toString(model));
        printResult(classicRes, "Result of classic genetic algorithm:", true);
        printResult(mutateRes, "Result of genetic algorithm with breeding by mutation only:", false);
    }

    private AlgoResult getBestResult(int[] model, int[] input, IAlgo algo, int[] popSizeRange, int[] numBreedingRange,
                                     int[] mutateChanceRange) {
        AlgoResult bestResult = null;

        for (int popIndex = popSizeRange[0]; popIndex <= popSizeRange[1]; popIndex++) {
            for (int breedingIndex = numBreedingRange[0]; breedingIndex <= numBreedingRange[1]; breedingIndex++) {
                for (int mutatIndex = mutateChanceRange[0]; mutatIndex <= mutateChanceRange[1]; mutatIndex++) {
                    double mutatChance = (double) mutatIndex / 10.0;

                    AlgoResult result = algo.executeAlgo(model, input, popIndex, breedingIndex, mutatChance);

                    result.setPopulationSize(popIndex);
                    result.setNumberOfBreeding(breedingIndex);
                    result.setMutateChance(mutatChance);

                    if (bestResult == null || result.isBetter(bestResult)) {
                        bestResult = result;
                    }
                }
            }
        }
        return bestResult;
    }

    private void printResult(AlgoResult result, String message, boolean isClassicAlgo) {
        String solution = Arrays.toString(result.getIndivid().getChromosome());
        int fitDev = result.getIndivid().getFitDeviation();
        int totGen = result.getTotalGenerations();
        int popSize = result.getPopulationSize();
        int breeding = result.getNumberOfBreeding();
        double mutChance = result.getMutateChance();

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("solution: " + solution + ", debt = " + fitDev);
        System.out.println("generation totally: " + totGen);
        System.out.println("population size: " + popSize);
        System.out.println("pairs for breeding: " + breeding);
        System.out.println((isClassicAlgo)?("mutation chance: " + mutChance):(""));
    }
}
