<%@page import="sun.misc.BASE64Encoder"%>
<%@page contentType="text/html; charset=UTF-8"  errorPage="error.jsp"  
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*,java.io.*,java.sql.*"  
%><%@page import="com.millionasia.asuscloud.*,com.millionasia.asuscloud.entity.*"  
%><%@page import="com.millionasia.framework.*,com.millionasia.kscloud.servlet.*,com.millionasia.kscloud.entity.*"
%><%@page import="org.apache.logging.log4j.*" 
%><%@page import="org.json.*"  
%><%@include file="inc_bundle.jsp" 
%><%
Logger logger = LogManager.getLogger("index.jsp");
request.setCharacterEncoding("UTF-8") ;
String AFTER_LOGOUT_PAGE = (String)Configurations.getParamValue("AFTER_LOGOUT_PAGE");

//openID
if(request.getParameter("uid") == null){
	out.print(Utilities.jsp_alert("您尚未登入系統", AFTER_LOGOUT_PAGE));
}else{
	 String uid = (String)request.getParameter("uid");
	 session.setAttribute("userid", uid);
	 response.sendRedirect("main.jsp");
}

%>