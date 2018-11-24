/* Starter code for enumerating topological orders of a DAG
 * @author
 */

package shb170230;
import rbk.Graph;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Vertex;
import rbk.Graph.Factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnumerateTopological extends GraphAlgorithm<EnumerateTopological.EnumVertex> {
    boolean print;  // Set to true to print array in visit
    long count;      // Number of permutations or combinations visited
    Selector sel;
    Vertex[] arr;
    Set<Vertex> visitedSet =  new HashSet<>();

    public EnumerateTopological(Graph g) {
	super(g, new EnumVertex());
	print = false;
	count = 0;
	sel = new Selector();
    }

    static class EnumVertex implements Factory {

		List<Vertex> outEdges = new ArrayList<>();

		EnumVertex() {

		}

	public EnumVertex make(Vertex u) { return new EnumVertex();	}
    }

    class Selector extends Enumerate.Approver<Vertex> {
	@Override
	public boolean select(Vertex u) {
	    return true;
	}

	@Override
	public void unselect(Vertex u) {
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

    //TODO
	void swap(int i, int j) {
		Vertex tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	private boolean isValid(Vertex d, Vertex i)
	{
		for(Graph.Edge v: g.outEdges(d))
		{
			if(v.toVertex().equals(i))
			{
				return false;
			}
		}
		return true;
	}

    private void enumerateAllTopological(int c)
	{
		int k = arr.length;
		if (c == 0) {
			Selector s = new Selector();
			s.visit(arr,k);
		} else {
			int d = k - c;
			enumerateAllTopological(c - 1);
			for (int i = d + 1; i < arr.length; i++) {

				if(isValid(arr[d], arr[i]))
				{
					swap(d, i);
					enumerateAllTopological(c - 1);
					swap(d, i);
				}
			}
		}
	}

    // To do: LP4; return the number of topological orders of g
    public long enumerateTopological(boolean flag) {

    	DFS d = new DFS(g);
		List<Vertex> topologicalList = d.topologicalOrder1();
		arr = topologicalList.toArray(arr);

		if(d.isCycle){
			return 0;
		}

		EnumerateTopological e = new EnumerateTopological(g);
		e.enumerateAllTopological(g.size());

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

