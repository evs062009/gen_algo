package domains;

public class Individ implements Comparable{

    private int[] chromosome;
    private int fitScore;

    public Individ(int[] chromosome, int fitScore) {
        this.chromosome = chromosome;
        this.fitScore = fitScore;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public int getChromosomeI(int i) {
        return chromosome[i];
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public void setChromosomeI(int gene, int i) {
        this.chromosome[i] = gene;
    }

    public int getFitScore() {
        return fitScore;
    }

    public void setFitScore(int fitScore) {
        this.fitScore = fitScore;
    }

    @Override
    public int compareTo(Object o) {
        return fitScore - ((Individ) o).fitScore;
    }
}
