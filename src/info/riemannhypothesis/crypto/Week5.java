package info.riemannhypothesis.crypto;

public class Week5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int x = 0; x < 23; x++) {
			int sol = (x*x + 4*x + 1) % 23;
			System.out.println("x = " + x + "; sol = " + sol);
		}
		System.out.println("x = " + ((14*14*14*14*14*14*14*14*14*14*14) % 19));
	}

}
