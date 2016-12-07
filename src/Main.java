
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author Martin
 */
public class Main
{
    /**
     * Reads file by lines and passes data to solver.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {        
        try (Stream<String> stream = Files.lines(Paths.get("./input/knap_4.inst.dat"))) {
            // for each line run solve given problem
            stream.forEach(line -> new KnapsackSolver(400, 0.95, 1, 2).solve(line));
        } catch (IOException ex) {
            System.out.println("Input file error! " + ex);
        }
    }
    
}
