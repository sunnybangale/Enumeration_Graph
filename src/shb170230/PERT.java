/* Driver code for PERT algorithm (LP4)
 * @author
 */

// change package to your netid
package shb170230;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class PERT extends GraphAlgorithm<PERT.PERTVertex> {
    public static class PERTVertex implements Factory {

    	int ec;
    	int lc;
    	int slack;
    	int duration;
    	boolean critical;


	public PERTVertex(Vertex u) {

		this.ec = 0;
		this.lc = 0;
		this.slack = 0;
		this.duration = 0;
		this.critical = false;
	}

	public PERTVertex make(Vertex u) { return new PERTVertex(u); }
    }

    public PERT(Graph g) {
	super(g, new PERTVertex(null));
    }

    public void setDuration(Vertex u, int d) {
    }
    
    public boolean pert() {
	return false;
    }
    public int ec(Vertex u) {
	return 0;
    }

    public int lc(Vertex u) {
	return 0;
    }

    public int slack(Vertex u) {
	return 0;
    }

    public int criticalPath() {
	return 0;
    }

    public boolean critical(Vertex u) {
	return false;
    }

    public int numCritical() {
	return 0;
    }

    // setDuration(u, duration[u.getIndex()]);
    public static PERT pert(Graph g, int[] duration) {

    	DFS d = new DFS(g);
    	List<Vertex> topologicalList = d.topologicalOrder1();


    	for(Vertex u: topologicalList)
		{
			PERTVertex p = new PERTVertex(u);
			p.ec = 0;
		}

		for(Vertex u : d.finishList)
		{
			for(Graph.Edge e: g.outEdges(u))
			{

			}
		}


	return null;
    }
    
    public static void main(String[] args) throws Exception {
	String graph = "11 12   2 4 1   2 5 1   3 5 1   3 6 1   4 7 1   5 7 1   5 8 1   6 8 1   6 9 1   7 10 1   8 10 1   9 10 1      0 3 2 3 2 1 3 2 4 1 0";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(graph);
	Graph g = Graph.readDirectedGraph(in);
	g.printGraph(false);
	
	PERT p = new PERT(g);
	for(Vertex u: g) {
	    p.setDuration(u, in.nextInt());
	}
	// Run PERT algorithm.  Returns null if g is not a DAG
	if(p.pert()) {
	    System.out.println("Invalid graph: not a DAG");
	} else {
	    System.out.println("Number of critical vertices: " + p.numCritical());
	    System.out.println("u\tEC\tLC\tSlack\tCritical");
	    for(Vertex u: g) {
		System.out.println(u + "\t" + p.ec(u) + "\t" + p.lc(u) + "\t" + p.slack(u) + "\t" + p.critical(u));
	    }
	}
    }
}
