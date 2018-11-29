package probability;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import util.Pair;
public class Battleship {

	public static void main(String[] args) {
		int boardsize = 10;
		
		ArrayList<Pair<Integer, Integer>> hits = new ArrayList<Pair<Integer, Integer>>();
		ArrayList<Pair<Integer, Integer>> misses = new ArrayList<Pair<Integer, Integer>>();
		ArrayList<Ship> sunkenShips = new ArrayList<Ship>();
		
		/*
		sunkenShips.add(new Ship(1, 1, 7, 5, boardsize));
		
		misses.add(new Pair<Integer, Integer>(5, 4));
		misses.add(new Pair<Integer, Integer>(4, 5));
		misses.add(new Pair<Integer, Integer>(3, 3));
		misses.add(new Pair<Integer, Integer>(6, 6));
		
		hits.add(new Pair<Integer, Integer>(3, 7));
		hits.add(new Pair<Integer, Integer>(4, 7));
		hits.add(new Pair<Integer, Integer>(5, 7));
		hits.add(new Pair<Integer, Integer>(2, 7));
		hits.add(new Pair<Integer, Integer>(1, 7));
		*/
				
		int simulations = 500;
		long runtime = 3000;
		
		boolean win = false;
		Scanner inputScanner = new Scanner(System.in);
		while (win != true) {
			long loopStartTime = System.currentTimeMillis();
			Simulation simulation = new Simulation(boardsize, loopStartTime, simulations, runtime, hits, misses, sunkenShips);
			simulation.simulate();
			inputScanner.reset();
			System.out.println("Hit/miss, and coordinates?");
			String input = inputScanner.nextLine();
			String[] parsedInput = input.split(", ");
			if (parsedInput[0].toLowerCase().equals("hit")) {
				hits.add(new Pair<Integer, Integer>(Integer.parseInt(parsedInput[1])-1, Integer.parseInt(parsedInput[2])-1));
			} else if (parsedInput[0].toLowerCase().equals("miss")) {
				misses.add(new Pair<Integer, Integer>(Integer.parseInt(parsedInput[1])-1, Integer.parseInt(parsedInput[2])-1));
			}
			inputScanner.reset();
			System.out.println("Any sunken ships?");
			String sunk = inputScanner.nextLine();
			if (sunk.equals("yes")) {
				inputScanner.reset();
				System.out.println("Coordinates of one end: ");
				String coordinatesA = inputScanner.nextLine();
				inputScanner.reset();
				System.out.println("Coordinates of the other end: ");
				String coordinatesB = inputScanner.nextLine();
				int xA = Integer.parseInt(coordinatesA.split(", ")[0])-1;
				int yA = Integer.parseInt(coordinatesA.split(", ")[1])-1;
				int xB = Integer.parseInt(coordinatesB.split(", ")[0])-1;
				int yB = Integer.parseInt(coordinatesB.split(", ")[1])-1;
				int length = Math.abs(((xA-xB)+(yA-yB)))+1;
				int orientation = 0;
				if (yB < yA) {
					orientation = 0;
				} else if (xB > xA){
					orientation = 1;
				} else if (yA < yB) {
					orientation = 2;
				} else if (xA > xB) {
					orientation = 3;
				}
				sunkenShips.add(new Ship(orientation, xA, yA, length, boardsize));
			}
		}
		inputScanner.close();
	}
}
