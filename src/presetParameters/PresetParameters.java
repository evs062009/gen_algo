package presetParameters;

import java.util.Arrays;
import java.util.Random;

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
    public int getMaxCoin() {
        return 10;
    }

    @Override
    public int totalCostMoreThan() {
        return 55;
    }

    @Override
    public int[] getCostsArr() {
        Random random = new Random();
        int[] costsArr = random.ints(getCheckPointsNumber(), getMinCoin(), getMaxCoin() + 1)
                .toArray();
        int needToAdd = (totalCostMoreThan() + 1) - Arrays.stream(costsArr).sum();
        if (needToAdd > 0) {
            for (int i = 0; i < costsArr.length; i++) {
                int add = getMaxCoin() - costsArr[i];
                costsArr[i] = getMaxCoin();
                needToAdd -= add;
                if (needToAdd <= 0){
                    break;
                }
            }
        }
        return costsArr;
    }

    @Override
    public int[] getCoinsArr() {
        int[] coinsArr = new int[getCheckPointsNumber()];
        for (int i = 0, coin = getMinCoin(); i < coinsArr.length; i++, coin++) {
            coinsArr[i] = coin;
        }
        return coinsArr;
    }
}
