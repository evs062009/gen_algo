package domains;

/**
 * Class of individual, what contains chromosome array.
 * @author eshevtsov
 */
public class Individ implements Comparable{
    private int[] chromosome;           //the array of genes of individual
    private int fitDeviation = 0;       //chromosome deviation from the model

    public Individ(int[] chromosome) {
        this.chromosome = chromosome;
    }

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

    /**
     * Returns the value of inverse ratio, that determine by chromosome deviation value.
     * If chromosome deviation equals 0, the inverse ratio sets as enough big number.
     * Otherwise the inverse ratio is calculated as 1 / chromosome deviation;
     * @return the value of inverse ratio
     */
    public double getInverseRatio() {
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
