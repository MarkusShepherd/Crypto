package info.riemannhypothesis.math.structure;

public interface VectorSpace<V extends VectorSpace<V, F>, F extends Field<F>>
		extends GroupAdditive<V> {

	public V multiply(F that);

}
