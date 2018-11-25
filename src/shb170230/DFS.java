/** Implementation of DFS on a Graph.
 *
 * @authors
 * Ameya Kasar      (aak170230)
 * Shreyash Mane    (ssm170730)
 * Sunny Bangale    (shb170230)
 * Ketki Mahajan    (krm150330)
 */


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
     * @param g on which dfs is to performed
     */
    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        finishList = new LinkedList<>();
        isCycle = false;
    }

    /**
     * depthFirstSearch
     * static function to call DFS on Graph g
     * @param g on which dfs is to performed
     * @return object of DFS class
     */
    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        d.dfs();
        return d;
    }

    /**
     * topologicalOrder1
     * Purpose: Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
     * @param g on which dfs is to performed
     * @return List of vertices if there's no cycle otherwise null
     */
   public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    public static void main(String[] args) throws Exception {
        String string = "8 9  2 1 2  3 2 3  4 2 5  6 5 1  6 3 7  7 5 1  7 4 0  8 6 1  8 7 1 0";

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
     */
    private void dfs() {

        for (Vertex u : g) {
            get(u).vertexColor = Color.WHITE ;
            get(u).parent = null;
        }

        for (Vertex u : g)
        {
            if (get(u).vertexColor == Color.WHITE)
            {
                dfsVisit(u);
            }
        }
    }

    /**
     * dfsVisit
     * Purpose: Helper method for dfs. It recursively iterates every node of given vertex u.
     * @param u mark this node as visited
     */
    private void dfsVisit(Vertex u)
    {
        get(u).vertexColor = Color.GRAY;

        for(Graph.Edge e: g.outEdges(u))
        {
            Vertex v = e.toVertex();

            if(get(v).vertexColor == Color.WHITE)
            {
                get(v).parent = u;
                dfsVisit(v);
            }
            else
            if(get(v).vertexColor == Color.GRAY)
            {
                isCycle = true;
            }
        }

        finishList.addFirst(u);
        get(u).vertexColor = Color.BLACK;
    }


    /**
     * topologicalOrder1
     * Purpose: Member function to find topological order
     * Returns List of vertices if there's no cycle otherwise null
     */
    public List<Vertex> topologicalOrder1()
    {
        dfs();
        return isCycle ? null : finishList;
    }


    enum Color //enumeration for storing Color of vertices
    {
        WHITE, GRAY, BLACK
    }

    public static class DFSVertex implements Factory {

        Vertex parent; //stores parent of vertex
        Color vertexColor; //stores Color of vertex

        public DFSVertex(Vertex u) {
            parent = null;
            vertexColor = Color.WHITE;
        }

        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }
}