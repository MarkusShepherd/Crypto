package info.riemannhypothesis.math.structure;

public interface GroupMultiplicative<G extends GroupMultiplicative<G>> extends
		MonoidMultiplicative<G> {

	public G inverse();

	public G divide(G that);

}
