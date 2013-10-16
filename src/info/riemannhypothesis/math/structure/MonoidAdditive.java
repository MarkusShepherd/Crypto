package info.riemannhypothesis.math.structure;

public interface MonoidAdditive<M extends MonoidAdditive<M>> {

	public boolean isZero();

	public M add(M that);

}
