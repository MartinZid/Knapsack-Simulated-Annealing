
import java.util.concurrent.ThreadLocalRandom;

/**
 * Solves knapsack problem with simulated annealing.
 * @author Martin
 */
public class KnapsackSolver {
    
    /*                  PARAMETRS                       */
    private final int startT;       // starting temperature
    private final double a;         //cooling coeficient
    private final double finalT;    // temperature of frozen
    private final int equilibrium;  // equilibrium coeficient
    /* ------------------------------------------------ */
    
    private State bestSolution;
    private double temperature;
    private Knapsack knapsack;
    private State state;
    
    public KnapsackSolver(int startT, double a, double finalT, int equilibrium)
    {
        this.temperature = startT;
        this.startT = startT;
        this.a = a;
        this.finalT = finalT;
        this.equilibrium = equilibrium;
    }
    
    private void parseLine(String line)
    {
        String [] data = line.split(" ");
        int id = Integer.parseInt(data[0]);
        int n = Integer.parseInt(data[1]);
        int capacity = Integer.parseInt(data[2]);
        
        knapsack = new Knapsack(id, n, capacity);
        
        
        //parse items
        for(int i = 3; i < 2*n + 3; i+=2)
        {
            int weight = Integer.parseInt(data[i]);
            int price = Integer.parseInt(data[i+1]);
            knapsack.addItem(new Item(weight, price));
        }
    }
    
    private boolean frozen()
    {
        return temperature <= finalT;
    }
    
    private boolean equilibrium(int i)
    {
        return i < (knapsack.getN() * equilibrium);
    }
    
    private double cool()
    {
        return temperature * a;
    }
    
    private State tryState()
    {
        // get random number from <0, n-1>
        int position = ThreadLocalRandom.current().nextInt(0, knapsack.getN());
        State newState = new State(state);
        newState.toggleBit(position);
        if(newState.better(state))
            return newState;
        int delta = newState.cost()-state.cost();
        double x = (float)Math.random();
        if(x < Math.pow(Math.E, ((double)delta/temperature)))
            return newState;
        return state;
    }
    
    public int solve(String line)
    {
        parseLine(line);
        // init state (empty knapsack)
        state = new State(knapsack.getN(), knapsack.getItems());
        bestSolution = new State(knapsack.getN(), knapsack.getItems());
        
        while(!frozen())
        {
            for(int i = 0; equilibrium(i); i++)
            {
                state = tryState();
                if(state.better(bestSolution)
                        && (state.weight() <= knapsack.getCapacity()))
                    bestSolution = state;
            }
            temperature = cool();
        }
        System.out.println(bestSolution.cost());
        return bestSolution.cost();
    }
}
