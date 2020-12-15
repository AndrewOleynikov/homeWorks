package Main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ScanThread extends Thread {

	private int start;
	private int end;
	private int[] a;
	private int[] b;
	private int[][] pair;
	private int[] phases;
	private CyclicBarrier barrier;
	private int id;

	ScanThread(int id, int[] a, int[] b, int start, int end, CyclicBarrier barrier, int[][] pair, int[] phases) {
		this.id = id;
		this.a = a;
		this.b = b;
		this.start = start;
		this.end = end;
		this.pair = pair;
		this.phases = phases;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		sequance();
		waiting();
		collect();
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

	private void sequance() {
		int a = this.a[start];
		int b = this.b[start];
		for (int i = start + 1; i <= end; i++) {
			a *= this.a[i];
			b = this.a[i] * b + this.b[i];
		}
		pair[0][id] = a;
		pair[1][id] = b;
	}

	private void rule(int id, int delta) {
		pair[1][id] = pair[0][id] * pair[1][id - delta] + pair[1][id];
		pair[0][id] *= pair[0][id - delta];
	}

	private void collect() {
		int delta = 1;
		while (id - delta >= 0) {
			if ((id + 1) % (delta * 2) != 0) {
				return;
			}
			while (phases[id] != phases[id - delta]) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			rule(id, delta);
			phases[id] ++;
			delta *= 2;
		}
	}
}
