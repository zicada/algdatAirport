package com.algdat.oblig1;

import java.util.ArrayList;
import java.util.Random;

public class Airport extends Thread implements Runnable{
	
	private int speed;
	private int intervals;
	private double counter;
	private double mean;
	private int ids;
	private int maxPlanesPerQueue;
	private int emptyRuns;
	private double totalArrivalWaitingTime;
	private double totalDepartureWaitingTime;
	private double totalArrivals;
	private LinkedQueue<Airplane> arrivalsQueue;
	private LinkedQueue<Airplane> departuresQueue;
	private ArrayList<Airplane> arrived;
	private ArrayList<Airplane> departed;
	private ArrayList<Airplane> rejected;
	
	public Airport(int intervals, int speed, double mean, int maxPlanesPerQueue) {
		counter = 1;
		ids = 0;
		totalArrivals = 0;
		this.speed = speed;
		this.intervals = intervals;
		this.mean = mean;
		this.maxPlanesPerQueue = maxPlanesPerQueue;
		arrivalsQueue = new LinkedQueue<Airplane>();
		departuresQueue = new LinkedQueue<Airplane>();
		arrived = new ArrayList<Airplane>();
		departed = new ArrayList<Airplane>();
		rejected = new ArrayList<Airplane>();
	}
	
	public void run() {
		while(counter <= intervals) {
			System.out.print((int)counter + ": ");
			
			generateTraffic();
			updateMean();
			processTraffic();
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			counter++;
			if(counter > intervals)
				printReport();
		}
	}
		
	private void generateTraffic() {
		for (Airplane arrival : getPlanes(mean, true)) {
			if((arrivalsQueue.size() >= maxPlanesPerQueue)) {
				rejected.add(arrival);
				System.out.println(arrival + " cannot land because the queue is full");
			} else {
				arrivalsQueue.enqueue(arrival);
				totalArrivals++;
				System.out.println(arrival + " is coming in for landing");
			}
		}
		
		for (Airplane departure : getPlanes(mean, false)) {
			if((departuresQueue.size() >= maxPlanesPerQueue)) {
				rejected.add(departure);
				System.out.println(departure + " cannot take off because the queue is full");
			} else {
				departuresQueue.enqueue(departure);
				System.out.println(departure + " wants to take off");
			}
		}
	}
	
	private void updateMean() {
		// update mean, which is passed to the randomizer on each run, making sure it doesn't run amok.
		mean = totalArrivals / counter;
		if (mean > 1.0 || mean < 0.1)
			mean = 0.5;
	}
	
	private void processTraffic() {
		if((!arrivalsQueue.isEmpty())) {
			Airplane plane = arrivalsQueue.dequeue();
			arrived.add(plane);
			totalArrivalWaitingTime += plane.getWaiting(counter);
			System.out.println(plane + " has landed successfully. Waiting time: " +(int)plane.getWaiting(counter));

		} else if ((!departuresQueue.isEmpty())) {
			Airplane plane = departuresQueue.dequeue();
			departed.add(plane);
			totalDepartureWaitingTime += plane.getWaiting(counter);
			System.out.println(plane + " has taken off successfully. Waiting time: " +(int)plane.getWaiting(counter));

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
		System.out.println(" Time Airport was empty: " + (emptyRuns*100)/intervals + "%");
		System.out.println(" Average waiting time, landings: " + totalArrivalWaitingTime/arrived.size());
		System.out.println(" Average waiting time, take-offs: " + totalDepartureWaitingTime/departed.size());
	}
	
	private ArrayList<Airplane> getPlanes(double randNum, Boolean isArrival) {
		ArrayList<Airplane> arrivals = new ArrayList<Airplane>();
		for(int i = 0; i < getPoissonRandom(randNum); i++) {
			arrivals.add(new Airplane(genID(), isArrival, counter));
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
		// Arguments: number of iterations, speed (lower == faster), seedvalue to rand (should not be > 1.0),
		// max size of the queues
		
		Airport airport = new Airport(100,0,0.8,10);
		airport.run();
	}

}
