package solver;


import java.util.concurrent.ThreadLocalRandom;
import tester.StatesCounter;

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
    
    private final StatesCounter counter;
    private int id;
    
    private State bestSolution;
    private double temperature;
    private Knapsack knapsack;
    private State state;
    
    public KnapsackSolver(int startT, double a, double finalT, 
            int equilibrium, StatesCounter counter)
    {
        this.temperature = startT;
        this.startT = startT;
        this.a = a;
        this.finalT = finalT;
        this.equilibrium = equilibrium;
        this.counter = counter;
    }
    
    /**
     * Parse each line into vector, capacity, n and id.
     * 9000 4 100 18 114 42 136 88 192 3 223 =>
     * id = 9000
     * n = 4
     * capacity = 100
     * items = [[18, 144], [42, 136], [88, 192], [3, 223]] (ArrayList of Items)
     * @param line 
     */
    private void parseLine(String line)
    {
        String [] data = line.split(" ");
        this.id = Integer.parseInt(data[0]);
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
    
    /**
     * Solve knapsack problem by simulated annealing.
     * @param line
     * @return 
     */
    public int solve(String line)
    {
        parseLine(line);
        // init state (empty knapsack)
        state = new State(knapsack.getN(), knapsack.getItems());
        bestSolution = new State(knapsack.getN(), knapsack.getItems());
        
        if(id != 9550)
            return 0;
        
        while(!frozen())
        {
            // stay on this temperature for a while (equilibrium)
            for(int i = 0; equilibrium(i); i++)
            {
                System.out.println(temperature + "\t" + state.cost());
                counter.add();
                // get (new) state
                state = tryState();
                // found better solution? Save it!
                if(state.better(bestSolution)
                        && (state.weight() <= knapsack.getCapacity()))
                    bestSolution = state;
            }
            temperature = cool();
        }
        System.out.println(bestSolution.cost());
        return bestSolution.cost();
    }

    private State tryState()
    {
        // get random number from <0, n-1>
        State newState;
        int position;
        do {
            position = ThreadLocalRandom.current().nextInt(0, knapsack.getN());
            newState = new State(state);
            // get new configuration (switch one bit)
            newState.toggleBit(position);
        } while(newState.weight() > knapsack.getCapacity());
        
        if(newState.better(state))
            return newState;
        
        // newState still got change even if it is worse
        int delta = newState.cost()-state.cost();
        double x = (float)Math.random();
        if(x < Math.pow(Math.E, ((double)delta/temperature)))
            return newState;
        return state;
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
}
