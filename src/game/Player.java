package game;

import java.util.Random;

public class Player {
	private int difficulty; //0 is easy, 1 is medium, 2 is hard
	private int[][] grid;
	private Random rand;

	public Player(int difficulty) {
		this.difficulty = difficulty;
		rand = new Random();
	}

	public int[] playerTurn(int[][] grid) {
		this.grid = grid;
		int[] move = move(); //if move() is null, then it goes on
		
		if (move == null) {
			move = new int[2];
			move[0] = rand.nextInt(grid.length-3);
			move[1] = rand.nextInt(grid.length-3);
		}
		
		return move;
	}
	
	private int[] move() {
		int move[] = hasThree(2);
		if (move != null) return move;
		move = hasThree(1);
		if (move != null) return move;		
		
		boolean attack = rand.nextBoolean();
		if (attack) {
			move = probTwo(2);
			if (move != null) return move;
			move = probTwo(1);
			if (move != null) return move;
			move = probOne(2);
			if (move != null) return move;
			move = probOne(1);
			if (move != null) return move;
		}
		else {
			move = probTwo(1);
			if (move != null) return move;
			move = probTwo(2);
			if (move != null) return move;
			move = probOne(1);
			if (move != null) return move;
			move = probOne(2);
			if (move != null) return move;
		}
		
		return null;
	}

	private int[] hasThree(int user) {
		int move[] = new int[2];
		int prob = 0;
		if (difficulty == 0) prob = 60;
		else if (difficulty == 1) prob = 80;
		else prob = 100;
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == user && grid[i+1][j] == user
						&& grid[i][j+1] == user) {
					move[0] = i+1;
					move[1] = j+1;
					if (grid[move[0]][move[1]] == 0 
							&& getProb() < prob) return move;
				}
				else if (grid[i][j] == user && grid[i+1][j] == user
						&& grid[i][j-1] == user) {
					move[0] = i+1;
					move[1] = j-1;
					if (grid[move[0]][move[1]] == 0 
							&& getProb() < prob) return move;
				}
				else if (grid[i][j] == user && grid[i-1][j] == user
						&& grid[i][j+1] == user) {
					move[0] = i-1;
					move[1] = j+1;
					if (grid[move[0]][move[1]] == 0 
							&& getProb() < prob) return move;
				}
				else if (grid[i][j] == user && grid[i-1][j] == user
						&& grid[i][j-1] == user) {
					move[0] = i-1;
					move[1] = j-1;
					if (grid[move[0]][move[1]] == 0 
							&& getProb() < prob) return move;
				}
			}
		}
		return null;
	}

	private int[] probTwo(int user) {
		int[] move = new int[2];
		int numOfDots = findNumOfDots();
		int side = 0, end = 0;
		if (user == 2) {
			if (difficulty == 0) {
				side = 7;
				end = 2;
			}
			else if (difficulty == 1) {
				side = 10;
				end = 4;
			}
			else {
				side = 22;
				end = 10;
			}
		}
		else {
			if (difficulty == 0) {
				side = 5;
				end = 1;
			}
			else if (difficulty == 1) {
				side = 10;
				end = 5;
			}
			else {
				side = 15;
				end = 10;
			}
		}
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == user && grid[i+1][j] == user) {
					if (getProb() < side+1/numOfDots) {
						move[0] = i+1;
						move[1] = j+1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i+1;
						move[1] = j-1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i;
						move[1] = j+1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i;
						move[1] = j-1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < end+1/numOfDots) {
						move[0] = i+2;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < end+1/numOfDots) {
						move[0] = i-1;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
				}
				else if (grid[i][j] == user	&& grid[i][j+1] == user) {
					if (getProb() < side+1/numOfDots) {
						move[0] = i+1;
						move[1] = j+1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i-1;
						move[1] = j+1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i+1;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i-1;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < end+1/numOfDots) {
						move[0] = i;
						move[1] = j+2;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < end+1/numOfDots) {
						move[0] = i;
						move[1] = j-1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
				}
				else if (grid[i][j] == user && grid[i-1][j] == user) {
					if (getProb() < side+1/numOfDots) {
						move[0] = i-1;
						move[1] = j+1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i-1;
						move[1] = j-1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i;
						move[1] = j+1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i;
						move[1] = j-1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < end+1/numOfDots) {
						move[0] = i-2;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < end+1/numOfDots) {
						move[0] = i+1;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
				}
				else if (grid[i][j] == user && grid[i][j-1] == user) {
					if (getProb() < side+1/numOfDots) {
						move[0] = i+1;
						move[1] = j-1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i-1;
						move[1] = j-1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i+1;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i-1;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < end+1/numOfDots) {
						move[0] = i;
						move[1] = j+1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < end+1/numOfDots) {
						move[0] = i;
						move[1] = j-2;
						if (grid[move[0]][move[1]] == 0) return move;
					}
				}
			}
		}
		return null;
	}
	
	private int[] probOne(int user) {
		int[] move = new int[2];
		int numOfDots = findNumOfDots();
		int side;
		if (user == 2) {
			side = 20;
			if (difficulty == 0) {
				side = 2;
			}
			else if (difficulty == 1) {
				side = 10;
			}
			else {
				side = 20;
			}
		}
		else {
			side = 5;
			if (difficulty == 0) {
				side = 2;
			}
			else if (difficulty == 1) {
				side = 8;
			}
			else {
				side = 10;
			}
		}
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == user) {
					if (getProb() < side+1/numOfDots) {
						move[0] = i+1;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i;
						move[1] = j+1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i-1;
						move[1] = j;
						if (grid[move[0]][move[1]] == 0) return move;
					}
					else if (getProb() < side+1/numOfDots) {
						move[0] = i;
						move[1] = j-1;
						if (grid[move[0]][move[1]] == 0) return move;
					}
				}
			}
		}
		return null;
	}
	
	private double getProb() {
		int prob = rand.nextInt(10000);
		return prob/100;
	}

	private int findNumOfDots() {
		int numOfDots = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] != 0) numOfDots++;
			}
		}
		return numOfDots;
	}
}
