import algorithms.IAlgo;
import domains.AlgoResult;
import inputParameters.IInputParameters;
import presetParameters.IPresetParameters;

/**
 * Class  gets parameters, invoke the algorithm and returns solution.
 * @author eshevtsov
 */
class App {
    private IAlgo algo;
    private IPresetParameters presetParameters;
    private IInputParameters inputParameters;

    App(IAlgo algo, IPresetParameters presetParameters, IInputParameters inputParameters) {
        this.algo = algo;
        this.presetParameters = presetParameters;
        this.inputParameters = inputParameters;
    }

    int[] execute() {
        int[] costsArr = inputParameters.getCostsArr();
        int[] coinsArr = presetParameters.getCoinsArr();
        int populationSize = presetParameters.getPopulationSize();
        int numberOfPairs = presetParameters.getNumberOfPairs();
        double mutateChance = presetParameters.getMutateChance();
        AlgoResult result = algo.executeAlgo(costsArr, coinsArr, populationSize, numberOfPairs, mutateChance);
        return result.getIndivid().getChromosome();
    }
}
