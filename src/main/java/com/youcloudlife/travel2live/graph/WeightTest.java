package com.youcloudlife.travel2live.graph;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Test attraction vetex and edge modules
 * 
 * @author Paul Chen
 *
 */
public class WeightTest {

	// We add all attractions that are met based on the ranking and
	// can be used for 4 days of trip based on the google distance for example
	List<Attraction> attractionList = new ArrayList<Attraction>();
	

	// constructor
	public WeightTest() {

		// add 5 test attractions, sorted based on the distance from the near to the far
		// from the start location and google distance calculation
		Attraction a0 = new Attraction("a0", 1);
		Attraction a1 = new Attraction("a1", 905.1);
		Attraction a2 = new Attraction("a2", 102.9);
		Attraction a3 = new Attraction("a3", 205.5);
		Attraction a4 = new Attraction("a4", 50.9);
		

		attractionList.add(a0);
		attractionList.add(a1);
		attractionList.add(a2);
		attractionList.add(a3);
		attractionList.add(a4);

		SimpleDirectedWeightedGraph<Attraction, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<Attraction, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		// add vertex as attractions
		for (Attraction attraction : attractionList) {
			g.addVertex(attraction);
		}

		// Beginning from the start location, for each attraction, we build
		// vertex and direct edge model and set weight for the edges on the way
		System.out.println("\n================= Set weight on attraction edges.... =============");
		for (int i = 0; i < attractionList.size(); i++) {
			// for the rest of the attractions, we add edges
			for (int j = 1; j < attractionList.size(); j++) {
				if (i != j) { // not itself
					DefaultWeightedEdge e1 = g.addEdge(attractionList.get(i), attractionList.get(j));
					// set weight for the edge, for now, set the sum of the 2 side attraction
					// weight, but need to add the distance as the factor as well later
					double weight = attractionList.get(i).getWeight() + attractionList.get(j).getWeight();
					g.setEdgeWeight(e1, weight);
					System.out.println("Set weight " + weight + " on attraction from " + attractionList.get(i).getName() + " to " + attractionList.get(j).getName());
				}
			}

		}

		// print the graph
		System.out.println("\n================= Print graph .... =============");
		System.out.println(g.toString());

		// get all possible attraction paths
		List<GraphPath<Attraction, DefaultWeightedEdge>> allPaths = (new AllDirectedPaths<Attraction, DefaultWeightedEdge>(
				g)).getAllPaths(attractionList.get(0), attractionList.get(attractionList.size() - 1), true, null);

		System.out.println("\n================= Print the total # of paths and weight ....=============");
		System.out.println("There are total " + allPaths.size() + " possible paths.");
		
		// let's print all these possible path
		int bestPathIndex = 0;
		double bestPathWeight = 0;
		for (int i=0; i< allPaths.size(); i++) {
			GraphPath<Attraction, DefaultWeightedEdge> path = allPaths.get(i);
			double weight = getTotalWeightOnPath(g, path);
			if (weight > bestPathWeight) {
				bestPathWeight = weight;
				bestPathIndex = i;
			}
			System.out.println("Possible path " + i + ": Weight(" + weight + ")");
		}
		
		System.out.println("\n================= Best path to select =============");
		System.out.println("We choose the best path with the highest total weight: " + bestPathWeight);
		// we print the best path here
		System.out.println("Here is the best path we choose:" );
		GraphPath<Attraction, DefaultWeightedEdge> bestGraphPath = allPaths.get(bestPathIndex);
		
		List<Attraction> bestAttractions = bestGraphPath.getVertexList();
		for (Attraction att: bestAttractions) {
			System.out.println(" " + att.getName());
		}
	}

	
	/**
	 * Get the sum of the weight for a given path
	 * @return
	 */
	private double getTotalWeightOnPath(SimpleDirectedWeightedGraph<Attraction, DefaultWeightedEdge> graph, 
			                            GraphPath<Attraction, DefaultWeightedEdge> path) {
		
		 double totalWeight = 0;
		 List<DefaultWeightedEdge> edgeList= path.getEdgeList();
		 for (DefaultWeightedEdge edge: edgeList) {
			 Attraction source = graph.getEdgeSource(edge);
			 Attraction end =  graph.getEdgeTarget(edge);
			 double weight= graph.getEdgeWeight(edge);
			 // System.out.println("The weight from " + source.getName() + " to " + end.getName() + " is " + weight);
			 totalWeight += weight;
		 }
		return totalWeight;
	}
	
	
	// test main
	public static void main(String[] args) {
		new WeightTest();
	}
}
