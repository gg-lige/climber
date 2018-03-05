package com.lg.entities;

import java.util.Date;

public class SearchLog {

	private Integer id;
	private Date date;
	private String ip;
	private String keyWords;
	private String userAgent;
	private String acceptLanguage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	@Override
	public String toString() {
		return "SearchLog [id=" + id + ", date=" + date + ", ip=" + ip + ", keyWords=" + keyWords + ", userAgent=" + userAgent + ", acceptLanguage=" + acceptLanguage + "]";
	}

}
