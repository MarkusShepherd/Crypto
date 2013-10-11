package info.riemannhypothesis.math.structure;

public interface GroupMultiplicative<G> {

	public G inverse();

	public G multiply(G that);

	public G divide(G that);

}
