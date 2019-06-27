/**
 * 
 */
package com.asx.parser.counter;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors;
import java.util.logging.Level;

import com.asx.parser.counter.singletons.Globals;
import com.asx.parser.counter.threading.Job;
import com.asx.parser.counter.threading.ThreadPool;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * @author krishnaashili
 *
 */
@Slf4j
public class Directory {

	public void parallelParse(String path ) throws Exception{	
		log.info( "Processing directory "+path);
		File root = new File( path );
		File[] files = root.listFiles();
		if (files == null) 
			return;

		for ( File file : files ) {
			if ( file.isDirectory() ) {
				parallelParse( file.getAbsolutePath());
			} else {
				//create runnable task and submit it to threadpool
				Runnable job = new Job(file.getAbsolutePath()); 
				Globals.jobs.add(job);
				Globals.threadPool.execute(job);
			}
		}
	}
}