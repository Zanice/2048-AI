package Main;

import java.util.Random;

public class BoardAction {
	public enum Type {
		UP		(0, -1),
		DOWN	(0, 1),
		LEFT	(-1, 0),
		RIGHT	(1, 0);
		
		private final int stepChangeX;
		private final int stepChangeY;
		
		private Type(int stepChangeX, int stepChangeY) {
			this.stepChangeX = stepChangeX;
			this.stepChangeY = stepChangeY;
		}
		
		public final int getStepChangeX() {
			return stepChangeX;
		}
		
		public final int getStepChangeY() {
			return stepChangeY;
		}
		
		public static final Type[] possibleActionTypesForBoard(Board board) {
			Type[] actions = Type.values();
			Type[] possibleActions = new Type[actions.length];
			
			Type actionType;
			for (int actionIndex = 0; actionIndex < actions.length; actionIndex++) {
				actionType = actions[actionIndex];
				if (canBePerformed(actionType, board))
					possibleActions[actionIndex] = actionType;
			}
			
			int resultLength = 0;
			for (int index = 0; index < possibleActions.length; index++) {
				if (possibleActions[index] != null)
					resultLength++;
			}
			
			Type[] result = new Type[resultLength];
			int resultIndex = 0;
			for (int index = 0; index < possibleActions.length; index++) {
				if (possibleActions[index] != null)
					result[resultIndex++] = possibleActions[index];
			}
			
			return result;
		}
		
		public static final boolean canBePerformed(Type actionType, Board board) {
			int boardDimension = Board.DIMENSION;
			int[][] boardMatrix = board.getBoard();
			int compX;
			int compY;
			int currentValue;
			int compValue;
			
			for (int xIndex = 0; xIndex < boardDimension; xIndex++) {
				for (int yIndex = 0; yIndex < boardDimension; yIndex++) {
					compX = xIndex + actionType.stepChangeX;
					compY = yIndex + actionType.stepChangeY;
					
					if (!board.locationInsideBoard(compX, compY))
						continue;
					
					currentValue = boardMatrix[xIndex][yIndex];
					
					if (currentValue == Board.DEAD_VALUE)
						continue;
					
					compValue = boardMatrix[compX][compY];
					
					if (compValue == Board.DEAD_VALUE || compValue == currentValue)
						return true;
				}
			}
			
			return false;
		}
	}
	
	private final Type type;
	private final boolean spawnSpecial;
	
	public BoardAction(Type type, boolean spawnSpecial) {
		this.type = type;
		this.spawnSpecial = spawnSpecial;
	}
	
	public final Type getType() {
		return type;
	}
	
	public final boolean willSpawnSpecial() {
		return spawnSpecial;
	}
	
	public static final Board processAction(BoardAction action, Board board) {
		if (!Type.canBePerformed(action.type, board))
			return board;
		
		int minConsiderX = 0;
		int maxConsiderX = Board.DIMENSION - 1;
		int minConsiderY = 0;
		int maxConsiderY = Board.DIMENSION - 1;
		
		switch (action.type) {
			case UP:
				minConsiderY++;
				break;
			case DOWN:
				maxConsiderY--;
				break;
			case LEFT:
				minConsiderX++;
				break;
			case RIGHT:
				maxConsiderX--;
				break;
		}
		
		int[][] boardCopy = board.getBoardCopy();
		int currentValue;
		int compX;
		int compY;
		int compValue = 0;
		
		if (action.type == Type.UP || action.type == Type.LEFT) {
			for (int xIndex = minConsiderX; xIndex <= maxConsiderX; xIndex++) {
				for (int yIndex = minConsiderY; yIndex <= maxConsiderY; yIndex++) {
					currentValue = boardCopy[xIndex][yIndex];
					
					if (currentValue == Board.DEAD_VALUE)
						continue;
					
					compX = xIndex + action.type.stepChangeX;
					compY = yIndex + action.type.stepChangeY;
					
					while (board.locationInsideBoard(compX, compY)) {
						compValue = boardCopy[compX][compY];
						
						if (compValue != Board.DEAD_VALUE)
							break;
						
						compX += action.type.stepChangeX;
						compY += action.type.stepChangeY;
					}
					
					boardCopy[xIndex][yIndex] = 0;
					if (!board.locationInsideBoard(compX, compY)) {
						compX -= action.type.stepChangeX;
						compY -= action.type.stepChangeY;
						boardCopy[compX][compY] = currentValue;
					}
					else {
						if (currentValue == compValue)
							boardCopy[compX][compY] = compValue + currentValue;
						else {
							compX -= action.type.stepChangeX;
							compY -= action.type.stepChangeY;
							boardCopy[compX][compY] = currentValue;
						}
					}
				}
			}
		}
		else {
			for (int xIndex = maxConsiderX; xIndex >= minConsiderX; xIndex--) {
				for (int yIndex = maxConsiderY; yIndex >= minConsiderY; yIndex--) {
					currentValue = boardCopy[xIndex][yIndex];
					
					if (currentValue == Board.DEAD_VALUE)
						continue;
					
					compX = xIndex + action.type.stepChangeX;
					compY = yIndex + action.type.stepChangeY;
					
					while (board.locationInsideBoard(compX, compY)) {
						compValue = boardCopy[compX][compY];
						
						if (compValue != Board.DEAD_VALUE)
							break;
						
						compX += action.type.stepChangeX;
						compY += action.type.stepChangeY;
					}
					
					boardCopy[xIndex][yIndex] = 0;
					if (!board.locationInsideBoard(compX, compY)) {
						compX -= action.type.stepChangeX;
						compY -= action.type.stepChangeY;
						boardCopy[compX][compY] = currentValue;
					}
					else {
						if (currentValue == compValue)
							boardCopy[compX][compY] = compValue + currentValue;
						else {
							compX -= action.type.stepChangeX;
							compY -= action.type.stepChangeY;
							boardCopy[compX][compY] = currentValue;
						}
					}
				}
			}
		}
		
		//Spawn random
		int[][] openIndeces = new int[Board.DIMENSION * Board.DIMENSION][2];
		int openIndexCount = 0;
		
		for (int xIndex = 0; xIndex < Board.DIMENSION; xIndex++) {
			for (int yIndex = 0; yIndex < Board.DIMENSION; yIndex++) {
				if (boardCopy[xIndex][yIndex] == Board.DEAD_VALUE) {
					openIndeces[openIndexCount][0] = xIndex;
					openIndeces[openIndexCount][1] = yIndex;
					openIndexCount++;
				}
			}
		}
		
		Random random = new Random();
		int randomOpenIndex = random.nextInt(openIndexCount);
		int randomX = openIndeces[randomOpenIndex][0];
		int randomY = openIndeces[randomOpenIndex][1];
		
		if (action.spawnSpecial)
			boardCopy[randomX][randomY] = Board.SPECIAL_VALUE;
		else
			boardCopy[randomX][randomY] = Board.NORMAL_VALUE;
		
		Board result = new Board(boardCopy);
		return result;
	}
}
