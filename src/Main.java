import algorithms.GeneticAlgo;
import domains.Individ;
import presetParameters.PresetParameters;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        GeneticAlgo algo = new GeneticAlgo();
        PresetParameters param = new PresetParameters();
        int[] model = param.getCostsArr();
        int[] solution = algo.executeAlgo(model, param.getCoinsArr());

        System.out.println("================================================");
        System.out.println("model: " + Arrays.toString(model));
        System.out.println("solution: " + Arrays.toString(solution));
    }
}
