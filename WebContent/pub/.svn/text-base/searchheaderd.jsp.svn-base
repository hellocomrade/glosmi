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
		<table cellspacing="0" cellpadding="0" border="0" align="right" style="margin-top:38px;width:30%;">
			<tbody>
				<tr>
					<td>
					               <div align="center" style="margin-right:10px;margin-bottom:0;">
									    <form name="f" action="search.glos" method="GET" onsubmit="return keycheck();">
											<table cellspacing="0" cellpadding="0" border="0" style="padding:8px 0pt 0pt;">
												<tr>
													<td class="searchbox-td" style="width:100%;">
														<input type="text" id="txt_sbox" value="<%=request.getParameter("searchkey")==null?"": request.getParameter("searchkey")%>" maxlength="255" size="30" name="key" class="searchbox" onkeypress="keycheck(this);">
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
												<tr>
													<td align="right" colspan="2" style="vertical-align:text-top;">
														<div style="font-size:12px;margin-top:0;padding-top:0;vertical-align:text-top;">
															<a href='filter.jsp?searchkey=<%=request.getParameter("searchkey")==null?"": request.getParameter("searchkey")%>' style="color:#ffffff;">Advanced Search</a>
														</div>
													</td>
												</tr>
											</table>
										</form>
										
									</div>
					</td>
				</tr>
			</tbody>
		</table>
<%
}
%>	
</div>