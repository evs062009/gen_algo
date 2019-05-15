package algorithms;

import domains.AlgoResult;
import domains.Individ;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GeneticAlgo implements IAlgo {
    private Random random = new Random();

    @Override
    public AlgoResult executeAlgo(int[] model, int[] input, int populationSize, int numberOfPairs,
                                  double mutateChance) {
        if (model.length == input.length) {

            List<Individ> population = initiatePopulation(model, input, populationSize);

            Individ betterIndivid = population.get(0);
            int terminateCounter = 0;
            int generation = 0;

            boolean resetTerminateCounter;

            while (true) {

                //
//                System.out.println();
//                System.out.println("-----------------------------------");
//                System.out.println("generation " + generation);
//                for (int i = 0; i < population.size(); i++) {
//                    System.out.println(population.get(i).toString(i));
//                }
//                System.out.println("-----------------------------------");
//                System.out.println(": average fitDeviation = " +
//                        (double) population.stream().map(Individ::getFitDeviation).reduce(0, Integer::sum) /
//                                population.size() + ", better result: " + betterIndivid.getFitDeviation() + "\n");
                //

                resetTerminateCounter = false;

                for (Individ individ : population) {
                    if (individ.getFitDeviation() == Arrays.stream(model).sum() - Arrays.stream(individ.getChromosome())
                            .sum()) {
                        return new AlgoResult(individ, generation);         //optimal solution
                    }
                    if (individ.getFitDeviation() < betterIndivid.getFitDeviation()) {
                        betterIndivid = individ;
                        resetTerminateCounter = true;
                    }
                }
                if (resetTerminateCounter) {
                    terminateCounter = 0;
                } else if (++terminateCounter > 10) {
                    return new AlgoResult(betterIndivid, generation);
                }

                population = getNextGeneration(model, population, numberOfPairs, mutateChance);
                generation++;
            }
        } else {
            throw new InvalidParameterException("Coins quantity is not equal checkpoint quantity.");
        }
    }

    private List<Individ> initiatePopulation(int[] model, int[] input, int populationSize) {
        List<Individ> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            int[] chromosome = shuffle(input);
            Individ individ = new Individ(chromosome);
            fitTest(individ, model);
            population.add(individ);
        }
        return population;
    }

    private List<Individ> getNextGeneration(int[] model, List<Individ> population, int numberOfPairs,
                                            double mutateChance) {
        List<Individ> parents;
        List<Individ> children = new ArrayList<>(numberOfPairs * 2);
        double totalInvertRatio = population.stream().map(Individ::getInvertRatio).reduce(0.0, Double::sum);

        for (int i = 0; i < numberOfPairs; i++) {
            parents = getParents(population, totalInvertRatio);
            children.addAll(getChildren(parents, model, mutateChance));
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

    private List<Individ> getParents(List<Individ> population, double totalInvertRatio) {
        List<Individ> parents = new ArrayList<>(2);
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
        return individIndex;
    }

    private void fitTest(Individ individ, int[] model) {
        int totalDeviation = 0;
        for (int i = 0; i < individ.getChromosome().length; i++) {
            int deviation = model[i] - individ.getChromosomeI(i);
            if (deviation > 0) {
                totalDeviation += deviation;
            }
        }
        individ.setFitDeviation(totalDeviation);
    }

    private List<Individ> getChildren(List<Individ> parents, int[] model, double mutateChance) {
        List<Individ> children = new ArrayList<>(2);
        int length = parents.get(0).getChromosome().length;
        int[] fatherChromosome = parents.get(0).getChromosome();
        int[] motherChromosome = parents.get(1).getChromosome();

        //
//        System.out.println("fatherChromosome: " + Arrays.toString(fatherChromosome));
//        System.out.println("motherChromosome: " + Arrays.toString(motherChromosome));
        //

        int[] chromosomeChild1 = new int[length];
        int[] chromosomeChild2 = new int[length];

        doCrossover(length, fatherChromosome, motherChromosome, chromosomeChild1, chromosomeChild2);
        fillRest(length, fatherChromosome, chromosomeChild1);
        fillRest(length, motherChromosome, chromosomeChild2);

        //
        if (Arrays.stream(chromosomeChild1).distinct().count() != chromosomeChild1.length ||
                Arrays.stream(chromosomeChild2).distinct().count() != chromosomeChild2.length) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("repeated genes!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        //

        //
//        System.out.println("chromosomeChild1: " + Arrays.toString(chromosomeChild1));
//        System.out.println("chromosomeChild2: " + Arrays.toString(chromosomeChild2));
        //

        children.addAll(Arrays.asList(new Individ(chromosomeChild1), new Individ(chromosomeChild2)));
        return children.stream().peek(child -> mutate(child, mutateChance)).peek(child -> fitTest(child, model))
                .collect(Collectors.toList());
    }

    private void doCrossover(int length, int[] fatherChromosome, int[] motherChromosome, int[] chromosomeChild1,
                             int[] chromosomeChild2) {
        int startCrossPoint = random.nextInt(length - 1);
        int endCrossPoint = startCrossPoint + random.nextInt(length - startCrossPoint);

        //
//        System.out.println("startCrossPoint: " + startCrossPoint);
//        System.out.println("endCrossPoint: " + endCrossPoint);
        //

        for (int crossIndex = startCrossPoint; crossIndex < endCrossPoint; crossIndex++) {
            chromosomeChild1[crossIndex] = motherChromosome[crossIndex];
            chromosomeChild2[crossIndex] = fatherChromosome[crossIndex];
        }
    }

    private void fillRest(int length, int[] parentChromosome, int[] childChromosome) {
        for (int childIndex = 0, parentIndex = 0; childIndex < length; ) {
            if (childChromosome[childIndex] == 0) {
                int finalParentIndex = parentIndex;
                if (IntStream.of(childChromosome).noneMatch(x -> x == parentChromosome[finalParentIndex])) {
                    childChromosome[childIndex] = parentChromosome[parentIndex];
                    childIndex++;
                }
                parentIndex++;
            } else {
                childIndex++;
            }
        }
    }

    private void mutate(Individ child, double mutateChance) {
        double mutateSign = random.nextDouble();
        if (mutateSign < mutateChance) {
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
}
