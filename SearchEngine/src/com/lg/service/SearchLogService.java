package com.lg.service;

import java.util.List;
import java.util.Set;

import com.lg.dao.SearchLogDao;
import com.lg.entities.SearchLog;

public class SearchLogService {

	private SearchLogDao searchLogDao;

	public void setSearchLogDao(SearchLogDao searchLogDao) {
		this.searchLogDao = searchLogDao;
	}
	
	public long getLogCount() {
		return searchLogDao.getLogCount();
	}

	public void saveOrUpdate(SearchLog searchLog) {
		searchLogDao.saveOrUpdate(searchLog);
	}

	public List<String> getTopSearchKey(int number) {
		return searchLogDao.getTopSearchKey(number);
	}
	
	public List<String> getTopSearchKeyWeekly(int number) {
		return searchLogDao.getTopSearchKeyWeekly(number);
	}
	
	public List<List<String>> getSearchLogForFPTree() {
		return searchLogDao.getSearchLogForFPTree();
	}
	
	public Set<String> getDecideAttr() {
		return searchLogDao.getDecideAttr();
	}
	
	public List<String> getSearchLogByIP(String ip) {
		return searchLogDao.getSearchLogByIP(ip);
	}
}
