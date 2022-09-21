package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionCellListCache {
	HashMap<Integer, CacheItem> cache;
	int renderedReadingNo = 0;

	public QuestionCellListCache() {

		this.cache = new HashMap<Integer, CacheItem>();
		renderedReadingNo = 0;

	}

	public void clear() {
		this.cache.clear();
		renderedReadingNo = 0;
	}

	public HashMap<Integer, CacheItem> getCache() {
		return cache;
	}

	public List<CacheItem> getCachedItems(int actualIndex, int actualLen) {
		List<CacheItem> result = new ArrayList<CacheItem>();

		for (int i = actualIndex; i < actualIndex + actualLen; i++) {
			CacheItem item = cache.get(i);
			if (item != null)
				result.add(item);
		}

		return result;
	}

	public List<QuestionProxy> getQuestions(int actualIndex, int actualLen) {
		List<QuestionProxy> result = new ArrayList<QuestionProxy>();
		for (int i = actualIndex; i < actualIndex + actualLen; i++) {
			CacheItem item = cache.get(i);
			if (item != null)
				result.add(item.getQuestion());
		}
		return result;
	}

	public boolean isEmpty() {

		return cache.size() == 0 ? true : false;
	}

	public int populateCache(List<QuestionProxy> questions, int requestedIndex) {
		for (QuestionProxy questionkProxy : questions) {
			CacheItem item = new CacheItem(questionkProxy, questionkProxy.getRowNo());
			cache.put(requestedIndex, item);
			requestedIndex++;

		}
		int result = this.cache.size();
		return result;
	}

	public int getSize() {

		return cache.size();
	}
}
