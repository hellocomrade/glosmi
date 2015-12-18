<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="us.glos.mi.db.DBCache" %>
<%@ page import="us.glos.mi.dao.MIDefaultModAvailDAO" %>
<%@ page import="us.glos.mi.domain.NameValuePair" %>
<%

List<NameValuePair> list=DBCache.getThemes();
ArrayList<String> l=new ArrayList<String>();
l.add("1");
l.add("3");
l.add("10");

MIDefaultModAvailDAO dao1=new MIDefaultModAvailDAO();
List<NameValuePair> list1=dao1.getAllNameValuePairs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
<script type="text/javascript">
function test()
{
	alert(mod_theme_div.checkboxtag.validateCheckbox());
}
function test1()
{
	alert(mod_avail_div.radiotag.validateRadio());	
}
</script>
</head>
<body>
<glos:checkboxdisplay keyValues="<%=list %>" namePrefix="test_check_theme" divId="mod_theme_div" checkedIds="<%=l %>"/>
<input type="button" onclick="test();" value="test checkbox" />

<glos:radiodisplay keyValues="<%=list1 %>" name="test_radio_theme" idPrefix="test_radio_theme" divId="mod_avail_div" checkedId="<%=100 %>"/>
<input type="button" onclick="test1();" value="test checkbox" />
</body>
</html>

