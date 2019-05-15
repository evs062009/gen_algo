package inputParameters;

import java.util.Arrays;
import java.util.Random;

/**
 * Class gives input parameters.
 * @author eshevtsov
 */
public class InputParameters implements IInputParameters{

    @Override
    public int[] getCostsArr() {
        int checkPointsNumber = 10;
        int minCoin = 1;
        int maxCoin = 10;
        int totalCostMoreThan = 55;

        Random random = new Random();
        int[] costsArr = random.ints(checkPointsNumber, minCoin, maxCoin + 1)
                .toArray();
        int needToAdd = (totalCostMoreThan + 1) - Arrays.stream(costsArr).sum();
        if (needToAdd > 0) {
            for (int i = 0; i < costsArr.length; i++) {
                int add = maxCoin - costsArr[i];
                costsArr[i] = maxCoin;
                needToAdd -= add;
                if (needToAdd <= 0){
                    break;
                }
            }
        }
        return costsArr;
    }
}
