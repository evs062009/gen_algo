package algorithms;

import domains.Individual;
import presetParameters.IPresetParameters;

import java.security.InvalidParameterException;
import java.util.*;

public class GeneticAlgo implements IAlgo {
    private Individual individual;
    private IPresetParameters presetParameters;
    private Random random = new Random();

    public GeneticAlgo(Individual individual, IPresetParameters presetParameters) {
        this.individual = individual;
        this.presetParameters = presetParameters;
    }

    @Override
    public int[] executeAlgo(int[] targetArr, int[] inputArr) throws IndexOutOfBoundsException {
        if (targetArr.length == inputArr.length) {
            List<Individual> population = createStartPopulation(inputArr);
            Individual bestIndividual = population.get(0);
            int roundCounter = 0;
            boolean resetRoundCounter;

            while (true) {
                resetRoundCounter = false;
                evolve(population, targetArr);
                for (Individual individual : population) {

                    //check for the best solution
                    if (individual.getFitScore() == Arrays.stream(targetArr).sum() - Arrays.stream(inputArr).sum()) {
                        return individual.getChromosome();
                    }
                    if (individual.getFitScore() < bestIndividual.getFitScore()) {
                        bestIndividual = individual;
                        resetRoundCounter = true;
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
            }
        } else {
            throw new InvalidParameterException("Coins quantity is not equal checkpoint quantity.");
        }
    }

    private List<Individual> createStartPopulation(int[] inputArr) {

    }

    private void evolve(List<Individual> population, int[] targetArr) throws IndexOutOfBoundsException {
        population.forEach(individual -> individual.setFitScore(calculateFitScore(individual, targetArr)));
        Collections.sort(population);
        refreshPopulation(population, targetArr);
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

    private void refreshPopulation(List<Individual> population, int[] targetArr) {
        for (int i = 0; i < getPairsNumber(); i++) {
            Individual father = population.get(i * 2);
            Individual mother = population.get(i * 2 + 1);
            List<Individual> offsprings = cross(father, mother, targetArr);
            for (int j = 0; j < offsprings.size(); j++) {
                population.set(getPairsNumber() * (2 + i) + j, offsprings.get(j));
            }
        }
    }

    private List<Individual> cross(Individual father, Individual mother, int[] targetArr) {
        final int length = father.getChromosome().length;
        int crossPoint = random.nextInt(length - 1);
        int[] chromosomeOffspring1 = new int[length];
        int[] chromosomeOffspring2 = new int[length];
        for (int i = 0; i < length; i++) {
            if (i <= crossPoint){
                chromosomeOffspring1[i] = father.getChromosomeI(i);
                chromosomeOffspring2[i] = mother.getChromosomeI(i);
            } else {
                chromosomeOffspring1[i] = mother.getChromosomeI(i);
                chromosomeOffspring2[i] = father.getChromosomeI(i);
            }
        }
        Individual offspring1 = new Individual(chromosomeOffspring1, 0);
        Individual offspring2 = new Individual(chromosomeOffspring2, 0);
        mutate(offspring1);
        mutate(offspring2);
        calculateFitScore(offspring1, targetArr);
        calculateFitScore(offspring1, targetArr);
        return Arrays.asList(offspring1, offspring2);
    }

    private void mutate(Individual offspring) {
        int mutateSign = random.nextInt(100);
        if (mutateSign < getMutateChance()){
            int length = offspring.getChromosome().length;
            int index1 = random.nextInt(length);
            int index2 = random.nextInt(length);
            if (index1 != index2){
                int temp = offspring.getChromosomeI(index1);
                offspring.setChromosomeI(index1, offspring.getChromosomeI(index2));
                offspring.setChromosomeI(index2, temp);
            }
        }
    }

    @Override
    public int getPairsNumber() {
        return 2;
    }

    @Override
    public int getMutateChance() {
        return 5;
    }
}
