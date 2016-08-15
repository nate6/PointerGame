package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {
	private static final long serialVersionUID = 3890849015970696271L;
	private Player player;
	private int[][] grid;
	private JButton[][] spots;
	private JPanel[][] dots;
	private int gridSize;
	
	public Board(int gridSize) {
		this.gridSize = gridSize;
		setBounds(0, 0, 100*gridSize, 100*gridSize);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
	}
	
	public void setBoard(int difficulty) {
		player = new Player(difficulty);
		grid = new int[gridSize+2][gridSize+2];
		spots = new JButton[gridSize][gridSize];
		for (int i = 0; i < gridSize+2; i++) {
			for (int j = 0; j < gridSize+2; j++) {
				grid[i][j] = 0;
			}
		}
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				generateSpot(i, j);
			}
		}
		dots = new JPanel[gridSize][gridSize];
	}
	
	private void generateSpot(int i, int j) {
		spots[i][j] = new JButton();
		spots[i][j].setBounds(i*100+2, j*100+2, 96, 96);
		spots[i][j].setBackground(Color.GRAY);
		spots[i][j].addActionListener(this);
		spots[i][j].setRolloverEnabled(false);
		add(spots[i][j]);
	}
	
	private void placeDot(boolean user, int pos1, int pos2) {
		dots[pos1][pos2] = new JPanel();
		dots[pos1][pos2].setBounds(25, 25, 50, 50);
		if (user) {
			dots[pos1][pos2].setBackground(Color.BLACK);
			grid[pos1+1][pos2+1] = 1;
		}
		else {
			dots[pos1][pos2].setBackground(Color.LIGHT_GRAY);
			grid[pos1+1][pos2+1] = 2;
		}
		spots[pos1][pos2].add(dots[pos1][pos2]);
	}
	
	private boolean checkEnd() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if ((grid[i][j] == 1 && grid[i+1][j] == 1
						&& grid[i][j+1] == 1 && grid[i+1][j+1] == 1)
						|| grid[i][j] == 1 && grid[i+1][j] == 1
						&& grid[i][j-1] == 1 && grid[i+1][j-1] == 1
					    || grid[i][j] == 1 && grid[i-1][j] == 1
						&& grid[i][j+1] == 1 && grid[i-1][j+1] == 1
						|| grid[i][j] == 1 && grid[i-1][j] == 1
						&& grid[i][j-1] == 1 && grid[i-1][j-1] == 1) {
					sendMessage("Game Over: You Win");
					return false;
				}
				else if ((grid[i][j] == 2 && grid[i+1][j] == 2
						&& grid[i][j+1] == 2 && grid[i+1][j+1] == 2)
						|| grid[i][j] == 2 && grid[i+1][j] == 2
						&& grid[i][j-1] == 2 && grid[i+1][j-1] == 2
					    || grid[i][j] == 2 && grid[i-1][j] == 2
						&& grid[i][j+1] == 2 && grid[i-1][j+1] == 2
						|| grid[i][j] == 2 && grid[i-1][j] == 2
						&& grid[i][j-1] == 2 && grid[i-1][j-1] == 2) {
					sendMessage("Game Over: You Lose");
					return false;
				}
			}
		}
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (dots[i][j] == null) {
					return true;
				}
			}
		}
		sendMessage("Game Over: Tie");
		return false;
	}
	
	private void sendMessage(String message) {
		JLabel end = new JLabel(message);
		end.setFont(new Font("Calibri", Font.PLAIN, 30));
		end.setBounds(gridSize*50 - (message.length()*14/2), 
				gridSize*50 - 15, message.length()*30, 30);
		end.setForeground(Color.WHITE);
		add(end);
		setComponentZOrder(end, 0);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (ae.getSource() == spots[i][j] 
						&& dots[i][j] == null && checkEnd()) {
					placeDot(true, i, j);
					
					int[] turn = player.playerTurn(grid);
					boolean works = false;
					while ((turn[0] >= 9 || turn[1] >= 9
							|| turn[0] <= 0 || turn[1] <= 0) && !works) {
						turn = player.playerTurn(grid);
						if (dots[turn[0]-1][turn[1]-1] != null && checkEnd()) works = true;
					}
					//System.out.println(""+turn[0]+turn[1]);
					if (checkEnd()) placeDot(false, turn[0]-1, turn[1]-1);
					
					checkEnd();
					repaint();
				}
			}
		}
	}
}
