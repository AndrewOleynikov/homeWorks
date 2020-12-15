package Main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class PrefixScanThread extends Thread {

	private final static int X = 0;
	private final static int Y = 1;
	private int start;
	private int end;
	private double[] ang;
	private double[] dist;
	private double[] c;
	private int[] phases;
	private boolean[] isReady;
	private double[][] pairs;
	private CyclicBarrier barrier;
	private int id;
	private int procs;

	PrefixScanThread(int id, int procs, double[] ang, double[] dist, int start, int end, CyclicBarrier barrier, int[] phases, boolean[] isReady, double[] c, double[][] pairs) {
		this.id = id;
		this.procs = procs;
		this.ang = ang;
		this.dist = dist;
		this.start = start;
		this.end = end;
		this.c = c;
		this.phases = phases;
		this.barrier = barrier;
		this.isReady = isReady;
		this.pairs = pairs;
	}

	@Override
	public void run() {
		angles();
		waiting();
		collect();
		waiting();
		distribute();
		waiting();
		angelsAfter();
		waiting();
		sequanse();
		waiting();
		collectCoord();
	}

	private void waiting() {
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	private void angles() {
		for (int i = start; i <= end; i++) {
			c[id] += ang[i];
			ang[i] = c[id];
		}
	}

	private void collect() {
		int dist = 1;
		while(id - dist >= 0) {
			if ((id + 1) % (dist * 2) != 0) {
				return;
			}
			while (phases[id] != phases[id - dist]) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			c[id] = (c[id] + c[id - dist]) % 360;
			phases[id] ++;
			dist *= 2;
		}
	}

	private void distribute() {
		int dist;
		if (id % 2 == 0) {
			isReady[id] = true;
			return;
		}
		if (id == procs - 1) {
			c[id] = 0;
			phases[id]--;
			dist = (int)Math.pow(2, phases[id]);
			isReady[id] = true;
		} else {
			dist = (int)Math.pow(2, phases[id] - 1);
			isReady[id] = true;
		}
		while (phases[id] >= 0) {
			if (phases[id - dist] == phases[id] && isReady[id - dist]) {
				double temp = c[id - dist];
				c[id - dist] = c[id];
				c[id] += temp;
				phases[id]--;
				phases[id - dist]--;
				dist /= 2;
			} else {
				try{
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void angelsAfter() {
		for (int i = start; i <= end; i++) {
			ang[i] = (ang[i] + c[id]) % 360;
		}
	}

	private void sequanse() {
		for (int i = start; i <= end; i++) {
			pairs[0][id] += Math.round(dist[i] * Math.cos(Math.toRadians(ang[i])));
			pairs[1][id] += Math.round(dist[i] * Math.sin(Math.toRadians(ang[i])));
		}
	}

	private void collectCoord() {
		phases[id] = 0;
		int dist = 1;
		while(id - dist >= 0) {
			if ((id + 1) % (dist * 2) != 0) {
				return;
			}
			while (phases[id] != phases[id - dist]) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			pairs[X][id] += pairs[X][id - dist];
			pairs[Y][id] += pairs[Y][id - dist];
			phases[id] ++;
			dist *= 2;
		}
	}

}
