package com.akshat.springmvc.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class PageHitsService {
	private static Map<String, Integer> hits = new HashMap<String, Integer>();
	
	public void pageCalled(String page) {
		if(hits.containsKey(page))
			hits.put(page, hits.get(page) + 1);
		else
			hits.put(page, 1);
	}
	
	public Integer getPageHits(String page) {
		if(hits.containsKey(page))
			return hits.get(page);
		return 0;
	}
}
