package Main;

import java.util.concurrent.CyclicBarrier;

public class Scan {
	
	public static boolean isCorrectSingleThread(char[] a) {
		
		int open = 0;
		int close = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == ')' && open == 0) {
				close++;
			} else if (a[i] == ')') {
				open--;
			} else {
				open++;
			}
		}
		if (close == 0 && open == 0) {
			return true;
		}
		return false;
	}

	public static boolean isCorrect(char[] a, int procs) {
		ScanThread[] threads = new ScanThread[procs];
		CyclicBarrier barrier = new CyclicBarrier(procs);
		int size = a.length / procs;
		int[] phases = new int[procs];
		int[][] pairs = new int[3][procs];

		for (int i = 0; i < procs; i++) {
			if (i != procs - 1) {
				threads[i] = new ScanThread(i, a, i * size, (i + 1) * size - 1, barrier, phases, pairs);
			} else {
				threads[i] = new ScanThread(i, a, i * size, a.length - 1, barrier, phases, pairs);
			}
			threads[i].start();
		}
		for (int i = 0; i < procs; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (pairs[0][procs - 1] == 0 && pairs[1][procs - 1] == 0) {
			//System.out.println("OK");
			return true;
		}
		//System.out.println("NO");
		return false;
	}
}
