package com.snhien.xpath;

/**
 * 
 * @author Siengxay
 * Abstract decorator class to add predicates to XPathSelector
 */

public abstract class XPathPredicate implements XPathSelector{
	XPathSelector selector;
	
	public XPathPredicate(XPathSelector selector){
		this.selector = selector;
	}
	
	public String getPath(){
		return selector.getPath();
	}
	
	public void setPath(String path){
		selector.setPath(path);
	}
	
	public String getPredicate(){
		return selector.getPredicate();
	}
	
	public void setPredicate(String predicate){
		selector.setPredicate(predicate);
		
	}
	
	public String getCount(){
		return selector.getCount();
	}

}
