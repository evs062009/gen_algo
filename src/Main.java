import algorithms.GeneticAlgo;
import algorithms.IAlgo;
import inputParameters.IInputParameters;
import inputParameters.InputParameters;
import presetParameters.IPresetParameters;
import presetParameters.PresetParameters;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        IAlgo algo = new GeneticAlgo();
        IPresetParameters presetParameters = new PresetParameters();
        IInputParameters inputParameters = new InputParameters();
        App app = new App(algo, presetParameters, inputParameters);

        System.out.println(Arrays.toString(app.execute()));
    }
}
