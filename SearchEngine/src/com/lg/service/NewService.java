package com.lg.service;

import java.util.List;

import com.lg.dao.NewDao;
import com.lg.entities.New;
import com.lg.entities.PageBean;
import com.lg.entities.PageBeanForSearchResult;
import com.zxl.javaBean.UrlData;

public class NewService {

	private NewDao newDao;

	public void setNewDao(NewDao newDao) {
		this.newDao = newDao;
	}

	public List<New> getAll() {
		List<New> news = newDao.getAll();
		return news;
	}

	/**
	 * pageSize为每页显示的记录数 page为当前显示的网页
	 */
	public PageBean getPageBean(int pageSize, int page) {
		PageBean pageBean = new PageBean();

		Long allRows = newDao.getAllRowCount();

		int totalPage = pageBean.getTotalPages(pageSize, allRows);

		int currentPage = pageBean.getCurPage(page);

		int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);

		List<New> list = newDao.queryByPage(offset, pageSize);

		pageBean.setList(list);
		pageBean.setAllRows(allRows);
		pageBean.setCurrentPage(currentPage);
		pageBean.setTotalPage(totalPage);

		return pageBean;
	}

	public PageBeanForSearchResult getPageBeanForSearchResult(int pageSize, int page, String key) {
		PageBeanForSearchResult pageBean = new PageBeanForSearchResult();

		Long allRows = newDao.getSearchResultCount(key);

		int totalPage = pageBean.getTotalPages(pageSize, allRows);

		int currentPage = pageBean.getCurPage(page);

		int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);

		List<UrlData> list = newDao.queryByPage(offset, pageSize, key);

		pageBean.setList(list);
		pageBean.setAllRows(allRows);
		pageBean.setCurrentPage(currentPage);
		pageBean.setTotalPage(totalPage);

		return pageBean;
	}
}
