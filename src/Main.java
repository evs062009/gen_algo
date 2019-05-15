import algorithms.GeneticAlgo;
import algorithms.IAlgo;
import domains.AlgoResult;
import presetParameters.IPresetParameters;
import presetParameters.PresetParameters;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        IAlgo algo = new GeneticAlgo();
        IPresetParameters param = new PresetParameters();
        App app = new App(algo, param);
        app
    }
}
