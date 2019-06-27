/**
 * 
 */
package com.asx.parser.counter.singletons;

/**
 * @author krishnaashili
 *
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Cache { //singleton global cache
	private static Cache cache = new Cache();
	private static Map<String,Integer> regularCache = new ConcurrentHashMap<>(); //synchronized by default
	private static Map<String,Integer> sortedCache;//sorted by value
	private static List<Map.Entry<String,Integer>> sortedList;


	private Cache() {}

	public static Cache getInstance() {
		return cache;
	}

	private static void count(String token,Integer count) {
		Integer oldcount = regularCache.containsKey(token)?regularCache.get(token):0;
		regularCache.put(token, oldcount+count);
	}

	public static void merge( Map<String,Integer>   fileCache) {	 
		fileCache.entrySet().stream().forEach(e -> count(e.getKey(),e.getValue()));

	}
	public static void sort() {
		sortedCache = regularCache
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(
						Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
								LinkedHashMap::new));
		
		sortedList = new ArrayList(sortedCache.entrySet());

	}
	 
	public static void print(int limit) {  //print "limit" number of unique 'hash map values' from the top
		int i=0;
		int count = 0;
		System.out.println("\n=====================================");
		while (count<limit && i<sortedList.size()) {
			System.out.println(count+"<"+sortedList.get(i).getKey()+"> occurred <"+sortedList.get(i).getValue()+"> times" );
			int prevMaxValue = sortedList.get(i).getValue();
			i++;
			if(i<sortedList.size() && prevMaxValue !=sortedList.get(i).getValue() )
				count++;
		}
		System.out.println("\n=====================================");
	}

	public static void print() {
		print(sortedList.size());//all
	}



}
