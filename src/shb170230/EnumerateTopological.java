/* Starter code for enumerating topological orders of a DAG
 * @author
 */

package shb170230;
import rbk.Graph;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Vertex;
import rbk.Graph.Factory;

import java.util.List;

public class EnumerateTopological extends GraphAlgorithm<EnumerateTopological.EnumVertex> {
    boolean print;  // Set to true to print array in visit
    long count;      // Number of permutations or combinations visited
    Selector sel;
    public EnumerateTopological(Graph g) {
	super(g, new EnumVertex());
	print = false;
	count = 0;
	sel = new Selector();
    }

    static class EnumVertex implements Factory {

    	int inDegree;

		EnumVertex() {
			this.inDegree = 0;
		}

		public EnumVertex make(Vertex u) { return new EnumVertex();	}


    }

	private void init (/*Graph g*/){
    	for(Vertex u : g){
    		get(u).inDegree = u.inDegree();
		}
	}

    class Selector extends Enumerate.Approver<Vertex> {
		@Override
		public boolean select(Vertex u) {

			if(u.inDegree() == 0){
				for(Graph.Edge x : g.outEdges(u)){
					Vertex t = x.toVertex();
					get(t).inDegree--;
				}
				return true;
			}


			return false;
		}

		@Override
		public void unselect(Vertex u) {
			for(Graph.Edge x : g.outEdges(u)){
				Vertex t = x.toVertex();
				get(t).inDegree++;
			}
		}

		@Override
		public void visit(Vertex[] arr, int k) {
			count++;
			if(print) {
			for(Vertex u: arr) {
				System.out.print(u + " ");
			}
			System.out.println();
			}
		}
    }
    
    
    // To do: LP4; return the number of topological orders of g
    public long enumerateTopological(boolean flag) {
		init();
		DFS dfs = new DFS(g);
		List<Vertex> topologicalList = dfs.topologicalOrder1();
		Vertex[] arr = topologicalList.toArray(new Vertex[topologicalList.size()]);
		if(dfs.isCycle){
			return 0;
		}
		Enumerate<Vertex> etop = new Enumerate<>(arr, sel);
		etop.permute(arr.length);

    	print = flag;
    	return count;
    }

    //-------------------static methods----------------------

    public static long countTopologicalOrders(Graph g) {
        EnumerateTopological et = new EnumerateTopological(g);
	return et.enumerateTopological(false);
    }

    public static long enumerateTopologicalOrders(Graph g) {
        EnumerateTopological et = new EnumerateTopological(g);
	return et.enumerateTopological(true);
    }

    public static void main(String[] args) {
	int VERBOSE = 0;
        if(args.length > 0) { VERBOSE = Integer.parseInt(args[0]); }
        Graph g = Graph.readDirectedGraph(new java.util.Scanner(System.in));
        Graph.Timer t = new Graph.Timer();
        long result;
	if(VERBOSE > 0) {
	    result = enumerateTopologicalOrders(g);
	} else {
	    result = countTopologicalOrders(g);
	}
        System.out.println("\n" + result + "\n" + t.end());
    }

}

