package com.nike.core.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class AtomicInteger_Nike {

    void updateAndGet() {
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, 1000)
                .forEach(i -> {
                    System.out.println(i + "   ==" + Thread.currentThread());
                    Runnable task = () ->
                            atomicInt.updateAndGet(n -> n + 2);
                    executor.submit(task);
                    System.out.println(i);
                });
        stop(executor);
        System.out.println(atomicInt.get());    // => 2000
    }

    void incrementAndGet() {
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        IntStream.range(0, 1000)
                .forEach(i ->
                {
                    System.out.println(i + "   ==" + atomicInt.get());
                    executor.submit(atomicInt::incrementAndGet);
                });
        stop(executor);
        System.out.println(atomicInt.get());
    }

    private void stop(ExecutorService executor) {
        executor.shutdown();
    }

}

public class AtomicInteger_Example {

    public static void main(String[] args) {
        AtomicInteger_Nike atomicInteger_nike = new AtomicInteger_Nike();
        atomicInteger_nike.incrementAndGet();
      //  atomicInteger_nike.updateAndGet();
    }
}
