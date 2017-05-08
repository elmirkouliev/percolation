
public class QuickFind {
	
	private int[] id;
	private int[] sz;
	
	public QuickFind(int N) {
		id = new int[N];
		sz = new int[N];
		
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}	
	}
	
	public void log() {
		System.out.println("Current tree:");
		
		for (int i = 0; i < id.length; i++) {
			System.out.println("Value:" + i + " - Root:" + id[i] + " - Size:" + sz[i]);
		}
	}
	
	public int root(int i) {
		//Loop up tree
		while (i != id[i]) {
			int currentRoot = id[i];
			//Set i to the root, of its root
			id[i] = id[currentRoot];
			i = id[i];
		}
		
		return i;
	}
	
	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}
	
	public void union(int p, int q) {
		int pRoot = root(p);
		int qRoot = root(q);

		//Roots equal, don't do anything
		if(pRoot == qRoot) return;
		
		if(sz[pRoot] < sz[qRoot]) {
			id[pRoot] = qRoot;
			sz[qRoot] += sz[pRoot];
		} else {
			id[qRoot] = pRoot;
			sz[pRoot] += sz[qRoot];
		}
	}

}
