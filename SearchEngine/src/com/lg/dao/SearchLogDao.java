package com.lg.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.lg.entities.SearchLog;

public class SearchLogDao extends BaseDao {

	public long getLogCount() {
		String hql = "select count(*) from SearchLog e";
		return (long) getSession().createQuery(hql).uniqueResult();
	}

	public void saveOrUpdate(SearchLog searchLog) {
		// getSession().saveOrUpdate(searchLog);
		getSession().save(searchLog);
	}

	public List<String> getTopSearchKey(int number) {

		// String hql = "select e.keyWords from SearchLog e where LENGTH(e.keyWords) <= 12 group by e.keyWords order by count(e.id) desc";
		String hql = "select e.keyWords from SearchLog e group by e.keyWords order by count(e.id) desc";

		Session session = getSession();
		List<String> list = new ArrayList<>();
		try {
			Query query = session.createQuery(hql);
			query.setMaxResults(number);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getTopSearchKeyWeekly(int number) {

		// String hql = "select e.keyWords from SearchLog e where LENGTH(e.keyWords) <= 12 and e.date > :oneWeekBefore group by e.keyWords order by count(e.id) desc";
		String hql = "select e.keyWords from SearchLog e where e.date > :oneWeekBefore group by e.keyWords order by count(e.id) desc";

		Session session = getSession();
		List<String> list = new ArrayList<>();
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -7); // 得到一周前的时间
			Date date = calendar.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// System.out.println(df.format(date));

			Query query = session.createQuery(hql);
			query.setMaxResults(number);
			query.setDate("oneWeekBefore", date);

			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<List<String>> getSearchLogForFPTree() {

		String sql = "SELECT group_concat(DISTINCT(l.KEY_WORDS)) FROM SEARCH_LOG l GROUP BY l.IP";

		Session session = getSession();
		List<List<String>> list = new ArrayList<>();
		try {

			SQLQuery query = session.createSQLQuery(sql);
			List<String> result = query.list();
			for (int i = 0; i < result.size(); i++) {
				// System.out.println(result.get(i));
				list.add(Arrays.asList(result.get(i).split(",")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Set<String> getDecideAttr() {
		String hql = "select distinct(e.keyWords) from SearchLog e";
		Set<String> decideAttr = new HashSet<String>();
		Session session = getSession();
		List<String> list = new ArrayList<>();
		try {
			Query query = session.createQuery(hql);
			list = query.list();
			decideAttr.addAll(list);
			// System.out.println(decideAttr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decideAttr;
	}

	public List<String> getSearchLogByIP(String ip) {
		String hql = "select distinct(e.keyWords) from SearchLog e where e.ip = '" + ip + "'";
		Session session = getSession();
		List<String> list = new ArrayList<>();
		try {
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
