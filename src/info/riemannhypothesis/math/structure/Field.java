package info.riemannhypothesis.math.structure;

public interface Field<F extends Field<F>> extends Ring<F>,
		GroupMultiplicative<F>, VectorSpace<F, F> {

}
