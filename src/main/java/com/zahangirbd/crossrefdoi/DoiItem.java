package com.zahangirbd.crossrefdoi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DoiItem {
	
	private String doi;
	private int isReferencedByCount;
	private String publisher;
	private String type;
	private List<String> title;
	private String member;
	
	public DoiItem() {}
	public DoiItem(String doi, int isReferencedByCount, String publisher, String type, List<String> title, String member) {
		this.doi = doi;
		this.isReferencedByCount = isReferencedByCount;
		this.publisher = publisher;
		this.type = type;
		this.title = title;
		this.member = member;
	}
	
	@JsonProperty("DOI")
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	@JsonProperty("is-referenced-by-count")
	public int getIsReferencedByCount() {
		return isReferencedByCount;
	}
	public void setIsReferencedByCount(int isReferencedByCount) {
		this.isReferencedByCount = isReferencedByCount;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getTitle() {
		return title;
	}
	public void setTitle(List<String> title) {
		this.title = title;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
}


