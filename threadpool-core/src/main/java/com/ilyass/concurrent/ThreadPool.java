package com.ilyass.concurrent;

public class ThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final WorkerThread[] workers;
    private final RejectionPolicy rejectionPolicy;
    private volatile boolean isShutdown = false;

    public ThreadPool(int poolSize, int queueCapacity, RejectionPolicy rejectionPolicy) {
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.workers = new WorkerThread[poolSize];
        this.rejectionPolicy = rejectionPolicy;

        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread(taskQueue, i);
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shutdown.");
        }

        try {
            taskQueue.enqueue(task);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            rejectionPolicy.reject(task);
        }
    }

    public void shutdown() {
        isShutdown = true;
        for (WorkerThread worker : workers) {
            worker.stopWorker();
        }
    }
}
