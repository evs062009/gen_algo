package algorithms;

import domains.Individual;
import presetParameters.IPresetParameters;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

public class GeneticAlgo implements IAlgo {
    Individual individual;
    IPresetParameters presetParameters;

    public GeneticAlgo(Individual individual, IPresetParameters presetParameters) {
        this.individual = individual;
        this.presetParameters = presetParameters;
    }

    @Override
    public int[] executeAlgo(int[] targetArr, int[] inputArr) throws IndexOutOfBoundsException {
        if (targetArr.length == inputArr.length) {
            List<Individual> population = createStartPopulation(inputArr);
            Individual bestIndividual = population.get(0);
            int roundCounter;
            boolean resetRoundCounter;

            while (true) {
                resetRoundCounter = false;
                evolve(population, targetArr);
                for (Individual individual : newPopulation) {

                    //the best solution
                    if (individual.getFitScore() == Arrays.stream(targetArr).sum() - Arrays.stream(inputArr).sum()) {
                        return individual.getChromosome();
                    }
                    if (individual.getFitScore() < bestIndividual.getFitScore()) {
                        bestIndividual = individual;
                        resetRoundCounter = true;
                    }
                }
            }
            if (resetRoundCounter) {
                roundCounter = 0;
            } else {
                roundCounter++;
                if (roundCounter > 3) {
                    return bestIndividual.getChromosome();
                }
            }
        } else {
            throw new InvalidParameterException("Coins quantity is not equal checkpoint quantity.");
        }
    }

    private void evolve(List<Individual> population, int[] targetArr) throws IndexOutOfBoundsException {
        population.Fo
        for (Individual individual : population) {
            individual.setFitScore(calculateFitScore(individual, targetArr));
        }
        select(population);
        cross(population);
        return population;
    }

    private int calculateFitScore(Individual individual, int[] targetArr) throws IndexOutOfBoundsException {
        int sum = 0;
        int[] chromosome = individual.getChromosome();
        for (int i = 0; i < chromosome.length; i++) {
            int diff = targetArr[i] - chromosome[i];
            if (diff > 0) {
                sum += diff;
            }
        }
        return sum;
    }

    private int[] select(Individual[] oldPopulation) {
        Arrays.sort(oldPopulation);
        return Arrays.copyOf(oldPopulation, getPairQuantity() * 2);
    }

    @Override
    public int getPairQuantity() {
        return 2;
    }
}
