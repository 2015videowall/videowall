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

import com.millionasia.asuscloud.entity.FolderBrowseResponse;
import com.millionasia.asuscloud.entity.PageRsp;
import com.millionasia.asuscloud.entity.Parent;
import com.millionasia.asuscloud.entity.PublicFileBase;
import com.millionasia.asuscloud.entity.PublicFolderBase;
import com.millionasia.asuscloud.entity.PublicMetaData;
import com.millionasia.kscloud.entity.DatabaseHelper;
import com.millionasia.kscloud.servlet.WebStorageUser;

public class FolderBrowse 
{	
	
	static String API = "/folder/browse/";// Service api
	private static Logger logger = LogManager.getLogger(FolderBrowse.class.getName());
	static public String Sortby_LastModifiedTime = "2";
	static public String Sortby_FileName = "1";
	
	static public String Sortdirection_ASC = "0";
	static public String Sortdirection_DESC = "1";
	

	public FolderBrowseResponse getResponse(String userid, String inforelay, String token, String folderid, String browseType, String sortby, String sortdirection) throws Exception
	{
	

		String urlstr = inforelay + API;
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
		String root = "browse";// The root element of request payload  
		String rootParent = "page";// The fixed tag name of request payload  
		
		String language = "zh_TW";// The service language
		//String folderid = "44812268";// Browsing the specify folder's ID
		String page = "";// The tag name is "page"
		String pageno = "1";// Page number
		String pagesize = "10";// Amount of information access granted to a page 
		String enable = "0";// The parameters(pagesize) has no effect
		//String sortby = "1"; // The way of sorting file: 1 = Sort the catalog by file(or folder) name | 2 = Sort the catalog by time 
		//String sortdirection = "0";// The way of sorting file: 0 = ASC | 1 = DESC 
		String issibiling = "0";// 0 = List the files of the child node | 1 = List the files of the same parent folder		
			
		String[] elmNameRoot = { "token", "language", "userid", "folderid",	"page", "sortby", "sortdirection", "issibiling", "type" };// Define each XML tag name			
		String[] dataRoot = { token, language, userid, folderid, page, sortby, sortdirection, issibiling, browseType };// Set each value of tag
		
		String[] elmName = { "pageno", "pagesize", "enable" };// Define child node tag name of "page"(XML tag name)
		String[] data = { pageno, pagesize, enable };// Set each value of tag in "page" child nodes
		
		/* To create XML documents. DocumentBuilderFactory can obtain a parser that produces DOM object trees from XML documents */
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement(root);
		document.appendChild(rootElement);  
		Element emPage = document.createElement(rootParent);		
		Element emRoot, em;
		
		int h, i;
		for (h = 0; h < data.length; h++) {
			em = document.createElement(elmName[h]);
			em.appendChild(document.createTextNode(data[h]));
			emPage.appendChild(em);
		}
		
		rootElement.appendChild(emPage);
		
		for (i = 0; i < dataRoot.length; i++) {
			emRoot = document.createElement(elmNameRoot[i]);
			emRoot.appendChild(document.createTextNode(dataRoot[i]));

			if (emRoot.getTagName() == "page") {
				rootElement.appendChild(emPage);
			} else {
				rootElement.appendChild(emRoot);
			}
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
		
		logger.debug("*******FOLDER BROWSE REQUEST*******");
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
		
		logger.debug("*******FOLDER BROWSE RESPONSE*******");
		logger.debug(xmlRsp);
		logger.debug("*************************************");

		FolderBrowseResponse rsp = new FolderBrowseResponse();		
		Parent parent = new Parent();
		PageRsp pagersp = new PageRsp();				
		LinkedList<PublicFolderBase> pfolder =new LinkedList<PublicFolderBase>(); 
		LinkedList<PublicFileBase> pfile = new LinkedList<PublicFileBase>();
		
		// Compose the payload
		int j, k, m;	
		
		DatabaseHelper db = new DatabaseHelper();
		db.openConnection(); 
		
		for (j = 0; j < nodelist.getLength(); j++) {
			Node node = nodelist.item(j);
			
			logger.debug("L1>" + node.getNodeName() + ":" +node.getTextContent() );
			/* status */
			if(node.getNodeName().equals("status")){
				rsp.setStatus(node.getTextContent());
			}	
			
			/* parentfolder */			
			if(node.getNodeName().equals("parentfolder")){	
				for(k = 0; k < node.getChildNodes().getLength(); k++){
					
					logger.debug("L2>" + node.getChildNodes().item(k).getNodeName() + ":" +node.getChildNodes().item(k).getTextContent() );
					
					if(node.getChildNodes().item(k).getNodeName().equals("name")){
						parent.setName(node.getChildNodes().item(k).getTextContent());					
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("id")){
						parent.setId(node.getChildNodes().item(k).getTextContent());
					}
				}
				rsp.setParentfolder(parent);
			}					
			
			/* page */	
			if(node.getNodeName().equals("page")){	
				for(k = 0; k < node.getChildNodes().getLength(); k++){
					logger.debug("L2-page>" + node.getChildNodes().item(k).getNodeName() + ":" +node.getChildNodes().item(k).getTextContent() );
					
					if(node.getChildNodes().item(k).getNodeName().equals("pageno")){
						pagersp.setPageno(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));			
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("pagesize")){
						pagersp.setPagesize(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("totalcount")){
						pagersp.setTotalcount(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("hasnextpage")){
						pagersp.setHasnextpage(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));	
					}
				}				
			}

			/* folder */
			if(node.getNodeName().equals("folder")){	
				PublicFolderBase publicfolderbase = new PublicFolderBase();
				for(k = 0; k < node.getChildNodes().getLength(); k++){	
					logger.debug("L2-folder>" + node.getChildNodes().item(k).getNodeName() + ":" +node.getChildNodes().item(k).getTextContent() );
					if(node.getChildNodes().item(k).getNodeName().equals("id")){
						publicfolderbase.setId(node.getChildNodes().item(k).getTextContent());			
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("display")){
						publicfolderbase.setBase64display(node.getChildNodes().item(k).getTextContent());	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("attribute")){
						publicfolderbase.setAttribute(node.getChildNodes().item(k).getTextContent());	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("issharing")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIssharing(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isencrypted")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIsencrypted(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isowner")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIsowner(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isbackup")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIsbackup(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isorigdeleted")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIsorigdeleted(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("ispublic")){	
						boolean b =new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfolderbase.setIspublic(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("createdtime")){
						publicfolderbase.setCreatedtime(node.getChildNodes().item(k).getTextContent());	
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("markid")){
						publicfolderbase.setMarkid(node.getChildNodes().item(k).getTextContent());							
					}
				}				
				pfolder.add(publicfolderbase);	
			}			

			/* file */	
			if(node.getNodeName().equals("file")){
				PublicFileBase publicfilebase = new PublicFileBase();
				for(k = 0; k < node.getChildNodes().getLength(); k++){
					logger.debug("L2-file>" + node.getChildNodes().item(k).getNodeName() + ":" +node.getChildNodes().item(k).getTextContent() );
					if(node.getChildNodes().item(k).getNodeName().equals("id")){
						publicfilebase.setId(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("status")){
						publicfilebase.setStatus(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("display")){
						publicfilebase.setBase64display(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("attribute")){
						publicfilebase.setAttribute(node.getChildNodes().item(k).getTextContent());
					}

					/* MetaData */
					if(node.getChildNodes().item(k).getNodeName().equals("metadata")){
						PublicMetaData publicmetadata = new PublicMetaData();
						Node nodeChild = node.getChildNodes().item(k);
						for(m = 1; m < nodeChild.getChildNodes().getLength(); m++){
							logger.debug("L3-metadata>" + nodeChild.getChildNodes().item(m).getNodeName() + ":" +nodeChild.getChildNodes().item(m).getTextContent() );
							if(nodeChild.getChildNodes().item(m).getNodeName().equals("tag")){
								publicmetadata.setTag(nodeChild.getChildNodes().item(m).getTextContent());
							}
							else if(nodeChild.getChildNodes().item(m).getNodeName().equals("name")){
								publicmetadata.setName(nodeChild.getChildNodes().item(m).getTextContent());
							}
							else if(nodeChild.getChildNodes().item(m).getNodeName().equals("version")){
								publicmetadata.setVersion(nodeChild.getChildNodes().item(m).getTextContent());
							}
							else if(nodeChild.getChildNodes().item(m).getNodeName().equals("disp")){
								publicmetadata.setDisp(nodeChild.getChildNodes().item(m).getTextContent());
							}
							else if(nodeChild.getChildNodes().item(m).getNodeName().equals("desc")){
								publicmetadata.setDesc(nodeChild.getChildNodes().item(m).getTextContent());
							}
							else if(nodeChild.getChildNodes().item(m).getNodeName().equals("sharelink")){
								publicmetadata.setSharelink(nodeChild.getChildNodes().item(m).getTextContent());
							}
							else if(nodeChild.getChildNodes().item(m).getNodeName().equals("domain")){
								publicmetadata.setDomain(nodeChild.getChildNodes().item(m).getTextContent());
							}
							else if(nodeChild.getChildNodes().item(m).getNodeName().equals("author")){
								publicmetadata.setAuthor(nodeChild.getChildNodes().item(m).getTextContent());
							}
						}
						publicfilebase.setMetadata(publicmetadata);
					}	
					else if(node.getChildNodes().item(k).getNodeName().equals("issharing")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIssharing(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isencrypted")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIsencrypted(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isowner")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIsowner(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isbackup")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIsbackup(b);
					}				
					else if(node.getChildNodes().item(k).getNodeName().equals("isorigdeleted")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIsorigdeleted(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("isinfected")){
						publicfilebase.setIsinfected(Integer.parseInt(node.getChildNodes().item(k).getTextContent()));
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("ispublic")){
						boolean b=new Boolean(node.getChildNodes().item(k).getTextContent()).booleanValue();
						publicfilebase.setIspublic(b);
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("headversion")){
						publicfilebase.setHeadversion(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("createdtime")){
						publicfilebase.setCreatedtime(node.getChildNodes().item(k).getTextContent());
					}
					else if(node.getChildNodes().item(k).getNodeName().equals("markid")){
						publicfilebase.setMarkid(node.getChildNodes().item(k).getTextContent());
					}
				}
				
				int votes = db.getVotes(publicfilebase.getId());
				publicfilebase.setVotes(votes);
				
				pfile.add(publicfilebase);					
			}
		}
		db.release();
		
		rsp.setPage(pagersp);	
		rsp.setFolders(pfolder);
		rsp.setFiles(pfile);

		// The correct response payload's status value must equals "0"
		if ( !"0".equals(rsp.getStatus()) )
		{
			System.out.println("status:"+rsp.getStatus());
			throw new Exception("Error : You have to check FolderBrowse api response, status code:" + rsp.getStatus());
		}
		
		return rsp;
	}
}
