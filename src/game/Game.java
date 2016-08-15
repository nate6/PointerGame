package game;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Game extends JFrame {
	private static final long serialVersionUID = 7059646278559620203L;

	public Game() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(806, 835);
		setLayout(null);
		setTitle("Dot Game");
		setVisible(true);
		Board board = new Board(8);
		add(board);
		board.setBoard(0); //sets difficulty (higher is harder, 0-2)
		board.repaint();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
