/* Starter code for enumerating topological orders of a DAG
 * @author
 */

package shb170230;
import rbk.Graph;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Vertex;
import rbk.Graph.Factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

			if(get(u).inDegree == 0){
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
		print = flag;
    	init();
		DFS dfs = new DFS(g);
		List<Vertex> topologicalList = dfs.topologicalOrder1();
		Vertex[] arr = topologicalList.toArray(new Vertex[topologicalList.size()]);
		System.out.println(Arrays.toString(arr));
		if(dfs.isCycle){
			return 0;
		}
		Enumerate<Vertex> etop = new Enumerate<>(arr, sel);
		etop.permute(arr.length);


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

		String graph = "11 12   2 4 1   2 5 1   3 5 1   3 6 1   4 7 1   5 7 1   5 8 1   6 8 1   6 9 1   7 10 1   8 10 1   9 10 1      0 3 2 3 2 1 3 2 4 1 0";

		int VERBOSE = 0;
		Scanner in;
		if(args.length > 0) { VERBOSE = Integer.parseInt(args[0]); }
		try {
			in = args.length > 0 ? new Scanner(new File(args[1])) : new Scanner(graph);
			Graph g = Graph.readDirectedGraph(in);
			Graph.Timer t = new Graph.Timer();
			long result = 0;
			result = enumerateTopologicalOrders(g);
			result = countTopologicalOrders(g);
			/*if (VERBOSE > 0) {
				result = enumerateTopologicalOrders(g);
			} else {
				result = countTopologicalOrders(g);
			}*/
			System.out.println("\n" + result + "\n" + t.end());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}

