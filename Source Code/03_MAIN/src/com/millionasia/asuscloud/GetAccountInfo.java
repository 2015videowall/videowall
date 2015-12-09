package com.millionasia.asuscloud;

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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.millionasia.asuscloud.entity.GetAccountInfoResponse;
import com.millionasia.kscloud.servlet.WebStorageUser;


public class GetAccountInfo
{		
	
	private static String API = "/member/getinfo/";// Service api
	 
	public GetAccountInfoResponse getResponse(String userid, String server, String token) throws Exception
	{			
	
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
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setConnectTimeout(60 * 1000); 
		connection.setReadTimeout(60 * 1000);

		StringBuilder cookie = new StringBuilder();
		cookie.append("sid=").append(WebStorageUser.SID()).append(";");// Cookies have to add the value of SID

		connection.addRequestProperty("cookie", cookie.toString());// User must add the cookies in the header
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);	
		try 
		{
			connection.connect();
		}
		catch (IOException e) 
		{			
			System.err.println("Get Connection Error:" + e.getMessage());
			throw e;
		}

		/* Preparing for DocumentBuilderFactory. This class is available at: 
		 * http://download.oracle.com/javase/1.4.2/docs/api/javax/xml/parsers/DocumentBuilderFactory.html
		 * */		
		String root = "getinfo";// The root element of request payload 
		
		String[] elmName = { "userid", "token" };// Define each XML tag name
		String[] data = { userid, token };// Set each value of tag

		/* To create XML documents. DocumentBuilderFactory can obtain a parser that produces DOM object trees from XML documents */
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try 
		{
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e) 
		{			
			System.err.println("ParserConfigurationException Error:" + e.getMessage());
			throw e;
		}
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
		Transformer transformer = null;
		try 
		{
			transformer = transformerFactory.newTransformer();
		} 
		catch (TransformerConfigurationException e) 
		{
			System.err.println("Get TransformerConfiguration Error:" + e.getMessage());
			throw e;
		}
		DOMSource source = new DOMSource(document);
		StreamResult result = null;
		try 
		{
			result = new StreamResult(connection.getOutputStream());
		} 
		catch (IOException e) 
		{			
			System.err.println("Connection getOutputStream Error:" + e.getMessage());
			throw e;
		}		
		try 
		{
			transformer.transform(source, result);
		} 
		catch (TransformerException e) 
		{			
			System.err.println("XML transformer Error:" + e.getMessage());
			throw e;
		}

		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = null;
		try 
		{
			documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		} 
		catch (Exception e) 
		{
			System.err.println("Connection getInputStream Error:" + e.getMessage() + e);
			throw e;
		}
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		GetAccountInfoResponse rsp = new GetAccountInfoResponse();
		int j;
		for (j = 0; j < nodelist.getLength(); j++) {
			if (nodelist.item(j).getNodeName().equals("status")) {
				rsp.setStatus(nodelist.item(j).getTextContent());
			}
			if (nodelist.item(j).getNodeName().equals("account")) {
				rsp.setAccount(nodelist.item(j).getTextContent());
			}
			if (nodelist.item(j).getNodeName().equals("email")) {
				rsp.setEmail(nodelist.item(j).getTextContent());
			}
		}
		
		// The correct response payload's status value must equals "0"
		if ( !"0".equals(rsp.getStatus()) )
			System.err.println("GetAccountInfo response status is:" + rsp.getStatus());
		
		return rsp;		
	}
}