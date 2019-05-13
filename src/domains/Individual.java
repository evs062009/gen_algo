package domains;

public class Individual {

    private int [] chromosome;
    private int fitScore;

    public Individual(int[] chromosome, int fitScore) {
        this.chromosome = chromosome;
        this.fitScore = fitScore;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public int getFitScore() {
        return fitScore;
    }

    public void setFitScore(int fitScore) {
        this.fitScore = fitScore;
    }
}
