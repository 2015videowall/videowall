<%@page contentType="text/html; charset=UTF-8" 
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*" 
%><%@page import="com.millionasia.framework.*,com.millionasia.kscloud.servlet.*"  
%><%
request.setCharacterEncoding("UTF-8") ;
%>
  <div id="video_area">
  		<div id="worning">如果沒有辦法正常播放，請點選下方進行檔案下載</div>
  		<img src="images/ic_close.png" class="btnClose" id="btnClose">
        <div id="play_area">
             <video id="theVideo" width="960" height="540" controls>
  					<source id="videoUrl" src="" type="video/mp4">
					Your browser does not support the video tag.
			</video> 
            <div id="actions">
                <a id="cancel" href="#">關閉</a>
                <span id="download"  style="cursor:pointer;">下載</span>
            </div>
        </div>
</div>