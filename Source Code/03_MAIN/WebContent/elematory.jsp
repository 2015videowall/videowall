<%@page contentType="text/html; charset=UTF-8"  errorPage="error.jsp"  
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*,java.io.*,java.sql.*"  
%><%@page import="com.millionasia.asuscloud.*,com.millionasia.asuscloud.entity.*"  
%><%@page import="com.millionasia.framework.*,com.millionasia.kscloud.servlet.*,com.millionasia.kscloud.entity.*"
%><%@page import="org.apache.logging.log4j.*" 
%><%@include file="inc_bundle.jsp" 

%><%
String filename = "elematory.jsp";
Logger logger = LogManager.getLogger(filename);

request.setCharacterEncoding("UTF-8") ;

boolean isShowLikesBtn = Boolean.valueOf((String)Configurations.getParamValue("SHOW_LIKES_BTN"));
boolean isShowUploaderId = Boolean.valueOf((String)Configurations.getParamValue("SHOW_UPLOADER_ID"));
boolean isShowVotesInfo = Boolean.valueOf((String)Configurations.getParamValue("SHOW_VOTES_INFO"));
%><%@include file="inc_sessionchk.jsp" 
%><%

String TAG = "微電影@EduCase創意競賽-國小組";
WebStorageUser wsu;

//Create Web Storage User.
if(session.getAttribute("wsu") == null){
	out.println(Utilities.jsp_alert("狀態遺失，請從首頁進入", "index,jsp"));
	return;
	
}else{
	wsu = (WebStorageUser)session.getAttribute("wsu");
}

String sort = request.getParameter("sort") == null ? "time" : (String)request.getParameter("sort");
String sortby = FolderBrowse.Sortby_LastModifiedTime;
String sortdirection = FolderBrowse.Sortdirection_DESC;

//Sql Search
/*
if("time".equals(sort)){
	sort = "lastchangetime";
}else if("file".equals(sort)){
	sort = "name";
}else if("vote".equals(sort)){
	sort = "vote";
}else{
	sort = "lastchangetime";
}
*/

//FolderBorwse
if("time".equals(sort)){
	sortby = FolderBrowse.Sortby_LastModifiedTime;
	sortdirection = FolderBrowse.Sortdirection_DESC;
}else if("file".equals(sort)){
	sortby = FolderBrowse.Sortby_FileName;
	sortdirection = FolderBrowse.Sortdirection_ASC;
}else if("vote".equals(sort)){
	sortby = FolderBrowse.Sortby_LastModifiedTime;
	sortdirection = FolderBrowse.Sortdirection_DESC;
}


SqlQuery sq = new SqlQuery();
SqlQueryResponse resp;
String pageSize = (String)Configurations.getParamValue("PAGE_SIZE");

FolderBrowse fb= new FolderBrowse();
FolderBrowseResponse respf;

//國小組
String FOLDER_ELEMATORY = (String)Configurations.getParamValue("FOLDER_ELEMATORY");
resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), FOLDER_ELEMATORY, "", "", "", SqlQuery.SearchFolder,"");
List<SearchResultEntry> elematoryList = resp.getEntries();
String folderIDElematory = "";
for(SearchResultEntry item : elematoryList){
	if("1".equals(item.getKind())){
		folderIDElematory = item.getId();
		break;
	}
}


//**** 採用 SqlQuery取得檔案
//resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), "", "1", folderIDElematory, pageSize, SqlQuery.SearchFile,sort);
//List<SearchResultEntry> elematoryVideoList = resp.getEntries();
//****

//**** 採用 FolderBrowse 取得檔案
respf = fb.getResponse(wsu.getUserID(), wsu.getInfoRelay(), wsu.getCurrentToken(), folderIDElematory, "VIDEO", sortby, sortdirection);
List<PublicFileBase> elematoryVideoList = respf.getFiles();



if("vote".equals(sort)){
	//處理投票排序
	Collections.sort(elematoryVideoList);
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
        <div class="content">
	            <div class="light"></div>
	            <div class="elematoryall"><a name="start">&nbsp;</a>
	            	<h1>國小組</h1>
	            	<div class="linkarea" id="linkarea">
	            		快速連結：
		            	<select name="link" id="link">
		            		<option value="main.jsp">回首頁</option>
		            		<option value="elematory.jsp#start" selected>國小組</option>
		            		<option value="juniorhigh.jsp#start">國中組</option>
		            		<option value="seniorhigh.jsp#start">高中組</option>
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
	            for(PublicFileBase item : elematoryVideoList){            	
	            	if(item.getMarkid().equals("1")){
	            		
	            		GetEntryInfo g = new GetEntryInfo();
	            		GetEntryInfoResponse respentry = g.getResponse(wsu.getInfoRelay(), wsu.getCurrentToken(), item.getId(), GetEntryInfo.File);
	            		
		            	String encString = "fi=" + item.getId() + ",pv=1";
		        		encString = Utilities.getBase64String(encString) + ".jpg?dis=" + WebStorageUser.SID()  + "&ecd=1&fue=0";
		            	String imgUrl = wsu.getWebRelay() + "/webrelay/getvideosnapshot/" + wsu.getCurrentToken()  + "/" + encString ;
		            	
		            	if(imgUrl.startsWith("https://")){
		            		if(!Utilities.isHttpsExists(imgUrl)){
		            			imgUrl = "images/ic_video.png";
		            		};
		            	}else{
		            		if(!Utilities.isHttpExists(imgUrl)){
		            			imgUrl = "images/ic_video.png";
		            		};
		            	}
		            	
		            	String fileName = item.getBase64display().substring(0, item.getBase64display().length()-4);
		            	String directDownload =  wsu.getWebRelay() + "/webrelay/directdownload/" + wsu.getCurrentToken() + "/?dis=" + WebStorageUser.SID() + "&fi=" + item.getId() ;
				%>
				<div id="video" class="video">
					<img src="<%=imgUrl %>" class="videosnap" id="videosnap"></img>
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
					<%if(isShowUploaderId){ %><div ><%=respentry.getContributor() %></div><%} %>
					<div class="filename" id="filename"><%=fileName%></div>
					<span id="download" style="display:none;"><%=directDownload%></span>
					<span id="fileid" style="display:none;"><%=item.getId()%></span>
				</div>
				<% 
	            	}
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