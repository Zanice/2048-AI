package Main;

import java.util.Random;

public class Board {
	public static final int DIMENSION = 4;
	public static final int DEAD_VALUE = 0;
	public static final int NORMAL_VALUE = 2;
	public static final int SPECIAL_VALUE = 4;
	public static final double SPECIAL_SPAWN_PROBABILITY = .2;
	
	private final int[][] board;
	
	public Board(int[][] board) {
		this.board = new int[DIMENSION][DIMENSION];
		
		try {
			for (int xIndex = 0; xIndex < DIMENSION; xIndex++) {
				for (int yIndex = 0; yIndex < DIMENSION; yIndex++) {
					this.board[xIndex][yIndex] = board[xIndex][yIndex];
				}
			}
		} catch (IndexOutOfBoundsException ioobe) {
			System.err.println("ERROR: Parameter for new board had incorrect dimensions. " + ioobe.getMessage());
			System.exit(0);
		}
	}
	
	public final int[][] getBoard() {
		return board;
	}
	
	public final int[][] getBoardCopy() {
		int[][] copy = new int[DIMENSION][DIMENSION];
		
		for (int xIndex = 0; xIndex < DIMENSION; xIndex++) {
			for (int yIndex = 0; yIndex < DIMENSION; yIndex++) {
				copy[xIndex][yIndex] = board[xIndex][yIndex];
			}
		}
		
		return copy;
	}
	
	public final boolean locationInsideBoard(int x, int y) {
		boolean xInBounds = 0 <= x && x < DIMENSION;
		boolean yInBounds = 0 <= y && y < DIMENSION;
		
		return xInBounds && yInBounds;
	}
	
	public final int getBoardScore() {
		int score = 0;
		
		for (int[] row : board) {
			for (int tile : row) {
				score += tile;
			}
		}
		
		return score;
	}
	
	public final int numberOfValues(int value) {
		int count = 0;
		
		for (int[] row : board) {
			for (int tile : row) {
				if (tile == value)
					count++;
			}
		}
		
		return count;
	}
	
	public final int highestValue() {
		int highest = 0;
		
		for (int[] row : board) {
			for (int tile : row) {
				if (tile > highest)
					highest = tile;
			}
		}
		
		return highest;
	}
	
	@Override
	public String toString() {
		String output = "";
		
		for (int rowIndex = 0; rowIndex < DIMENSION; rowIndex++) {
			output += "[ ";
			
			for (int columnIndex = 0; columnIndex < DIMENSION; columnIndex++)
				output += board[columnIndex][rowIndex] + " ";
			
			output += "]";
			
			if (rowIndex != DIMENSION - 1)
				output += "\n";
		}
		
		return output;
	}
	
	public static final Board createInitialBoard() {
		Random random = new Random();
		
		//Create two initial tiles
		int indexCap = DIMENSION * DIMENSION;
		int randomIndex1 = random.nextInt(indexCap);
		int randomIndex2 = random.nextInt(indexCap);
		
		if (indexCap > 1) {
			while (randomIndex2 == randomIndex1)
				randomIndex2 = random.nextInt(indexCap);
		}
		
		int[][] board = new int[DIMENSION][DIMENSION];
		board[randomIndex1 % DIMENSION][randomIndex1 / DIMENSION] = random.nextDouble() < Board.SPECIAL_SPAWN_PROBABILITY ? SPECIAL_VALUE : NORMAL_VALUE;
		board[randomIndex2 % DIMENSION][randomIndex2 / DIMENSION] = random.nextDouble() < Board.SPECIAL_SPAWN_PROBABILITY ? SPECIAL_VALUE : NORMAL_VALUE;
		
		return new Board(board);
	}
}
