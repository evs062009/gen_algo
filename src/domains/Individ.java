package domains;

public class Individ implements Comparable{
    private int[] chromosome;
    private int fitScore;
    private double invertRatio;

    public Individ(int[] chromosome, int fitScore, double invertRatio) {
        this.chromosome = chromosome;
        this.fitScore = fitScore;
        this.invertRatio = invertRatio;
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

    public double getInvertRatio() {
        return invertRatio;
    }

    public void setInvertRatio(double invertRatio) {
        this.invertRatio = invertRatio;
    }

    @Override
    public int compareTo(Object o) {
        return fitScore - ((Individ) o).fitScore;
    }
}
