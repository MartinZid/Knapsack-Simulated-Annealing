
import java.util.ArrayList;

/**
 * State represents configuration.
 * @author Martin
 */
public class State {
    
    private ArrayList<Boolean> configuration;
    private final ArrayList<Item> items;
    
    /**
     * Constructor. Creates configuration of empty knapsack.
     * @param n
     * @param items 
     */
    public State(int n, ArrayList<Item> items)
    {
        configuration = new ArrayList<>();
        for(int i = 0; i < n; i++)
        {
            configuration.add(Boolean.FALSE);
        }
        this.items = items;
    }
    
    /**
     * Copy constructor. Created deep copy of given state.
     * @param state 
     */
    public State(State state)
    {
        configuration = new ArrayList<>();
        for(int i = 0; i < state.getConfiguration().size(); i++)
        {
            configuration.add(state.getConfiguration().get(i));
        }
        this.items = state.items;
    }
    
    /**
     * Switch bit value on position.
     * @param position 
     */
    public void toggleBit(int position)
    {
        configuration.set(position, !configuration.get(position));
    }
    
    /**
     * Compares two states.
     * @param state
     * @return 
     */
    public boolean better(State state)
    {
        return cost() > state.cost();
    }
    
    /**
     * Count cost of this configuration.
     * @return cost
     */
    public int cost()
    {
        int cost = 0;
        for(int i = 0; i < configuration.size(); i++)
        {
            if(configuration.get(i))
                cost += items.get(i).getPrice();
        }
        return cost;
    }
    
    /**
     * Counts weight of this configuration.
     * @return weight
     */
    public int weight()
    {
        int weight = 0;
        for(int i = 0; i < configuration.size(); i++)
        {
            if(configuration.get(i))
                weight += items.get(i).getWeight();
        }
        return weight;        
    }

    public ArrayList<Boolean> getConfiguration()
    {
        return configuration;
    }    
}
