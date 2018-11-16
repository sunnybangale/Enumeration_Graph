/** Starter code for SP8
 *
 *  SP8 for CS 5V81 (F18)
 *
 *  Team members:
 *  1. Sunny Bangale (shb170230)
 *  2. Utkarsh Gandhi (usg170030)
 */

// change to your netid
package shb170230;

import rbk.Graph;
import rbk.Graph.Factory;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Vertex;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex>
{

    boolean isCycle; //boolean variable to check cycle in graph
    LinkedList<Vertex> finishList; //list to store topological order
    /**
     * Constructor for DFS class
     * @param g
     */
    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        finishList = new LinkedList<>();
        isCycle = false;
    }

    /**
     * depthFirstSearch
     * static function to call DFS on Graph g
     * @param g
     * @return object of DFS class
     */
    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        d.dfs(g);
        return d;
    }

    /**
     * topologicalOrder1
     * Purpose: Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
     * @param g
     * @return List of vertices if there's no cycle otherwise null
     */
   public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    public static void main(String[] args) throws Exception {
        //String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
        //String string = "4 2  1 2 2   3 4 2 0";
        //String string = "2 0  0";
        //String string = "10 12   1 3 2   1 8 3   2 4 5   3 2 4   4 7 1   5 4 7   5 10 1   6 8 1   6 10 1   8 2 1  8 5 1  10 9 1  0";
        String string = "8 9  2 1 2  3 2 3  4 2 5  6 5 1  6 3 7  7 5 1  7 4 0  8 6 1  8 7 1 0";
        //String string = "0 0 0";

        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readGraph(in,true);
        g.printGraph(false);

        List<Vertex> topologicalList = DFS.topologicalOrder1(g);

        System.out.println();
        System.out.println("Topological Order of the Graph "+topologicalList);

    }

    /**
     * dfs
     * Purpose: Helper method for DFS. It performs Depth first search of graph
     * @param g
     */
    private void dfs(Graph g) {

        for (Vertex u : g) {
            get(u).vertexColor = color.WHITE ;
            get(u).parent = null;
        }

        for (Vertex u : g)
        {
            if (get(u).vertexColor == color.WHITE)
            {
                dfsVisit(u);
            }
        }
    }

    /**
     * dfsVisit
     * Purpose: Helper method for dfs. It recursively iterates every node of given vertex u.
     * @param u
     */
    private void dfsVisit(Vertex u)
    {
        get(u).vertexColor = color.GRAY;

        for(Graph.Edge e: g.outEdges(u))
        {
            Vertex v = e.toVertex();

            if(get(v).vertexColor == color.WHITE)
            {
                get(v).parent = u;
                dfsVisit(v);
            }
            else
            if(get(v).vertexColor == color.GRAY)
            {
                //System.out.println("Cycle detected in the graph");
                isCycle = true;
                return;
            }
        }

        finishList.addFirst(u);
        get(u).vertexColor = color.BLACK;
    }


    /**
     * topologicalOrder1
     * Purpose: Member function to find topological order
     * Returns List of vertices if there's no cycle otherwise null
     */
    public List<Vertex> topologicalOrder1()
    {
        dfs(g);
        return isCycle ? null : finishList;
    }


    enum color //enumeration for storing color of vertices
    {
        WHITE, GRAY, BLACK;
    }

    public static class DFSVertex implements Factory {

        Vertex parent; //stores parent of vertex
        color vertexColor; //stores color of vertex

        public DFSVertex(Vertex u) {
            parent = null;
            vertexColor = color.WHITE;
        }

        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }
}