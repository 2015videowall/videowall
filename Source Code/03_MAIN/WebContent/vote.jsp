<%@page contentType="text/html; charset=UTF-8"  errorPage="error.jsp"  
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*,java.io.*,java.sql.*"  
%><%@page import="com.millionasia.asuscloud.*,com.millionasia.asuscloud.entity.*"  
%><%@page import="com.millionasia.framework.*,com.millionasia.kscloud.servlet.*,com.millionasia.kscloud.entity.*"
%><%@page import="org.apache.logging.log4j.*" 
%><%@include file="inc_bundle.jsp" 
%><%
request.setCharacterEncoding("UTF-8") ;
%><%@include file="inc_sessionchk.jsp" 
%><%
Logger logger = LogManager.getLogger("vote.jsp");

String userid = request.getParameter("u") == null ? "" : (String)request.getParameter("u");
String fileid = request.getParameter("f") == null ? "" : (String)request.getParameter("f");

if("".equals(userid) || "".equals("fileid")){
	out.print(Utilities.jsp_alert("資料不正確"));
	return;
}

DatabaseHelper db = new DatabaseHelper();
db.openConnection();
boolean rtnValue = db.vote(userid, fileid);
db.release();

if(rtnValue){
	logger.info(userid + "-->" + fileid +", success");
	out.print(Utilities.jsp_alert("投票成功", "main.jsp"));
}else{
	out.print(Utilities.jsp_alert("您已經投過票了!"));
}

%>