package domains;

public class AlgoResult {
    private Individ individ;        //the resulting individual
    private int totalGenerations;   //the number of generations, that was necessary to calculate the result

    public AlgoResult(Individ individ, int totalGenerations) {
        this.individ = individ;
        this.totalGenerations = totalGenerations;
    }

    public Individ getIndivid() {
        return individ;
    }

    public int getTotalGenerations() {
        return totalGenerations;
    }
}
