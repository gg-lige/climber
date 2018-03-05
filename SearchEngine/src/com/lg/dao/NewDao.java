package com.lg.dao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.lg.entities.New;
import com.zxl.javaBean.UrlData;
import com.zxl.reverseIndex.ReverseIndex;

public class NewDao extends BaseDao {

	/**
	 * 通过hql语句得到数据库中记录总数
	 */
	public Long getAllRowCount() {
		String hql = "select count(*) from New";
		Session session = getSession();
		Long allRows = 0L;
		try {
			Query query = session.createQuery(hql);
			allRows = (Long) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allRows;
	}

	/**
	 * 使用hibernate提供的分页功能，得到分页显示的数据
	 */
	public List<New> queryByPage(int offset, int pageSize) {
		String hql = "FROM New e";
		Session session = getSession();
		List<New> news = null;
		List<New> result = new ArrayList<>();

		try {
			Query query = session.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize);
			news = query.list();
			// news过长，截断部分内容以便显示
			for (int i = 0; i < news.size(); i++) {
				New tempNew = news.get(i);
				if (tempNew.getContent().length() >= 256) {
					tempNew.setContent(tempNew.getContent().substring(0, 255));
					tempNew.setUrlList("");
					result.add(tempNew);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private ArrayList<UrlData> searchResults = new ArrayList<>();

	public ArrayList<UrlData> getSearchResults(String key) {
		return searchResults;
	}

	public void setSearchResults(ArrayList<UrlData> searchResults) {
		this.searchResults = searchResults;
	}

	/**
	 * 得到分页显示的数据
	 */
	public List<UrlData> queryByPage(int offset, int pageSize, String key) {
		ArrayList<UrlData> searchResults = getSearchResults(key);
//		for (int i = 0; i < searchResults.size(); i++) {
//			System.out.println(searchResults.get(i));
//		}

		// for (UrlData urlData : searchResults) {
		// System.out.println(urlData);
		// }
		int endIndex = offset + pageSize;
		if (endIndex > searchResults.size()) {
			endIndex = searchResults.size();
		}
		List<UrlData> page = searchResults.subList(offset, endIndex);

		String[] keys = key.split(" ");
		// news过长，截断部分内容以便显示
		for (int i = 0; i < page.size(); i++) {
			UrlData tempNew = page.get(i);
			tempNew.setTitle(replaceKeys(tempNew.getTitle(), keys));
			// 设置pageRank评分为两位小数
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			tempNew.setRank(Double.parseDouble(decimalFormat.format(tempNew.getRank())));
			// 优化：从查询到的内容中以关键词为核心，找到对应的段落予以显示
			String content = tempNew.getHtml();
			int maxLength = 256;
			int index = Integer.MAX_VALUE;
			for (int j = 0; j < keys.length; j++) {
				// System.out.println(content);
				// System.out.println(keys.toString());
				// System.out.println(content.contains(keys[j]));
				// System.out.println(keys.length);
				if (content.contains(keys[j])) {
					int start = content.indexOf(keys[j]);
					if (start > 0 && start < index) {
						index = start;
					}
				}
			}
			if (index < Integer.MAX_VALUE) {
				if (index < maxLength && tempNew.getHtml().length() >= maxLength) {
					tempNew.setHtml(replaceKeys(content.substring(0, 255).replace(" ", "") + " ...", keys));
				} else if (index >= maxLength) {
					// 如果从当前关键词开始算256个字符超过了新闻内容的长度，则按照字符串的截止位置进行截取
					if (index - 20 + maxLength < content.length()) {
						tempNew.setHtml(replaceKeys(" ..." + content.substring(index - 20, index - 20 + maxLength).replace(" ", "") + " ...", keys));
					} else {
						tempNew.setHtml(replaceKeys(content.substring(content.length() - maxLength - 1, content.length() - 1).replace(" ", ""), keys));
					}
				}
			} else if (tempNew.getHtml().length() >= maxLength) {
				tempNew.setHtml(replaceKeys(content.substring(0, 255).replace(" ", "") + " ...", keys));
			}

			// if (tempNew.getHtml().length() >= 256) {
			// tempNew.setHtml(replaceKeys(tempNew.getHtml().substring(0, 255).replace(" ", ""), keys));
			// result.add(tempNew);
			// }
		}
		return page;
	}

	private String replaceKeys(String title, String[] keys) {
		String newTitle = title;
		for (int i = 0; i < keys.length; i++) {
			newTitle = newTitle.replace(keys[i], "<span style='color:#C00'>" + keys[i] + "</span>");
		}
		return newTitle;
	}

	public List<New> getAll() {
		String hql = "FROM New e";
		Query query = getSession().createQuery(hql);
		query.setMaxResults(50);
		List<New> news = query.list();
		List<New> result = new ArrayList<>();
		// news过长，截断部分内容以便显示
		for (int i = 0; i < news.size(); i++) {
			New tempNew = news.get(i);
			if (tempNew.getContent().length() >= 256) {
				tempNew.setContent(tempNew.getContent().substring(0, 255));
				tempNew.setUrlList("");
				result.add(tempNew);
			}
		}

		// System.out.println(news);
		return result;
	}

	public Long getSearchResultCount(String key) {
		setSearchResults(ReverseIndex.reverseIndex("reverseIndex", key, false));
		return (long) searchResults.size();
	}

}
