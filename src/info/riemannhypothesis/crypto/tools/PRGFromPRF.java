/**
 * 
 */
package info.riemannhypothesis.crypto.tools;

/**
 * @author MarkusSchepke
 *
 */
public class PRGFromPRF extends PseudoRandomGenerator {

    private final PseudoRandomFunction prf;
    private final int numberOfBytes;

    /**
     * @param seedLength
     * @param outputLength
     */
    public PRGFromPRF(PseudoRandomFunction prf, int numberOfBytes) {
        super(prf.keyLength, numberOfBytes * prf.outputLength);
        this.prf = prf;
        this.numberOfBytes = numberOfBytes;
    }

    @Override
    public ByteSequence random(ByteSequence seed) {
        ByteSequence result = ByteSequence.EMPTY_SEQUENCE;
        for (int t = 0; t < numberOfBytes; t++) {
            ByteSequence next = prf.encrypt(seed, ByteSequence.fromInt(t, prf.inputLength));
            result = result.append(next);
        }
        return result;
    }

}
