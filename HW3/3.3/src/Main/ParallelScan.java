package Main;

import java.util.concurrent.CyclicBarrier;

public class ParallelScan {

	public static int computeSingleThread(int[] a, int[] b, int n) {
		int ans = b[0];
		for (int i = 1; i < n; i++) {
			ans = a[i] * ans + b[i];
		}
		return ans;
	}

	public static int compute(int[] a, int[] b, int n, int procs) {
		CyclicBarrier barrier = new CyclicBarrier(procs);
		int[][] pairs = new int[2][procs];
		int length = n / procs;
		int[] phases = new int[procs];
		ScanThread[] threads = new ScanThread[procs];

		for (int i = 0; i < procs; i ++) {
			if (i != procs - 1) {
				threads[i] = new ScanThread(i, a, b, i * length, (i + 1) * length - 1, barrier, pairs, phases);
			} else {
				threads[i] = new ScanThread(i, a, b,i * length, n - 1, barrier, pairs, phases);
			}
			threads[i].start();
		}
		for (int i = 0; i < procs; i ++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return pairs[1][procs - 1];
	}

	public static int oneThreadSolution(int[] a, int[] b, int n) {
		int ans = b[0];
		for (int i = 1; i < n; i++) {
			ans = a[i] * ans + b[i];
		}
		return ans;
	}
}
