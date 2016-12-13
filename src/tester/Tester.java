package tester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 *
 * @author Martin
 */
public class Tester {

    private final int fileNumber;
    private final ArrayList<Integer> results;
    private int i = 0;
    private double maxError = 0;
    private double relativeError = 0;
    
    public Tester(int fileNumber, ArrayList<Integer> results)
    {
        this.fileNumber = fileNumber;
        this.results = results;
    }
    
    public void test()
    {
        System.out.println("MAX \t REL");
        try (Stream<String> stream = Files.lines(
                Paths.get("./solution/knap_" + fileNumber + ".sol.dat"))) {
            stream.forEach(line -> checkResult(line));
        } catch (IOException ex) {
            System.out.println("Input file error! " + ex);
        }
        System.out.println(maxError + "\t" + relativeError/50);
    }

    private void checkResult(String line)
    {
        int opt = parseLine(line);
        int apx = results.get(i++);
        double error = (double)(opt-apx)/opt;
        relativeError += error;
        if(error > maxError)
            maxError = error;        
    }
    
    
    private int parseLine(String line)
    {
        String [] data = line.split(" ");
        return Integer.parseInt(data[2]);
    }
}
