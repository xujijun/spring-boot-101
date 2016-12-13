package com.xjj;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xjj.task.AsyncTask;

public class TaskTests extends BasicUtClass{
	@Autowired
	private AsyncTask asyncTask;
	
	
	@Test
	public void AsyncTaskTest() throws InterruptedException, ExecutionException {
		Future<String> task1 = asyncTask.doTask1();
		Future<String> task2 = asyncTask.doTask2();
		
		while(true) {
			if(task1.isDone() && task2.isDone()) {
				logger.info("Task1 result: {}", task1.get());
				logger.info("Task2 result: {}", task2.get());
				break;
			}
			Thread.sleep(1000);
		}
		
		logger.info("All tasks finished.");
	}
}
