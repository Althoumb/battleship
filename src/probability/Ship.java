package probability;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import util.Pair;

public class Ship {
	int orientation = 0; // 0 = vertical up, 1 = right, 2 = down, 3 = left
	int xPos = 0;
	int yPos = 0;
	int length = 4;
	int boardsize = 10;
	ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<Pair<Integer, Integer>>();
	
	public Ship(int orientation, int xPos, int yPos, int length, int boardsize) {
		this.orientation = orientation; //0 = vertical up, 1 = right, 2 = down, 3 = left
		this.xPos = xPos;
		this.yPos = yPos;
		this.length = length;
		this.boardsize = boardsize;
	}
	
	public Ship(int length, int boardsize) {
		this.length = length;
		this.boardsize = boardsize;
	}
	
	public int getLength() {
		return length;
	}
	
	public ArrayList<Pair<Integer, Integer>> getCoordinates(){
		switch (orientation) {
			case(0):
				for (int i = 0; i < length; i++) {
					coordinates.add(new Pair<Integer, Integer>(xPos, yPos-i));
				}
				break;
			case(1):
				for (int i = 0; i < length; i++) {
					coordinates.add(new Pair<Integer, Integer>(xPos+i, yPos));
				}
				break;
			case(2):
				for (int i = 0; i < length; i++) {
					coordinates.add(new Pair<Integer, Integer>(xPos, yPos+i));
				}
				break;
			case(3):
				for (int i = 0; i < length; i++) {
					coordinates.add(new Pair<Integer, Integer>(xPos-i, yPos));
				}
				break;
			default:
				break;		
		}
		return coordinates;
	}
	
	public ArrayList<Pair<Integer, Integer>> randomize() {		
		boolean complete = false;
		while (!complete) {
			coordinates.clear();
			xPos = ThreadLocalRandom.current().nextInt(0, boardsize - 1 + 1);
			yPos = ThreadLocalRandom.current().nextInt(0, boardsize - 1 + 1);
			orientation = ThreadLocalRandom.current().nextInt(0, 3 + 1);
			switch (orientation) {
				case(0):
					for (int i = 0; i < length; i++) {
						coordinates.add(new Pair<Integer, Integer>(xPos, yPos-i));
					}
					break;
				case(1):
					for (int i = 0; i < length; i++) {
						coordinates.add(new Pair<Integer, Integer>(xPos+i, yPos));
					}
					break;
				case(2):
					for (int i = 0; i < length; i++) {
						coordinates.add(new Pair<Integer, Integer>(xPos, yPos+i));
					}
					break;
				case(3):
					for (int i = 0; i < length; i++) {
						coordinates.add(new Pair<Integer, Integer>(xPos-i, yPos));
					}
					break;
				default:
					break;		
			}
			complete = true;
			for (Pair<Integer, Integer> point : coordinates) {
				if ((point.getElement0() > boardsize - 1)||(point.getElement0() < 0)||(point.getElement1() > boardsize - 1)||(point.getElement1() < 0)) {
					complete = false;
				}
			}
		}
		return coordinates;
	}
}
