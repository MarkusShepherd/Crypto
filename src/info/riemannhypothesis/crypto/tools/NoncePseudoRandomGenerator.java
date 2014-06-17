/**
 * 
 */
package info.riemannhypothesis.crypto.tools;

/**
 * @author MarkusSchepke
 * 
 */
public abstract class NoncePseudoRandomGenerator extends PseudoRandomGenerator {

    public final ByteSequence nonce;

    public NoncePseudoRandomGenerator(int seedLength, int outputLength,
            ByteSequence nonce) {
        super(seedLength, outputLength);
        this.nonce = nonce;
    }

}
