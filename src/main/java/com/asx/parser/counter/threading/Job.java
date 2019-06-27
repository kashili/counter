/**
 * 
 */
package com.asx.parser.counter.threading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.asx.parser.counter.singletons.Configuration;
import com.asx.parser.counter.singletons.Globals;
import com.google.common.base.Throwables;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * @author krishnaashili
 *
 */
@Slf4j
public class Job implements Runnable {
	private String path;
	private Map<String, Integer> fileCache;
	private int nLines = 0;
	private boolean startMerging;

	private Job() {}

	public Job (String path) {
		this.path = path;
		fileCache = new HashMap<String,Integer>();
		startMerging = false;
	}

	private void count(String token)  {
		Integer counter = fileCache.containsKey(token)?1+fileCache.get(token):1;
		fileCache.put(token, counter);
	}
	public boolean isFilter(String token) {
		return false;//todo: disregard predictable words like "a an and the is for" etc
	}

	public void parseFile(long threadId) {
		log.info("Thread "+threadId+" PROCESSING ... "+path);
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			String line;
			while((line= br.readLine())!=null) {
				nLines++;
				String regex = Configuration.getProperty("regex");
				String tokens[] = line.split(regex); 
				for (String token:tokens) {
					token = token.trim();
					if(token.length()>0 && !isFilter(token)) {
						count(token.toLowerCase());
					}
				}
			}
		} catch(Exception e) {

			log.error(Throwables.getStackTraceAsString (e));
		}
		log.info("Thread "+threadId+" parsed "+nLines+" lines in the file ... "+path);
		startMerging = true;
	}

	public void mergeCaches(long threadId) {
		Globals.cache.merge(fileCache);
		log.info("Thread "+threadId+" merged with master cache.. "+path);

	}

	@Override
	public void run() 
	{ 
		long threadId = Thread.currentThread().getId()%Runtime.getRuntime().availableProcessors() +1;
		//reuse the threadpool and jobs. a)parsing files on separate threads and b)merging all caches together by thread
		if(!startMerging)
			parseFile(threadId);
		else
			mergeCaches(threadId);

	}
}
