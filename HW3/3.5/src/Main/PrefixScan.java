package Main;

import java.util.concurrent.CyclicBarrier;

public class PrefixScan {

	public static double[] computeSingleThread(double[] ang, double[] dist) {
		double a = ang[0];
		double x = Math.round(dist[0] * Math.cos(Math.toRadians(ang[0])));
		double y = Math.round(dist[0] * Math.sin(Math.toRadians(ang[0])));
		for (int i = 1; i < dist.length; i++) {
			a = (a + ang[i]) % 360;
			x +=  Math.round(dist[i] * Math.cos(Math.toRadians(a)));
			y += Math.round(dist[i] * Math.sin(Math.toRadians(a)));
		}
		return new double[]{x, y};
	}

	public static double[] compute(double[] ang, double[] dist, int procs) {
		boolean isReady[] = new boolean[procs];
		int[] phases = new int[procs];
		PrefixScanThread[] threads = new PrefixScanThread[procs];
		CyclicBarrier barrier = new CyclicBarrier(procs);
		int size = dist.length / procs;
		double[] c = new double[procs];
		double[][] coordForThr = new double[2][procs];

		for (int i = 0; i < procs; i ++) {
			if (i != procs - 1) {
				threads[i] = new PrefixScanThread(i, procs, ang, dist, i * size, (i + 1) * size - 1, barrier, phases, isReady, c, coordForThr);
			} else {
				threads[i] = new PrefixScanThread(i, procs, ang, dist,i * size, dist.length - 1,	barrier, phases, isReady, c, coordForThr);
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
		//System.out.println(a[0]);
		//System.out.println(a[1]);
		return new double[] {coordForThr[0][procs - 1], coordForThr[1][procs - 1]};
	}




}
