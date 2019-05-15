package presetParameters;

public class PresetParameters implements IPresetParameters {

    @Override
    public int getCheckPointsNumber() {
        return 10;
    }

    @Override
    public int getMinCoin() {
        return 1;
    }

   @Override
    public int[] getCoinsArr() {
        int[] coinsArr = new int[getCheckPointsNumber()];
        for (int i = 0, coin = getMinCoin(); i < coinsArr.length; i++, coin++) {
            coinsArr[i] = coin;
        }
        return coinsArr;
    }

    @Override
    public int getPopulationSize() {
        return 14;
    }

    @Override
    public int getNumberOfPairs() {
        return 3;
    }

    @Override
    public double getMutateChance() {
        return 0.2;
    }
}
