package Main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class PrefixScanThread extends Thread {
	private int start;
	private int end;
	private int a[];
	private int b[];
	private Carry[] c;
	private Carry[] carries;
	private int[] sum;
	private int[] phases;
	private boolean[] isReady;
	private CyclicBarrier barrier;
	private int id;
	private int procs;

	PrefixScanThread(int id, int procs, int[] a, int[] b, int start, int end, Carry[] carries, int[] sum, CyclicBarrier barrier, Carry[] c, int[] phases, boolean[] isReady) {
		this.id = id;
		this.procs = procs;
		this.a = a;
		this.b = b;
		this.start = start;
		this.end = end;
		this.carries = carries;
		this.sum = sum;
		this.c = c;
		this.phases = phases;
		this.barrier = barrier;
		this.isReady = isReady;
	}

	@Override
	public void run() {
		sequence();
		waiting();
		collect();
		waiting();
		distribute();
		waiting();
		amountedCarries();
		waiting();
		result();

	}

	private void sequence() {
		Carry s = Carry.M;
		Carry carr;
		for (int i = start; i <= end; i++) {
			carr = intToCarry(a[i] + b[i]);
			sum[i] = (a[i] + b[i]) % 10;
			s = Carry.opp(s, carr);
			c[id] = s;
			carries[i] = s;
		}
	}

	private Carry intToCarry(int a) {
		if (a == 9) {
			return Carry.M;
		} else if (a < 9) {
			return Carry.N;
		} else {
			return Carry.C;
		}
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

	private void collect() {
		int dist = 1;
		while (id - dist >= 0) {
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
			c[id] = Carry.opp(c[id - dist], c[id]);
			phases[id] ++;
			dist *= 2;
		}
	}

	private void distribute() {
		int delta;
		if (id % 2 == 0) {
			isReady[id] = true;
			return;
		}
		if (id == procs - 1) {
			c[id] = Carry.M;
			phases[id]--;
			delta = (int)Math.pow(2, phases[id]);
			isReady[id] = true;
		} else {
			delta = (int)Math.pow(2, phases[id] - 1);
			isReady[id] = true;
		}
		while (phases[id] >= 0) {
			//System.out.println("ID : "  + id + " DIST : " + delta + " PH : " + phases[id] + " C : " + c[id] + " Zer : "  + c[0]);
			if (phases[id - delta] == phases[id] && isReady[id - delta]) {
				Carry temp = c[id - delta];
				c[id - delta] = c[id];
				c[id] = Carry.opp(temp, c[id]);
				phases[id]--;
				phases[id - delta]--;
				delta /= 2;
			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//System.out.println("-- ID : "  + id + " DIST : " + delta + " PH : " + phases[id] + " C : " + c[id] + " Zer : "  + c[0]);
		}

	}

	private void amountedCarries() {
		for (int i = start; i <= end; i++) {
			carries[i] = Carry.opp(c[id], carries[i]);
		}
	}

	private void result() {
		for (int i = start; i <= end; i++) {
			if (i>= 0 && carries[i] == Carry.C) {
				sum[i + 1] = (sum[i + 1] + 1) % 10;
			}
		}
	}
}
