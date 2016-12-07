
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class State {
    
    private ArrayList<Boolean> configuration;
    private final ArrayList<Item> items;
    
    public State(int n, ArrayList<Item> items)
    {
        configuration = new ArrayList<>();
        for(int i = 0; i < n; i++)
        {
            configuration.add(Boolean.FALSE);
        }
        this.items = items;
    }
    
    public State(State state)
    {
        configuration = new ArrayList<>();
        for(int i = 0; i < state.getConfiguration().size(); i++)
        {
            configuration.add(state.getConfiguration().get(i));
        }
        this.items = state.items;
    }
    
    public void toggleBit(int position)
    {
        configuration.set(position, !configuration.get(position));
    }

    public ArrayList<Boolean> getConfiguration()
    {
        return configuration;
    }
    
    public boolean better(State state)
    {
        return cost() > state.cost();
    }
    
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
}
