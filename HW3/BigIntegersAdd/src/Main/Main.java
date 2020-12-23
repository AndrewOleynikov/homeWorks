package Main;

import java.util.concurrent.CyclicBarrier;

public class Main {

    private  static void reversePrint(int[] a) {
        for (int i = a.length - 1; i >= 0; i--) {
            System.out.print(a[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int procs = 2;
        int[] a = {9,9,9,9,9};
        int[] b = {9,9,9,9,9};
        reversePrint(PrefixScan.bigIntAdd(a, b, procs));
        reversePrint(PrefixScan.bigIntAddSingleThread(a, b));
    }
}
