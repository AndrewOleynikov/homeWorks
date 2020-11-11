package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TTASLock implements Lock {
    private AtomicBoolean locked = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (true) {
            while (locked.get()) {}
            if (!locked.getAndSet(true)) {
                return;
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        locked.getAndSet(false);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
