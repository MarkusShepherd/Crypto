/**
 * 
 */
package info.riemannhypothesis.math.primes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MarkusSchepke
 * 
 */
public class ChebyshevBias {

    private final List<Long> primes;

    /**
     * @throws IOException
     * @throws NumberFormatException
     * 
     */
    public ChebyshevBias(InputStream is) throws NumberFormatException,
            IOException {

        primes = new LinkedList<Long>();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            primes.add(Long.parseLong(line, 10));
        }

        br.close();
    }

    public double[] bias(int mod) {
        double[] result = new double[mod];
        int[] count = new int[mod];
        int leaderCount = 0;
        HashSet<Integer> leaders = new HashSet<Integer>();

        for (long prime : primes) {
            int r = (int) (prime % mod);
            count[r]++;
            if (count[r] > leaderCount) {
                leaderCount = count[r];
                result[r]++;
                leaders.clear();
                leaders.add(r);
            } else {
                if (count[r] > leaderCount) {
                    leaders.add(r);
                }
                double inc = 1.0 / leaders.size();
                for (int leader : leaders) {
                    result[leader] += inc;
                }
            }
        }

        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ChebyshevBias cb;
        try {
            cb = new ChebyshevBias(new FileInputStream(new File(args[0])));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int mod = Integer.parseInt(args[1], 10);
        System.out.println(Arrays.toString(cb.bias(mod)));
    }

}
