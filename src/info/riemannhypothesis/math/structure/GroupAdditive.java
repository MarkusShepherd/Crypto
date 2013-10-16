package info.riemannhypothesis.math.structure;

public interface GroupAdditive<G extends GroupAdditive<G>> extends
		MonoidAdditive<G> {

	public G negate();

	public G subtract(G that);

}
