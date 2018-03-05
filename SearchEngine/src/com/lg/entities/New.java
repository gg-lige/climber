package com.lg.entities;

public class New {

	private String url;
	private String content;
	private String type;
	private String urlList;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrlList() {
		return urlList;
	}

	public void setUrlList(String urlList) {
		this.urlList = urlList;
	}

	@Override
	public String toString() {
		return "News [url=" + url + ", content=" + content + ", type=" + type + ", urlList=" + urlList + "]";
	}

}
