package info.riemannhypothesis.math.structure;

public interface VectorSpace<V, F extends Field<F>> extends GroupAdditive<V> {

	public V multiply(F that);

}
