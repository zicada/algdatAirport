package com.algdat.oblig1;

public class Airplane {
	
	
	private int id;
	private Boolean arriving = false;
	private int waiting;
	
	public Airplane(int id, Boolean arriving) {
		this.id = id;
		this.arriving = arriving;
		waiting = 0;
	}
	
	public void addWaitingIncrement() {
		waiting++;
	}
	
	public int getWaiting() {
		return waiting;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		if(arriving)
			return "Flight_" +id;
		else
			return "Flight_" +id;
		
	}

}
