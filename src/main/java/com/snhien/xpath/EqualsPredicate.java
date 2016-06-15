package com.snhien.xpath;

public class EqualsPredicate extends XPathPredicate {

	public EqualsPredicate(XPathSelector c, String name, String value){
		super(c);
		StringBuilder sb = new StringBuilder();
		if ( this.selector.getPredicate()!=null){
			sb.append(this.selector.getPredicate());
			sb.append(" and ");
		}
		sb.append(name);
		sb.append("=\"");
		sb.append(value);
		sb.append("\"");

		this.selector.setPredicate(sb.toString());
	}


}
