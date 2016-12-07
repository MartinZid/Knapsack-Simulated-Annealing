
import java.util.ArrayList;



/**
 *
 * @author Martin
 */
public class Knapsack {

    private final int id;
    private final int n;
    private final int capacity;
    private final ArrayList<Item> items;
    
    public Knapsack(int id, int n, int capacity)
    {
        this.id = id;
        this.n = n;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }
    
    public void addItem(Item item)
    {
        this.items.add(item);
    }
    
    public ArrayList<Item> getItems()
    {
        return items;
    }
    
    public int getN()
    {
        return n;
    }
    
    public int getCapacity()
    {
        return capacity;
    }
    
}
