import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private WeightedQuickUnionUF grid;
	private WeightedQuickUnionUF backwash;
	private boolean[] items;
	private int rowSize;
	private int columnSize;
	private int virtualTop;
	private int virtualBottom;
	
	public Percolation(int N) {
		if ( N < 0) {
			throw new IllegalArgumentException(Integer.toString(N));
		}
		
		grid = new WeightedQuickUnionUF(N*N + 2); // Add 2 for our virtual top/bottom
		backwash = new WeightedQuickUnionUF(N*N + 1); // Add 1 for our virtual top
		items = new boolean[N*N];
		rowSize = N;
		columnSize = N;
		virtualTop = gridIndex(N, N) + 1;
		virtualBottom = gridIndex(N, N) + 2;
	}
	
	/*
	 * Nice log method to output a grid for us
	 * "-" in between sites represent their connection to each other
	 */
	private void log(boolean logTree) {
		System.out.println("Current grid:");
		for (int i = 1; i <= columnSize; i++) {
			for (int j = 1; j <= rowSize; j ++) {
				int curr = gridIndex(i, j);
			
				System.out.print(items[gridIndex(i, j)] ? "1" : "0");
				
				// Add row connection
				if (j+1 <= rowSize) {
					int next = gridIndex(i, j+1);
					
					if (grid.connected(curr, next)) {
						System.out.print(" - ");
						continue;
					}
				}
				
				System.out.print("   ");
			}
			
			System.out.println("");
		}
	}
	
	/*
	 * Opens a site for us
	 */
	public void open(int row, int col) {
		//Get index of grid item
		int gridIndex = gridIndex(row, col);
		
		// Set to true, meaning it's open
		items[gridIndex] = true;
		
		connectToSurroundingSites(row, col);
	}
	
	/*
	 * Connects provided grid index to its surrounding sites
	 */
	private void connectToSurroundingSites(int row, int col) {
		
		int gridIndex = gridIndex(row, col);
		
		// Site is at the top, so connect to the virtual grid
		if (row == 1) {
			grid.union(virtualTop, gridIndex);
			backwash.union(virtualTop, gridIndex);
		}
		
		// At the bottom. No back-wash, because we don't track the bottom grid.
		if (row == rowSize) {
			grid.union(virtualBottom, gridIndex);
		}
		
		// Check if surrounding items are open, and connect.
		if (withinGrid(row, col-1) && isOpen(row, col-1)) {
			grid.union(gridIndex(row, col-1), gridIndex);
			backwash.union(gridIndex(row, col-1), gridIndex);
		}
		if (withinGrid(row, col+1) && isOpen(row, col+1)) {
			grid.union(gridIndex(row, col+1), gridIndex);
			backwash.union(gridIndex(row, col+1), gridIndex);
		}
		if (withinGrid(row-1, col) && isOpen(row - 1, col)) {
			grid.union(gridIndex(row-1, col), gridIndex);
			backwash.union(gridIndex(row-1, col), gridIndex);
		}
		if (withinGrid(row+1, col) && isOpen(row + 1, col)) {
			grid.union(gridIndex(row+1, col), gridIndex);
			backwash.union(gridIndex(row+1, col), gridIndex);
		}
	}
	
	/*
	 * Checks if item at grid index is open. Also checks for valid input
	 */
	public boolean isOpen(int row, int column) {
		int index = gridIndex(row, column);				
		return items[index] == true;
	}
	
	/*
	 * Tells us if the grid currently percolates.
	 */
	public boolean percolates() {
		return grid.connected(virtualTop, virtualBottom);
	}
	
	/*
	 * Checks if site is full by checking connection against sites at the top
	 */
	public boolean isFull(int row, int col) {
		// Make sure it's even open
		if(!isOpen(row, col)) return false;
		
		// Get index of the item we're checking in our grid
		int index = gridIndex(row, col);
		
		return backwash.connected(index, virtualTop);
	}
	
	public int numberOfOpenSites()  {
		int count = 0;
		
		for (boolean i: items) count += i ? 1 : 0;

		// number of open sites
		return count;
	}
	
	
	/*
	 * Provides index of item at passed row, column. -1 if not a valid input
	 * We subtract one to account for grid index being 0-indexed
	 */
	private int gridIndex(int row, int col) {
		if (!withinGrid(row, col)) {
			throw new IndexOutOfBoundsException(Integer.toString(row));
		}
		
		if (row == 1) {
			// Minus one to account for array index starting at 0
			return col - 1;
		} else {
			return  (((row-1) * rowSize) + col) - 1;
		}
	}
	
	private boolean withinGrid(int row, int col) {
		return col >= 1 && row >= 1 && col <= columnSize && row <= rowSize;
	}
	
}
