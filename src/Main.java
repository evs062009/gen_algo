import algorithms.ClassicGeneticAlgo;
import algorithms.IAlgo;
import algorithms.OnlyMutateGeneticAlgo;
import inputParameters.IInputParameters;
import inputParameters.InputParameters;
import presetParameters.IPresetParameters;
import presetParameters.PresetParameters;

public class Main {

    public static void main(String[] args) {
        IPresetParameters presetParameters = new PresetParameters();
        IInputParameters inputParameters = new InputParameters();
        IAlgo classicAlgo = new ClassicGeneticAlgo();
        IAlgo onlyMutateAlgo = new OnlyMutateGeneticAlgo();
        App app = new App(presetParameters, inputParameters, classicAlgo, onlyMutateAlgo);
        app.showResults();
    }
}
