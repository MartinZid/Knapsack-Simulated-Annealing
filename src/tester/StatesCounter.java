package tester;

/**
 *
 * @author Martin
 */
public class StatesCounter {

    private int statesCount = 0;
    
    public void add()
    {
        statesCount++;
    }
    public int getStatesCount()
    {
        return statesCount;
    }
}
