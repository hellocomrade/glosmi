<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@page import="us.glos.mi.domain.ModelInfo"%>
<%@page import="us.glos.mi.domain.SearchResultParam"%>
<%
String key=null;
ArrayList<ModelInfo> mods=null;
int count=0;
String url_prev=null;
String url_next=null;
if(null!=request.getAttribute(SearchResultParam.getSearchWordsParam()))
	key=request.getAttribute(SearchResultParam.getSearchWordsParam()).toString();
if(null!=request.getAttribute(SearchResultParam.getSearchResultParam())
		&&request.getAttribute(SearchResultParam.getSearchResultParam()) instanceof ArrayList<?>)
	mods=(ArrayList<ModelInfo>)request.getAttribute(SearchResultParam.getSearchResultParam());
if(null!=request.getAttribute(SearchResultParam.getSearchNextURLParam()))
	url_next=request.getAttribute(SearchResultParam.getSearchNextURLParam()).toString().trim();
if(null!=request.getAttribute(SearchResultParam.getSearchPrevURLParam()))
	url_prev=request.getAttribute(SearchResultParam.getSearchPrevURLParam()).toString().trim();
if(null!=request.getAttribute(SearchResultParam.getSearchCountParam()))
	count=Integer.parseInt(request.getAttribute(SearchResultParam.getSearchCountParam()).toString().trim());
if(key==null)
	response.sendRedirect("search.html");	
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="pube.css" rel="stylesheet" type="text/css" />
<style>
body
{
	background:none repeat scroll 0 0 #ffffff;
}
h3
{
font-size:14px;
}

</style>
<title>
<%

if(literals!=null&&literals.getText("/pub/searchresult.jsp")!=null)
	out.println(String.format("%s%s - %s",literals.getText("title"),literals.getText("/pub/searchresult.jsp"),key));
else
	out.println(String.format("Search Result for - %s",key));
%>
</title>
<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
<script type="text/javascript">
/*$(function() {
	$(".r").mouseover(function(){
		$(this).addClass("highlight");
	});
	$(".r").mouseout(function(){
		$(this).removeClass("highlight");
	});
});*/
function keycheck_btm()
{
	var id=document.getElementById('txt_btm_sbox');
	if(id!=null)
	{
		if(id.value!='')return true;
		else return false;
	}
	return false;
}
</script>
</head>
<body>
<jsp:include page="searchheadere.jsp" flush="true">
<jsp:param value="<%=key%>" name="searchkey"/>
</jsp:include>	

<!--  <div style="width:100%;height:20px;" class="strip_s"></div>-->
<div style="width:100%;margin:auto;font-size:12px;text-align:center;padding-top:10px;">
	<span style="font-weight:bold;"><%=count>1?String.format("%d results returned",count):count==1?"1 result returned":"" %></span>
</div>
<div style="width:95%;margin:auto;padding-top:10px;">
<ol style="margin-left:0;padding-left:0;">
<%
if(mods!=null&&!mods.isEmpty())
{
	for(ModelInfo mod:mods)
	{
		
%>
	<li class="r">
		<h3>
			<a href="model/<%=mod.getId() %>">
				<%=mod.getName()+(mod.getVersionNo()==null||mod.getVersionNo().trim().equals("")?"":"("+mod.getVersionNo()+")")%>
			</a>
		</h3>
		<div style="margin-top:10px;font-size:12px;"><span style="font-weight:bold;">Description:</span>&nbsp;<%=mod.getDescription()%></div>
	</li>
<%
	}
}

%>
</ol>
<%if(mods==null){ %>
<div style="margin-left:120px;">
	<p>Your search <span style="font-weight:bold;"><%=key %></span> did not match any documents in the inventory.</p>
	<p>Suggestions:</p>
	<ul>
		<li>Make sure all words are spelled correctly.</li>
		<li>Try different keywords.</li>
		<li>Try more general keywords.</li>
	</ul>
</div>
<%} %>
</div>
<br/>

<br/>


<table width="95%" cellspacing="0" cellpadding="0" bgcolor="#CEE22A" align="center" style="">
	<tr>
		<td>
			<div style="margin-left:16px;width:408px;">
				<div align="left">
<%if(url_prev!=null){ %>
					<a href="<%=url_prev %>">Previous</a>
<%}else{ %>
					<span>Previous</span>
<%} %>
&nbsp;&nbsp;&nbsp;&nbsp;
<%if(url_next!=null){ %>
					<a href="<%=url_next%>">Next</a>
<%}else{ %>
					<span>Next</span>
<%} %>
				</div>
			</div>
		</td>
		<td align="right" style="width:30%;">
			<div style="margin-right:10px;height:50px;">
			<form name="f" action="search.glos" method="GET" onsubmit="return keycheck_btm();">
				<table cellspacing="0" cellpadding="0" border="0" style="padding:8px 0pt 0pt;">
					<tr>
						<td class="searchbox-td" style="width:100%;">
							<input type="text" id="txt_btm_sbox" value="<%=key %>" maxlength="255" size="40" name="key" class="searchbox" style="background-color:#FFFFA2;"/>
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
				</form>
			</div>
		</td>
	</tr>
</table>
</body>
</html>