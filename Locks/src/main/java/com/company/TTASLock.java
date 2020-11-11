package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TTASLock extends ALock {
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
    public void unlock() {
        locked.getAndSet(false);
    }
}
