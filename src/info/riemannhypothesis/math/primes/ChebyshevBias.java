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

    public static double[] percent(double[] bias) {
        double total = 0;

        for (double t : bias) {
            total += t;
        }

        double[] result = new double[bias.length];

        for (int i = 0; i < bias.length; i++) {
            result[i] = bias[i] / total;
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
        
        System.out.println("Ready. Type a modulus and hit return:");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;

        try {
            while ((line = br.readLine()) != null) {

                line = line.trim();
                int mod;
                try {
                    mod = Integer.parseInt(line, 10);
                } catch (NumberFormatException e) {
                    return;
                }

                final double[] bias = cb.bias(mod);

                // System.out.println(Arrays.toString(bias));
                // System.out.println(Arrays.toString(percent(bias)));

                for (int i = 0; i < bias.length; i++) {
                    if (gcd(mod, i) == 1) {
                        System.out.format("Mod %3d: %8.4f%%%n", i,
                                percent(bias)[i] * 100);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public static int gcd(int a, int b) {
        if (b > a) {
            return gcd(b, a);
        } else if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}
