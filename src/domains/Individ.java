package domains;

public class Individ implements Comparable{
    private int[] chromosome;
    private int fitDeviation = 0;

    public Individ(int[] chromosome) {
        this.chromosome = chromosome;
    }

    //
    public Individ(int fitDeviation) {
        this.fitDeviation = fitDeviation;
    }
    //

    public int[] getChromosome() {
        return chromosome;
    }

    public int getChromosomeI(int i) {
        return chromosome[i];
    }

    public int getFitDeviation() {
        return fitDeviation;
    }

    public void setFitDeviation(int fitDeviation) {
        this.fitDeviation = fitDeviation;
    }

    public double getInvertRatio() {
        if (fitDeviation == 0){
            return 1000000.0;
        } else {
            return 1.0 / fitDeviation;
        }
    }

    @Override
    public int compareTo(Object o) {
        return fitDeviation - ((Individ) o).fitDeviation;
    }
}
