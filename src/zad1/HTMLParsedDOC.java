package zad1;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.text.html.parser.*;

public class HTMLParsedDOC extends Parser {
	
	private DTD dtd;
	
	static public HTMLParsedDOC createParsedDOC(String name) {
		HTMLParsedDOC doc = null ;
		try {
			DTD dtd = DTD.getDTD(name);
			doc = new HTMLParsedDOC(dtd);
			doc.dtd = dtd;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private HTMLParsedDOC(DTD dtd) {
		super(dtd);
	}
	
/*	@Override
	protected void handleStartTag(TagElement tag) {
		if(tag==null)
			return;
		System.out.println(tag.getHTMLTag().toString()+" ");
	}
	
	public TagElement getTag(String pathToTag, String delimeter) {
		
		String[] path = pathToTag.split(delimeter);
		if (path.length!=1) {
			String[] path2 = new String[path.length-1];
			for(int i = 1 ; i<path.length;i++) {
				path2[i-1]=path[i];
			}
			String deeper = Arrays.stream(path2).collect(Collectors.joining(delimeter.subSequence(0, delimeter.length()))).toString();
			//System.out.println(deeper);
			getTag(deeper, delimeter);
		}
		return null;
	}*/

}
