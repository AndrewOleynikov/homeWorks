package Main;

import Main.PrefixScan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {
    @Test
    public void single1() {
        int[] a = {9, 9, 9, 9};
        int[] b = {9, 9, 9, 9};
        int[] c = {8, 9, 9, 9, 1};
        assertEquals(Arrays.toString(PrefixScan.bigIntAddSingleThread(a, b)), Arrays.toString(c));
    }

    @Test
    public void single2() {
        int[] a = {5, 4, 3, 2, 1};
        int[] b = {9, 8, 7, 6, 0};
        int[] c = {4, 3, 1, 9, 1, 0};
        assertEquals(Arrays.toString(PrefixScan.bigIntAddSingleThread(a, b)), Arrays.toString(c));
    }


    @Test
    public void threads1() {
        int[] a = {9, 9, 9, 9};
        int[] b = {9, 9, 9, 9};
        int[] c = {8, 9, 9, 9, 1};
        assertEquals(Arrays.toString(PrefixScan.bigIntAdd(a, b, 8)), Arrays.toString(c));
    }

    @Test
    public void threads2() {
        int[] a = {5, 4, 3, 2, 1};
        int[] b = {9, 8, 7, 6, 0};
        int[] c = {4, 3, 1, 9, 1, 0};
        assertEquals(Arrays.toString(PrefixScan.bigIntAdd(a, b, 2)), Arrays.toString(c));
    }
}
