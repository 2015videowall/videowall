<%@page contentType="text/html; charset=UTF-8" 
%><%@page pageEncoding = "UTF-8" 
%><%@page import="java.lang.*,java.util.*" 
%><%@page import="com.millionasia.framework.*,com.millionasia.kscloud.servlet.*"  
%><%
request.setCharacterEncoding("UTF-8") ;
boolean isShowSearchBar = Boolean.valueOf((String)Configurations.getParamValue("SHOW_SEARCH_BAR"));
%>
				<div id="search"><%
				if(isShowSearchBar){
				%><form id="newsearch" name="newsearch" method="post" action="search.action">
												<input type="hidden" name="t" value="filename" />
										        <input type="text" id="search_text" class="search_text" name="q" size="21" maxlength="120">
					        <input type="button" value="搜尋" class="search_button" id="search_button">
					</form><%
				}
					%><div id="copyright">
					請閱覽者尊重各影音檔的著作權，本影音牆內的影片不得私自轉載、散佈及修改。<br/>
                    檢舉影片侵權，請洽高雄市教育局資訊教育中心網路組(07)7136536
					</div>
                     <div class="search_clear"></div>
				</div>