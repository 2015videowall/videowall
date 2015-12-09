<%@page contentType="text/html; charset=UTF-8"  errorPage="error.jsp"  
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*,java.io.*,java.sql.*"  
%><%@page import="com.millionasia.asuscloud.*,com.millionasia.asuscloud.entity.*"  
%><%@page import="com.millionasia.framework.*,com.millionasia.kscloud.servlet.*,com.millionasia.kscloud.entity.*"
%><%@page import="org.apache.logging.log4j.*" 
%><%@page import="org.apache.logging.log4j.*" 
%><%@include file="inc_bundle.jsp" 
%><%
String filename = "search_result.jsp";
Logger logger = LogManager.getLogger(filename);
request.setCharacterEncoding("UTF-8") ;

boolean isShowLikesBtn = Boolean.valueOf((String)Configurations.getParamValue("SHOW_LIKES_BTN"));
boolean isShowUploaderId = Boolean.valueOf((String)Configurations.getParamValue("SHOW_UPLOADER_ID"));
boolean isShowVotesInfo = Boolean.valueOf((String)Configurations.getParamValue("SHOW_VOTES_INFO"));

%><%@include file="inc_sessionchk.jsp" 
%><%
String TAG = "微電影@EduCase創意競賽-搜尋結果";
WebStorageUser wsu;

if(session.getAttribute("result") == null || session.getAttribute("wsu") == null){
	out.println(Utilities.jsp_alert("查無資料"));
	return;
}

String sort = request.getParameter("sort") == null ? "time" : (String)request.getParameter("sort");
if("time".equals(sort)){
	sort = "lastchangetime";
}else if("file".equals(sort)){
	sort = "name";
}else if("vote".equals(sort)){
	sort = "vote";
}else{
	sort = "lastchangetime";
}

List<SearchResultEntry> list = (List<SearchResultEntry>)session.getAttribute("result");
wsu = (WebStorageUser)session.getAttribute("wsu");

if("vote".equals(sort)){
	//處理投票排序
	Collections.sort(list);
}

String height = "height:800px";
if(list.size() <= 20){
	height = "height:800px";
}else if(list.size() > 20 && list.size() <= 30){
	height = "height:1200px";
}else if(list.size() > 30 && list.size() <= 40){
	height = "height:1500px";
}else if(list.size() > 40 && list.size() <= 50){
	height = "height:1800px";
}else if(list.size() > 50 && list.size() <= 60){
	height = "height:2100px";
}else if(list.size() > 60 && list.size() <= 70){
	height = "height:2400px";
}else if(list.size() > 70 && list.size() <= 80){
	height = "height:2700px";
}else if(list.size() > 80 && list.size() <= 90){
	height = "height:3000px";
}else {
	height = "height:3600px";
}

DatabaseHelper db = new DatabaseHelper();
db.openConnection(); 

boolean isVoted = db.isVoted( (String)session.getAttribute("userid"));
String myVoteFileID = db.getMyVote( (String)session.getAttribute("userid"));

%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="inc_header.jsp"  flush="false">
        <jsp:param name="TAG" value="<%=TAG%>" />
        <jsp:param name="isVoted" value="<%=isVoted%>" />
 </jsp:include>
 <body>
  <%@include file="inc_video_play.jsp" %>
<div id="bgpic">
    <div class="topimg"><img src="images/bg.jpg"/></div>
    <div class="container">
        <div class="main">
        <button class="exit-btn exit-btn-3" id="logout">登出</button>
            <div class="box animated fadeInDown"></div>
            <a class="logo" href="https://educase.kh.edu.tw/navigate/"></a>
            <div class="w1 animated fadeInLeft"></div>
                <%@include file="inc_search_sorting.jsp" %>
        </div>
        <div class="content" style="<%=height%>">
	            <div class="light"></div>
				<div class="seniorhighall"><a name="start">&nbsp;</a>
					<h1>查詢結果</h1>
					<div class="linkarea" id="linkarea">
	            		快速連結：
		            	<select name="link" id="link">
		            		<option value="main.jsp">回首頁</option>
		            		<option value="elematory.jsp#start" >國小組</option>
		            		<option value="juniorhigh.jsp#start" >國中組</option>
		            		<option value="seniorhigh.jsp#start" >高中組</option>
		            		<option value="voctional.jsp#start">高職組</option>
		            		<option value="teacher.jsp#start">教職員組</option>
		            	</select>
	            	</div>
	            	<div class="sortingarea" id="sortingarea">
							排序：
							<select name="sorting" id="sorting">
								<option value="<%=filename%>?sort=time#start" <%if(("lastchangetime").equals(sort)){out.print("selected");} %>>時間</option>
		            			<option value="<%=filename%>?sort=file#start"  <%if(("name").equals(sort)){out.print("selected");} %>>檔名</option>
		            			<%if(isShowVotesInfo){ %><option value="<%=filename%>?sort=vote#start" <%if(("vote").equals(sort)){out.print("selected");} %>>投票數</option><%} %>
		            		</select>
					</div>
	            <%
	            for(SearchResultEntry item : list){
	            	
	            	GetEntryInfo g = new GetEntryInfo();
            		GetEntryInfoResponse respentry = g.getResponse(wsu.getInfoRelay(), wsu.getCurrentToken(), item.getId(), GetEntryInfo.File);
	            	
	            	String encString = "fi=" + item.getId() + ",pv=1";
	        		encString = Utilities.getBase64String(encString) + ".jpg?dis=" + WebStorageUser.SID()  + "&ecd=1&fue=0";
	            	String imgUrl = wsu.getWebRelay() + "/webrelay/getvideosnapshot/" + wsu.getCurrentToken()  + "/" + encString;
	            	
	            	if(imgUrl.startsWith("https://")){
	            		if(!Utilities.isHttpsExists(imgUrl)){
	            			imgUrl = "images/ic_video.png";
	            		};
	            	}else{
	            		if(!Utilities.isHttpExists(imgUrl)){
	            			imgUrl = "images/ic_video.png";
	            		};
	            	}
	            	
	            	String fileName = item.getRawEntryName().substring(0, item.getRawEntryName().length()-4);
	            	String directDownload =  wsu.getWebRelay() + "/webrelay/directdownload/" + wsu.getCurrentToken() + "/?dis=" + WebStorageUser.SID() + "&fi=" + item.getId() ;
				%>
				<div id="video" class="video">
					<img src="<%=imgUrl %>" class="videosnap"></img>
					<%if(isShowLikesBtn || isShowVotesInfo){ %><div class="description">
						<%
							String good = "讚啦";
							if(myVoteFileID.equals(item.getId())){
								good = "我的讚";
							}
							%>
						<%if(isShowLikesBtn){ %><a href="javascript:void(0)" class="vote" id="<%=item.getId() %>"><%=good %></a><%} %>
						<%if(isShowVotesInfo){ %><span class="likes"><img src="images/ic_likes.png"  width="20" height="20" /><%=item.getVotes() %></span><%} %>
					</div><%} %>
					<%if(isShowUploaderId){ %><div ><%=respentry.getContributor()%></div><%} %>
					<div class="filename" id="filename"><%=fileName%></div>
					<span id="download" style="display:none;"><%=directDownload%></span>
				</div>
				<% 
	            }
	            %>
				</div>
			</div>
			<br/><br/><br/><br/>
            <div class="people"></div>
            <div class="bottom">
            	<div class="sponsor"></div>
        	</div>
        </div>
    </div>

</body>
</html>
<%
db.release();
%>