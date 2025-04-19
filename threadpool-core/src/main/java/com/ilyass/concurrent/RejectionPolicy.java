package com.ilyass.concurrent;


@FunctionalInterface
public interface RejectionPolicy {
    void reject(Runnable task);
}
