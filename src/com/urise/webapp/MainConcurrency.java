package com.urise.webapp;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREAD_NUMBERS = 10_000;
    private static final Lock LOCK = new ReentrantLock();
    private final AtomicInteger atomicCounter = new AtomicInteger();
    private int counter;

    public static void main(String[] args) throws InterruptedException {
        MainConcurrency mc = new MainConcurrency();
        MainConcurrency mc1 = new MainConcurrency();
        MainConcurrency mc2 = new MainConcurrency();
        CountDownLatch latch1 = new CountDownLatch(THREAD_NUMBERS);
        CountDownLatch latch2 = new CountDownLatch(THREAD_NUMBERS);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < THREAD_NUMBERS; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mc.lockedInc();
                }
                latch1.countDown();
            }).start();
        }
        latch1.await(5, TimeUnit.SECONDS);
        System.out.println(mc.counter);

        mc.counter = 0;
        for (int i = 0; i < THREAD_NUMBERS; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mc.lockedInc();
                }
                latch2.countDown();
            });
        }
        latch2.await();
        executorService.shutdown();
        System.out.println(mc.counter);

        executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_NUMBERS; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mc.atomicInc();
                }
            });
        }
        executorService.shutdown();
        System.out.println(mc.atomicCounter.get());

        System.out.println("DEADLOCK:");
        new Thread(() -> mc1.print(mc2)).start();
        new Thread(() -> mc2.print(mc1)).start();
    }

    private void lockedInc() {
        LOCK.lock();
        try {
            counter++;
        } finally {
            LOCK.unlock();
        }
    }

    private void atomicInc() {
        atomicCounter.incrementAndGet();
    }

    private synchronized void print(MainConcurrency mc) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (mc) {
            System.out.println(mc);
        }
    }
}
