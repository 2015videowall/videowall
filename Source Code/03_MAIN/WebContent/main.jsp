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
Logger logger = LogManager.getLogger("main.jsp");
request.setCharacterEncoding("UTF-8") ;
%><%@include file="inc_sessionchk.jsp" 
%><%
 
String TAG = "微電影@EduCase創意競賽";
WebStorageUser wsu;
session.setAttribute("wsu", null);

boolean isShowLikesBtn = Boolean.valueOf((String)Configurations.getParamValue("SHOW_LIKES_BTN"));
boolean isShowUploaderId = Boolean.valueOf((String)Configurations.getParamValue("SHOW_UPLOADER_ID"));
boolean isShowVotesInfo = Boolean.valueOf((String)Configurations.getParamValue("SHOW_VOTES_INFO"));

//Create Web Storage User.
if(session.getAttribute("wsu") == null){
	String UserID = (String)Configurations.getParamValue("USER_ID");
	String UserPwd = (String)Configurations.getParamValue("USER_PWD");
	String ServicesGateway = (String)Configurations.getParamValue("ASUS_WEBSTORAGE_SERVICE_GATEWAY");
	String encKey = (String)Configurations.getParamValue("ASUS_WEBSTORAGE_PROGKEY").substring(0, 24);
	boolean isPrivateCloud = Boolean.valueOf((String)Configurations.getParamValue("ASUS_WEBSTORAGE_IS_PRIVATE_CLOUD"));

	
	wsu = new WebStorageUser();
	wsu.setAccount(UserID);
	wsu.setPassword(UserPwd);
	wsu.setServiceGateway(ServicesGateway);
	
	Object at;
	logger.debug("Start Acquire Token");
	if(isPrivateCloud){
		 at = new AcquireTokenPrivateCloud();
	}else{
		at = new AcquireToken();
	}
	//AcquireTokenPrivateCloud at = new AcquireTokenPrivateCloud();
	//AcquireToken at = new AcquireToken();
	try{
		AcquireTokenResponse rsp;
		if(isPrivateCloud){
			 at = new AcquireTokenPrivateCloud();
			 rsp =  ((AcquireTokenPrivateCloud)at).getResponse(wsu.getAccount(), wsu.getHashPassword(), wsu.getServiceGateway(), encKey);
		}else{
			at = new AcquireToken();
			rsp = ((AcquireToken)at).getResponse(wsu.getAccount(), wsu.getHashPassword());
		}
		wsu.setUserID(wsu.getAccount());
		wsu.setCurrentToken(rsp.getToken());
		wsu.setInfoRelay(rsp.getInfoRelay());
		wsu.setWebRelay(rsp.getWebRelay());
		wsu.setSearchServer(rsp.getSearchServer());
		wsu.setServiceGateway(ServicesGateway);
		session.setAttribute("wsu", wsu);
		logger.debug("Acquire Token Success-" + wsu.getCurrentToken());
	}catch(Exception ex){
		throw ex;
	}
	
}else{
	wsu = (WebStorageUser)session.getAttribute("wsu");
	logger.debug("Restore User Token");
	logger.debug("User Token-" + wsu.getCurrentToken());
}

String sort = "lastchangetime";

SqlQuery sq = new SqlQuery();
SqlQueryResponse resp;

FolderBrowse fb= new FolderBrowse();
FolderBrowseResponse respf;

//國小組
String FOLDER_ELEMATORY = (String)Configurations.getParamValue("FOLDER_ELEMATORY");
resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), FOLDER_ELEMATORY, "", "", "10", SqlQuery.SearchFolder, sort);
List<SearchResultEntry> elematoryList = resp.getEntries();
String folderIDElematory = "";
for(SearchResultEntry item : elematoryList){
	if("1".equals(item.getKind())){
		folderIDElematory = item.getId();
		break;
	}
}

//**** 採用 SqlQuery取得檔案
//resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), "", "1", folderIDElematory, "100", SqlQuery.SearchFile, sort);
//List<SearchResultEntry> elematoryVideoList = resp.getEntries();
//****

//**** 採用 FolderBrowse 取得檔案
respf = fb.getResponse(wsu.getUserID(), wsu.getInfoRelay(), wsu.getCurrentToken(), folderIDElematory, "VIDEO", FolderBrowse.Sortby_LastModifiedTime, FolderBrowse.Sortdirection_DESC);
List<PublicFileBase> elematoryVideoList = respf.getFiles();

//國中組
String FOLDER_JUNIORHIGH = (String)Configurations.getParamValue("FOLDER_JUNIORHIGH");
resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), FOLDER_JUNIORHIGH, "", "", "10", SqlQuery.SearchFolder, sort);
List<SearchResultEntry> juniorhighList = resp.getEntries();
String folderIDJuniorhigh = "";
for(SearchResultEntry item : juniorhighList){
	if("1".equals(item.getKind())){
		folderIDJuniorhigh = item.getId();
		break;
	}
}
//**** 採用 SqlQuery取得檔案
//resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), "", "1", folderIDJuniorhigh, "100", SqlQuery.SearchFile, sort);
//List<SearchResultEntry> juniorhighVideoList = resp.getEntries();
//****

//**** 採用 FolderBrowse 取得檔案
respf = fb.getResponse(wsu.getUserID(), wsu.getInfoRelay(), wsu.getCurrentToken(), folderIDJuniorhigh, "VIDEO", FolderBrowse.Sortby_LastModifiedTime, FolderBrowse.Sortdirection_DESC);
List<PublicFileBase> juniorhighVideoList = respf.getFiles();

//高中組
String FOLDER_SENIORHIGH = (String)Configurations.getParamValue("FOLDER_SENIORHIGH");
resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), FOLDER_SENIORHIGH, "", "", "10", SqlQuery.SearchFolder, sort);
List<SearchResultEntry> seniorhighList = resp.getEntries();
String folderIDSeniorhigh = "";
for(SearchResultEntry item : seniorhighList){
	if("1".equals(item.getKind())){
		folderIDSeniorhigh = item.getId();
		break;
	}  
}

//**** 採用 SqlQuery取得檔案
//resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), "", "1", folderIDSeniorhigh, "100", SqlQuery.SearchFile, sort);
//List<SearchResultEntry> seniorhighVideoList = resp.getEntries();
//****

//**** 採用 FolderBrowse 取得檔案
respf = fb.getResponse(wsu.getUserID(), wsu.getInfoRelay(), wsu.getCurrentToken(), folderIDSeniorhigh, "VIDEO", FolderBrowse.Sortby_LastModifiedTime, FolderBrowse.Sortdirection_DESC);
List<PublicFileBase> seniorhighVideoList = respf.getFiles();

//高職組
String FOLDER_VOCTIONAL = (String)Configurations.getParamValue("FOLDER_VOCTIONAL");
resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), FOLDER_VOCTIONAL, "", "", "10", SqlQuery.SearchFolder, sort);
List<SearchResultEntry> voctionalList = resp.getEntries();
String folderIDVoctional = "";
for(SearchResultEntry item : voctionalList){
	if("1".equals(item.getKind())){
		folderIDVoctional = item.getId();
		break;
	}
}

//**** 採用 SqlQuery取得檔案
//resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), "", "1", folderIDVoctional, "100", SqlQuery.SearchFile, sort);
//List<SearchResultEntry> voctionalVideoList = resp.getEntries();
//****

//**** 採用 FolderBrowse 取得檔案
respf = fb.getResponse(wsu.getUserID(), wsu.getInfoRelay(), wsu.getCurrentToken(), folderIDVoctional, "VIDEO", FolderBrowse.Sortby_LastModifiedTime, FolderBrowse.Sortdirection_DESC);
List<PublicFileBase> voctionalVideoList = respf.getFiles();

//教職員組
String FOLDER_TEACHER = (String)Configurations.getParamValue("FOLDER_TEACHER");
resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), FOLDER_TEACHER, "", "", "10", SqlQuery.SearchFolder, sort);
List<SearchResultEntry> teacherList = resp.getEntries();
String folderIDTeacher = "";
for(SearchResultEntry item : teacherList){
	if("1".equals(item.getKind())){
		folderIDTeacher = item.getId();
		break;
	}
}

//**** 採用 SqlQuery取得檔案
//resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), "", "1", folderIDTeacher, "100", SqlQuery.SearchFile, sort);
//List<SearchResultEntry> teacherVideoList = resp.getEntries();
//****

//**** 採用 FolderBrowse 取得檔案
respf = fb.getResponse(wsu.getUserID(), wsu.getInfoRelay(), wsu.getCurrentToken(), folderIDTeacher, "VIDEO", FolderBrowse.Sortby_LastModifiedTime, FolderBrowse.Sortdirection_DESC);
List<PublicFileBase> teacherVideoList = respf.getFiles();

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
            <button class="exit-btn-main exit-btn-3" id="logout">登出</button>
            <div class="box animated fadeInDown"></div>
            <a class="logo" href="https://educase.kh.edu.tw/navigate/"></a>
            <div class="w1 animated fadeInLeft"></div>
            <%@include file="inc_search_sorting.jsp" %>
        </div>
        <div class="content">
	            <div class="light"></div>
	            <div class="elematory">
	            	<h1>國小組</h1>
	            <%
            	int count1 = 0;
	            for(PublicFileBase item : elematoryVideoList){

	            	if(item.getMarkid().equals("1")){  //僅列出標示星號的檔案
	            		
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
		            	
		            	String fileName = item.getBase64display().substring(0, item.getBase64display().length()-4);
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
					<%if(isShowUploaderId){ %><div ><%=respentry.getContributor() %></div><%} %>
					<div class="filename" id="filename"><%=fileName%></div>
					<span id="download" style="display:none;"><%=directDownload%></span>
				</div>
				<% 
						 count1++;
						if(count1>=5) 	break;
	            	}
	            }
	            %>
	            	<div class="more"><a href="elematory.jsp#start">顯示更多</a></div>
	            </div>
				<div class="juniorhigh">
				    <h1>國中組</h1>
	            <%
            	int count2 = 0;
	            for(PublicFileBase item : juniorhighVideoList){

	            	
	            	if(item.getMarkid().equals("1")){
	            		
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
		            	
		            	String fileName = item.getBase64display().substring(0, item.getBase64display().length()-4);
		            	String directDownload =  wsu.getWebRelay() + "/webrelay/directdownload/" + wsu.getCurrentToken() + "/?dis=" + WebStorageUser.SID() + "&fi=" + item.getId() ;
				%>
				<div id="video" class="video">
					<img src="<%=imgUrl %>" class="videosnap" onclick=""></img>
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
				        count2++;
						if(count2>=5) 
									break;
	            	}
	            }
	            %>
	            	<div class="more"><a href="juniorhigh.jsp#start">顯示更多</a></div>
				</div>
				<div class="seniorhigh">
					<h1>高中組</h1>
	            <%
            	int count3 = 0;
	            for(PublicFileBase item : seniorhighVideoList){

	            	
	            	if(item.getMarkid().equals("1")){
	            		
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
		            	
		            	String fileName = item.getBase64display().substring(0, item.getBase64display().length()-4);
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
						count3++;
						if(count3>=5) 
									break;
        	}
	            }
	            %>
	            	<div class="more"><a href="seniorhigh.jsp#start">顯示更多</a></div>
				</div>
				<div class="vocational">
						<h1>高職組</h1>
	            <%
            	int count4 = 0;
	            for(PublicFileBase item : voctionalVideoList){

	            	
	            	if(item.getMarkid().equals("1")){
	            	
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
		            	
		            	String fileName = item.getBase64display().substring(0, item.getBase64display().length()-4);
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
						count4++;
						if(count4>=5) 
									break;
	            	}
	            }
	            %>
	            	<div class="more"><a href="voctional.jsp#start">顯示更多</a></div>
				</div>
				<div class="teacher">
					<h1>教職員組</h1>
	            <%
            	int count5 = 0;
	            for(PublicFileBase item : teacherVideoList){

	            	
	            	if(item.getMarkid().equals("1")){
	            		
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
		            	
		            	String fileName = item.getBase64display().substring(0, item.getBase64display().length()-4);
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
						count5++;
						if(count5>=5) 
									break;
		        	}
	            }
	            %>
	            	<div class="more"><a href="teacher.jsp#start">顯示更多</a></div>
				</div>
			</div>

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