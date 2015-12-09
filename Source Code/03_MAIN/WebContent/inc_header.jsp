<%@page contentType="text/html; charset=UTF-8" 
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*" 
%><%
request.setCharacterEncoding("UTF-8") ; 
String TAG=request.getParameter("TAG");
Boolean isVoted=Boolean.valueOf((String)request.getParameter("isVoted"));
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=IE9" />
<title><%=TAG %></title>
<link href="images/aws.ico" rel="shortcut icon" />
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/stylemovie.css" rel="stylesheet" type="text/css" />
<link href="css/animate-custom.css" rel="stylesheet" type="text/css" />
<link href="css/videowall.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-2.1.3.min.js"></script>
<script src="js/jquery.lightbox_me.js"></script>
<script>
 $(function() {
	    function launch() {
	         $('#video_area').lightbox_me({centered: true, onLoad: function() { }});
	    } 
	    
	    $( ".videosnap" ).click(function(e) {
	    	var filename = $(this).parent().find("#filename").text();
	    	var source = $(this).parent().find("#download").text();
	    	
			$("#videoUrl").attr("src", source);
			$("#theVideo")[0].load();
			
	    	$('#video_area').lightbox_me({
	    			centered: true,
	    			closeClick: false,
	    			closeEsc: false,
	    			onLoad: function(){

	    			}});
	    	$('#video_area').trigger('reposition');
	        e.preventDefault();
	        
	     });

	    $('#cancel').click(function(e) {
	    	$("#videoUrl").attr("src", "#");
	    	$("#theVideo")[0].pause();
	        $("#video_area").trigger('close');

	        e.preventDefault();
	    });
	    
	    $('#btnClose').click(function(e) {
	    	$("#videoUrl").attr("src", "#");
	    	$("#theVideo")[0].pause();
	        $("#video_area").trigger('close');

	        e.preventDefault();
	    });
	    
	    $('#download').click(function(e) {
	    	location.href = $("#videoUrl").attr("src");

	        e.preventDefault();
	    });
	    
	    $('.vote').click(function(e) {
	    	var isVoted = <%=isVoted%>;
	    	if(isVoted){
	    		var msg = "您已經投過票了!";
	    		alert(msg);
	    	}else{
		    	var fileid = $(this).attr("id");
		    	var msg = "您確定要把神聖的一票投給這支影片？";
		    	if(confirm(msg)){
		    		location.href = "vote.jsp?u=<%=session.getAttribute("userid")%>&f=" + fileid;
		    	}
	    	}
	        e.preventDefault();
	    });
	    
	    $('#link').change(function(e) {
	    	var l =  $('#link option:selected').val();
	    	location.href = l;
	        e.preventDefault();
	    });
	    
	    $('#sorting').change(function(e) {
	    	var l =  $('#sorting option:selected').val();
	    	location.href = l;
	        e.preventDefault();
	    });
	    
	    $('.search_button').click(function(e) {
	    	var q =  $('.search_text').val();
	    	if(q==""){
	    		alert("請輸入查詢字串");
	    		$('.search_text').focus();
	    		return;
	    	}
	    	$('#newsearch').submit();
	        e.preventDefault();
	    });
	    
	    $('#logout').click(function(e) {
	    	var msg = "您確定要登出？";
	    	if(confirm(msg)){
	    		location.href = "logout.jsp";
	    	}
	        e.preventDefault();
	    });
 });
</script>
</head>