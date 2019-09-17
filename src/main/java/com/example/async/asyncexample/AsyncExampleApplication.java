package com.example.async.asyncexample;

import com.sun.xml.internal.ws.util.CompletedFuture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class AsyncExampleApplication {

	public static void main(String[] args) {
		List<CompletableFuture<String>> completableFutures = new ArrayList<>();
		ConfigurableApplicationContext run = SpringApplication.run(AsyncExampleApplication.class, args);
		MyService myService = run.getBean(MyService.class);
		for (int i=1; i <= 20; i++) {
			completableFutures.add(myService.doSomethingTimeConsuming(i));
		}
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
		//Above statement of join will ensure that all running threads completes before process moves to next statement.
		System.out.println("Completed all processing");
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}

}
