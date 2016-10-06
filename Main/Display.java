package Main;

import java.awt.Color;
import java.awt.Graphics;

import ZClasses.ZDisplay;

public class Display extends ZDisplay {
	public Display(int dimX, int dimY) {
		super(dimX, dimY);
	}
	
	public void syncBoard(Board board) {
		int[][] boardMatrix = board.getBoard();
		
		int id;
		int value;
		for (int xIndex = 0; xIndex < Board.DIMENSION; xIndex++) {
			for (int yIndex = 0; yIndex < Board.DIMENSION; yIndex++) {
				id = getFields().IDOf(xIndex + (yIndex * Board.DIMENSION));
				value = boardMatrix[xIndex][yIndex];
				
				getFields().get(id).setElement(value);
				switch (value) {
					case 0:
						getFields().get(id).setFieldColor(Color.WHITE);
						break;
					case 2:
						getFields().get(id).setFieldColor(new Color(200, 200, 200));
						break;
					case 4:
						getFields().get(id).setFieldColor(new Color(150, 150, 150));
						break;
					case 8:
						getFields().get(id).setFieldColor(new Color(220, 220, 190));
						break;
					case 16:
						getFields().get(id).setFieldColor(new Color(250, 250, 150));
						break;
					case 32:
						getFields().get(id).setFieldColor(new Color(220, 200, 180));
						break;
					case 64:
						getFields().get(id).setFieldColor(new Color(250, 200, 150));
						break;
					case 128:
						getFields().get(id).setFieldColor(new Color(200, 170, 150));
						break;
					case 256:
						getFields().get(id).setFieldColor(new Color(200, 150, 150));
						break;
					default:
						getFields().get(id).setFieldColor(new Color(230, 150, 150));
						break;
				}
			}
		}
	}

	@Override
	public void setup() {
		getButtons().addNewLockedButton(0, "Reset Board", 37, 225, 5, 150, 30, new Color(200, 200, 255), new Color(100, 100, 100));
		
		for (int xIndex = 0; xIndex < Board.DIMENSION; xIndex++) {
			for (int yIndex = 0; yIndex < Board.DIMENSION; yIndex++) {
				getFields().addNewLockedField(xIndex + (yIndex * Board.DIMENSION), (int) 0, 5 + (55 * xIndex), 5 + (55 * yIndex), 50, 50, Color.WHITE, Color.BLACK);
			}
		}
	}

	@Override
	public void paintDisplay(Graphics g) {
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, 500, 500);
		
		g.setColor(Color.BLACK);
		g.drawString("Score: " + Main.score, 10, 240);
	}

	@Override
	public void paintElements(Graphics g, Object obj, int x, int y, int fieldID, int fieldAssignedID) {
		int tileValue = (int) obj;
		
		g.setColor(Color.BLACK);
		g.drawString(tileValue + "", x + 10, y + 30);
	}

	@Override
	public void buttonEvent(int buttonID, int assignedID) {
		switch (assignedID) {
			case 0:
				Main.resetBoard();
				break;
		}
	}

	@Override
	public void fieldEvent(int fieldID, int assignedID) {
		
	}

	@Override
	public void textFieldTypeEvent(int textfieldID, int assignedID, String text, boolean insertion) {
		
	}

	@Override
	public void textFieldEnterEvent(int textfieldID, int assignedID, String text) {
		
	}

	@Override
	public void dropDownSelectionEvent(int dropdownlistID, int assignedID, int index, Object item) {
		
	}

	@Override
	public void typeEvent(boolean pressed, int keycode) {
		switch (keycode) {
			case 8:
				if (!pressed)
					Main.resetBoard();
				break;
			case 10:
				if (!pressed)
					Main.stepMove();
				break;
			case 37:
				if (!pressed)
					Main.moveLeft();
				break;
			case 38:
				if (!pressed)
					Main.moveUp();
				break;
			case 39:
				if (!pressed)
					Main.moveRight();
				break;
			case 40:
				if (!pressed)
					Main.moveDown();
				break;
		}
	}

	@Override
	public void onResize() {
		
	}
}
