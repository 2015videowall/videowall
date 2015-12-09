package com.millionasia.asuscloud;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;

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

import com.millionasia.asuscloud.entity.SearchResultEntry;
import com.millionasia.asuscloud.entity.SqlQueryResponse;
import com.millionasia.kscloud.entity.DatabaseHelper;
import com.millionasia.kscloud.servlet.WebStorageUser;

public class SqlQuery 
{	
	
	static String API = "/fulltext/sqlquery/";// Service api
	
	static public int SearchFolder = 1;
	static public int SearchFile = 2;
	private static Logger logger = LogManager.getLogger(SqlQuery.class.getName());

	public SqlQueryResponse getResponse(String userid, String searchserver, String token, String keyword, String markid, String ancestorid, String pagesize, int searchType, String orderby ) throws Exception
	{
	

		String urlstr = searchserver + API + token;
		//logger.debug("url=" + urlstr);
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
			logger.error("Get Connection Error:" + ioe.getMessage());
			throw ioe;
		} 		 		

		/* Preparing for DocumentBuilderFactory. This class is available at: 
		 * http://download.oracle.com/javase/1.4.2/docs/api/javax/xml/parsers/DocumentBuilderFactory.html
		 * */		
         String root = "sqlquery";// The root element of request payload  
				
         String[] elmName;
 		String[] data;
         
 		String descending = "0";
 		if("name".equals(orderby)){
 			descending = "0";
 		}
 		
         if("".equals(keyword)){
        	 //markid
         	elmName =new String[]{ "userid", "markid", "kind", "ancestorid", "pagesize", "offset", "ext", "orderby", "descending"};// Define each XML tag name
      		data =new String[] { userid, markid, String.valueOf(searchType), ancestorid, pagesize, "0", "",  orderby, descending };// Set each value of tag
         }else{
        	elmName =new String[]{ "userid", "keyword", "kind", "ancestorid", "pagesize", "offset", "ext", "orderby", "descending"};// Define each XML tag name
     		data =new String[] { userid, keyword, String.valueOf(searchType), ancestorid, pagesize, "0", "",  orderby, descending };// Set each value of tag
         }

		
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
		
		logger.debug("*******SQL QUERTY REQUEST*******");
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
		
		logger.debug("*******SQL QUERTY RESPONSE*******");
		logger.debug(xmlRsp);
		logger.debug("*************************************");
		

		SqlQueryResponse rsp = new SqlQueryResponse();					
		LinkedList<SearchResultEntry> entries =new LinkedList<SearchResultEntry>(); 
		
		// Compose the payload
		int j, k;		
		
		DatabaseHelper db = new DatabaseHelper();
		db.openConnection(); 
		
		for (j = 0; j < nodelist.getLength(); j++) {
			Node node = nodelist.item(j);
			
			/* status */
			if(node.getNodeName().equals("status")){
				rsp.setStatus(node.getTextContent());
			}	
			
			/* total */
			if(node.getNodeName().equals("total")){
				rsp.setTotal(node.getTextContent());
			}					

			/* entry */
			if(node.getNodeName().equals("entry")){	
				SearchResultEntry entry = new SearchResultEntry();
				for(k = 0; k < node.getChildNodes().getLength(); k++){		
					logger.debug(node.getChildNodes().item(k).getNodeName() + ":" +node.getChildNodes().item(k).getTextContent() );
					if(node.getChildNodes().item(k).getNodeName().equals("userid")){
						entry.setUserId(node.getChildNodes().item(k).getTextContent());			
					}
					
					if(node.getChildNodes().item(k).getNodeName().equals("id")){
						entry.setId(node.getChildNodes().item(k).getTextContent());			
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("parent")){
						entry.setParent(node.getChildNodes().item(k).getTextContent());	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("rawentryname")){
						entry.setRawEntryName(node.getChildNodes().item(k).getTextContent());	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("kind")){	
						entry.setKind(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("time")){	
						entry.setTime(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("ispublic")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						entry.setPublic(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isorigdeleted")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						entry.setOrigDeleted(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("marks")){
						entry.setMarks(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("size")){	
						entry.setSize(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("lastchangetime")){
						entry.setLastChangeTime(node.getChildNodes().item(k).getTextContent());
					}
				}			

				int votes = db.getVotes(entry.getId());
				entry.setVotes(votes);
				
				entries.add(entry);	
			}			

		}
		db.release(); 
		rsp.setEntries(entries);

		
		//logger.info("*******SQL QUERTY RESPONSE*******");
		//logger.info(rsp.toString());
		//logger.info("*************************************");
		
		// The correct response payload's status value must equals "0"
		if ( !"0".equals(rsp.getStatus()) )
		{
			logger.debug("sqlquery response:"+rsp.toString());
			throw new Exception("Error : You have to check SqlQuery api response, status code:" + rsp.getStatus());
		}
		
		return rsp;
	}
}
