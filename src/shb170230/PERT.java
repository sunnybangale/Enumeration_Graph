/**
 * Implementation of PERT/CPM in a directed graph using DFS
 *
 * @author
 * Ameya Kasar      (aak170230)
 * Shreyash Mane    (ssm170730)
 * Sunny Bangale    (shb170230)
 * Ketki Mahajan    (krm150330)
 */

package shb170230;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class PERT extends GraphAlgorithm<PERT.PERTVertex> {

    //Number of critical nodes
    private int criticalCount = 0;

    //Flag to check if PERT is a DAG
    private boolean isCycle = false;

    /**
     * Finds all the nodes having slack value of 0 in the graph
     * @return number of critical nodes
     */
    public int getCriticalCount() {
        return criticalCount;
    }


    public static class PERTVertex implements Factory {

    	private int ec; // Earliest completion time of vertex
        private int lc; //  Latest completion time of vertex
        private int slack; // Slack of vertex
        private int duration; // time required to finish the vertex
        private boolean critical; // Is vertex u on a critical path?


	public PERTVertex(Vertex u) {

		this.ec = 0;
		this.lc = 0;
		this.slack = 0;
		this.duration = 0;
		this.critical = false;
	}

	public PERTVertex make(Vertex u) {
	    return new PERTVertex(u); }
    }

    public PERT(Graph g) {
	super(g, new PERTVertex(null));
    }

    public void setDuration(Vertex u, int d) {
        get(u).duration = d;
    }

    public boolean pert() {
	    return isCycle;
    }

    public int ec(Vertex u) {
	return get(u).ec;
    }

    public int lc(Vertex u) {
	return get(u).lc;
    }

    public int slack(Vertex u) {
	return get(u).slack;
    }

    /**
     * Duration required to complete the task
     * @return Duration of critical path
     */
    public int criticalPath() {

        Vertex t = g.getVertex(g.size());
        return get(t).ec;
    }

    /**
     * Returns if a vertex is critical
     * @param u the vertex whose value is needed
     * @return true if slack is 0
     */

    public boolean critical(Vertex u) {
	return get(u).critical;
    }

    /**
     * Number of critical nodes in the graph
     * @return Number of critical nodes
     */
    public int numCritical() {
	    return getCriticalCount();
    }


    /**
     * Calculates the @ec and @lc and sets all the important params  for each vertex in the graph
     * @param duration an array containing time taken to complete each task
     */
    private void pert(int[] duration){
        //Adding source and target node
        Vertex s = g.getVertex(1);
        Vertex t = g.getVertex(g.size());
        int m = g.edgeSize();
        for(int i=2; i<g.size(); i++) {
            g.addEdge(s, g.getVertex(i), 1, ++m);
            g.addEdge(g.getVertex(i), t, 1, ++m);
        }

        for (Vertex u : g){
            setDuration(u,duration[u.getIndex()]);
        }

        DFS dfs = new DFS(g);
        List<Vertex> topologicalList = dfs.topologicalOrder1();

        isCycle = dfs.isCycle;
        if(isCycle){
            return;
        }
        for(Vertex u : topologicalList)
        {
            for(Edge e: g.outEdges(u))
            {
                Vertex v = e.otherEnd(u);
                if(get(u).ec + get(v).duration > get(v).ec)
                {
                    get(v).ec = get(u).ec + get(v).duration;
                }
            }
        }

        int maxTime = get(t).ec;

        for(Vertex u: g)
        {
            if(!u.equals(g.getVertex(1))){
                get(u).lc = maxTime;
            }
        }

        Collections.reverse(topologicalList);


        for(Vertex u: topologicalList) {
            for(Edge e: g.outEdges(u))
            {
                Vertex v = e.otherEnd(u);

                if(get(v).lc - get(v).duration < get(u).lc)
                {
                    get(u).lc = get(v).lc - get(v).duration;
                }
            }
            get(u).slack = get(u).lc - get(u).ec;
            setCritical(u);

        }

    }

    /**
     * A utility method that is used to increase the count of critical nodes and sets the criticalValue for a node
     * @param u which should be made critical or not
     */
    private void setCritical(Vertex u)
    {
        if(get(u).slack == 0)
        {
            get(u).critical = true;
            criticalCount++;
        }
    }

    public static PERT pert(Graph g, int[] duration) {
    	PERT p= new PERT(g);
        p.pert(duration);
        if(p.pert()){
            return null;
        }else{
            return p;
        }
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
