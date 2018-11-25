Long Project 4 - PERT, Enumeration of topological orders

Team Number: LP 24

Team Members:
		Sunny Bangale (shb170230)
		Ketki Mahajan (krm150330)
		Ameya Kasar   (aak170230)
		Shreyash Mane (ssm170730)

Project Description:

Task 1. Enumeration of permutations.
	This enumerates the array elements with given k value. 

Task 2. Enumeration of all topological orders of a given directed graph.
	
	Explaination- We explored two methodologies for Initialization of enumerations of Topological Orders.
			a)Initialize with Indegree count 
			b)Initialize with one of the Topological Order of graph using DFS.
		We are submitting the code with method (b) as runtime for this method is less than method (a).  
		We are returing 0 if graph is not a DAG.

Task 3. Implement PERT functions as follows-
	
	public static PERT pert(Graph g, int[] duration); // Run PERT algorithm on graph
	public int ec(Vertex u);            		  // Calculate Earliest completion time of u
	public int lc(Vertex u);            		  // Calculate Latest completion time of u
	public int slack(Vertex u);         		  // Calculate Slack of u
	public int criticalPath();          		  // Calculate Length of critical path
	public boolean critical(Vertex u);  		  // Is vertex u on a critical path?
	public int numCritical();           		  // Calculate Number of critical nodes in graph

Note-
We are providing DFS.java file along with our code as PERT.java and EnumerateTopological.java uses DFS.java.


Steps to run the code:
1. Unzip the file
2. Open a java IDE like Eclipse/IntelliJ
3. Go to File  -> New  -> Project  and create a new java project 
4. Copy the java files along with the package folder  (shb170230) in the source folder
5. Run the required files according to tasks.(Enumerate.java, EnumerateTopological.java, PERT.java)
