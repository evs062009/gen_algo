package algorithms;

import domains.Individ;

import java.security.InvalidParameterException;
import java.util.*;

public class GeneticAlgo implements IAlgo {
    private Random random = new Random();

    @Override
    public int[] executeAlgo(int[] model, int[] input) throws IndexOutOfBoundsException {
        if (model.length == input.length) {

            List<Individ> population = initPopulation(input);

            Individ betterIndivid = population.get(0);
            int roundCounter = 0;
            boolean resetRoundCounter;

            while (true) {
                resetRoundCounter = false;

                for (Individ individ : population) {
                    if (individ.getFitScore() == Arrays.stream(model).sum() - Arrays.stream(individ.getChromosome())
                            .sum()) {
                        return individ.getChromosome();         //optimal solution
                    }
                    if (individ.getFitScore() < betterIndivid.getFitScore()) {
                        betterIndivid = individ;
                        resetRoundCounter = true;
                    }
                }
                if (resetRoundCounter) {
                    roundCounter = 0;
                } else if (roundCounter++ > 4) {
                    return betterIndivid.getChromosome();
                }

                population = evolve(model, population);
            }
        } else {
            throw new InvalidParameterException("Coins quantity is not equal checkpoint quantity.");
        }
    }

    private List<Individ> evolve(int[] model, List<Individ> population) {
        List<Individ> parents;
        List<Individ> children;

        for (int i = 0; i < getPairsNumber(); i++) {
            parents = chooseParents(population);
            children = breed(parents);
            mutate(children);
            fitTest(children, model);
            population = createNewPopulation(population, children);
        }
        return population;
    }

    private List<Individ> initPopulation(int[] input, int[] model) {
        List<Individ> population = new ArrayList<>(getPopulationSize());
        for (int i = 0; i < getPopulationSize(); i++) {
            int[] chromosome = shuffle(input);
            Individ individ = new Individ(chromosome, 0);
            individ.setFitScore(fitTest(individ.getChromosome(), model));
            population.add(individ);
        }
        return population;
    }

    private int[] shuffle(int[] input) {
        int[] arr = Arrays.copyOf(input, input.length);
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, i, random.nextInt(i));
        }
        return arr;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private int fitTest(int[] chromosome, int[] model) throws IndexOutOfBoundsException {
        int sum = 0;
        for (int i = 0; i < chromosome.length; i++) {
            int diff = model[i] - chromosome[i];
            if (diff > 0) {
                sum += diff;
            }
        }
        return sum;
    }

    private void refreshPopulation(List<Individ> population, int[] model) {
        for (int i = 0; i < getPairsNumber(); i++) {
            Individ father = population.get(i * 2);
            Individ mother = population.get(i * 2 + 1);
            List<Individ> children = cross(father, mother, model);
            for (int j = 0; j < children.size(); j++) {
                population.set(getPairsNumber() * (2 + i) + j, children.get(j));
            }
        }
    }

    private List<Individ> cross(Individ father, Individ mother, int[] model) {
        final int length = father.getChromosome().length;
        int crossPoint = random.nextInt(length - 1);
        int[] children = new int[length];
        int[] chromosomeOffspring2 = new int[length];
        for (int i = 0; i < length; i++) {
            if (i <= crossPoint) {
                children[i] = father.getChromosomeI(i);
                chromosomeOffspring2[i] = mother.getChromosomeI(i);
            } else {
                children[i] = mother.getChromosomeI(i);
                chromosomeOffspring2[i] = father.getChromosomeI(i);
            }
        }
        Individ offspring1 = new Individ(children, 0);
        Individ offspring2 = new Individ(chromosomeOffspring2, 0);
        mutate(offspring1);
        mutate(offspring2);
        fitTest(offspring1, model);
        fitTest(offspring1, model);
        return Arrays.asList(offspring1, offspring2);
    }

    private void mutate(Individ offspring) {
        int mutateSign = random.nextInt(100);
        if (mutateSign < getMutateChance()) {
            int length = offspring.getChromosome().length;
            int index1 = random.nextInt(length);
            int index2 = random.nextInt(length);
            if (index1 != index2) {
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

    @Override
    public int getPopulationSize() {
        return 10;
    }
}
