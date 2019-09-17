package com.example.async.asyncexample;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MyService {

    @Async
    public CompletableFuture<String> doSomethingTimeConsuming(int identifier) {

        System.out.println("Currently running for identifier: " + identifier);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Running my service for identifier: " + identifier);
    }
}
