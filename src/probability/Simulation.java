package probability;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import util.Pair;

public class Simulation {
	private boolean debug = true;
	
	private int boardsize = 10;
	private long loopStartTime = System.currentTimeMillis();		
	private int simulations = 500;
	private long runtime = 3000;
	private ArrayList<Pair<Integer, Integer>> hits = new ArrayList<Pair<Integer, Integer>>();
	private ArrayList<Pair<Integer, Integer>> misses = new ArrayList<Pair<Integer, Integer>>();
	private ArrayList<Ship> sunkenShips = new ArrayList<Ship>();
	
	private int monteCarlo[][] = new int[boardsize][boardsize];
	private ArrayList<Pair<Integer, Integer>> claimedCoordinates = new ArrayList<Pair<Integer, Integer>>();
	private Set<Pair<Integer, Integer>> coordinateSet = new HashSet<Pair<Integer, Integer>>();
	
	public Simulation(int boardsize, long loopStartTime, int simulations, long runtime, ArrayList<Pair<Integer, Integer>> hits, ArrayList<Pair<Integer, Integer>> misses, ArrayList<Ship> sunkenShips) {
		this.boardsize = boardsize;
		this.loopStartTime = loopStartTime;
		this.simulations = simulations;
		this.runtime = runtime;
		this.hits = hits;
		this.misses = misses;
		this.sunkenShips = sunkenShips;
	}
	
	public void simulate() {
		for (Ship ship : sunkenShips) {
			hits.removeAll(ship.getCoordinates());
			misses.addAll(ship.getCoordinates());
		}		
		int iterations = 0;
		boolean criteriaMatch = true;
		int percentcompletion = 0;
		int lastpercent = 0;
		
		while (((System.currentTimeMillis() - loopStartTime <= runtime)||(iterations < simulations))&&(System.currentTimeMillis() - loopStartTime <= 20000)) {
			if (debug) {
				percentcompletion = (int) (100.0*iterations/simulations);
				if ((percentcompletion > lastpercent)&&(iterations % (simulations / 100) == 0)) {
					System.out.print("\n"+percentcompletion+"% complete");
					lastpercent = percentcompletion;
					if (iterations > simulations) {
						System.out.print(" (using time left over for more calculations)");
					}
				}
			}
			
			claimedCoordinates.clear();
			coordinateSet.clear();
			
			criteriaMatch = true;
			
			boolean spawnCarrier = true;
			boolean spawnBattleship = true;
			int spawn3length = 2;
			boolean spawnPatrolBoat = true;
			
			for (Ship ship : sunkenShips) {
				switch(ship.getLength()) {
					case(5):
						spawnCarrier = false;
						break;
					case(4):
						spawnBattleship = false;
						break;
					case(3):
						spawn3length--;
						break;
					case(2):
						spawnPatrolBoat = false;
						break;
					default:
						break;
				}
			}
			
			ArrayList<Ship> shipSpawn = new ArrayList<Ship>();
			if (spawnCarrier) {
				shipSpawn.add(new Ship(5, boardsize));
			}
			if (spawnBattleship) {
				shipSpawn.add(new Ship(4, boardsize));
			}
			if (spawn3length == 2) {
				shipSpawn.add(new Ship(3, boardsize));
				shipSpawn.add(new Ship(3, boardsize));
			} else if (spawn3length == 1) {
				shipSpawn.add(new Ship(3, boardsize));
			}
			if (spawnPatrolBoat) {
				shipSpawn.add(new Ship(2, boardsize));
			}
			
			for (Ship ship : shipSpawn) {
				claimedCoordinates.addAll(ship.randomize());
			}
			
			coordinateSet = new HashSet<Pair<Integer, Integer>>(claimedCoordinates);
			
			if (coordinateSet.size() == claimedCoordinates.size()) {
				for(Pair<Integer, Integer> point : hits) {
					if(!coordinateSet.contains(point)) {
						criteriaMatch = false;
					}
				}
				
				for(Pair<Integer, Integer> point : misses) {
					if(coordinateSet.contains(point)) {
						criteriaMatch = false;
					}
				}
				
				if(criteriaMatch) {
					iterations++;
					for(Pair<Integer, Integer> point : claimedCoordinates) {
						monteCarlo[point.getElement0()][point.getElement1()] = monteCarlo[point.getElement0()][point.getElement1()] + 1;						
					}
				}
			}
		}
		
		int maxX = 0;
		int maxY = 0;
		int max = 0;
		
		if (debug) {
			System.out.print("\nDone in " + (System.currentTimeMillis() - loopStartTime) + "ms\n");
		}
		for (int y = 0; y < boardsize; y++) {
			for (int x = 0; x < boardsize; x++) {
				if (debug) {
					System.out.print((double) monteCarlo[x][y]/iterations+ "	");
				}
				if ((monteCarlo[x][y] > max)&&(monteCarlo[x][y] < iterations)){
					maxX = x;
					maxY = y;
					max = monteCarlo[x][y];
				}
			}
			if (debug) {
				System.out.print("\n");
			}
		}
		if (debug) {
			System.out.println(iterations+" valid simulations completed");
		}
		System.out.println("Highest probability: "+(int) Math.round(100.0*max/iterations)+"% at "+(maxX+1)+", "+(maxY+1));
	}
}
