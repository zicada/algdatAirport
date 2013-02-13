package com.algdat.oblig1;

public class Airplane {
	
	
	public int id;
	public Boolean arriving = false;
	public Boolean departing = false;
	public int waiting;
	
	public Airplane(int id, Boolean arriving, Boolean departing) {
		this.id = id;
		this.arriving = arriving;
		this.departing = departing;
		waiting = 0;
	}
	
	public void isWaiting() {
		waiting++;
	}
	
	public String toString() {
		if(arriving)
			return "Flight_" +id;
		else
			return "Flight_" +id;
		
	}

}
