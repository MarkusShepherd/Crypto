/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2006 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.physics.amount;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Duration;
import javax.measure.quantity.ElectricCharge;
import javax.measure.quantity.ElectricConductance;
import javax.measure.quantity.ElectricResistance;
import javax.measure.quantity.Length;
import javax.measure.quantity.MagneticFlux;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Velocity;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import javolution.lang.MathLib;

/**
 * <p> This class provides most accurate physical constants measurement;
 *     the more accurate the constants, the higher the precision 
 *     of the calculations making use of these constants.</p>
 *     
 * <p> Constant names use the full range of Unicode characters and
 *     are mixed uppercase/lowercase to resemble symbolic names as much
 *     as possible </p>
 *
 * <p> Reference: <a href="http://physics.nist.gov/cuu/Constants/index.html">
 *     CODATA Internationally recommended values of the Fundamental Physical
 *     Constants (2002)</a></p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 3.1, April 7, 2006
 */
public final class Constants {

    /**
     * Holds the standard acceleration due to gravity (approximately equal 
     * to the acceleration due to gravity on the Earth's surface).
     * @see <a href="http://en.wikipedia.org/wiki/Acceleration_due_to_gravity">
     *      Wikipedia: Acceleration due to gravity</a>
     */
    public final static Amount<Acceleration> g = 
        Amount.valueOf(980665, SI.METRES_PER_SQUARE_SECOND).divide(100000); 

    /**
     * Holds the electron rest mass.
     */
    public final static Amount<Mass> me = 
        Amount.valueOf(9.1093826E-31, 0.0000016E-31, SI.KILOGRAM);

    /**
     * Holds the proton rest mass.
     */
    public final static Amount<Mass> mp 
            = Amount.valueOf(1.67262171E-27, 0.00000029E-27, SI.KILOGRAM);

    /**
     * Holds the neutron rest mass.
     */
    public final static Amount<Mass> mn 
    = Amount.valueOf(1.67492728E-27, 0.00000029E-27, SI.KILOGRAM);

    /**
     * Holds the deuteron rest mass.
     */
    public final static Amount<Mass> md 
    = Amount.valueOf(3.34358335E-27, 0.00000057E-27, SI.KILOGRAM);

    /**
     * Holds the muon rest mass.
     */
    public final static Amount<Mass> mŒº 
    = Amount.valueOf(1.88353140E-28, 0.00000033E-28, SI.KILOGRAM);

    /**
     * Holds the ratio of the circumference of a circle to its diameter.
     */
    public final static Amount<Dimensionless> œÄ = Amount.valueOf(MathLib.PI, Unit.ONE);

    /**
     * Holds {@link #œÄ}/2.
     */
    public final static Amount<Dimensionless> half_œÄ = Amount.valueOf(MathLib.HALF_PI, Unit.ONE);

    /**
     * Holds 2¬∑{@link #œÄ}.
     */
    public final static Amount<Dimensionless> two_œÄ = Amount.valueOf(MathLib.TWO_PI, Unit.ONE);

    /**
     * Holds 4¬∑{@link #œÄ}.
     */
    public final static Amount<Dimensionless> four_œÄ = Amount.valueOf(12.566370614359172953850573533118, Unit.ONE);

    /**
     * Holds {@link #œÄ}¬≤.
     */
    public final static Amount<Dimensionless> œÄ_square = Amount.valueOf(9.8696044010893586188344909998762, Unit.ONE);

    /**
     * Holds the speed of light in vacuum (exact).
     */
    public final static Amount<Velocity>  c = Amount.valueOf(299792458, SI.METRES_PER_SECOND);
    
    /**
     * Holds {@link #c}¬≤.
     */
    public final static Amount<?> c_square = 
        Amount.valueOf(299792458L * 299792458L, SI.METRES_PER_SECOND.pow(2));

    /**
     * Holds the Boltzmann constant.
     * @see <a href="http://en.wikipedia.org/wiki/Boltzmanns_constant">
     *      Wikipedia: Boltzmann constant</a>
     */
    public final static Amount<?> k = Amount.valueOf(
        1.3806505E-23, 0.0000024E-23, SI.JOULE.divide(SI.KELVIN));

    /**
     * Holds the Planck constant.
     * @see <a href="http://en.wikipedia.org/wiki/Plank%27s_constant">
     *      Wikipedia: Plank's constant</a>
     */
    public final static Amount<?> h = Amount.valueOf(
        6.6260693E-34, 0.0000011E-34, SI.JOULE.times(SI.SECOND));

    /**
     * Holds the Planck constant over 2œÄ.
     */
    public final static Amount<?> ħ = h.divide(two_œÄ);

    /**
     * Holds the elementary charge (positron charge).
     * @see <a href="http://en.wikipedia.org/wiki/Elementary_charge">
     *      Wikipedia: Elementary Charge</a>
     */
    public final static Amount<ElectricCharge> e =
        Amount.valueOf(
            1.60217653E-19, 0.00000014E-19, SI.COULOMB);

    /**
     * Holds the permeability of vacuum or magnetic constant.
     * @see <a href="http://en.wikipedia.org/wiki/Permeability_%28electromagnetism%29">
     *      Wikipedia: Permeability (electromagnetism)</a>
     */
    public final static Amount<?> µ0 =  Amount.valueOf(
            1.2566370614359172953850573533118E-6,
            SI.NEWTON.divide(SI.AMPERE.pow(2))); // 4œÄ√ó10‚àí7 N/A¬≤

    /**
     * Holds the permittivity of vacuum or electric constant (1/(¬µ0¬∑c¬≤))
     * @see <a href="http://en.wikipedia.org/wiki/Permittivity">
     *      Wikipedia: Permittivity</a>
     */
    public final static Amount<?> Œµ0 = µ0.times(c.pow(2)).inverse();

    /**
     * Holds the characteristic impedance of vacuum (¬µ0¬∑c).
     */
    public final static Amount<ElectricResistance> Z0 =  µ0.times(c).to(SI.OHM);

    /**
     * Holds the fine structure constant (e¬≤/(2¬∑Œµ0¬∑c¬∑h))
     * @see <a href="http://en.wikipedia.org/wiki/Fine_structure_constant">
     *      Wikipedia: Fine Structure Constant</a>
     */
    public final static Amount<Dimensionless> d
        = e.pow(2).divide(Œµ0.times(c).times(ħ).times(2)).to(Unit.ONE);

    /**
     * Holds the Newtonian constant of gravitation.
     * @see <a href="http://en.wikipedia.org/wiki/Gravitational_constant">
     *      Wikipedia: Gravitational Constant</a>
     */
    public final static Amount<?> G = Amount.valueOf(
        6.6742E-11, 0.001E-11,
        SI.METRE.pow(3).divide(SI.KILOGRAM).divide(SI.SECOND.pow(2)));

    /**
     * Holds the Avogadro constant.
     * @see <a href="http://en.wikipedia.org/wiki/Avogadro%27s_number">
     *      Wikipedia: Avogadro's number</a>
     */
    public final static Amount<?> N = Amount.valueOf(
        6.0221415E23, 0.0000010E23, Unit.ONE.divide(SI.MOLE));

    /**
     * Holds the molar gas constant (N¬∑k)
     * @see <a href="http://en.wikipedia.org/wiki/Gas_constant">
     *      Wikipedia: Gas constant</a>
     */
    public final static Amount<?> R = N.times(k);

    /**
     * Holds the Faraday constant (N¬∑e)
     * @see <a href="http://en.wikipedia.org/wiki/Faraday_constant">
     *      Wikipedia: Faraday constant</a>
     */
    public final static Amount<?> F = N.times(e);

    /**
     * Holds the Stefan-Boltzmann constant ((œÄ¬≤/60)¬∑k<sup>4</sup>/(‚Ñè¬≥¬∑c¬≤))
     */
    public final static Amount<?> œÉ
        = œÄ_square.divide(60).times(k.pow(4)).divide(ħ.pow(3).times(c.pow(2)));

    /**
     * Holds the unified atomic mass unit (0.001 kg/mol)/N
     */
    public final static Amount<Mass> amu
        =  Amount.valueOf(
            1E-3, SI.KILOGRAM.divide(SI.MOLE)).divide(N).to(SI.KILOGRAM);

    /**
     * Holds the Rydberg constant (Œ±¬≤¬∑me¬∑c/2h).
     * @see <a href="http://en.wikipedia.org/wiki/Rydberg_constant">
     *      Wikipedia: Rydberg constant</a>
     */
    public final static Amount<?> Rinf 
        // Do not use formala as experimental incertainty is very low. 
        = Amount.valueOf(10973731.568525, 0.000073, SI.METRE.inverse());
    
    /**
     * Holds the Bohr radius (Œ±/(4œÄ¬∑Rinf))
     */
    public final static Amount<Length> a0
        = g.divide(œÄ.times(Rinf).times(4)).to(SI.METRE);

    /**
     * Holds the Hartree energy (2Rinf¬∑h¬∑c)
     */
    public final static Amount<?> Eh
        =  Rinf.times(ħ).times(c).times(2);

    /**
     * Holds the magnetic flux quantum (h/2e)
     */
    public final static Amount<MagneticFlux> m
        = ħ.divide(e).divide(2).to(SI.WEBER);

    /**
     * Holds the conductance quantum (2e¬≤/h)
     */
    public final static Amount<ElectricConductance> G0
        =  e.pow(2).divide(ħ).times(2).to(ElectricConductance.UNIT);

    /**
     * Holds the Bohr magneton (‚Ñè¬∑e/2me)
     */
    public final static Amount<?> µB
        = e.times(ħ).divide(me).divide(2);

    /**
     * Holds the nuclear magneton (‚Ñè¬∑e/2mp)
     */
    public final static Amount<?> µN
        = e.times(ħ).divide(mp).divide(2);

    /**
     * Holds the Planck mass (‚Ñè¬∑c/G)<sup>1/2</sup>
     */
   public final static Amount<Mass> mP = ħ.times(c).divide(G).root(2).to(SI.KILOGRAM);

    /**
     * Holds the Planck length (‚Ñè/(mP¬∑c))
     */
    public final static Amount<Length> lP = ħ.divide(mP.times(c)).to(SI.METRE);

    /**
     * Holds the Planck time (lP/c)
     */
    public final static Amount<Duration> tP = lP.divide(c).to(SI.SECOND);

    /**
     * Default constructor (no derivation allows).
     */
    private Constants() {}
}