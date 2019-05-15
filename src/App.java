import algorithms.IAlgo;
import domains.AlgoResult;
import presetParameters.IPresetParameters;

public class App {
    private IAlgo algo;
    private IPresetParameters param;

    public App(IAlgo algo, IPresetParameters param) {
        this.algo = algo;
        this.param = param;
    }

    public int[] execute() {
        int[] costsArr = param.getCostsArr();
        int[] coinsArr = param.getCoinsArr();
        int populationSize = param.getPopulationSize();
        int numberOfPairs = param.getNumberOfPairs();
        double mutateChance = param.getMutateChance();
        AlgoResult result = algo.executeAlgo(costsArr, coinsArr, populationSize, numberOfPairs, mutateChance);
        return result.getIndivid().getChromosome();
    }
}
