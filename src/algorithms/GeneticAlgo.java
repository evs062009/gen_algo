package algorithms;

import domains.Individ;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneticAlgo implements IAlgo {
    private Random random = new Random();

    @Override
    public int[] executeAlgo(int[] model, int[] input) throws IndexOutOfBoundsException {
        if (model.length == input.length) {

            List<Individ> population = initiatePopulation(model, input);

            Individ betterIndivid = population.get(0);
            int terminateCounter = 0;
            boolean resetTerminateCounter;

            while (true) {
                resetTerminateCounter = false;

                for (Individ individ : population) {
                    if (individ.getFitScore() == Arrays.stream(model).sum() - Arrays.stream(individ.getChromosome())
                            .sum()) {
                        return individ.getChromosome();         //optimal solution
                    }
                    if (individ.getFitScore() < betterIndivid.getFitScore()) {
                        betterIndivid = individ;
                        resetTerminateCounter = true;
                    }
                }
                if (resetTerminateCounter) {
                    terminateCounter = 0;
                } else if (terminateCounter++ > 4) {
                    return betterIndivid.getChromosome();
                }

                population = evolve(model, population);
            }
        } else {
            throw new InvalidParameterException("Coins quantity is not equal checkpoint quantity.");
        }
    }

    private List<Individ> initiatePopulation(int[] model, int[] input) {
        List<Individ> population = new ArrayList<>(getPopulationSize());
        for (int i = 0; i < getPopulationSize(); i++) {
            int[] chromosome = shuffle(input);
            Individ individ = new Individ(chromosome, 0, 0);
            setFitScoreAndInvertRatio(individ, model);
            population.add(individ);
        }
        return population;
    }

    private List<Individ> evolve(int[] model, List<Individ> population) {
        List<Individ> parents;
        List<Individ> children = new ArrayList<>(getNumberOfPairs() * 2);

        for (int i = 0; i < getNumberOfPairs(); i++) {
            parents = getParents(population);
            children.addAll(getChildren(parents, model));
        }
        return createNewPopulation(population, children);
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

    private List<Individ> getParents(List<Individ> population) {
        List<Individ> parents = new ArrayList<>(2);
        double totalInvertRatio = population.stream().map(Individ::getInvertRatio).reduce(0.0, Double::sum);
        int fatherIndex = chooseParent(population, totalInvertRatio);
        int motherIndex = chooseParent(population, totalInvertRatio);

        if (motherIndex == fatherIndex) {
            motherIndex = motherIndex + population.size() / 2;
            if (motherIndex >= population.size()) {
                motherIndex = motherIndex - population.size();
            }
        }
        parents.addAll(Arrays.asList(population.get(fatherIndex), population.get(motherIndex)));
        return parents;
    }

    private int chooseParent(List<Individ> population, double totalInvertRatio) {
        double parentSign = random.nextDouble();
        double bottomBound = 0;
        double topBound;
        int individIndex;

        for (individIndex = 0; individIndex < population.size() - 1; individIndex++) {
            topBound = bottomBound + population.get(individIndex).getInvertRatio() / totalInvertRatio;
            if (parentSign >= bottomBound && parentSign < topBound) {
                return individIndex;
            }
            bottomBound = topBound;
        }
        return ++individIndex;
    }

    private void setFitScoreAndInvertRatio(Individ individ, int[] model) {
        int sum = 0;
        for (int i = 0; i < individ.getChromosome().length; i++) {
            int diff = model[i] - individ.getChromosomeI(i);
            if (diff > 0) {
                sum += diff;
            }
        }
        individ.setFitScore(sum);
        individ.setInvertRatio((individ.getFitScore() == 0) ? (1000000000) : (1.0 / individ.getFitScore()));
    }

    private List<Individ> getChildren(List<Individ> parents, int[] model) {
        List<Individ> children = new ArrayList<>(2);
        int length = parents.get(0).getChromosome().length;
        int crossPoint = random.nextInt(length - 2);
        int[] chromosome1 = new int[length];
        int[] chromosome2 = new int[length];

        for (int i = 0; i < length; i++) {
            if (i <= crossPoint) {
                chromosome1[i] = parents.get(0).getChromosomeI(i);
                chromosome2[i] = parents.get(1).getChromosomeI(i);
            } else {
                chromosome1[i] = parents.get(1).getChromosomeI(i);
                chromosome2[i] = parents.get(0).getChromosomeI(i);
            }
        }
        children.addAll(Arrays.asList(  new Individ(chromosome1, 0, 0),
                                        new Individ(chromosome2, 0, 0)));
        return children.stream().peek(this::mutate).peek(child -> setFitScoreAndInvertRatio(child, model))
                .collect(Collectors.toList());
    }

    private void mutate(Individ child) {
        double mutateSign = random.nextDouble();
        if (mutateSign < getMutateChance()) {
            int length = child.getChromosome().length;
            int index1 = random.nextInt(length);
            int index2 = random.nextInt(length);
            if (index1 != index2) {
                swap(child.getChromosome(), index1, index2);
            }
        }
    }

    private List<Individ> createNewPopulation(List<Individ> population, List<Individ> children) {
        return Stream.concat(population.stream(), children.stream()).sorted().limit(population.size())
                .collect(Collectors.toList());
    }

    @Override
    public int getNumberOfPairs() {
        return 2;
    }

    @Override
    public double getMutateChance() {
        return 0.05;
    }

    @Override
    public int getPopulationSize() {
        return 10;
    }
}
