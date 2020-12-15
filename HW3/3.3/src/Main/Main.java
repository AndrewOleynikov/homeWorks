package Main;

import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {
        int procs = 4;
        int n = 5;
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(ParallelScan.compute(a, b, 5,procs));
        System.out.println(ParallelScan.computeSingleThread(a, b, 5));
    }
}
