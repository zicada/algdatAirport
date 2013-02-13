package com.algdat.oblig1;

public class Main {

	public static void main(String[] args) {
		// Arguments: number of iterations, speed (10 = 250ms sleep), seedvalue to rand (should not be > 1.0),
		// max size of the queues
		
		Airport airport = new Airport(100,25,0.8,10);
		airport.run();
	}

}
