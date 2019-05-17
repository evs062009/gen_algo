package domains;

/**
 * Class of resulting object.
 * @author eshevtsov
 */
public class AlgoResult {
    private Individ individ;        //the resulting individual
    private int totalGenerations;   //the number of generations, that was necessary to calculate the result
    private int populationSize;
    private int numberOfBreeding;
    private double mutateChance;

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

    public int getPopulationSize() {
        return populationSize;
    }

    public int getNumberOfBreeding() {
        return numberOfBreeding;
    }

    public double getMutateChance() {
        return mutateChance;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setNumberOfBreeding(int numberOfBreeding) {
        this.numberOfBreeding = numberOfBreeding;
    }

    public void setMutateChance(double mutateChance) {
        this.mutateChance = mutateChance;
    }

    /**
     * Check is this result better when another one, considering fitness deviation, number of generations and
     * population size.
     * @param anotherRes compared {@code AlgoResult} object
     * @return true is this result better when another one, false otherwise
     */
    public boolean isBetter(AlgoResult anotherRes){
        if (individ.getFitDeviation() < anotherRes.individ.getFitDeviation()){
            return true;
        }
        if (individ.getFitDeviation() == anotherRes.individ.getFitDeviation() &&
                totalGenerations < anotherRes.totalGenerations){
            return true;
        }
        return  (individ.getFitDeviation() == anotherRes.individ.getFitDeviation() &&
                totalGenerations == anotherRes.totalGenerations && populationSize < anotherRes.populationSize);
    }
}
