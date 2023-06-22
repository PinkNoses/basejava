package com.urise.webapp;

import com.urise.webapp.util.LazySingleton;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        deadlock();

        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState()))
                .start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(counter);
        LazySingleton.getInstance();
    }

    private static void deadlock() {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 is running");
            synchronized (LOCK1) {
                System.out.println("t1 holds LOCK1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (LOCK2) {
                    System.out.println("t1 holds LOCK2");
                }
                System.out.println("t1 finished");
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 is running");
            synchronized (LOCK2) {
                System.out.println("t2 holds LOCK2");
                synchronized (LOCK1) {
                    System.out.println("t2 holds LOCK1");
                }
                System.out.println("t2 finished");
            }
        });
        t1.start();
        t2.start();
    }

    private synchronized void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
        counter++;
//          wait();
//          readFile
//          ...
    }
//             }
}