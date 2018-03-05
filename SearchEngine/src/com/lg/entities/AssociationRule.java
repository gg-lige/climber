package com.lg.entities;

public class AssociationRule {

	private Integer id;
	private String prerequisite;// 先决条件
	private String result;
	private Integer support;
	private Double confidence;
	
	public AssociationRule() {
		// TODO Auto-generated constructor stub
	}

	public AssociationRule(String prerequisite, String result, Integer support, Double confidence) {
		super();
		this.prerequisite = prerequisite;
		this.result = result;
		this.support = support;
		this.confidence = confidence;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getSupport() {
		return support;
	}

	public void setSupport(Integer support) {
		this.support = support;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}

	@Override
	public String toString() {
		return "AssociationRule [id=" + id + ", prerequisite=" + prerequisite + ", result=" + result + ", support=" + support + ", confidence=" + confidence + "]";
	}

}
