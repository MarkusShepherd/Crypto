package info.riemannhypothesis.math.structure;

public interface MonoidMultiplicative<M extends MonoidMultiplicative<M>> {

	public M multiply(M that);

	public boolean isOne();

}
