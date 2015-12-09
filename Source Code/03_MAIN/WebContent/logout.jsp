<%@page contentType="text/html; charset=UTF-8" 
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*" 
%><%@page import="com.millionasia.framework.*,com.millionasia.kscloud.servlet.*"  
%><%
request.setCharacterEncoding("UTF-8") ;
%>
<%
session.invalidate();
String AFTER_LOGOUT_PAGE = (String)Configurations.getParamValue("AFTER_LOGOUT_PAGE");

out.print(Utilities.jsp_alert("您已登出系統", AFTER_LOGOUT_PAGE));

%>