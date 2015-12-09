 package com.millionasia.asuscloud;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;

import com.millionasia.asuscloud.sax.MultiPart;
import com.millionasia.kscloud.servlet.WebStorageUser;

public class DirectUpload {

	static String API = "/webrelay/directupload/";// Service api
    public String fileID = "";
    
	public  void upload(String token, String webrelay, String folderId, String folderName, String fileName, InputStream in) throws Exception {

		String server =webrelay;// Connection server

		String urlstr = server + API;
		URL url = new URL(urlstr);
		
		/******** BY PASS SSL ******/
		  TrustManager tm = new X509TrustManager() {
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	        };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { tm }, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
		    public boolean verify(String hostname, SSLSession session) {
		      return true;
		    }
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/******** BY PASS SSL  END******/

		/* The HttpsURLConnection start */
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();
		connection.setConnectTimeout(60 * 1000);
		connection.setReadTimeout(60 * 1000);

		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(WebStorageUser.SID()).append(";");// Cookies have to add the
														// value of SID

		connection.addRequestProperty("cookie", cookie.toString());// User must
																	// add the
																	// cookies
																	// in the
																	// header
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		try {
			connection.connect();
		} catch (IOException ioe) {
			System.err.println("Get Connection Error:" + ioe.getMessage());
			throw ioe;
		}

		//String parentID = "42157194";// Parent folder's id
		String parentID = folderId;
		
		//String Parent_folder="testupload";
		//String folderDisplay = new sun.misc.BASE64Encoder().encode( Parent_folder.getBytes()); // Parent folder's name
		String folderDisplay = new String(Base64.encodeBase64(folderName.getBytes()), "UTF-8"); // Parent folder's name

		long progressID = 123;// Check the status dof the upload

		// Directuploading the specify file attribute, it needs to be encoded
		java.util.Date now = new java.util.Date(); 
		String timestamp = String.valueOf(now.getTime());
		String attribute =  URLEncoder.encode("<creationtime>" + timestamp + "</creationtime><lastaccesstime>"+ timestamp + "</lastaccesstime><lastwritetime>" + timestamp + "</lastwritetime>","UTF-8"); 


		int fileSize = 8 * 1024; // Size of uploaded file in one time

		//long olderFileID = 20090123;// If you want to overwrite the original
									// file in the server, you need to specify
									// this parameter

		String autoRename = "0";// 0 = no, 1= yes

		String urlQuery = token + "/?dis=" + WebStorageUser.SID();
		String urlStrDirectupload = server + API + urlQuery;//D:\\testuploadfile\\log.txt


		/* Doing MultiPart to upload file */
		URL urlDirectupload = new URL(urlStrDirectupload);
		HttpsURLConnection connectionDirectupload = (HttpsURLConnection) urlDirectupload
				.openConnection();

		// MultiPart form
		String status = "";
		try {
			MultiPart multipart = new MultiPart(connectionDirectupload);
			multipart.setParameter("pa", parentID);
			multipart.setParameter("d", folderDisplay);
			multipart.setParameter("pr", progressID);
			multipart.setParameter("at", attribute);
			multipart.setParameter("fs", fileSize);
			//multipart.setParameter("fi", olderFileID);
			multipart.setParameter("ar", autoRename);
			multipart.setParameter("av", "1");
			multipart.setParameter(fileName, fileName, in);

			multipart.post();
			fileID = multipart.fileID;
			status = multipart.status;


		} catch (IOException e) {
		    e.printStackTrace();
			throw new Exception(
					"Error : You have to check MultiPart when doing DirectUpload api, status = " + status);
		}
	}
    
	public  void upload(String token, String webrelay, String folderid, String foldername, String filepath, String ufilename) throws Exception {

		String server =webrelay;// Connection server

		String urlstr = server + API;
		URL url = new URL(urlstr);

		/* The HttpsURLConnection start */
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();
		connection.setConnectTimeout(60 * 1000);
		connection.setReadTimeout(60 * 1000);

		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(WebStorageUser.SID()).append(";");// Cookies have to add the
														// value of SID

		connection.addRequestProperty("cookie", cookie.toString());// User must
																	// add the
																	// cookies
																	// in the
																	// header
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		try {
			connection.connect();
		} catch (IOException ioe) {
			System.err.println("Get Connection Error:" + ioe.getMessage());
			throw ioe;
		}

		//String parentID = "42157194";// Parent folder's id
		String parentID = folderid;
		
		//String Parent_folder="testupload";
		//String folderDisplay = new sun.misc.BASE64Encoder().encode( Parent_folder.getBytes()); // Parent folder's name
		String folderDisplay = new String(Base64.encodeBase64(foldername.getBytes()), "UTF-8"); // Parent folder's name

		long progressID = 123;// Check the status of the upload

		// Directuploading the specify file attribute, it needs to be encoded
		String attribute = URLEncoder
				.encode("<creationtime>1313054123</creationtime><lastaccesstime>1313054123</lastaccesstime><lastwritetime>1313054123</lastwritetime>",
						"UTF-8");

		int fileSize = 4 * 1024; // Size of uploaded file in one time

		//long olderFileID = 20090123;// If you want to overwrite the original
									// file in the server, you need to specify
									// this parameter

		boolean autoRename = true;// If the upload file already exists in the
									// server, this parameter means auto rename
									// this file ???

		String urlQuery = token + "/?dis=" + WebStorageUser.SID();
		String urlStrDirectupload = server + API + urlQuery;//D:\\testuploadfile\\log.txt

		File file = new File(filepath, ufilename);// The path of
															// uploading file
		String filename = ufilename;// Directuploading the specify file's
										// name

		/* Doing MultiPart to upload file */
		URL urlDirectupload = new URL(urlStrDirectupload);
		HttpsURLConnection connectionDirectupload = (HttpsURLConnection) urlDirectupload
				.openConnection();

		// MultiPart form
		try {
			MultiPart multipart = new MultiPart(connectionDirectupload);
			multipart.setParameter("pa", parentID);
			multipart.setParameter("d", folderDisplay);
			multipart.setParameter("pr", progressID);
			multipart.setParameter("at", attribute);
			multipart.setParameter("fs", fileSize);
			//multipart.setParameter("fi", olderFileID);
			multipart.setParameter("ar", autoRename);
			multipart.setParameter(filename, file);
			//multipart.setParameter(filename, file);

			multipart.post();
			fileID = multipart.fileID;

		} catch (IOException e) {
			System.out.println("DirectUploadError");
			e.printStackTrace();
			throw new Exception(
					"Error : You have to check MultiPart when doing DirectUpload api");
		}
	}
}
