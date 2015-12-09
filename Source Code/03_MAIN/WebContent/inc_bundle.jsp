<%@page contentType="text/html; charset=UTF-8" 
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*" 
%><%
request.setCharacterEncoding("UTF-8") ; 
%>
<%
//Loading configuration.
String systemName = "Asus KS Cloud";

Integer minimumFirefox = 22;
Integer minimumSafari = 50; /* This number is 10 times the actual version number */
Integer minimumChrome = 33;
Integer minimumIE = 9;

String ua = request.getHeader("User-Agent");

boolean isMSIE = (ua != null && ua.indexOf("MSIE") != -1);
boolean isMSIE10 = (ua != null && ua.indexOf("MSIE 10") != -1);
boolean isMSIE9 = (ua != null && ua.indexOf("MSIE 9") != -1);
boolean isMSIE8 = (ua != null && ua.indexOf("MSIE 8") != -1);
boolean isChrome = (ua != null && ua.indexOf("Chrome/") != -1);
boolean isSafari = (!isChrome && (ua != null && ua.indexOf("Safari/") != -1));
boolean isSafari5 = (!isChrome && (ua != null && ua.indexOf("Safari/") != -1 && ua.indexOf("Version/5.") != -1));
boolean isFirefox = (ua != null && ua.indexOf("Firefox/") != -1);
boolean isAndroid = (ua != null && ua.indexOf("Android") != -1);
boolean isIOS = (ua != null && (ua.indexOf("iPhone") != -1 || ua.indexOf("iPad") != -1));
 
String remoteIP = request.getRemoteAddr();


%>