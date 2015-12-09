package com.millionasia.kscloud.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Configurations  implements ServletContextListener{

	private static  Hashtable<String, String> mParas;
	//private final String DESCRIPTION = "Millionasia Technology Ltd.";
	private static Logger logger = LogManager.getLogger(Configurations.class.getName());
    private static DataSource mDs;

	public static DataSource getDataSource(){
		return mDs;
	}

	public static String getParamValue(String name){
		String rtnValue = "";
		if(mParas != null){
			rtnValue =  (String)mParas.get(name);
		}
		return rtnValue;
	}
	
	//private static SharedPoolDataSource tds = null;
	
	public static int getNumActive(){
		return mDs.getNumActive();
	}
	
	public static int getNumIdle(){
		return mDs.getNumIdle();
	}
	
	@Override
	public  void contextDestroyed(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub
	      Enumeration<Driver> drivers = DriverManager.getDrivers();
	        while (drivers.hasMoreElements()) {
	            Driver driver = drivers.nextElement();
	            try {
	                DriverManager.deregisterDriver(driver);
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }

	        }
		
	}
	@Override
	public  void contextInitialized(ServletContextEvent contextEvent) {
		try {
			//System.setProperty("file.encoding", "UTF-8");
	
			mParas = new Hashtable<String, String>();
			ServletContext context = contextEvent.getServletContext();
			String config_file_path = (String) context.getInitParameter("CONFIG_XML");
			logger.info("configuration path: " + config_file_path);
			File file = new File(config_file_path);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			NodeList nodeLst = doc.getElementsByTagName("param");
			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = (Node) nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					Element fstElmnt = (Element) fstNode;
					logger.info("Get config params - " + fstElmnt.getAttribute("name") + ":" + fstElmnt.getTextContent());
					mParas.put(fstElmnt.getAttribute("name"), fstElmnt.getTextContent());
				}

			}
			
			//Create the DBCP Datasource
		          PoolProperties p = new PoolProperties();
		          p.setUrl(mParas.get("DB_URL"));
		          p.setDriverClassName(mParas.get("DB_DRIVER"));
		          p.setUsername(mParas.get("DB_USER_NAME"));
		          p.setPassword(mParas.get("DB_USER_PASS"));
		          p.setMaxActive(Integer.valueOf(mParas.get("DB_MAX_ACTIVE")));
		          p.setMaxIdle(Integer.valueOf(mParas.get("DB_MAX_IDLE")));
		          p.setMinIdle(50);
		          p.setInitSQL("SELECT 1");
		          p.setInitialSize(50);
		          p.setMaxWait(Integer.valueOf(mParas.get("DB_MAX_WAIT")));
		          p.setTestOnBorrow(true);
		          p.setTestOnReturn(false);
		          p.setValidationQuery("SELECT 1");
		          p.setValidationInterval(60000); // 1 minutes
		          p.setTimeBetweenEvictionRunsMillis(30000);  //30 seconds
		          p.setMinEvictableIdleTimeMillis(60000);
		          p.setJmxEnabled(false);
		          p.setTestWhileIdle(false);
		          p.setRemoveAbandoned(true);
		          p.setRemoveAbandonedTimeout(180);
		          p.setLogAbandoned(false);
                  p.setJdbcInterceptors(
		            "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
		            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		          mDs = new DataSource();
		          mDs.setPoolProperties(p);
		          
		     
			
			
			//Create the DBCP Datasource
		      /*
	         DriverAdapterCPDS cpds = new DriverAdapterCPDS();
	         cpds.setDriver(mParas.get("DB_DRIVER"));
	         cpds.setUrl(mParas.get("DB_URL"));
	         cpds.setUser(mParas.get("DB_USER_NAME"));
	         cpds.setPassword(mParas.get("DB_USER_PASS"));
	         cpds.setDescription(DESCRIPTION);

	         
	         tds = new SharedPoolDataSource();
	         tds.setConnectionPoolDataSource(cpds);
	         tds.setMaxActive(Integer.valueOf(mParas.get("DB_MAX_ACTIVE")));
	         tds.setMaxIdle(Integer.valueOf(mParas.get("DB_MAX_IDLE")));
	         tds.setMaxWait(Integer.valueOf(mParas.get("DB_MAX_WAIT")));
	         tds.setDescription(DESCRIPTION);
	         tds.setLogWriter(new PrintWriter(System.out));
	 		mDs = tds;
	 		*/
		    logger.info("**************************************************************");
		    logger.info("DBCP - Connection Pool created.");
		    logger.info("Max active num is " + mParas.get("DB_MAX_ACTIVE") );
		    logger.info("Max idle num is " + mParas.get("DB_MAX_IDLE") );
		    logger.info("**************************************************************");
			logger.info("SYSTEM START UP!!");	
	        
			
		} catch(FileNotFoundException fex){
			System.out.println("The configuration xml path is not found.");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
