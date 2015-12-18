package us.glos.mi.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.glc.Logger;
import org.glc.utils.HTMLUtilities;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.domain.NameValuePair;

public class RadioTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -570683342937532906L;

    static final String CHECKED="checked='checked'";
	
	private String idPrefix="glosmiradiotag_input";
	private String name="glosmiradiotag_input";
	private String cssClass="";
		
	private String divId="glosmiradiostagdiv";
	private List<NameValuePair> keyvalues=null;
	private int checkedId=-1;
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
				int len=keyvalues.size();
				String checked="";
				
				for(NameValuePair nvp:keyvalues)
				{
					if(this.checkedId>=0&&
					    this.checkedId==nvp.id)
						checked=CHECKED;
					else
						checked="";
					jw.print(String.format("<span><input id='%s_%d' name='%s' type='radio' %s value='%s'/><span class='%s'>%s</span></span>",
							this.idPrefix,count,this.name,checked,
							nvp.id,this.cssClass,
							HTMLUtilities.filterDisplay(nvp.name)));
					
					++count;
				}
				count=0;
				
			    jw.print("</div>");
			    jw.print("<script type='text/javascript'>");
			    jw.print(String.format("var %s={};",this.divId));
			    jw.print(String.format("%s.radiotag= function(){",this.divId));
			    jw.print("return{");
			    jw.print("validateRadio:function(){");
			    jw.print("var result=false;");
			    jw.print(String.format("$('#%s input:radio').each(",this.divId));
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
	public void setName(String name) {
		if(name!=null)
			this.name = name;
		else
			this.name="";
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
	public void setCheckedId(int checkedId) {
		this.checkedId = checkedId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}
}
