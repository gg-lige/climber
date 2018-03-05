package com.lg.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.lg.entities.AssociationRule;
import com.lg.entities.New;

public class AssociationRuleDao extends BaseDao {
	public void saveOrUpdate(AssociationRule associationRule) {
		getSession().saveOrUpdate(associationRule);
	}
	
	public List<AssociationRule> getAll() {
		String hql = "FROM AssociationRule e";
		Query query = getSession().createQuery(hql);
		List<AssociationRule> result = query.list();
		return result;
	}

	public void saveBatch(List<AssociationRule> rules) {
		Session session = getSession();
		for (int i = 0; i < rules.size(); i++) {
			session.saveOrUpdate(rules.get(i));
			if (i % 100 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public void deleteAll() {
		Session session = getSession();
		String hql = "delete AssociationRule";
		session.createQuery(hql).executeUpdate();
	}
}
