package Main;

import Main.BoardAction.Type;

public class AI {
	public static final int DEPTH = 5;
	
	public static Type decideNextMove(Board board) {
		if (DEPTH <= 0)
			return null;
		
		Board normalActionResult;
		double normalScore;
		double specialScore;
		double typeScore;
		double optimalScore = -1;
		Type optimalType = null;
		
		for (Type type : Type.possibleActionTypesForBoard(board)) {
			normalScore = optimalSearchRecursion(BoardAction.processAction(new BoardAction(type, false), board), DEPTH - 1);
			normalScore /= .9;
			
			specialScore = optimalSearchRecursion(BoardAction.processAction(new BoardAction(type, false), board), DEPTH - 1);
			
			typeScore = Math.max(normalScore, specialScore);
			
			if (optimalScore == -1 || typeScore > optimalScore) {
				optimalScore = typeScore;
				optimalType = type;
			}
		}
		
		return optimalType;
	}
	
	private static double optimalSearchRecursion(Board board, int depth) {
		if (depth == 0)
			return calculateScore(board);
		
		double normalScore;
		double specialScore;
		double score;
		double optimalScore = -1;
		
		for (Type type : Type.possibleActionTypesForBoard(board)) {
			normalScore = optimalSearchRecursion(BoardAction.processAction(new BoardAction(type, false), board), depth - 1);
			normalScore /= .9;
			
			specialScore = optimalSearchRecursion(BoardAction.processAction(new BoardAction(type, false), board), depth - 1);
			
			score = Math.max(normalScore, specialScore);
			
			if (optimalScore == -1 || score > optimalScore)
				optimalScore = score;
		}
		
		return optimalScore;
	}
	
	private static double calculateScore(Board board) {
		int highest = board.highestValue();
		double score = 0;
		
		int currentValue = 2;
		int currentCount;
		while (currentValue <= highest) {
			currentCount = board.numberOfValues(currentValue);
			score += currentCount * currentValue;
			
			currentValue *= 2;
		}
		
		int zeroFactor = (Board.DIMENSION * Board.DIMENSION) - board.numberOfValues(0);
		if (zeroFactor == 0)
			return 0;
		
		score /= zeroFactor;
		return score;
		
//		int distanceFromCorner = 1;
//		int xOfHighest = 0;
//		int yOfHighest = 0;
//		int currentTile;
//		
//		while (xOfHighest < Board.DIMENSION && yOfHighest < Board.DIMENSION) {
//			currentTile = board.getBoardCopy()[xOfHighest][yOfHighest];
//			if (currentTile == board.highestValue()) {
//				distanceFromCorner = 1 + Math.max(xOfHighest, yOfHighest);
//				break;
//			}
//			
//			xOfHighest++;
//			if (xOfHighest == Board.DIMENSION) {
//				xOfHighest = 0;
//				yOfHighest++;
//			}
//		}
//		
//		return score / (distanceFromCorner * distanceFromCorner);
	}
}
