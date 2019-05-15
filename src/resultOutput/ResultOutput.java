package resultOutput;

import domains.AlgoResult;

public class ResultOutput implements IResultOutput {

    @Override
    public void printResult(String string, AlgoResult result) {
        System.out.println(string);
        System.out.println("Result ");
    }
}
