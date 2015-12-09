<%@page contentType="text/html; charset=UTF-8" 
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*" 
%><%@page import="com.millionasia.framework.*,com.millionasia.kscloud.servlet.*"  
%><%
request.setCharacterEncoding("UTF-8") ;
%>
<%
//Check the login status
/*
System.out.println("session=====================");
System.out.println("file name: " + request.getRequestURI());
System.out.println("session ID: " + session.getId());
System.out.println("session created time: " + Utilities.getTime(session.getCreationTime()));
System.out.println("session LastAccessedTime: " + Utilities.getTime(session.getLastAccessedTime()));
System.out.println("session status  : session(login) " + session.getAttribute("login") );
*/

String OPENID_LOGIN_PAGE = (String)Configurations.getParamValue("OPENID_LOGIN_PAGE");
Boolean isLogin = session.getAttribute("userid")== null? false:true;
if(!isLogin){
	out.print(Utilities.jsp_alert("請先完成OPEN ID登入", OPENID_LOGIN_PAGE));
	return;
}

%>