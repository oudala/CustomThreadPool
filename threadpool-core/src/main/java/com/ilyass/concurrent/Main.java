package com.ilyass.concurrent;


public class Main {
    public static void main(String[] args) {
        RejectionPolicy rejectionPolicy = task -> {
            System.out.println("Task rejected: " + task);
        };

        ThreadPool threadPool = new ThreadPool(3, 5, rejectionPolicy);

        for (int i = 1; i <= 10; i++) {
            threadPool.execute(new Task("Task-" + i));
        }

        try {
            Thread.sleep(7000); // Let tasks complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        threadPool.shutdown();
    }

}
