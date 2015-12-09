package com.millionasia.kscloud.servlet;

import java.io.Serializable;

import javax.servlet.ServletConfig; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
 


import com.millionasia.asuscloud.sax.EncryptBean;
 
public class WebStorageUser  extends HttpServlet  implements Serializable {
	
	private static final long serialVersionUID = -6183127893230853788L;
	
	public static final String MY_COLLECTION_FOLDER_ID = "0";
	public static final String MY_BACKUP_FOLDER_ID = "-3";
	public static final String MY_SYNC_FOLDER_ID = "-5";
	
	public static String SID(){
		return mSid;
	}
	
	public static String ProgKey(){
		return mProgKey;
	}
	
	public static String ConnectionServer(){
		return mAsusConnectionServer;
	}
	

	
	//======

	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
		mHashpassword = EncryptBean.getMD5(mPassword.toLowerCase());
		//System.out.println("HashPassword:" + mHashpassword);
	}
	
	public String getHashPassword() {
		return mHashpassword;
	}
	
	public String getPassword() {
		return mPassword;
	}

	public String getUserID() {
		return mUserID;
	}

	public void setUserID(String mUserID) {
		this.mUserID = mUserID;
	}
	
	public String getCurrentToken() {
		return mToken;
	}

	public void setCurrentToken(String token) {
		this.mToken = token;
	}
	
	public String getAccount() {
		return mAccount;
	}

	public void setAccount(String mAccount) {
		this.mAccount = mAccount;
	}

	public String getInfoRelay() {
		return mInfoRelay;
	}

	public void setInfoRelay(String mInfoRelay) {
		this.mInfoRelay = mInfoRelay;
	}

	public String getWebRelay() {
		return mWebRelay;
	}

	public void setWebRelay(String mWebRelay) {
		this.mWebRelay = mWebRelay;
	}

	public String getServiceGateway() {
		return mServiceGateway;
	}

	public void setServiceGateway(String mServiceGateway) {
		this.mServiceGateway = mServiceGateway;
	}
	
	public String getSearchServer() {
		return mSearchServer;
	}
	
	public void setSearchServer(String mSearchServer) {
		this.mSearchServer = mSearchServer;
	}
	



	private String mUserID; //email
	private String mPassword;

	private String mHashpassword = "";
	private static String mAsusConnectionServer = "";
	private static String mSid = "";
	private static String mProgKey = "";
	private String mToken = "";
	private String mAccount = "";
	private String mInfoRelay = "";
	private String mWebRelay = "";
	private String mServiceGateway = "";
	private String mSearchServer = "";
	
	public WebStorageUser(){
		super();
	}
	
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	//String path = getServletContext().getInitParameter("CONFIG_XML");
   	
    	
    	mSid= Configurations.getParamValue("ASUS_WEBSTORAGE_SID");
    	mProgKey= Configurations.getParamValue("ASUS_WEBSTORAGE_PROGKEY");
    	mAsusConnectionServer= Configurations.getParamValue("ASUS_WEBSTORAGE_CONNECTION_SERVER");
    	mServiceGateway= Configurations.getParamValue("ASUS_WEBSTORAGE_SERVICE_GATEWAY");
	    	

        System.out.println("SID is " + mSid);
        System.out.println("ProgKey is " + mProgKey);
        System.out.println("Connection Server is " + mAsusConnectionServer);
        System.out.println("Service Gateway is " + mServiceGateway);

     }
    

}
