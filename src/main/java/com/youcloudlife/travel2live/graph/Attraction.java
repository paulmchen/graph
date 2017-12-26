package com.youcloudlife.travel2live.graph;

/**
 * An attraction test object
 * @author Paul Chen
 *
 */
public class Attraction {

	private String name;
	private double weight= 0;
	
	/**
	 * constructor
	 * @param name
	 * @param weight
	 */
	public Attraction(String name, double weight) {
	
		this.name = name;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return  name + ":" + weight;
	}
	
}
