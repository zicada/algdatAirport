package com.algdat.oblig1;

public class Airplane {
	
	
	private int id;
	@SuppressWarnings("unused")
	private Boolean arriving;
	private double timeCreated;
	
	public Airplane(int id, Boolean arriving, double timeCreated) {
		this.timeCreated = timeCreated;
		this.id = id;
		this.arriving = arriving;
	}
	
	public double getWaiting(double currTime) {
		return currTime - timeCreated;
	}
	
	public int getId() {
		return id;
	}
	
	public double getTimeCreated() {
		return timeCreated;
	}
	
	public String toString() {
		return "Flight_" +id;
	}

}
