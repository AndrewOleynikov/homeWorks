package Main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ScanThread extends Thread {

	private int start;
	private int end;
	private char[] a;
	private int[] phases;
	private int[][] pairs;
	private int close = 0;
	private int open = 0;
	private final int c = 0;
	private final int o = 1;
	private CyclicBarrier barrier;
	private int id;

	ScanThread(int id, char[] a, int start, int end, CyclicBarrier barrier, int[] phases, int[][] pairs) {
		this.id = id;
		this.a = a;
		this.start = start;
		this.end = end;
		this.phases = phases;
		this.barrier = barrier;
		this.pairs = pairs;
	}

	@Override
	public void run() {
		prepare();
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

	private void prepare() {
		for (int i = start; i <= end; i++) {
			if (a[i] == ')' && open == 0) {
				close++;
			} else if (a[i] == ')' && open != 0) {
				open--;
			} else {
				open++;
			}
		}
		pairs[c][id] = open;
		pairs[o][id] = close;
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
			funcForCollectPhase(id - dist, id);
			phases[id] ++;
			dist *= 2;
		}
	}

	private void funcForCollectPhase(int preId, int id) {
		close = 0;
		open = 0;
		if (pairs[c][preId] >= pairs[o][id]) {
			open = pairs[c][preId] - pairs[o][id] + pairs[c][id];
			close = pairs[o][preId];
		} else {
			close = pairs[o][id] - pairs[c][preId] + pairs[o][preId];
			open = pairs[c][id];
		}
		pairs[c][id] = open;
		pairs[o][id] = close;
	}

}
