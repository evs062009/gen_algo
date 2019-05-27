package algorithms;

import domains.Individ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class realizes genetic algorithm with only mutate (100% chance, without crossover) for new generation creating.
 * @author eshevtsov
 */
public class OnlyMutateGeneticAlgo extends ClassicGeneticAlgo {

    @Override
    protected List<Individ> getChildren(List<Individ> parents, int[] model, double mutateChance) {
        int numberOfChildren = 2;
        int length = parents.get(0).getChromosome().length;

        List<Individ> children = new ArrayList<>(parents.size() * numberOfChildren);
        int[] childChromosome;

        mutateChance = 1;

        for (Individ parent : parents) {
            int[] parentChromosome = parent.getChromosome();

            for (int childIndex = 0; childIndex < numberOfChildren; childIndex++) {
                childChromosome = Arrays.copyOf(parentChromosome, length);
                mutate(childChromosome, mutateChance);
                children.add(new Individ(childChromosome));
            }
        }
        return children.stream().peek(child -> fitTest(child, model)).collect(Collectors.toList());
    }
}
