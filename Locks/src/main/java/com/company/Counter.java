package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private long count = 0L;
    public ALock lock;

    public Counter(ALock lock) {
        this.lock = lock;
    }

    public void  incCount() {
        lock.lock();
        try {
            count++;
        }
        finally {
            lock.unlock();
        }
    }

    public long getCount() {
        return count;
    }
}
