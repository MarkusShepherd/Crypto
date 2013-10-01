package info.riemannhypothesis.gol;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StatePanel extends JPanel {

	private static final long serialVersionUID = -7977795800428230198L;

	private State state;

	private final int cellsX, cellsY, widthCell, heightCell, widthPX, heightPX;

	public StatePanel(State state, int widthCell, int heightCell) {
		this.state = state;
		this.cellsX = state.width;
		this.cellsY = state.height;
		this.widthCell = widthCell;
		this.heightCell = heightCell;
		this.widthPX = this.cellsX * this.widthCell;
		this.heightPX = this.cellsY * this.heightCell;
		setBackground(Color.WHITE);
		setSize(widthPX, heightPX);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for (int y = 0; y < cellsX; y++) {
			for (int x = 0; x < cellsY; x++) {
				if (state.cells[x][y]) {
					// g.drawRect(x * widthCell, y * heightCell, (x + 1) *
					// widthCell - 1, (y + 1) * heightCell - 1);
					g.fillRect(x * widthCell, y * heightCell, widthCell - 1,
							heightCell - 1);
				}
			}
		}
	}
}
