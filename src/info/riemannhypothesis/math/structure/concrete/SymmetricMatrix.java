package info.riemannhypothesis.math.structure.concrete;

import info.riemannhypothesis.math.structure.Field;

import java.util.ArrayList;

public class SymmetricMatrix<F extends Field<F>> extends SquareMatrix<F> {

	public SymmetricMatrix(ArrayList<ArrayList<F>> elements) {
		super(elements);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SymmetricMatrix<F> transpose() {
		return this;
	}
}
