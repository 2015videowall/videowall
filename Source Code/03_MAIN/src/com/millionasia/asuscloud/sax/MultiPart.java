package com.millionasia.asuscloud.sax;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.millionasia.asuscloud.entity.DirectUploadResponse;

public class MultiPart {
	HttpURLConnection connection;
	OutputStream os = null;
	Map<String, String> cookies = new HashMap<String, String>();

	protected void connect() throws IOException {
		if (os == null) os = connection.getOutputStream();
	}

	protected void write(char c) throws IOException {
		connect();
		os.write(c);
	}

	protected void write(String s) throws IOException {
		connect();
		os.write(s.getBytes());
	}

	protected void newline() throws IOException {
		connect();
		write("\r\n");
	}

	protected void writeln(String s) throws IOException {
		connect();
		write(s);
		newline();
	}

	private static Random random = new Random();

	protected static String randomString() {
		return Long.toString(random.nextLong(), 36);
	}

	String boundary = "---------------------------" + randomString() + randomString() + randomString();

	private void boundary() throws IOException {
		write("--");
		write(boundary);
	}

	/**
	 * Creates a new multipart POST HTTP request on a freshly opened URLConnection
	 *
	 * @param connection an already open URL connection
	 * @throws IOException
	 */
	public MultiPart(HttpURLConnection connection) throws IOException {
		this.connection = connection;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Charset", "UTF-8");
		connection.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);
	}

	/**
	 * Creates a new multipart POST HTTP request for a specified URL
	 *
	 * @param url the URL to send request to
	 * @throws IOException
	 */
	public MultiPart(URL url) throws IOException {
		this((HttpURLConnection) url.openConnection());
	}

	/**
	 * Creates a new multipart POST HTTP request for a specified URL string
	 *
	 * @param urlString the string representation of the URL to send request to
	 * @throws IOException
	 */
	public MultiPart(String urlString) throws IOException {
		this(new URL(urlString));
	}

	/**
	 * adds a cookie to the requst
	 * @param name cookie name
	 * @param value cookie value
	 * @throws IOException
	 */
	public void setCookie(String name, String value) throws IOException {
		cookies.put(name, value);
	}

	/**
	 * adds cookies to the request
	 * @param cookies the cookie "name-to-value" map
	 * @throws IOException
	 */
	public void setCookies(Map<String, String> cookies) throws IOException {
		if (cookies == null) return;
		this.cookies.putAll(cookies);
	}

	/**
	 * adds cookies to the request
	 * @param cookies array of cookie names and values (cookies[2*i] is a name, cookies[2*i + 1] is a value)
	 * @throws IOException
	 */
	public void setCookies(String[] cookies) throws IOException {
		if (cookies == null) return;
		for (int i = 0; i < cookies.length - 1; i+=2) {
			setCookie(cookies[i], cookies[i+1]);
		}
	}

	private void writeName(String name) throws IOException {
		newline();
		write("Content-Disposition: form-data; name=\"");
		write(name);
		write('"');
	}

	/**
	 * adds a string parameter to the request
	 * @param name parameter name
	 * @param value parameter value
	 * @throws IOException
	 */
	public void setParameter(String name, String value) throws IOException {
		boundary();
		writeName(name);
		newline(); newline();
		writeln(value);
	}

	@SuppressWarnings("unused")
	private static void pipe(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[500000];
		int nread;
		//int navailable;
		int total = 0;
		synchronized (in) {
			while((nread = in.read(buf, 0, buf.length)) >= 0) {
				out.write(buf, 0, nread);
				total += nread;
			}
		}
		out.flush();
		buf = null;
	}

	/**
	 * adds a file parameter to the request
	 * @param name parameter name
	 * @param filename the name of the file
	 * @param is input stream to read the contents of the file from
	 * @throws IOException
	 */
	public void setParameter(String name, String filename, InputStream is) throws IOException {
		boundary();
		writeName(name);
		write("; filename=\"");
		write(filename);
		write('"');
		newline();
		write("Content-Type: ");
		String type = URLConnection.guessContentTypeFromName(filename);
		if (type == null) type = "application/octet-stream";
		writeln(type);
		newline();
		pipe(is, os);
		newline();
	}

	/**
	 * adds a file parameter to the request
	 * @param name parameter name
	 * @param file the file to upload
	 * @throws IOException
	 */
	public void setParameter(String name, File file) throws IOException {
		setParameter(name, file.getPath(), new FileInputStream(file));
	}

	/**
	 * adds a parameter to the request; if the parameter is a File, the file is uploaded, otherwise the string value of the parameter is passed in the request
	 * @param name parameter name
	 * @param object parameter value, a File or anything else that can be stringified
	 * @throws IOException
	 */
	public void setParameter(String name, Object object) throws IOException {
		if (object instanceof File) {
			setParameter(name, (File) object);
		} else {
			setParameter(name, object.toString());
		}
	}

	/**
	 * adds parameters to the request
	 * @param parameters "name-to-value" map of parameters; if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
	 * @throws IOException
	 */
	public void setParameters(Map<String, String> parameters) throws IOException {
		if (parameters == null) return;
		for (Iterator<Map.Entry<String,String>> i = parameters.entrySet().iterator(); i.hasNext();) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>)i.next();
			setParameter(entry.getKey().toString(), entry.getValue());
		}
	}

	/**
	 * adds parameters to the request
	 * @param parameters array of parameter names and values (parameters[2*i] is a name, parameters[2*i + 1] is a value); if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
	 * @throws IOException
	 */
	public void setParameters(Object[] parameters) throws IOException {
		if (parameters == null) return;
		for (int i = 0; i < parameters.length - 1; i+=2) {
			setParameter(parameters[i].toString(), parameters[i+1]);
		}
	}

	/**
	 * posts the requests to the server, with all the cookies and parameters that were added
	 * @return input stream with the server response
	 * @throws Exception 
	 */
	public String fileID = "";
	public String status = "";
	
	public void post() throws Exception{
		boundary();
		writeln("--");
		os.close();

		/* Get the response from the server and parse it */						
		DocumentBuilderFactory documentBuilderFactoryResponse = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderResponse = documentBuilderFactoryResponse.newDocumentBuilder();
		Document documentResponse = documentBuilderResponse.parse(connection.getInputStream());
		Element rootResponse = (Element) documentResponse.getDocumentElement();
		NodeList nodelist;
		nodelist = rootResponse.getChildNodes();

		// Compose the payload
		DirectUploadResponse rsp = new DirectUploadResponse();
		int j;
		for(j=0 ; j<nodelist.getLength() ; j++){
			if(nodelist.item(j).getNodeName().equals("status")){
				rsp.setStatus(nodelist.item(j).getTextContent());
			}					
			if(nodelist.item(j).getNodeName().equals("fileid")){
				rsp.setFileid(nodelist.item(j).getTextContent());
			}	
			if(nodelist.item(j).getNodeName().equals("rawfilename")){
				rsp.setRawfilename(nodelist.item(j).getTextContent());
			}	
		}
		//System.out.println("Multipart status code:" + rsp.getStatus());
		status = rsp.getStatus();
		// The correct response payload's status value must equals "0"
		if (!"0".equals(rsp.getStatus())) {
			throw new Exception("Error : You have to check MultiPart, the status code is " + rsp.getStatus());
		} else {
			fileID = rsp.getFileid();
			//System.out.println(rsp.toString());
			//System.out.println("THE UPLOAD FILE'S ID IS : " + rsp.getFileid());
		}
	}
}