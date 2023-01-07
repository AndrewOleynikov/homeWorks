package Main;

import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {
        String str = ")(";
        char[] ch = str.toCharArray();
        System.out.println(Scan.isCorrect(ch, 4));
        System.out.println(Scan.isCorrectSingleThread(ch));
    }
}
