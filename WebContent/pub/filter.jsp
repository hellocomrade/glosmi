<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="us.glos.mi.db.DBCache" %>
<%@ page import="us.glos.mi.domain.SearchResultParam" %>
<%
String empty="";
String key=null;
key=request.getParameter("k");
ArrayList<String> cate=null;
ArrayList<String> them=null;
int avil=-1;
String[] temps=request.getParameterValues(SearchResultParam.getFilterCategoryParam());
if(temps==null)
	cate=null;
else
{
	if(temps.length>0)
	{
		cate=new ArrayList<String>(temps.length);
		for(int i=0;i<temps.length;++i)
			cate.add(temps[i]);
	}
		
}

temps=request.getParameterValues(SearchResultParam.getFilterThemeParam());
if(temps==null)
	them=null;
else
{
	if(temps.length>0)
	{
		them=new ArrayList<String>(temps.length);
		for(int i=0;i<temps.length;++i)
			them.add(temps[i]);
	}
		
}
String temp=request.getParameter(SearchResultParam.getFilterAvailParam());
if(temp==null||temp.equals(""))
	avil=-1;
else
{
	try
	{
		avil=Integer.parseInt(temp);
	}
	catch(NumberFormatException e)
	{
		avil=-1;
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Great Lakes Model Inventory Search Page -- Advanced Search</title>
<link href="pub.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function keycheck()
{
	var id=document.getElementById('txt_sbox');
	if(id!=null)
	{
		if(id.value!='')return true;
		else return false;
	}
	return false;
}
</script>
</head>
<body bgcolor="#ffffff">
<jsp:include page="searchheader.jsp" flush="true">
<jsp:param value="" name="searchkey"/>
<jsp:param value="<%=\"0\" %>" name="showbox"/>
</jsp:include>
<div style="width:95%;margin:auto;font-size:12px;padding-top:10px;">
	
	<form name="f" action="search.glos" method="GET" onsubmit="return keycheck();">
		<div style="font-size:14px;font-weight:bold;">Use the form below to set up the filter for your search</div>
		<div style="width:80%;">
			<fieldset>
				<legend>Model Category:</legend>
				<glos:checkboxdisplay keyValues="<%=DBCache.getModelCategories() %>" namePrefix="<%=SearchResultParam.getFilterCategoryParam() %>" divId="filter_cat_div" checkedIds="<%=cate%>"/>
			</fieldset>
		</div><br/>
		<div style="width:80%;">
			<fieldset>
				<legend>GLOS Theme:</legend>
				<glos:checkboxdisplay keyValues="<%=DBCache.getThemes() %>" namePrefix="<%=SearchResultParam.getFilterThemeParam() %>" divId="filter_theme_div" checkedIds="<%=them %>"/>
			</fieldset>
		</div><br/>
		<div style="width:80%;margin-bottom:10px;">
			<fieldset>
				<legend>Availability:</legend>
				<glos:radiodisplay keyValues="<%=DBCache.getModelAvailibilites() %>" name="<%=SearchResultParam.getFilterAvailParam() %>" idPrefix="filter_avail_radio" divId="filter_avail_div" checkedId="<%=avil %>"/>
			</fieldset>
		</div>
		<div style="background-color:#CEE22A;padding:6px;height:50px;">
		<table cellspacing="0" cellpadding="0" border="0" align="left" style="margin:6px;width:30%;">
			<tbody>
				<tr>
					<td>
					    <div align="center" style="margin-right:10px;margin-bottom:0;">
							<table cellspacing="0" cellpadding="0" border="0" style="">
								<tr>
									<td class="searchbox-td" style="width:100%;">
										<input type="text" id="txt_sbox" value="<%=request.getParameter("key")==null?empty: request.getParameter("key")%>" maxlength="255" size="30" name="key" class="searchbox" style="background-color:#FFFFA2;"/>
										<input type="hidden" value="10" name="l"/>
										<input type="hidden" value="0" name="o"/>
										<input type="hidden" value="0" name="f"/>
										<input type="hidden" value="" name="of"/>
									</td>
									<td>
										<div class="searchbtn-outter">
											<div class="searchbtn-inner">
												<input class="searchbtn" type="submit" value="Search" />
											</div>
										</div>
														
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		</div>
	</form>
</div>
</body>
</html>