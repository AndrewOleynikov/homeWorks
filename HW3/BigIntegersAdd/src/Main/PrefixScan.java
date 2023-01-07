package Main;

import java.util.concurrent.CyclicBarrier;

public class PrefixScan {

	public static int[] bigIntAddSingleThread(int[] a, int[] b) {
		int[] sum = new int[a.length + 1];
		sum[a.length] = 0;

		int carry = 0;
		for (int i = 0; i < a.length; i++) {
			sum[i] = ((a[i] + b[i] + carry) % 10);
			carry = ((a[i] + b[i] + carry) / 10);
		}
		sum[a.length] = carry;
		return sum;
	}



	public static int[] bigIntAdd(int[] a, int[] b, int procs) {
		CyclicBarrier barrier = new CyclicBarrier(procs);
		Carry[] c = new Carry[procs];
		int[] phases = new int[procs];
		boolean[] isReady = new boolean[procs];
		int size = a.length;
		int[] sum = new int[size + 1];
		Carry[] carries = new Carry[size];
		int length = a.length/procs;
		PrefixScanThread[] threads = new PrefixScanThread[procs];

		for (int i = 0; i < procs; i ++) {
			if (i != procs - 1) {
				threads[i] = new PrefixScanThread(i, procs, a, b, i * length, (i + 1) * length - 1, carries, sum, barrier, c, phases, isReady);
			} else {
				threads[i] = new PrefixScanThread(i, procs, a, b,i * length, a.length - 1, carries, sum, barrier, c, phases, isReady);
			}
			threads[i].start();
		}
		for(int i = 0; i < procs; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return sum;
	}


}
