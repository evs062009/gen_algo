package domains;

public class AlgoResult {
    private Individ individ;
    private int totalGenerations;

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
