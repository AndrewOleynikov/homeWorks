package Main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {
    @Test
    public void single1() {
        int n = 5;
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        assertEquals(ParallelScan.computeSingleThread(a, b, n), 325);
    }

    @Test
    public void single2() {
        int n = 9;
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        assertEquals(ParallelScan.computeSingleThread(a, b, n), 986409);
    }

    @Test
    public void threads1() {
        int n = 5;
        int procs = 2;
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        assertEquals(ParallelScan.compute(a, b, n, procs), 325);
    }

    @Test
    public void threads2() {
        int n = 9;
        int procs = 8;
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        assertEquals(ParallelScan.compute(a, b, n, procs), 986409);
    }

}
