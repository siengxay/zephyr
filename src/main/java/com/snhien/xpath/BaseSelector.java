package com.snhien.xpath;

public class BaseSelector implements XPathSelector{
	protected String path;
	protected String predicate;
	
	public BaseSelector(String path){
		this.path = path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	
	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getCount(){
		StringBuilder sb = new StringBuilder();
		sb.append("count(");
		sb.append(path);
		sb.append("[");
		sb.append(predicate);
		sb.append("])");
		return sb.toString();
	}

}
