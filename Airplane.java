package com.algdat.oblig1;

public class Airplane {
	
	
	private int id;
	private Boolean arriving = false;
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
		if(arriving)
			return "Flight_" +id;
		else
			return "Flight_" +id;
		
	}

}
