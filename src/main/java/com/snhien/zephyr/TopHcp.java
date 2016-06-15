package com.snhien.zephyr;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.snhien.xpath.BaseSelector;
import com.snhien.xpath.ContainsPredicate;
import com.snhien.xpath.EqualsPredicate;
import com.snhien.xpath.XPathSelector;

/**
 * Main entry point for this program
 * To execute, pass the path to XML file as first parameter
 * @param args
 */
public class TopHcp {
	private Author author; 
	private AtomicInteger nPubs;
	private XPathExpression xpe;
	
	private static final String PATH_PUB = "/PubmedArticle/MedlineCitation/Article/AuthorList/Author";
	private static final String ELT_PUBMEDARTICLE = "PubmedArticle";
	private static final String ELT_LASTNAME = "LastName";
	private static final String ELT_FORENAME = "ForeName";
	private static final String ELT_AFFILIATION = "AffiliationInfo/Affiliation";
	
	static final Logger log = Logger.getLogger(TopHcp.class);

	/**
	 * Curated list of top Health Care Provider from Zephyr document,
	 * similar to http://www.myelomabeacon.com/news/2014/03/01/top-myeloma-research-2013/
	 * 
	 */
	protected static final String[][] topHcpIn = new String[][]{
		{"Bensinger", "William", "Seattle, WA"},
		{"Bergsagel", "Leif", "Scottsdale, AZ"},
		{"Cohen", "Adam", "Philadelphia, PA"},
		{"Einsele", "Hermann", "Germany"},
		{"Krishnan", "Amrita","Duarte, CA"},
		{"Libby", "Edward", "Seattle, WA"},
		{"Lonial","Sagar","Atlanta, GA"},
		{"Ludwig", "Heinz", "Austria"},
		{"Mateos","María-Victoria","Spain"},
		{"McCarthy","Philip","Buffalo"},
		{"Rajkumar","Vincent","Rochester, MN"},
		{"Richardson", "Paul", "Boston, MA"},
		{"Shah", "Jatin","Houston, TX"},
		{"Usmani", "Saad", "Little Rock, AR"},
		{"Vesole", "David","Hackensack, NJ"},
		{"Vij", "Ravi","St. Louis, MO"}
	};

	public static void main(String args[]){
		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		XMLStreamReader xmlr = null; 
		try {
			long start = System.currentTimeMillis();
			List<TopHcp> topHpcs = initTopHpc(topHcpIn);
			String file = args[0];
			xmlr = xmlif.createXMLStreamReader(new FileInputStream(file));
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer t = tf.newTransformer();
	        
			// Use XMLStreamReader to chunk input file into smaller documents	        
	        while(xmlr.hasNext()){
	        	if (xmlr.getEventType()==XMLStreamConstants.START_ELEMENT){
	        		if (xmlr.getLocalName().equals(ELT_PUBMEDARTICLE)){
	        			DOMResult result = new DOMResult();
	        			Source src = new StAXSource(xmlr);
	                    t.transform(src, result);
	                    Node domNode = result.getNode().getFirstChild();
	        			for(TopHcp topHpc: topHpcs){
	        				String res = topHpc.xpe.evaluate(domNode);
	        				int count = Integer.parseInt(res);
	        				topHpc.nPubs.addAndGet(count);
	        				log.debug("res=" + res + "[" + topHpc.nPubs.get() + "]");
	        			}
	        		}
	        	}
	        	xmlr.next();
	        }
	        
	        long end = System.currentTimeMillis();
	        for(TopHcp topHpc: topHpcs){
				log.info(topHpc.author.toString() + ": nPubs=" + topHpc.nPubs.get() + "");
			}	        
	        log.info("Duration (ms)=" + (end - start));
		}
		catch(Exception e){
			log.error("Exception :" + e.getMessage());
		}
		finally{
			close(xmlr);
		}
	}
	
	public static void close(XMLStreamReader o){
		try{
			if (o!=null){
				o.close();
			}
		}
		catch(Exception e){
			log.error("Error closing XMLStreamReader " + e.getMessage());
		}
	}

	/**
	 * Initialize list XPathSelector and authors for TopHcp based on listTopHcp, reset nPubs=0
	 * @return list of TopHcp 
	 */
	public static List<TopHcp> initTopHpc(String[][] listTopHcp){
		List<TopHcp> topHpcs = new ArrayList<TopHcp>();
		try{
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			for (String[] predicateStrings: listTopHcp){
				Author author = new Author();
				author.setLastName(predicateStrings[0]);
				author.setForeName(predicateStrings[1]);
				author.setAffiliation(predicateStrings[2]);

				XPathSelector lastName = new EqualsPredicate(new BaseSelector(PATH_PUB), ELT_LASTNAME, predicateStrings[0]);
				XPathSelector foreName = new ContainsPredicate(lastName, ELT_FORENAME, predicateStrings[1]);
				XPathSelector affiliation= new ContainsPredicate(foreName, ELT_AFFILIATION, predicateStrings[2]);
				String xpathExpr = affiliation.getCount();
				XPathExpression expr = xpath.compile(xpathExpr);
				TopHcp topHpc = new TopHcp();
				topHpc.author = author;
				topHpc.nPubs = new AtomicInteger(0);
				topHpc.xpe = expr;
				topHpcs.add(topHpc);
			}
		}
		catch(Exception e){
			log.error("Exception :" + e.getMessage());
		}
		return (topHpcs);
	}
}
