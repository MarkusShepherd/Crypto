package info.riemannhypothesis.math.structure;

public interface VectorSpaceNormed<V extends VectorSpaceNormed<V, F>, F extends Field<F>>
		extends VectorSpace<V, F> {

	public double norm();

}
