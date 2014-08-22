package info.riemannhypothesis.crypto.tools;

import java.util.Arrays;

public class Convert {

    public static void main(String[] args) {
        String[] tokens = args[0].trim().split("\\s+");

        if ((tokens.length % 4) != 0) {
            throw new IllegalArgumentException("Found " + tokens.length
                    + " tokens");
        }

        int l = tokens.length / 4;

        String[] t0 = Arrays.copyOfRange(tokens, 0, l);
        String[] t1 = Arrays.copyOfRange(tokens, l, 2 * l);
        String[] t2 = Arrays.copyOfRange(tokens, 2 * l, 3 * l);
        String[] t3 = Arrays.copyOfRange(tokens, 3 * l, 4 * l);

        int[] result = new int[l * 4];

        for (int i = 0; i < t0.length; i++) {
            result[2 * i] = Integer.parseInt(t0[i], 10);
            result[2 * i + 1] = Integer.parseInt(t1[i], 10);
            result[2 * l + 2 * i] = Integer.parseInt(t2[i], 10);
            result[2 * l + 2 * i + 1] = Integer.parseInt(t3[i], 10);
        }

        System.out.println(Arrays.toString(result));
        
        for(int i = 0; i < 56; i++)
        System.out.print(""+i+"," );
    }

}
