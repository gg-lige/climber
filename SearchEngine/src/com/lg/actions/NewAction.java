package com.lg.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.lg.entities.AssociationRule;
import com.lg.entities.PageBean;
import com.lg.entities.PageBeanForSearchResult;
import com.lg.entities.SearchLog;
import com.lg.service.AssociationRuleService;
import com.lg.service.NewService;
import com.lg.service.SearchLogService;
import com.opensymphony.xwork2.ActionSupport;

import fp_tree.FPTree;
import fp_tree.StrongAssociationRule;

public class NewAction extends ActionSupport implements RequestAware {

	private static final long serialVersionUID = 1L;

	private NewService newService;

	public void setNewService(NewService newService) {
		this.newService = newService;
	}

	private SearchLogService searchLogService;

	public void setSearchLogService(SearchLogService searchLogService) {
		this.searchLogService = searchLogService;
	}

	private AssociationRuleService associationRuleService;

	public void setAssociationRuleService(AssociationRuleService associationRuleService) {
		this.associationRuleService = associationRuleService;
	}

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = trimKey(key);
	}

	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 去除字符串两端的空格（中文、英文均去除）
	 * 
	 * @param textContent
	 */
	private String trimKey(String textContent) {
		textContent = textContent.trim();
		while (textContent.startsWith("　")) {// 这里判断是不是全角空格
			textContent = textContent.substring(1, textContent.length()).trim();
		}
		while (textContent.endsWith("　")) {
			textContent = textContent.substring(0, textContent.length() - 1).trim();
		}
		return textContent;
	}

	public String getNewsInPages() throws Exception {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		// 在给出搜索结果前，插入查询日志[需要保证是第一页，这样翻页时不会再插一条记录]
		if (key != null && key.length() > 0 && (page == 1 || page == 0)) {
			insertSearchLog();
		}

		// 查询搜索日志，给出当前搜索热门记录
		List<String> topTen = searchLogService.getTopSearchKey(7);
		// for (int i = 0; i < topTen.size(); i++) {
		// System.out.println(topTen.get(i));
		// }
		request.put("topTen", topTen);

		// 查询搜索日志，给出当前搜索热门记录
		List<String> topTenWeekly = searchLogService.getTopSearchKeyWeekly(7);
		request.put("topTenWeekly", topTenWeekly);

		// 进行关联挖掘，返回推荐查询关键词
		Set<String> recommendations = associationMining();
		request.put("recommendations", recommendations);

		// 表示每页显示15条记录，page表示当前网页
		// PageBean pageBean = newService.getPageBean(10, page);
		PageBeanForSearchResult pageBean = newService.getPageBeanForSearchResult(10, page, key);
		request.put("key", key);
		request.put("pageBean", pageBean);
		return "getNewsInPages";
	}

	public Set<String> associationMining() throws IOException {
		List<List<String>> trans = searchLogService.getSearchLogForFPTree();
		Set<String> decideAttr = searchLogService.getDecideAttr();
		FPTree fpTree = new FPTree();
		List<AssociationRule> aRules = associationRuleService.getAll();
		List<StrongAssociationRule> rules = transFormAR2SAR(aRules);

		// 当满足一定条件时，才进行关联规则的更新，不然每次更新代价太大
		/**
		 * 条件<br>
		 * 1、若数据库中无规则，则直接更新<br>
		 * 2、若当前为用户搜索的首页面，且搜索日志记录数为10的倍数，则更新<br>
		 * 3、否则直接使用当前的关联规则<br>
		 */
		if (aRules.size() == 0 || ((page == 1 || page == 0) && searchLogService.getLogCount() % 10 == 0)) {
			rules = fpTree.execute(trans, decideAttr);
			// 类型转换
			List<AssociationRule> associationRules = new ArrayList<>();
			for (int i = 0; i < rules.size(); i++) {
				StrongAssociationRule strongAssociationRule = rules.get(i);
				AssociationRule associationRule = new AssociationRule(String.join(", ", strongAssociationRule.condition), strongAssociationRule.result, strongAssociationRule.support,
						strongAssociationRule.confidence);
				associationRules.add(associationRule);
			}

			// 批量删除，删除数据表中上次分析的结果
			associationRuleService.deleteAll();

			// 批量插入
			associationRuleService.saveBatch(associationRules);
		}
		// 返回推荐的关键词
		return getRecommendations(rules);
	}

	private List<StrongAssociationRule> transFormAR2SAR(List<AssociationRule> aRules) {
		List<StrongAssociationRule> rules = new ArrayList<>();
		for (int i = 0; i < aRules.size(); i++) {
			StrongAssociationRule rule = new StrongAssociationRule();
			AssociationRule aRule = aRules.get(i);
			rule.condition = Arrays.asList(aRule.getPrerequisite().replace(" ", "").split(","));
			rule.result = aRule.getResult();
			rule.support = aRule.getSupport();
			rule.confidence = aRule.getConfidence();
			rules.add(rule);
		}

		return rules;
	}

	/**
	 * 获取推荐的关键词
	 * 
	 * @param rules
	 * @return
	 */
	public Set<String> getRecommendations(List<StrongAssociationRule> rules) {
		String ip = getRemortIP(ServletActionContext.getRequest());
		List<String> keys = searchLogService.getSearchLogByIP(ip);
		Set<String> recommendations = new HashSet<>();
		// 对rules进行排序，优先选取置信度较高的规则
		Collections.sort(rules);
		for (StrongAssociationRule rule : rules) {
			// 推荐规则：根据rule进行推荐[不超过10条]（限制：必须包含本次搜索的关键词）
			if (recommendations.size() < 10 && rule.support >= 3 && rule.confidence >= 0.6 && rule.condition.contains(key) && isContained(keys, rule.condition)) {
				recommendations.add(rule.result);
			}
		}
		return recommendations;

	}

	/**
	 * 判断一个List是否包含另一个List，并且包含本次搜索的关键词
	 * 
	 * @param keys
	 * @param condition
	 * @return
	 */
	private boolean isContained(List<String> keys, List<String> condition) {
		for (String s : condition) {
			if (!keys.contains(s)) {
				return false;
			}
		}
		return true;
	}

	public void insertSearchLog() {
		// 查询关键词
		String keyWords = key;
		// System.out.println(keyWords);
		String ip = getRemortIP(ServletActionContext.getRequest());
		String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
		String acceptLanguage = ServletActionContext.getRequest().getHeader("Accept-Language");
		// System.out.println(keyWords);
		// System.out.println(ip);
		// System.out.println(userAgent);
		// System.out.println(acceptLanguage);
		SearchLog searchLog = new SearchLog();
		searchLog.setDate(new Date());
		searchLog.setIp(ip);
		searchLog.setKeyWords(keyWords);
		searchLog.setUserAgent(userAgent);
		searchLog.setAcceptLanguage(acceptLanguage);

		searchLogService.saveOrUpdate(searchLog);

		// 如果key包含空格，将其拆分为多条记录存入数据库
		if (key.indexOf(" ") > 0) {
			String[] split = key.split(" ");
			for (int i = 0; i < split.length; i++) {
				searchLog.setKeyWords(split[i]);
				searchLogService.saveOrUpdate(searchLog);
			}
		}
	}

	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	public String listAllNews() {
		request.put("news", newService.getAll());
		return "listAllNews";
	}

	// ============================================================================
	private Map<String, Object> request;

	@Override
	public void setRequest(Map<String, Object> arg0) {
		this.request = arg0;
	}
}
