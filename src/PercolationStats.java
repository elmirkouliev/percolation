import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private int [] trialData;
	private int gridSize;
	
	/*
	 * Analyze class to run tests on Percolation grids
	 */
	public PercolationStats(int n, int trials) {
		if (n < 1)
			throw new IllegalArgumentException(Integer.toString(n));
		else if (trials < 1)
			throw new IllegalArgumentException(Integer.toString(trials));
		
		gridSize = n;
		trialData = new int[trials];
		runTests();
	}
	
	/*
	 * Runs test on inputed trial data. 
	 * Relies on Percolation classes `fillUntilPercolation` method which runs
	 * a monte carlo simulation on the grid
	 */
	private void runTests() {
		for (int i = 0; i < trialData.length; i++) {
			Percolation grid = new Percolation(gridSize);
			trialData[i] = fillUntilPercolation(grid);
		}
	}
	
	/*
	 * Fills grid until it percolates. Returns total number of open sites.
	 */
	private int fillUntilPercolation(Percolation grid) {		
		int count = 0;
		
		while (!grid.percolates()) {
			int[] site = getRandomSite(grid);
			grid.open(site[0], site[1]);
			count++;	
		}
		
		return count;
	}
	
	/*
	 * Gives a random row, column in the passed grid
	 */
	private int[] getRandomSite(Percolation grid) {
		int row = StdRandom.uniform(1, gridSize + 1);
		int column = StdRandom.uniform(1, gridSize + 1);
		
		//Wait until we get a row, column pair that isn't open
		while (grid.isOpen(row, column)) {
			row = StdRandom.uniform(1, gridSize + 1);
			column = StdRandom.uniform(1, gridSize + 1);
		}
		
		return new int[] {row, column};
	}
	
	public double mean() { 
		return StdStats.mean(trialData);
	}
	
	public double stddev(){
		return StdStats.stddev(trialData);
	}
	
	public double confidenceLo(){
		return mean() - ((1.96 * stddev()) / Math.sqrt(trialData.length));
	}
	
	public double confidenceHi(){                 
		return mean() + ((1.96 * stddev()) / Math.sqrt(trialData.length));
	}

	public static void main(String[] args) {
        int n = StdIn.readInt();
        int T = StdIn.readInt();
        
        PercolationStats test = new PercolationStats(n, T);
		System.out.println("Mean = " + test.mean());
		System.out.println("Standard Deviation = " + test.stddev());
		System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
	}

}