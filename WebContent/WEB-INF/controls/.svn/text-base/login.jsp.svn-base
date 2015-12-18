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
<!--  <meta http-equiv="Expires" content="-1">-->
<meta http-equiv="Pragma" content="no-cache">
<link href="./pub.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.core.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.button.js"></script>
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
	
	$(function(){
		$("#invite_a").button();
		$("#invite_a").click(function(){alert('Kelli?');return false;
		});
	});	
</script>
</head>
<body bgcolor="#ffffff">
<jsp:include page="../../pub/searchheader.jsp" flush="true">
	<jsp:param value="1" name="allowempty"/>
</jsp:include>

	<table width="94%" cellspacing="1" cellpadding="5" align="center">
		<tbody>
			<tr>
				<td valign="top" style="text-align:left;">
					<span style="font-size:16px;font-weight:bold;font-family:Arial,Helvetica,sans-serif;">Welcome to the Great Lakes Model Inventory hosted by Great Lakes Observing System (GLOS)</span>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td width="75%" valign="top" style="font-size:12px;font-family:Arial,Helvetica,sans-serif;">
					<p style="margin-bottom:0pt;text-align:left;">
						The Great Lakes Model Inventory delivers an inventory and review of modeling and assessment tools that are in use throughout the Great Lakes basin and provides direct access to models (when available) as an on-line model clearinghouse. The inventory builds from a survey completed by LimnoTech Inc. in 2002 and uses a web interface to allow easy search of the records as well as content management access for modelers, developers and researchers to update information and add more models and applications. With a community-driven approach to content updates this inventory is dynamic and fosters communication not only among modelers, but between modelers and resource managers in the Great Lakes region.
					</p>
					<p style="margin-bottom:0pt;text-align:left;">
						GLOS is a regional association of the U.S. Integrated Ocean Observing System (IOOS) working to advance the coordination of the bi-national observing network of people, processes and technology that work together to maximize access to critical, real-time and historical information about the Great Lakes and St. Lawrence River system for use in managing, safeguarding and understanding these immensely valuable freshwater resources.
						
					</p>
					<p style="margin-bottom:0pt;text-align:left;">
						To begin, simply type a keyword into the search bar or refine your search with categories in the advanced search. A list of matching models and applications along with short descriptions will be generated for you to review. Click on model to find out more information about it including general characteristics, strengths and weaknesses, data requirements, contact information and references, as well as a list of applications that use the model.
					</p>
					<p style="margin-bottom:0pt;text-align:left;">
					    If you are a researcher, modeler or developer with a model or application to add to the database or if you already have a model in the inventory and need to make updates, please make sure to register to be a contributor to the inventory. 			
					</p>
					
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
							<table class="loginform" width="100%" cellspacing="3" cellpadding="5" border="0">
								<tbody>
									<tr>
										<td nowrap="nowrap" valign="top" bgcolor="#CEE22A" style="text-align:center;">
											<table cellspacing="0" cellpadding="1" border="0" align="center" style="text-align:center;">
												<tbody>
													<tr>
														<td align="center" colspan="2">
															<b class="loginfont">Member Sign in</b>
														</td>
													</tr>
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
														<td>
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
														<td>
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
															<input type="submit" value="Sign in" name="signin" id="signin" class="loginfont"/>
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
										</td>
									</tr>
								</tbody>
							</table>
						</form>

					</div>
					<br/>
					<table id="links" width="100%" cellpadding="0" class="loginform">
						<tbody>
							<tr bgcolor="#CEE22A">
								<td valign="top">
									<div align="center" style="margin:10px 0pt;">
										<span class="loginfont">Want to join GLMI? It's free.</span>
									</div>
									<div align="center" style="margin:10px 0pt;">
									    <a id="invite_a" href="#" style="font-size:larger;">Register Now >></a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>

				</td>

			</tr>
		</tbody>
	</table>
	<br/>
	
	<table width="95%" cellspacing="0" cellpadding="0" bgcolor="#cee22a" align="center" style="margin-bottom:5px;">
		<tbody>
			<tr>
				<td>
					<div align="center" style="margin-top:4px;margin-bottom:4px;">
						<a class="loginfont" href="" target="_blank">Term</a>-
						<a class="loginfont" href="" target="_blank">Help</a>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	
</body>
</html>