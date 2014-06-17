/**
 * 
 */
package info.riemannhypothesis.crypto.tools;

/**
 * @author MarkusSchepke
 * 
 */
public abstract class PseudoRandomGenerator {

    public final int seedLength;
    public final int outputLength;

    public PseudoRandomGenerator(int seedLength, int outputLength) {
        this.seedLength = seedLength;
        this.outputLength = outputLength;
    }

    public abstract ByteSequence random(ByteSequence seed);
}
