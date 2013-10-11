package info.riemannhypothesis.math.structure;

public interface GroupAdditive<G> {

	public G negate();

	public G add(G that);

	public G subtract(G that);

}
