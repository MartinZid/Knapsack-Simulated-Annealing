
import solver.KnapsackSolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import tester.Tester;

/**
 *
 * @author Martin
 */
public class Main
{
    private static ArrayList<Integer> results = new ArrayList<>();
    
    /**
     * Reads file by lines and passes data to solver.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {        
        int fileNumber = 4;
        try (Stream<String> stream = Files.lines(
                Paths.get("./input/knap_" + fileNumber + ".inst.dat"))) {
            // for each line run solve given problem
            stream.forEach(line -> 
                    results.add(new KnapsackSolver(400, 0.85, 1, 2).solve(line)));
        } catch (IOException ex) {
            System.out.println("Input file error! " + ex);
        }
        new Tester(fileNumber, results).test();
    }
    
}
