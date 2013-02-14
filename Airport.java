package com.algdat.oblig1;

import java.util.ArrayList;
import java.util.Random;

public class Airport extends Thread implements Runnable{
	
	private int intervals;
	private double time;
	private double avgArrivals;
	private double avgDepartures;
	private int ids;
	private int maxPlanesPerQueue;
	private int emptyRuns;
	private double totalArrivalWaitingTime;
	private double totalDepartureWaitingTime;
	private LinkedQueue<Airplane> arrivalsQueue;
	private LinkedQueue<Airplane> departuresQueue;
	private ArrayList<Airplane> arrived;
	private ArrayList<Airplane> departed;
	private ArrayList<Airplane> rejected;
	
	public Airport(int intervals, double avgIn, double avgOut, int maxPlanesPerQueue) {
		time = 1;
		ids = 0;
		this.intervals = intervals;
		this.avgArrivals = avgIn;
		this.avgDepartures = avgOut;
		this.maxPlanesPerQueue = maxPlanesPerQueue;
		arrivalsQueue = new LinkedQueue<Airplane>();
		departuresQueue = new LinkedQueue<Airplane>();
		arrived = new ArrayList<Airplane>();
		departed = new ArrayList<Airplane>();
		rejected = new ArrayList<Airplane>();
	}
	
	public void run() {
		if(avgArrivals + avgDepartures > 1.0) {
			System.err.println("The sum of average arrivals and departures can not exceed 1.0.\n" +
					" Setting both values to 0.5");
			avgArrivals = 0.5;
			avgDepartures = 0.5;
		}
		
		printStart();
		
		while(time <= intervals) {
			System.out.print((int) time + ": ");
			
			generateTraffic();
			handleTraffic();
			
			time++;
			
			System.out.println();
			if(time > intervals)
				printReport();
		}
	}
		
	private void printStart() {
		System.out.println("\n Starting simulation. " + intervals + " intervals to go");
		System.out.println(" Average arrivals per interval: " + avgArrivals);
		System.out.println(" Average departures per interval: " + avgDepartures + "\n");	
	}

	private void generateTraffic() {
		for (Airplane arrival : getPlanes(avgArrivals, true)) {
			if((arrivalsQueue.size() >= maxPlanesPerQueue)) {
				rejected.add(arrival);
				System.out.println(arrival + " cannot land because the queue is full");
			} else {
				arrivalsQueue.enqueue(arrival);
				System.out.println(arrival + " is coming in for landing");
			}
		}
		
		for (Airplane departure : getPlanes(avgDepartures, false)) {
			if((departuresQueue.size() >= maxPlanesPerQueue)) {
				rejected.add(departure);
				System.out.println(departure + " cannot take off because the queue is full");
			} else {
				departuresQueue.enqueue(departure);
				System.out.println(departure + " wants to take off");
			}
		}
	}
	
	private void handleTraffic() {
		if((!arrivalsQueue.isEmpty())) {
			Airplane plane = arrivalsQueue.dequeue();
			arrived.add(plane);
			totalArrivalWaitingTime += plane.getWaiting(time);
			System.out.println(plane + " has landed successfully. Waiting time: " 
					+ (int) plane.getWaiting(time));

		} else if ((!departuresQueue.isEmpty())) {
			Airplane plane = departuresQueue.dequeue();
			departed.add(plane);
			totalDepartureWaitingTime += plane.getWaiting(time);
			System.out.println(plane + " has taken off successfully. Waiting time: " 
					+ (int) plane.getWaiting(time));

		} else {
			emptyRuns++;
			System.out.println("The airport is empty");
		}
	}
	
	private void printReport() {
		System.out.println("\n ******* Results after " + intervals + " intervals ******\n ");
		System.out.println(" Total landed: " + arrived.size());
		System.out.println(" Total taken off: " + departed.size());
		System.out.println(" Total still arriving: " + arrivalsQueue.size());
		System.out.println(" Total still departing: " + departuresQueue.size());
		System.out.println(" Total rejected: " + rejected.size());
		System.out.println(" Airport was empty for: " + emptyRuns + " iterations");
		System.out.println(" Time Airport was empty: " + (emptyRuns*100) / intervals + "%");
		System.out.println(" Avg waiting time, landings: " + totalArrivalWaitingTime / arrived.size());
		System.out.println(" Avg waiting time, take-offs: " + totalDepartureWaitingTime / departed.size());
	}
	
	private ArrayList<Airplane> getPlanes(double mean, Boolean isArrival) {
		ArrayList<Airplane> arrivals = new ArrayList<Airplane>();
		for(int i = 0; i < getPoissonRandom(mean); i++) {
			arrivals.add(new Airplane(genID(), isArrival, time));
		}
		return arrivals;
	}
	
	private int genID() {
		ids++;
		return ids;
	}
	
	private static int getPoissonRandom(double mean) {
		Random r = new Random();
		double L = Math.exp(-mean);
		int k = 0;
		double p = 1.0;
		do {
			p = p * r.nextDouble();
			k++;
		} while (p > L);
		return k - 1;
	}
	
	public static void main(String[] args) {
		/* Arguments: 
		   number of iterations,
		   average arrivals per iteration,
		   average departures per iteration,
		   max size of the queues */
		
		Airport airport = new Airport(100,0.6,0.4,10);
		airport.run();
	}

}
