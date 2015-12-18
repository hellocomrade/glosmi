package us.glos.mi.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;

import java.util.List;
public class AgencySelectTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2335100668643924628L;
	static final String SELECTED="selected='selected'";
	List<String> keyvalues=null;
	
	private String id="agencyselecttag_input";
	private String name="";
	private String cssClass="";
	private String value="";
	private String size="25";
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		
		try
		{
			JspWriter jw=this.pageContext.getOut();
			jw.print("<div>");
			jw.print(String.format("<input id='%s' name='%s' type='textbox' value='%s' class='%s' size='%s'/>",
								this.id,this.name,this.value,this.cssClass+" "+id,this.size));
			jw.print("</div>");
			jw.print("<script>");
			
			jw.print("$(function() {");
			StringBuilder sb=new StringBuilder();
			for(String s:this.keyvalues)
			{
				sb.append("'"+s+"'");
				sb.append(",");
			}
			if(sb.length()>0)
				sb.deleteCharAt(sb.length()-1);
			jw.print(String.format("var availableTags = [%s];",sb.toString()));
			jw.print(String.format("$('.%s').autocomplete({",this.id));
			jw.print("source: availableTags,");
			jw.print("minLength: 2");
			jw.print("});");
			jw.print("});");

			jw.print("</script>");
		}
		catch(java.io.IOException ie)
		{
			Logger.writeLog("us.glos.mi.tags.AgencySelectTag: "+ie.getMessage(),LogLevel.SEVERE);
		}
		return (SKIP_BODY);
	}
	
	public void setKeyValue(List<String> list)
	{
		this.keyvalues=list;
	}

	public void setValue(String v)
	{
		this.value=v;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	public void setSize(String s)
	{
		this.size=s;
	}
}
