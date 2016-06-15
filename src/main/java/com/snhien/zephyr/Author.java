package com.snhien.zephyr;

public class Author {
	private String lastName;
	private String foreName;
	private String affiliation;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getForeName() {
		return foreName;
	}
	public void setForeName(String foreName) {
		this.foreName = foreName;
	}
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(foreName).append(" ").append(lastName).append("-").append(affiliation);
		return (sb.toString());
	}
}
