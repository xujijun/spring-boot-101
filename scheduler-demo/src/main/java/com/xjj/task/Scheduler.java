package com.xjj.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器
 * @author XuJijun
 *
 */
@Component
public class Scheduler {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次
	public void statusCheck() {
		logger.info("每分钟执行一次。开始……threadName ={} ", Thread.currentThread().getName());
		//statusTask.healthCheck();
		logger.info("每分钟执行一次。结束。");
	}

//	@Scheduled(fixedRate=10000)
	public void loggerTasks() {
		logger.info("每10秒执行一次。开始……");
		logger.trace("I am trace log.");
		logger.debug("I am debug log.");
		logger.info("I am info log.");
		logger.warn("I am warn log.");
		logger.error("I am error log.");
		//statusTask.healthCheck();
		//logger.info("每20秒执行一次。结束。");
	}

	@Scheduled(fixedRateString="PT3S")
	public void scheduler1(){
		logger.info("scheduler #1, threadName = {} ", Thread.currentThread().getName());
	}

	@Scheduled(fixedRateString="PT2S", initialDelayString = "PT1S")
	public void scheduler2(){
		logger.info("scheduler #2, threadName = {} ", Thread.currentThread().getName());
	}

//	@Async
	@Scheduled(fixedDelayString="PT5S", initialDelayString = "PT2S")
	public void scheduler3() throws InterruptedException {
		logger.info("start scheduler #3, threadName = {} ", Thread.currentThread().getName());
		Thread.sleep(12000);
		logger.info("end scheduler #3, threadName = {} ", Thread.currentThread().getName());
	}
}
