package com.urise.webapp;

public class MainConcurrency {
    public static void main(String[] args) {
        MainConcurrency mc1 = new MainConcurrency();
        MainConcurrency mc2 = new MainConcurrency();

        new Thread(() -> mc1.print(mc2)).start();
        new Thread(() -> mc2.print(mc1)).start();
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
