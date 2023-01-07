package Main;

import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {
        double[] ret = PrefixScan.compute(new double[] {45, 30, 105, 90}, new  double[] {40, 50, 40, 20}, 2);
        System.out.println(ret[0]);
        System.out.println(ret[1]);
        
        double[] retSingle = PrefixScan.computeSingleThread(new double[] {45, 30, 105, 90}, new  double[] {40, 50, 40, 20});
        System.out.println(retSingle[0]);
        System.out.println(retSingle[1]);

    }
}
