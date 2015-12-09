<%@page contentType="text/html; charset=UTF-8"  isErrorPage="true"  
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*"  
%><%@page import="com.millionasia.asuscloud.*"  
%><%@page import="org.apache.logging.log4j.*"  
%><%@include file="inc_bundle.jsp" 
%><%
request.setCharacterEncoding("UTF-8") ;
%><%
Logger logger = LogManager.getLogger("error.jsp");
String TAG = "發生錯誤";
logger.error("******************************************************************");
logger.error(exception.getMessage());

%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="inc_header.jsp"  flush="false">
        <jsp:param name="TAG" value="<%=TAG%>" />
        <jsp:param name="isVoted" value="false" />
 </jsp:include><body>
<div id="bgpic">
    <div class="topimg"><img src="images/bg.jpg"/></div>
    <div class="container">
        <div class="main">
            <button class="exit-btn exit-btn-3" id="logout">登出</button>
            <div class="box animated fadeInDown"></div>
            <a class="logo" href="https://educase.kh.edu.tw/navigate/"></a>
            <div class="w1 animated fadeInLeft"></div>
            <div class="w2 animated fadeIn"></div>
        </div>
        <div class="content">
            
          		<%
          				out.println(exception.getClass().getName() + "<br />" + exception.getMessage() + "<br />");
						out.println("<ul style=\"display:none;\">");
						
							for (StackTraceElement ste : exception.getStackTrace())
							{
								logger.error(ste.getClassName() + ste.getLineNumber() );
								out.println("<li>" + ste.getClassName() + "(line:" +ste.getLineNumber() + ")</li>");
							}
							out.println("</ul>");

 				%>
        </div>
        <div class="bottom">
            <div class="sponsor"></div>
        </div>
    </div>
</div>
</body>
</html>
