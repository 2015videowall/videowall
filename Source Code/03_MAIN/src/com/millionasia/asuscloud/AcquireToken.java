package com.millionasia.asuscloud;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;















import com.millionasia.asuscloud.entity.AcquireTokenResponse;
import com.millionasia.asuscloud.entity.PackageInfo;
import com.millionasia.asuscloud.entity.RequestServiceGatewayResponse;
import com.millionasia.kscloud.servlet.Configurations;
import com.millionasia.kscloud.servlet.WebStorageUser;

public class AcquireToken 
{	
	
	private static String API = "/member/acquiretoken/";// Service api
	private static Logger logger = LogManager.getLogger(AcquireToken.class.getName());

	public AcquireToken(){	
	}

	public AcquireTokenResponse getResponse(String userid, String pwd, String gateway) throws Exception
	{		
	String server = gateway;// Connection server		
		
		/* Get servicegateway domain/IP from RequestServiceGateway api by userid & pwd */
		if("".equals(server)){
			RequestServiceGateway requestservicegateway = new RequestServiceGateway();
			RequestServiceGatewayResponse requestservicegatewayresponse = null;
			try {
				requestservicegatewayresponse = requestservicegateway.getResponse(userid, pwd);
				// The correct RequestServiceGateway response payload's status value must equals "0
				if (requestservicegatewayresponse == null || !"0".equals(requestservicegatewayresponse.getStatus())) {
					throw new Exception("Error : You have to check the RequestServiceGateway api response");
				}	
				
				server = requestservicegatewayresponse.getServicegateway();
			} catch (java.net.UnknownHostException hostex){
				hostex.printStackTrace();
				throw hostex;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
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
		
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	
		connection.setConnectTimeout(60 * 1000); 
		connection.setReadTimeout(60 * 1000);
		
		/* Compose Developer Authorization String */
		String authorization;
		try {
			authorization = composeAuthorizationHeader();
		} catch (Exception e) {
			System.err.println("Get Authorization Error:" + e.getMessage());
			throw e;
		}		
		
		/* Setting developer authorization string into header */
		connection.addRequestProperty("Authorization", authorization);
		
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
		String root = "aaa";// The root element of request payload  
		
		String[] elmName = { "userid", "password" };// Define each XML tag name
		String[] data = { userid, pwd };// Set each value of tag

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
				
		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		AcquireTokenResponse rsp = new AcquireTokenResponse();
		LinkedList<PackageInfo> pinfo = new LinkedList<PackageInfo>(); 
		int j;
		for (j = 0; j < nodelist.getLength(); j++) {
			Node node = nodelist.item(j);
			//System.out.println(node.getNodeName() + ":" + node.getTextContent());
			if (node.getNodeName().equals("status")) {
				rsp.setStatus(node.getTextContent());
			}
			if (node.getNodeName().equals("token")) {
				rsp.setToken(node.getTextContent());
			}
			if (node.getNodeName().equals("inforelay")) {
				rsp.setInfoRelay(node.getTextContent());
			}
			if (node.getNodeName().equals("webrelay")) {
				rsp.setWebRelay(node.getTextContent());
			}
			if (node.getNodeName().equals("searchserver")) {
				rsp.setSearchServer(node.getTextContent());
			}
			if (node.getNodeName().equals("credential")) {
				rsp.setCredential(node.getTextContent());
			}
			if (node.getNodeName().equals("credentialstate")) {
				rsp.setCredentialState(node.getTextContent());
			}
			
			/* package */
			if(node.getNodeName().equals("package")){
				PackageInfo packageinfo = new PackageInfo();	
					if(node.getChildNodes().item(0).getNodeName().equals("id")){
						packageinfo.setId(Integer.parseInt(node.getChildNodes().item(0).getTextContent()));			
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("display")){
						packageinfo.setDisplay(node.getChildNodes().item(0).getTextContent());	
					}						
					else if(node.getChildNodes().item(0).getNodeName().equals("capacity")){
						packageinfo.setCapacity(Long.parseLong(node.getChildNodes().item(0).getTextContent()));	
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("uploadbandwidth")){
						packageinfo.setUploadbandwidth(Long.parseLong(node.getChildNodes().item(0).getTextContent()));	
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("downloadbandwidth")){
						packageinfo.setDownloadbandwidth(Long.parseLong(node.getChildNodes().item(0).getTextContent()));	
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("upload")){
						packageinfo.setUpload(Long.parseLong(node.getChildNodes().item(0).getTextContent()));	
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("download")){
						packageinfo.setDownload(Long.parseLong(node.getChildNodes().item(0).getTextContent()));	
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("concurrentsession")){
						packageinfo.setConcurrentsession(Integer.parseInt(node.getChildNodes().item(0).getTextContent()));	
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("maxfilesize")){
						packageinfo.setMaxfilesize(Long.parseLong(node.getChildNodes().item(0).getTextContent()));	
					}	
					else if(node.getChildNodes().item(0).getNodeName().equals("hasencryption")){							
						boolean b = new Boolean(node.getChildNodes().item(0).getTextContent()).booleanValue();
						packageinfo.setHasencryption(b);
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("expire")){
						packageinfo.setExpire(node.getChildNodes().item(0).getTextContent());	
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("maxbackuppc")){
						packageinfo.setMaxbackuppc(Integer.parseInt(node.getChildNodes().item(0).getTextContent()));	
					}
					else if(node.getChildNodes().item(0).getNodeName().equals("featurelist")){
						packageinfo.setFeaturelist(node.getChildNodes().item(0).getTextContent());	
					}
					pinfo.add(packageinfo);		
				}		 
			
			rsp.setPackageInfo(pinfo);	
			
			if(node.getNodeName().equals("auxpasswordurl")){
				rsp.setAuxpasswordurl(node.getTextContent());
			}
			if (node.getNodeName().equals("time")) {
				rsp.setTime(node.getTextContent());
			}
			
			rsp.setServiceGateway(server);
		}
		//System.out.println(rsp.toString());
		// The correct response payload's status value must equals "0"
		if ( "2".equals(rsp.getStatus()) )
		{
			throw new Exception("Error : Authentication Fail.");
		}
		if ( "999".equals(rsp.getStatus()) )
		{
			throw new Exception("Error : The user id do not exist.");
		}
		
		if ( !"0".equals(rsp.getStatus()) )
		{
			//System.out.println("AcquireToken: code:" + rsp.getStatus());
			logger.error("AcquireToken: code:" + rsp.getStatus());
			throw new Exception("Error in AcquireToken, status code:" + rsp.getStatus());
		}		
		return rsp;
	}
	
	public AcquireTokenResponse getResponse(String userid, String pwd) throws Exception
	{		
		return getResponse(userid, pwd, "");
	}
	


	/** We generate an HTTP header called "Authorization" with the relevant OAuth parameters for the request,
	 * it contains signature_method, timestamp, nonce and signature parameters. 
	 * 
	 * 1. signature_method: HMAC-SHA1 hashing algorithm.
	 * 2. timestamp: The number of milliseconds since 1970-01-01 00:00:00.
	 * 3. nonce: The random number is unique , it can only be sent once during 60 minutes.
	 * 4. signature: It contains a querystring parameters("signature_method", "timestamp" and "nonce"). 
	 * This parameters sort by alphabet and it needs to be URL encoded, then use the ProgKey to create the original signature in HMAC-SHA1 algorithm. 
	 * Finally, original signature value needs Base64 Hash conversion, once again URL encode, the results is the signature. 	   
	 * */	
	//@SuppressWarnings("deprecation")
	public String composeAuthorizationHeader() throws Exception
	{		
		StringBuilder authorization = new StringBuilder();

		final String SIGNATURE_METHOD = "HMAC-SHA1";
		String nonce = UUID.randomUUID().toString().replaceAll("-", "");// The random number is unique , it can only be sent once during 60 minutes		
		String timestamp = String.valueOf((long)Calendar.getInstance().getTimeInMillis());// The number of milliseconds since 1970-01-01 00:00:00
		String signature = null;

		//Step 1, Compose signature string
		StringBuilder signaturePre = new StringBuilder();
		signaturePre.append("nonce=").append(nonce)
					.append("&signature_method=").append(SIGNATURE_METHOD)
					.append("&timestamp=").append(timestamp);

		//Step 2, Doing urlencode before doing hash
		String signatureURLEn = URLEncoder.encode(signaturePre.toString(), "UTF-8");

		//Java Only Support HMACSHA1
		String signMethod = SIGNATURE_METHOD.replaceAll("-", "");

		//Step 3, Doing hash signature string by HMAC-SHA1
		SecretKey sk = new SecretKeySpec(WebStorageUser.ProgKey().getBytes("UTF-8"), signMethod);
		Mac m = Mac.getInstance(signMethod);
		m.init(sk);
		byte[] mac = m.doFinal(signatureURLEn.getBytes("UTF-8"));

		//Step 4, Doing base64 encoding & doing urlencode again 
		signature =Base64.encodeBase64String(mac);
		//signature=new sun.misc.BASE64Encoder().encode(mac);
		signature=URLEncoder.encode(signature, "UTF-8");	////signature=URLEncoder.encode(signature);
		//Final step, Put all parameters to be authorization header string
		authorization.append("signature_method=\"").append(SIGNATURE_METHOD).append("\",")
		.append("timestamp=\"").append(timestamp).append("\",")
		.append("nonce=\"").append(nonce).append("\",")
		.append("signature=\"").append(signature).append("\"");
		
		return authorization.toString();
	}	
}