import StdStats;

public class PercolationAnalyze {
	
	int [] trialData;
	int gridSize;
	
	public PercolationAnalyze(int n, int trials) {
		if(n < 0)
			throw new IllegalArgumentException(Integer.toString(n));
		else if(trials < 0)
			throw new IllegalArgumentException(Integer.toString(n));
		
		// perform trials independent experiments on an n-by-n grid
		gridSize = n;
		trialData = new int[trials];
		runTests();
	}
	
	public void runTests() {
		for(int i = 0; i < trialData.length; i++) {
			Percolation grid = new Percolation(gridSize);
			trialData[i] = grid.fillUntilPercolation();
		}
	}
	
	//Calculate sum
	public double mean() { 
		
		return StdStats.mean(trialData);
		
		int sum = 0;

		for(int i: trialData) {
			sum += i;
		}
		
		return sum / trialData.length;
	}
	
	public double stddev(){
		int sum = 0;
		double mean = mean();

		for(int i: trialData) {
			sum += i - mean;
		}
		
		return Math.sqrt(sum / (double)trialData.length);
	}
	
	public double confidenceLo(){
		// low  endpoint of 95% confidence interval
		return 0.0;
	}
	public double confidenceHi(){                 // high endpoint of 95% confidence interval
		return 0.0;
	}
	
	public static void main(int n, int trials) {
		PercolationAnalyze test = new PercolationAnalyze(n, trials);
		System.out.println(test.mean());
		System.out.println(test.stddev());
	}

	public static void main(String[] args) {
		main(20, 20);
	}

}