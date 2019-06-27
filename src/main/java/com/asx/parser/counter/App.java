package com.asx.parser.counter;
import java.util.List;
import java.util.logging.Level;
import com.asx.parser.counter.singletons.Cache;
import com.asx.parser.counter.singletons.Configuration;
import com.asx.parser.counter.singletons.Globals;
import com.asx.parser.counter.threading.ThreadPool;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
/**
 * @author krishnaashili
 *
 */
@Slf4j
public class App 
{
	 
	public App() {
		Configuration.load();
	}
	
	private void parallelMerge() throws Exception {
		for( Runnable Job: Globals.jobs) {
        	Globals.threadPool.execute(Job);
        }
	}
	
	private void parallelParse(String path) throws Exception {
		new Directory().parallelParse(path);// process all the files recursively
	}
	public void start(String path) throws Exception {
		Globals.threadPool.create();
        this.parallelParse(path);
        Globals.threadPool.waitForCompletion();
        log.info( "\ncompleted parsing the files");
        Globals.threadPool.reset();
        this.parallelMerge();
        Globals.threadPool.shutdown();
        Globals.cache.sort();
		
	}
	
	public void print(int count) {
		Globals.cache.print(count);
	}
	
	public void printAll() {
		Globals.cache.print();
	}
	public static void main( String[] args ) throws Exception
	{
		
		App app = new App();
		if(args.length >1) {
			int count = Integer.parseInt(args[0]);
			String path = args[1];
			app.start(path);
			app.print(count); 
		}
		else {
			System.out.println("Usage: pass 2 command line arguments: topN and path" );
		}

	}
}

 