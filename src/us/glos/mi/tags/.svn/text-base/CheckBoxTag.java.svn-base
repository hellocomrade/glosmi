package us.glos.mi.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspWriter;
import java.util.List;

import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;
import org.glc.utils.HTMLUtilities;

import us.glos.mi.domain.NameValuePair;

public class CheckBoxTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -570683342937532906L;

	static final String CHECKED="checked='checked'";
	
	private String idPrefix="glosmicheckboxtag_input";
	private String namePrefix="glosmicheckboxtag_input[]";
	private String cssClass="";
	private String nameIndex="glosmicheckboxnameIndex";
	private String nameSplitter=",";
	private String divId="glosmicheckboxtagdiv";
	private List<NameValuePair> keyvalues=null;
	private List<String> checkedIds=null;
	private int colPerRow=3;
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try
		{
			JspWriter jw=this.pageContext.getOut();
			jw.print(String.format("<div id='%s'>",this.divId));
			if(this.keyvalues!=null&&this.keyvalues.size()>0)
			{
				int count=0;
				int colCount=0;
				int len=keyvalues.size();
				String checked="";
				String[] names=new String[len];
				StringBuilder sb=new StringBuilder();
				if(this.checkedIds!=null&&this.checkedIds.size()>0)
					java.util.Collections.sort(this.checkedIds);
				jw.println("<table>");
				for(NameValuePair nvp:keyvalues)
				{
					if(colCount==0)
						jw.println("<tr>");
					if(this.checkedIds!=null&&
					    java.util.Collections.binarySearch(this.checkedIds,String.format("%d", nvp.id))>=0)
						checked=CHECKED;
					else
						checked="";
					jw.print(String.format("<td><span><input id='%s_%d' name='%s' type='checkbox' %s value='%s'/><span class='%s'>%s</span></span></td>",
							this.idPrefix,count,this.namePrefix,checked,
							nvp.id,this.cssClass,
							HTMLUtilities.filterDisplay(nvp.name)));
					names[count]=String.format("%s_%d", this.namePrefix,count,nvp.id,nvp.name);
					++count;
					
					if(++colCount==colPerRow)
					{
						jw.println("</tr>");
						colCount=0;
					}
				}
				jw.println("</table>");
				/*count=0;
				for(String temp:names)
				{
					sb.append(temp);
					if(count<len-1)
					    sb.append(this.nameSplitter);
					++count;
				}
				jw.print(String.format("<input type='hidden' name='%s' value='%s' />", this.nameIndex,sb.toString()));*/
			    jw.print("</div>");
			    jw.print("<script type='text/javascript'>");
			    jw.print(String.format("var %s={};",this.divId));
			    jw.print(String.format("%s.checkboxtag= function(){",this.divId));
			    jw.print("return{");
			    jw.print("validateCheckbox:function(){");
			    jw.print("var result=false;");
			    jw.print(String.format("$('#%s input:checkbox').each(",this.divId));
			    jw.print("function(){");
			    jw.print("if($(this).attr('checked')==true)result=true;");
			    jw.print("}");//end of function in each
			    jw.print(");");//end of each
			    jw.print("return result;}");//end of validateRadios
			    jw.print("};");//end of return
			    jw.print("}();");//end of #divid.checkboxtag namespace
				jw.print("</script>");
			}
		}
		catch(java.io.IOException ie)
		{
			Logger.writeLog("us.glos.mi.tags.RadioTag: "+ie.getMessage(),LogLevel.SEVERE);
		}
		return (SKIP_BODY);
	}
	public void setIdPrefix(String id) {
		if(id!=null)
			this.idPrefix = id;
		else
			this.idPrefix="";
	}
	public void setNamePrefix(String name) {
		if(name!=null)
			this.namePrefix = name;
		else
			this.namePrefix="";
	}
	public void setCssClass(String cssClass) {
		if(cssClass!=null)
			this.cssClass = cssClass;
		else
			this.cssClass="";
	}
	public void setKeyValues(List<NameValuePair> keyvalues) {
		this.keyvalues = keyvalues;
	}
	public void setCheckedIds(List<String> checkedIds) {
		this.checkedIds = checkedIds;
	}
	/*public void setNameIndex(String nameIndex) {
		if(nameIndex!=null)
		    this.nameIndex = nameIndex;
	}
	public void setNameSplitter(String nameSplitter) {
		if(nameSplitter!=null)
		    this.nameSplitter = nameSplitter;
	}*/
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public void setColPerRow(int colPerRow) {
		if(colPerRow>0)
			this.colPerRow = colPerRow;
	}
	
	

}
