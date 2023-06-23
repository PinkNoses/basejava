package com.urise.webapp;

public class MainDeadlock {
    private static final String LOCK1 = "Object_1";
    private static final String LOCK2 = "Object_2";

    public static void main(String[] args) {
        deadlock(LOCK1, LOCK2);
        deadlock(LOCK2, LOCK1);
    }

    private static void deadlock(Object obj1, Object obj2) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread() + " is running");
            synchronized (obj1) {
                System.out.println(Thread.currentThread() + " holds " + obj1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread() + " is waiting " + obj2);
                synchronized (obj2) {
                    System.out.println(Thread.currentThread() + " holds " + obj2);
                }
                System.out.println(Thread.currentThread() + " finished");
            }
        });
        thread.start();
    }
}