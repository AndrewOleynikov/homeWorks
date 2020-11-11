package com.company;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter(new TTASLock());
        for(int i = 0; i < 2; i++) {
            MyThread myThread = new MyThread(counter);
            myThread.start();
        }

        Thread.sleep(10000);

        System.out.println("Counter: " + counter.getCount());
    }
}
