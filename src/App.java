import algorithms.ClassicGeneticAlgo;
import algorithms.IAlgo;
import algorithms.OnlyMutateGeneticAlgo;
import domains.AlgoResult;
import inputParameters.IInputParameters;
import inputParameters.InputParameters;
import presetParameters.IPresetParameters;
import presetParameters.PresetParameters;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Class compares two algorithms solutions of one problem.
 *
 * @author eshevtsov
 */
class App {
    private IPresetParameters presetParameters = new PresetParameters();
    private IInputParameters inputParameters = new InputParameters();
    private IAlgo classicAlgo = new ClassicGeneticAlgo();
    private IAlgo onlyMutateAlgo = new OnlyMutateGeneticAlgo();

    void execute() {
        int[] populationSizeRange = {5, 20};
        int[] numberOfBreedingRange = {1, 5};
        int[] mutateChanceRange = {1, 5};

        int size = 10;
        int[] betterSolution = new int[size];
        double[] generatDiff = new double[size];
        double[] populatDiff = new double[size];

        for (int i = 0; i < size; i++) {
            int[] model = inputParameters.getCostsArr();
            int[] input = presetParameters.getCoinsArr();

            AlgoResult classicRes = getBestResult(model, input, classicAlgo, populationSizeRange,
                    numberOfBreedingRange, mutateChanceRange);
            AlgoResult mutateRes = getBestResult(model, input, onlyMutateAlgo, populationSizeRange,
                    numberOfBreedingRange, mutateChanceRange);

            if (classicRes.getIndivid().getFitDeviation() < mutateRes.getIndivid().getFitDeviation()) {
                betterSolution[i] = 1;
            } else if (mutateRes.getIndivid().getFitDeviation() < classicRes.getIndivid().getFitDeviation()) {
                betterSolution[i] = -1;
            } else {
                betterSolution[i] = 0;
            }

            try {
                generatDiff[i] = classicRes.getTotalGenerations() / mutateRes.getTotalGenerations();
            } catch (ArithmeticException ex) {
                generatDiff[i] = 0.05;
            }
            populatDiff[i] = classicRes.getPopulationSize() / mutateRes.getPopulationSize();
        }

        long classicBetterSolution = IntStream.of(betterSolution).filter(x -> x == 1).count();
        if (classicBetterSolution != 0) {
            System.out.println("classic algo find better solution " + classicBetterSolution);
        }
        long mutateBetterSolution = IntStream.of(betterSolution).filter(x -> x == -1).count();
        if (mutateBetterSolution != 0) {
            System.out.println("mutate algo find better solution " + mutateBetterSolution);
        }
        double generateDiffAv = DoubleStream.of(generatDiff).average().getAsDouble();
        System.out.println("generation better in " + ((generateDiffAv > 1) ? ("classic by " + (generateDiffAv - 1))
                : ("mutate by " + (1 - generateDiffAv))));
        double popSizeAv = DoubleStream.of(populatDiff).average().getAsDouble();
        System.out.println("popSize better in " + ((popSizeAv > 1) ? ("classic by " + (popSizeAv - 1))
                : ("mutate by " + (1 - popSizeAv))));
    }

    private AlgoResult getBestResult(int[] model, int[] input, IAlgo algo, int[] popSizeRange, int[] numBreedingRange,
                                     int[] mutateChanceRange) {
        AlgoResult bestResult = null;

        for (int popIndex = popSizeRange[0]; popIndex <= popSizeRange[1]; popIndex++) {
            for (int breedingIndex = numBreedingRange[0]; breedingIndex <= numBreedingRange[1]; breedingIndex++) {
                for (int mutatIndex = mutateChanceRange[0]; mutatIndex <= mutateChanceRange[1]; mutatIndex++) {
                    double mutatChance = (double) mutatIndex / 10.0;

                    AlgoResult result = algo.executeAlgo(model, input, popIndex, breedingIndex, mutatChance);

                    int fitDeviation = result.getIndivid().getFitDeviation();
                    int totalGenerations = result.getTotalGenerations();

                    result.setPopulationSize(popIndex);
                    result.setNumberOfBreeding(breedingIndex);
                    result.setMutateChance(mutatChance);

                    if (bestResult == null || fitDeviation < bestResult.getIndivid().getFitDeviation()
                            || ((fitDeviation == bestResult.getIndivid().getFitDeviation())
                            && totalGenerations < bestResult.getTotalGenerations())) {
                        bestResult = result;
                    }
                }
            }
        }
        return bestResult;
    }

    private void printResult(AlgoResult result, String message) {
        String solution = Arrays.toString(result.getIndivid().getChromosome());
        int fitDev = result.getIndivid().getFitDeviation();
        int totGen = result.getTotalGenerations();
        int popSize = result.getPopulationSize();
        int breeding = result.getNumberOfBreeding();
        double mutChance = result.getMutateChance();

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("solution: " + solution + "\tfitDev = " + fitDev + "\ttotGen = " + totGen);
        System.out.println("popSize = " + popSize + "\tbreeding = " + breeding + "\tmutChance = " + mutChance);
    }
}
