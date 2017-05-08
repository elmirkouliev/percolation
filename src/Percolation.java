import java.util.Arrays;
import java.util.Random;

public class Percolation {
	
	QuickFind grid;
	int[] items;
	int rowSize;
	int columnSize;
	Random r;
	
	public Percolation(int N) {
		grid = new QuickFind(N*N);
		items = new int[N*N];
		rowSize = N;
		columnSize = N;
		r = new Random();
	}
	
	//Nice log method to output a grid for us
	//"-" in between sites represent their connection to each other
	public void log(boolean logTree) {
		System.out.println("Current grid:");
		for(int i = 1; i <= columnSize; i++) {
			for(int j = 1; j <= rowSize; j ++) {
				int curr = gridIndex(i, j);
			
				System.out.print(items[gridIndex(i, j)]);
				
				//Add row connection
				if(j+1 <= rowSize) {
					int next = gridIndex(i, j+1);
					
					if(grid.connected(curr, next)) {
						System.out.print(" - ");
						continue;
					}
				}
				
				System.out.print(" x ");
			}
			
			System.out.println("");
		}
		
		if(logTree)
			grid.log();
	}
	
	public void open(int row, int col) {
		//Get index of grid item
		int gridIndex = gridIndex(row, col);
		
		if(gridIndex < 0) {
			throw new IllegalArgumentException(Integer.toString(gridIndex));
		}
		
		//Set to 1, meaning it's open
		items[gridIndex] = 1;
		
		//Check if surrounding items are open, and connect.
		//Max of 4 surrounding elements
		int leftItem = gridIndex(row, col-1);
		int rightItem = gridIndex(row, col+1);
		int topItem = gridIndex(row - 1, col);
		int bottomItem = gridIndex(row + 1, col);
		
		//Connect are site all of it's surrounding sites
		if(isOpen(topItem)) grid.union(topItem, gridIndex);
		if(isOpen(leftItem)) grid.union(leftItem, gridIndex);
		if(isOpen(rightItem)) grid.union(rightItem, gridIndex);
		if(isOpen(bottomItem)) grid.union(bottomItem, gridIndex);
	}
	
	//Checks if item at grid index is open. Also checks for valid input
	public boolean isOpen(int gridIndex) {
		//Check if valid item
		if(gridIndex < 0 || gridIndex >= items.length) {
			return false;
		}
				
		return items[gridIndex] == 1;
	}
	
	public int[] rowColumnForIndex(int index) {
		if(index > items.length) return new int[] {-1, -1};
		
		int row = (int) Math.floor((index / columnSize)) + 1;
		int column = index % columnSize + 1;
		
		return new int[] {row, column};
	}
	
	//Provides index of item at passed row, column. -1 if not a valid input
	public int gridIndex(int row, int col) {
		if(col < 1 || row < 1 || col > columnSize || row > rowSize) 
			return -1;
		
		if(row == 1) {
			//Minus one to account for array index starting at 0
			return col - 1;
		} else {
			return  (((row-1) * rowSize) + col) - 1;
		}
	}
	
	public boolean isFull(int row, int col) {
		//Make sure it's even open
		if(!isOpen(gridIndex(row, col))) return false;
		
		//Get index of the item we're checking in our grid
		int index = gridIndex(row, col);
		
		//Loop over top row and see if any of them connect
		for(int i = 1; i < columnSize; i++) {
			//1 is the first row
			if(grid.connected(index, gridIndex(1, i))) {
				return true;
			}
		}
		
		return false;
	}
	
	public int numberOfOpenSites()  {
		int count = 0;
		
		for (int i: items) count += i == 1 ? 1 : 0;

		// number of open sites
		return count;
	}
	
	public boolean percolates() {
		//Set row to last
		int row = rowSize;
		
		//Loop over bottom row grid items
		for(int i = 1; i <= columnSize; i++) {
			//Check if any are full
			if(isFull(row, i)) 
				return true;
		}
		
		return false;
	}
	
	//Fills grid until it percolates. Returns total number of open sites.
	public int fillUntilPercolation() {
		int min = 1;
		int row = r.nextInt((rowSize - min) + 1) + min;
		int column = r.nextInt((columnSize - min) + 1) + min;
		open(row, column);
		
		if(!percolates()) {
			return fillUntilPercolation();
		} else{
			return numberOfOpenSites();
		}
	}
	
	public static void main(String[] args) {		
		Percolation test = new Percolation(20);
		test.fillUntilPercolation();
	}
}
