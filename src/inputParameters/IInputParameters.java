package inputParameters;


public interface IInputParameters {

    /**
     * Returns the array of commission value for all checkpoints.
     * The values can repeat, but sum of all values has to be more than certain number.
     * This number is defined by the task and preset in method.
     * @return the array of commission value for all checkpoints
     */
    int[] getCostsArr();
}
