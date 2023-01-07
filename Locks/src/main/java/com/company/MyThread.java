package com.company;

import java.util.concurrent.locks.Lock;

public class MyThread extends Thread {

    private Counter counter;

    public MyThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            counter.incCount();
        }

        System.out.println("Thred #" + Thread.currentThread().getName() + " was finished: " + counter.getCount());
    }
}
