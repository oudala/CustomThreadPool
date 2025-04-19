package com.ilyass.concurrent;

import java.util.concurrent.BlockingQueue;


public class WorkerThread extends Thread {
    private final BlockingQueue<Runnable> taskQueue;
    private volatile boolean isStopped = false;

    public WorkerThread(BlockingQueue<Runnable> taskQueue, int index) {
        this.taskQueue = taskQueue;
        this.setName("Worker-" + index);
    }

    @Override
    public void run() {
        while (!isStopped) {
            Runnable task;
            try {
                task = taskQueue.take();
                task.run();
            } catch (Exception e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break; // Exit the loop if interrupted
            }
        }
    }

    public void stopWorker() {
        isStopped = true;
        this.interrupt();
    }
}
