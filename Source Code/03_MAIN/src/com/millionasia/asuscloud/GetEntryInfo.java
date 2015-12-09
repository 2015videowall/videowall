package com.millionasia.asuscloud;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.millionasia.asuscloud.entity.GetEntryInfoResponse;
import com.millionasia.kscloud.servlet.WebStorageUser;

public class GetEntryInfo 
{	
	
	static String API = "/fsentry/getentryinfo/";// Service api
	
	static public int Folder = 1;
	static public int File = 0;
	private static Logger logger = LogManager.getLogger(GetEntryInfo.class.getName());

	public GetEntryInfoResponse getResponse( String inforelay, String token, String entryid, int entryType ) throws Exception
	{
	

		String urlstr = inforelay + API ;
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
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setConnectTimeout(60 * 1000); 
		connection.setReadTimeout(60 * 1000);

		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(WebStorageUser.SID()).append(";");// Cookies have to add the value of SID

		connection.addRequestProperty("cookie", cookie.toString());// User must add the cookies in the header
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);			
		try {
			connection.connect();
		} catch (IOException ioe) {
			System.err.println("Get Connection Error:" + ioe.getMessage());
			throw ioe;
		} 		 		

		/* Preparing for DocumentBuilderFactory. This class is available at: 
		 * http://download.oracle.com/javase/1.4.2/docs/api/javax/xml/parsers/DocumentBuilderFactory.html
		 * */		
         String root = "sqlquery";// The root element of request payload  
		
		//String[] elmName = { "userid", "customeruniqueid","groupname","authdomain" };// Define each XML tag name
		//String[] data = { userid, userid,GROUP_NAME ,AUTH_DOMAIN };// Set each value of tag
		
		String[] elmName = { "token", "isfolder", "entryid"};// Define each XML tag name
		String[] data = { token, String.valueOf(entryType), entryid};// Set each value of tag
		
		/* To create XML documents. DocumentBuilderFactory can obtain a parser that produces DOM object trees from XML documents */
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement(root);
		document.appendChild(rootElement);
		Element elm;
		int i;
		for (i = 0; i < data.length; i++) {
			elm = document.createElement(elmName[i]);
			elm.appendChild(document.createTextNode(data[i]));
			rootElement.appendChild(elm);
		}
		
		/* Used to process XML from a variety of sources and write the transformation output to server */
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(connection.getOutputStream());
		transformer.transform(source, result);
		
		
		ByteArrayOutputStream   bos   =   new   ByteArrayOutputStream();
		transformer.transform(source, new StreamResult(bos));
		String xmlStr = bos.toString();
		
		logger.debug("*******GET ENTRYINFO REQUEST*******");
		logger.debug(xmlStr);
		logger.debug("*************************************");
		

		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();
		
		DOMSource sourceRsp = new DOMSource(documentResponse);
		ByteArrayOutputStream   bosrsp   =   new   ByteArrayOutputStream();
		transformer.transform(sourceRsp, new StreamResult(bosrsp));
		String xmlRsp = bosrsp.toString();
		
		logger.debug("*******GET ENTRYINFO RESPONSE*******");
		logger.debug(xmlRsp);
		logger.debug("*************************************");

		GetEntryInfoResponse rsp = new GetEntryInfoResponse();					
	
		// Compose the payload
		int j;		
		for (j = 0; j < nodelist.getLength(); j++) {
			Node node = nodelist.item(j);
			
			/* status */
			if(node.getNodeName().equals("status")){
				rsp.setStatus(node.getTextContent());
			}	
			
			/* isfolder */
			if(node.getNodeName().equals("isfolder")){
				rsp.setIsfolder(node.getTextContent());
			}		
			
			/* display */
			if(node.getNodeName().equals("display")){
				rsp.setDisplay(node.getTextContent());
			}	
			
			/* parent */
			if(node.getNodeName().equals("parent")){
				rsp.setParent(node.getTextContent());
			}	
			
			/* isbackup */
			if(node.getNodeName().equals("isbackup")){
				rsp.setIsbackup(node.getTextContent());
			}	
			
			/* attribute */
			if(node.getNodeName().equals("attribute")){
				rsp.setAttribute(node.getTextContent());
			}	
			
			/* mimetype */
			if(node.getNodeName().equals("mimetype")){
				rsp.setMimetype(node.getTextContent());
			}	
			
			/* isinfected */
			if(node.getNodeName().equals("isinfected")){
				rsp.setIsinfected(node.getTextContent());
			}	
			
			/* filesize */
			if(node.getNodeName().equals("filesize")){
				rsp.setFilesize(node.getTextContent());
			}	
			
			/* createdtime */
			if(node.getNodeName().equals("createdtime")){
				rsp.setCreatedtime(node.getTextContent());
			}	

			/* headversion */
			if(node.getNodeName().equals("headversion")){
				rsp.setHeadversion(node.getTextContent());
			}	
			
			/* contributor */
			if(node.getNodeName().equals("contributor")){
				rsp.setContributor(node.getTextContent());
			}	
			
			/* owner */
			if(node.getNodeName().equals("owner")){
				rsp.setOwner(node.getTextContent());
			}	

		}
		// The correct response payload's status value must equals "0"
		if ( !"0".equals(rsp.getStatus()) )
		{
			System.out.println("status:"+rsp.getStatus());
			throw new Exception("Error : You have to check GetEntryInfo api response, status code:" + rsp.getStatus());
		}
		
		return rsp;
	}
}
