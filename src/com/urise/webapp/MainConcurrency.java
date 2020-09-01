package com.urise.webapp;

public class MainConcurrency {
    public static void main(String[] args) {
        MainConcurrency mc1 = new MainConcurrency();
        MainConcurrency mc2 = new MainConcurrency();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> mc1.print(mc2)).start();
            new Thread(() -> mc2.print(mc1)).start();
        }
    }

    private synchronized void print(MainConcurrency mc) {
        synchronized (mc) {
            System.out.println(mc);
        }
    }
}
