package Main;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class UnitTest {
    @Test
    public void single1() {
        double[] ret = PrefixScan.computeSingleThread(new double[] {45, 30, 105, 90}, new  double[] {40, 50, 40, 20});

        assertEquals(Arrays.toString(ret), "[1.0, 56.0]");
    }

    @Test
    public void single2() {
        double[] ret = PrefixScan.computeSingleThread(new double[] {90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90}, new  double[] {90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90});

        assertEquals(Arrays.toString(ret), "[0.0, 0.0]");
    }

    @Test
    public void threads1() {
        double[] ret = PrefixScan.compute(new double[] {45, 30, 105, 90}, new  double[] {40, 50, 40, 20}, 2);
        System.out.println(Arrays.toString(ret));

        assertEquals(Arrays.toString(ret), "[1.0, 56.0]");
    }

    @Test
    public void threads2() {
        double[] ret = PrefixScan.compute(new double[] {90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90}, new  double[] {90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90}, 16);

        assertEquals(Arrays.toString(ret), "[0.0, 0.0]");
    }
}
