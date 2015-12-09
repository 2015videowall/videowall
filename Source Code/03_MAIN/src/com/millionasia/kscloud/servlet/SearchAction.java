package com.millionasia.kscloud.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.millionasia.asuscloud.SqlQuery;
import com.millionasia.asuscloud.entity.SearchResultEntry;
import com.millionasia.asuscloud.entity.SqlQueryResponse;
import com.millionasia.kscloud.entity.DatabaseHelper;

/**
 * Servlet implementation class test
 */
public class SearchAction extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(Configurations.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		
		String queryString = (String)request.getParameter("q");
		String queryType = (String)request.getParameter("t");
		
		WebStorageUser wsu = (WebStorageUser)session.getAttribute("wsu");
		SqlQuery sq = new SqlQuery();
		SqlQueryResponse resp;
		String pageSize = (String)Configurations.getParamValue("PAGE_SIZE");
		List<SearchResultEntry> resultlist = null;
		
		if("filename".equals(queryType)){
			logger.debug("檔案搜尋");
			try {
				//resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), queryString, "", "", pageSize, SqlQuery.SearchFile,"");
				resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), "", "1", "", pageSize, SqlQuery.SearchFile, "");
				resultlist = resp.getEntries();
				List<SearchResultEntry> resultlist2 = new ArrayList<SearchResultEntry>();
				for(SearchResultEntry item : resultlist){
					if(item.getRawEntryName().indexOf(queryString) >= 0){
						resultlist2.add(item);
					}
				}
				resultlist.clear();
				resultlist = resultlist2;
			} catch (Exception e) {
				e.printStackTrace();
				resultlist = null;
			}
		}else{
			//學校搜尋
			logger.debug("學校搜尋");
			try{
				DatabaseHelper db = new DatabaseHelper();
				db.openConnection();
				List<String> userlist = db.getUserList(queryString);
				db.release();

				resp = sq.getResponse(wsu.getUserID(), wsu.getSearchServer(), wsu.getCurrentToken(), "", "1", "", pageSize, SqlQuery.SearchFile,"");
				resultlist = resp.getEntries();
				List<SearchResultEntry> tmp = new ArrayList<SearchResultEntry>();
				for(SearchResultEntry item : resultlist){
					for(String uid : userlist){
						if(item.getUserId().equals(uid))
							tmp.add(item);
					}
				}
				resultlist.clear();
				resultlist = tmp;
			} catch (Exception e) {
				e.printStackTrace();
				resultlist = null;
			}
			
		}

	
		session.setAttribute("result", resultlist);
		
	    response.sendRedirect("search_result.jsp");
		
	}

}
