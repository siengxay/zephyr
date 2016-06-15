package com.snhien.xpath;

public class ContainsPredicate extends XPathPredicate{
	public ContainsPredicate(XPathSelector predicate, String name, String value){
		super(predicate);
		StringBuilder sb = new StringBuilder();
		if (selector.getPredicate()!=null){
			sb.append(selector.getPredicate());
			sb.append( " and ");
		}
		sb.append("contains(");
		sb.append(name);
		sb.append(",\"");
		sb.append(value);
		sb.append("\")");
		selector.setPredicate( sb.toString());
	}
}