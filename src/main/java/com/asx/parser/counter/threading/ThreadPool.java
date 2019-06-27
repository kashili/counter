/**
 * 
 */
package com.asx.parser.counter.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.asx.parser.counter.singletons.Cache;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * @author krishnaashili
 *
 */
@Slf4j
public class ThreadPool { //singleton

	private static ExecutorService pool;
	private static List<Future<?>>  futures =  new ArrayList<Future<?>>();
	 
	private ThreadPool() {}
	
	public static void reset() {
		if(futures !=null)
			futures.clear();
		
		futures = new ArrayList<Future<?>>();
	}
	public static void create() {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//minimizes context switching
			log.info("Started Thread Pool with size ... "+Runtime.getRuntime().availableProcessors());
	}
	
	public static void execute(Runnable job) throws Exception{
		futures.add(pool.submit(job));
	}
	
	public static void waitForCompletion() throws Exception {
		for(Future<?> future : futures) {
			future.get();
		}
	}
	
	public static void shutdown() throws Exception{
		pool.shutdown();
		pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}
}
