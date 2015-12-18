<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<%@ taglib uri="/WEB-INF/tlds/org.glc.tld" prefix="glc" %>
<%@ page import="org.glc.domain.User" %>
<%@ page import="org.glc.domain.ErrorMessage" %>
<%@page import="us.glos.mi.domain.UserParam"%>
<%@page import="org.glc.ILiteralProvider"%>
<%
String approot=this.getServletContext().getContextPath();
String last_url=request.getAttribute("LAST_URL")==null?null:request.getAttribute("LAST_URL").toString();
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link href="./pubd.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("login.jsp")));
else
	out.println("Login");
%>
</title>
<script type="text/javascript">
	
	
	function validate()
	{
		var result=true;
		var u=document.getElementById('usr');
		var p=document.getElementById('passwd');
		var e=document.getElementById('errmsg');
		if(u.value.length==0)
		{
		    if(jscss('check',u,'textbox-app'))
		    	jscss('swap',u,'textbox-app','textbox-error');
		    result=false;
		}
		else if(false==validateEmail($.trim(u.value)))
		{
			if(jscss('check',u,'textbox-app'))
				jscss('swap',u,'textbox-app','textbox-error');
		    result=false;
		}
		else
		{
			if(jscss('check',u,'textbox-error'))
				jscss('swap',u,'textbox-error','textbox-app');
			
		}
		
		if(p.value.length==0)
		{
			if(jscss('check',p,'textbox-app'))
		    	jscss('swap',p,'textbox-app','textbox-error');
		    result=false;
		}
		else
		{
			if(jscss('check',p,'textbox-error'))
				jscss('swap',p,'textbox-error','textbox-app');
			
		}
		
		if(result==false)
			e.style['visibility']='visible';
		else
			e.style['visibility']='hidden';
		return result;
	}
	
	
</script>
</head>
<body>
<jsp:include page="../../pub/searchheaderd.jsp" flush="true">
	<jsp:param value="1" name="allowempty"/>
</jsp:include>
<div style="width:95%;margin-left:auto;margin-right:auto;padding-left:auto;padding-right:auto;">
<table width="100%" cellspacing="1" cellpadding="5" align="center">
		<tbody>
			<tr>
				<td valign="top" style="text-align:left;">
					<h2>Welcome to the Great Lakes Model Inventory</h2>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td width="75%" valign="top">
					<p><strong>The Great Lakes Model Inventory</strong>, a product of the <a href="http://glos.us/">Great Lakes Observing System</a>, is an inventory and review of modeling and assessment tools that are in use throughout the Great Lakes basin and provides direct access to models (when available). The inventory builds on a survey completed by LimnoTech Inc. in 2002 and uses a web interface to allow easy search of the records as well as content management access for modelers, developers and researchers to update information and add additional models and applications. With a community-driven approach to content updates this inventory is dynamic and fosters communication not only among modelers, but between modelers and resource managers in the Great Lakes region. </p>
					<p style="padding-bottom:0px;margin-bottom:0px;"><strong>To search all records</strong>, simply  type a keyword into the search box or refine your search with categories in the  <a href='filter.jsp?searchkey=<%=request.getParameter("searchkey")==null?"": request.getParameter("searchkey")%>'>advanced search</a>. A list of matching models and applications along with short  descriptions will be generated for you to review. Click on model to find out  more information about it, including general characteristics, strengths and  weaknesses, data requirements, contact information and references, as well as a  list of applications that use the model. </p>
					<div id="searchwrapper"  style="margin-left:auto;margin-right:auto;padding-left:auto;padding-right:auto;">
						<form name="pf" action="search.glos" method="GET" onsubmit="return keycheck();">
							<input type="text" name="key" value="" class="photosearch" /><input class="photosearch_btn" type="submit" value="Search"/>
							<input type="hidden" value="10" name="l"/>
							<input type="hidden" value="0" name="o"/>
							<input type="hidden" value="0" name="f"/>
							<input type="hidden" value="" name="of"/>
						</form>
					</div>
					<p> <strong>If you are a researcher, modeler or developer</strong> with a  model or application to add to the database, or if you already have a model in  the inventory and need to make updates: please  <a href="registration.php">register</a> to become a  contributor to the inventory. </p>
					
				</td>
		
				<td valign="top">
					
					<div id="login">
<jsp:useBean id="usrp" scope="page" class="us.glos.mi.domain.UserParam" />
						<form id="form1" method="post" onsubmit="return validate();" action="<%=approot+"/pub/"%>">
<%
if(last_url!=null)
{
%>
							<input type="hidden" value="<%=last_url %>" name="<jsp:getProperty name="usrp" property="lastUrlParamName" />"  />
<%
}
%>
							<div id="navbox">
								<div class="navcontent">
									<div align="center">
										<strong>Member Sign In</strong>
									
											<table cellspacing="0" cellpadding="1" border="0" align="center" style="text-align:center;margin-top:10px;">
												<tbody>
													<tr>
														<td align="center" colspan="2">
															<glc:errordisplay id="errmsg" name="errmsg" errMsg="<%=request.getAttribute(ErrorMessage.getClassName()) %>" cssStyle="color:red;"/>
														</td>
													</tr>
													<!-- 
													<tr>
														<td align="center" colspan="2"><br/></td>
													</tr>
													-->
													
													<%
														Cookie[] cookies=request.getCookies();
														String up="";
														String pp="";
														if(cookies!=null)
														{
														    for(int i=0;i<cookies.length;++i)
														    {
															    if(cookies[i].getName().equals(usrp.getUserParamName()))
																    up=cookies[i].getValue();
															    else if(cookies[i].getName().equals(usrp.getPasswordParamName()))
																    pp=cookies[i].getValue();
														    }
														}
														
													%>
													<tr>
														<td nowrap="nowrap">
															<div align="right">
																<span style="font-size:smaller;">Username: </span>
															</div>
														</td>
														<td align="left">
															<input id="usr" type="text" value='<%= up.trim()%>' size="18" name="<jsp:getProperty name="usrp" property="userParamName" />" class="loginfont textbox-app"/>
														</td>
													</tr>
													<tr>
														<td align="center" colspan="2"></td>
													</tr>
													<tr>
														<td nowrap="nowrap">
															<div align="right">
																<span style="font-size:smaller;">Password: </span>
															</div>
														</td>
														<td align="left">
															<input id="passwd" type="password" value="<%= pp%>" size="18" name="<jsp:getProperty name="usrp" property="passwordParamName" />" class="loginfont textbox-app"/>
														</td>
													</tr>
													<tr>
														<td></td>
														<td align="left"></td>
													</tr>
													<tr>
														<td valign="top" align="right">
															<input id="pcookie" name="<jsp:getProperty name="usrp" property="rememberedParamName" />" type="checkbox" <%if(up!=null) out.println("checked"); %> />
														</td>
														<td align="left">
															<label class="loginfont">Remember me</label>
														</td>
													</tr>
													<tr>
														<td></td>
														<td align="left">
															<input type="submit" value="Sign in" name="signin" id="signin" class="genbtn"/>
														</td>
													</tr>
													<tr>
														<td align="center" colspan="2"><br/></td>
													</tr>
													<tr>
														<td align="center" colspan="2">
															<!--  <a href="#" class="loginfont">Forget your password?</a>-->
														</td>
													</tr>
												</tbody>
											</table>	
									</div>
								</div>
							</div>
						</form>

					</div>
					<div id="navbox">
						<div class="footercontent" align="center">
							<span class="loginfont" >
							    To add or edit your own model:
							</span><br/><br/>
          					<a id="invite_a" href="http://www.glos.us/glmi/registration.php" class="plainbox">Register Now</a>
							
						</div>
					</div>
					<div id="navbox">
						<div class="footercontent" align="center">
							<a href="http://glos.us/about/index.php">
								<strong>About GLOS</strong>
							</a>
							| 
							<a href="mailto:comments@glos.us?subject=Comment from GLMI Website">
								<strong>Contact GLOS</strong>
							</a>
							<br />&copy;2011 
							<a href="http://glos.us/">GLOS.us</a>. All rights reserved. 
						</div>
					</div>
					
				</td>

			</tr>
		</tbody>
	</table>
	
	
	
	
</div>	
</body>
</html>