package com.lg.service;

import java.util.List;

import org.hibernate.Session;

import com.lg.dao.AssociationRuleDao;
import com.lg.entities.AssociationRule;

public class AssociationRuleService {

	private AssociationRuleDao associationRuleDao;

	public void setAssociationRuleDao(AssociationRuleDao associationRuleDao) {
		this.associationRuleDao = associationRuleDao;
	}

	public void saveOrUpdate(AssociationRule associationRule) {
		associationRuleDao.saveOrUpdate(associationRule);
	}

	public void saveBatch(List<AssociationRule> rules) {
		associationRuleDao.saveBatch(rules);
	}

	public void deleteAll() {
		associationRuleDao.deleteAll();
	}

	public List<AssociationRule> getAll() {
		return associationRuleDao.getAll();
	}
}
