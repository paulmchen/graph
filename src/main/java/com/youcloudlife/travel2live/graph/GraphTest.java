package com.youcloudlife.travel2live.graph;

import java.net.MalformedURLException;
import java.net.URL;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

/**
 * Test Jgrapht libraries
 *
 */
public class GraphTest {

	// ~ Constructors
	public GraphTest() {

	}

	public static void main(String[] args) {
		UndirectedGraph<String, DefaultEdge> stringGraph = createStringGraph();

		// note undirected edges are printed as: {<v1>,<v2>}
		System.out.println(stringGraph.toString());

		// create a graph based on URL objects
		DirectedGraph<URL, DefaultEdge> hrefGraph = createHrefGraph();

		// note directed edges are printed as: (<v1>,<v2>)
		System.out.println(hrefGraph.toString());
		
		// weight graph test
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> hreWeightGraph = createHrefWeightGraph();
		System.out.println(hreWeightGraph.toString());
		
		// traverse the vertex
		GraphIterator<String, DefaultWeightedEdge> iterator = 
                new DepthFirstIterator<String, DefaultWeightedEdge>(hreWeightGraph);
        
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
       
	}

	/***
	 ** Creates a toy directed weight graph based on URL objects that represents link
	 ** structure.
	 **
	 ** @return a graph based on URL objects.
	 **/
	private static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> createHrefWeightGraph() {

		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		g.addVertex("vertex1");
		g.addVertex("vertex2");
		g.addVertex("vertex3");
		g.addVertex("vertex4");
		g.addVertex("vertex5");

		DefaultWeightedEdge e1 = g.addEdge("vertex1", "vertex2");
		g.setEdgeWeight(e1, 5);

		DefaultWeightedEdge e2 = g.addEdge("vertex2", "vertex3");
		g.setEdgeWeight(e2, 3);

		DefaultWeightedEdge e3 = g.addEdge("vertex4", "vertex5");
		g.setEdgeWeight(e3, 6);

		DefaultWeightedEdge e4 = g.addEdge("vertex2", "vertex4");
		g.setEdgeWeight(e4, 2);

		DefaultWeightedEdge e5 = g.addEdge("vertex5", "vertex4");
		g.setEdgeWeight(e5, 4);

		DefaultWeightedEdge e6 = g.addEdge("vertex2", "vertex5");
		g.setEdgeWeight(e6, 9);

		DefaultWeightedEdge e7 = g.addEdge("vertex4", "vertex1");
		g.setEdgeWeight(e7, 7);

		DefaultWeightedEdge e8 = g.addEdge("vertex3", "vertex2");
		g.setEdgeWeight(e8, 2);

		DefaultWeightedEdge e9 = g.addEdge("vertex1", "vertex3");
		g.setEdgeWeight(e9, 10);

		DefaultWeightedEdge e10 = g.addEdge("vertex3", "vertex5");
		g.setEdgeWeight(e10, 1);

		System.out.println("Shortest path from vertex1 to vertex5:");
		GraphPath<String, DefaultWeightedEdge> shortest_path =  DijkstraShortestPath.findPathBetween(g, "vertex1", "vertex5");
		System.out.println(shortest_path);

		return g;
	}

	/***
	 ** Creates a toy directed graph based on URL objects that represents link
	 ** structure.
	 **
	 ** @return a graph based on URL objects.
	 **/
	private static DirectedGraph<URL, DefaultEdge> createHrefGraph() {
		DirectedGraph<URL, DefaultEdge> g = new DefaultDirectedGraph<URL, DefaultEdge>(DefaultEdge.class);

		try {
			URL amazon = new URL("http://www.amazon.com");
			URL yahoo = new URL("http://www.yahoo.com");
			URL ebay = new URL("http://www.ebay.com");

			// add the vertices
			g.addVertex(amazon);
			g.addVertex(yahoo);
			g.addVertex(ebay);

			// add edges to create linking structure
			g.addEdge(yahoo, amazon);
			g.addEdge(yahoo, ebay);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return g;
	}

	/***
	 ** Craete a toy graph based on String objects.
	 **
	 ** @return a graph based on String objects.
	 */
	private static UndirectedGraph<String, DefaultEdge> createStringGraph() {
		UndirectedGraph<String, DefaultEdge> g = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

		String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";

		// add the vertices
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);

		// add edges to create a circuit
		g.addEdge(v1, v2);
		g.addEdge(v2, v3);
		g.addEdge(v3, v4);
		g.addEdge(v4, v1);

		return g;
	}
}
