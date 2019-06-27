/**
 * 
 */
package com.asx.parser.counter.singletons;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.asx.parser.counter.threading.ThreadPool;

/**
 * @author krishnaashili
 *
 */
public class Globals { //all singletons and globals referred from one place for readability
	public static List<Runnable> jobs = new ArrayList<>();
	public static Cache cache = Cache.getInstance();
	public static ThreadPool threadPool;
}
