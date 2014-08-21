package info.riemannhypothesis.crypto.tools;

/**
 * @author Markus Schepke
 * @date 20 Aug 2014
 */
public interface Function<Argument, Value> {

    public abstract Value apply(Argument arg);

}
