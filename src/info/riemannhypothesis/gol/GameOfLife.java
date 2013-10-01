package info.riemannhypothesis.gol;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class GameOfLife {

	public static void main(String[] args) {

		final int width = 51, height = 51;
		final int iterations = 1000;
		final boolean torus = true, random = false;
		final List<Point> livingCells = new ArrayList<Point>();

		if (random) {
			for (int i = 0; i < 25; i++) {
				livingCells.add(new Point((int) (Math.random() * width),
						(int) (Math.random() * height)));
			}
		} else {
			livingCells.add(new Point(width / 2, height / 2));
			livingCells.add(new Point(width / 2 + 1, height / 2 + 1));
			livingCells.add(new Point(width / 2 + 2, height / 2 + 2));
//			livingCells.add(new Point(width / 2 + 1, height / 2));
			livingCells.add(new Point(width / 2 - 1, height / 2 - 1));
//			livingCells.add(new Point(width / 2, height / 2 - 1));
		}

		State state = new State(width, height, torus, livingCells);
		// System.out.println("Initial state:");
		// System.out.println(state.toString());

		JFrame window = new JFrame("Game of Life");
		StatePanel content = new StatePanel(state, 10, 10);
		window.setContentPane(content);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(100, 75);
		window.setSize(width * 10 + 100, height * 10 + 100);
		window.setVisible(true);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		for (int i = 0; i < iterations; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			state = state.nextGeneration();
			content.setState(state);
			content.repaint();
			// System.out.println("State after " + (i + 1) + " generations:");
			// System.out.println(state.toString());
		}
	}

}
