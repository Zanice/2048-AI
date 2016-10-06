package Main;

import java.util.Random;
import java.util.Scanner;

public class Main {
	public static Window window;
	public static Display display;
	
	public static Board board;
	public static Random random;
	
	public static int score;
	public static boolean gameEnd;
	
	public static void main(String[] args) {
		random = new Random();
		
		window = new Window("2048 AI Player");
		display = new Display(380, 500);
		
		window.add(display);
		window.centerWindow();
		
		resetBoard();
		
		//loop();
	}
	
	public static void updateDisplay() {
		score = board.getBoardScore();
		display.syncBoard(board);
		display.repaint();
	}
	
	public static void resetBoard() {
		board = Board.createInitialBoard();
		gameEnd = false;
		updateDisplay();
	}
	
	public static void loop() {
		while (!gameEnd) {
			boolean specialSpawn = random.nextDouble() < Board.SPECIAL_SPAWN_PROBABILITY;
			
			BoardAction.Type actionType = AI.decideNextMove(board);
			if (actionType == null) {
				gameEnd = true;
				break;
			}
			
			BoardAction action = new BoardAction(actionType, specialSpawn);
			board = BoardAction.processAction(action, board);
			
			updateDisplay();
		}
	}
	
	public static void stepMove() {
		boolean specialSpawn = random.nextDouble() < Board.SPECIAL_SPAWN_PROBABILITY;
		
		BoardAction.Type actionType = LearningAgent.decideNextMove(board);
		if (actionType == null)
			return;
		
		BoardAction action = new BoardAction(actionType, specialSpawn);
		board = BoardAction.processAction(action, board);
		
		updateDisplay();
	}
	
	public static void moveUp() {
		boolean specialSpawn = random.nextDouble() < Board.SPECIAL_SPAWN_PROBABILITY;
		
		BoardAction action = new BoardAction(BoardAction.Type.UP, specialSpawn);
		board = BoardAction.processAction(action, board);
		
		updateDisplay();
	}
	
	public static void moveDown() {
		boolean specialSpawn = random.nextDouble() < Board.SPECIAL_SPAWN_PROBABILITY;
		
		BoardAction action = new BoardAction(BoardAction.Type.DOWN, specialSpawn);
		board = BoardAction.processAction(action, board);
		
		updateDisplay();
	}
	
	public static void moveLeft() {
		boolean specialSpawn = random.nextDouble() < Board.SPECIAL_SPAWN_PROBABILITY;
		
		BoardAction action = new BoardAction(BoardAction.Type.LEFT, specialSpawn);
		board = BoardAction.processAction(action, board);
		
		updateDisplay();
	}
	
	public static void moveRight() {
		boolean specialSpawn = random.nextDouble() < Board.SPECIAL_SPAWN_PROBABILITY;
		
		BoardAction action = new BoardAction(BoardAction.Type.RIGHT, specialSpawn);
		board = BoardAction.processAction(action, board);
		
		updateDisplay();
	}
}
