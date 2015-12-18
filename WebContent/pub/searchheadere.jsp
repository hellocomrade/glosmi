<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>

<script type="text/javascript">
function keycheck()
{
<%
if(request.getParameter("allowempty")==null)
{
%>
	var id=document.getElementById('txt_sbox');
	if(id!=null)
	{
		if(id.value!='')return true;
		else return false;
	}
	return false;
<%
}
else
{
%>
	return true;
<%
}
%>
}
</script>
<div align="right" id="header">
	<a href="http://glos.us/">
        <img src="../img/logo_glos.gif" alt="GLOS" hspace="0" align="left" border="0">
    </a>
    <a href="pub/">
        <img src="../img/logo_glmi.gif" alt="Great Lakes Observing System | Modeling Inventory" hspace="0" align="left" border="0">
    </a>
<%
if(request.getParameter("showbox")==null||!request.getParameter("showbox").equals("0"))
{
%>				
	<table width="254px;" border="0" align="right" cellpadding="0" cellspacing="0" style="margin-top:0px; height:100px; padding-right:0px;">
		<tr >
		    <td> 
		        <div id="headersearchwrapper" align="center">
		            <form name="f" action="search.glos" method="GET" onsubmit="return keycheck();">
						<input type="text" id="txt_sbox" value="<%=request.getParameter("searchkey")==null?"Enter keywords, developer, agency": request.getParameter("searchkey")%>" name="key"  class="hphotosearch" onfocus="if (this.value==this.defaultValue) this.value='';" /><input class="header_searchbtn" type="submit" value="Search" style="margin-top:57px" />
						<input type="hidden" value="10" name="l"/>
						<input type="hidden" value="0" name="o"/>
						<input type="hidden" value="0" name="f"/>
						<input type="hidden" value="" name="of"/>
					</form>
					<br/>
					<span style="margin-left:140px">
					    <a href='filter.jsp?<%=request.getParameter("searchkey")==null?"key=": request.getQueryString()%>' class="headersearch">Advanced Search</a>
					</span>
				</div>
     	  </td>
        </tr>
    </table>

<%
}
%>	
</div>