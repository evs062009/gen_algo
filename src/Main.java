import algorithms.GeneticAlgo;
import domains.Individ;
import presetParameters.PresetParameters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        GeneticAlgo algo = new GeneticAlgo();
        int[] arr = new int[10];
        for (int i = 1; i <= arr.length; i++) {
            arr[i] = i;
        }


//        int[] arr1 = {1,1,1,1,1,1,1,1,1,1};
//        int[] arr2 = {2,2,2,2,2,2,2,2,2,2};
//        int[] model = {1,1,1,1,1,2,2,2,2,2};

//        List<Individ> population = Arrays.asList(
//                new Individ(10),
//                new Individ(2),
//                new Individ(4),
//                new Individ(7),
//                new Individ(5),
//                new Individ(1),
//                new Individ(3),
//                new Individ(6),
//                new Individ(9),
//                new Individ(8)
//        );
//        double total = population.stream().map(Individ::getInvertRatio).reduce(0.0, Double::sum);

//        for (int i = 0; i < children.size(); i++) {
//            System.out.println("child" + i);
//            System.out.println(Arrays.toString(children.get(i).getChromosome()));
//            System.out.println("fitDeviation: " + children.get(i).getFitDeviation());
//            System.out.println("invertRatio: " + children.get(i).getInvertRatio());
//        }
    }
}
