package com.millionasia.kscloud.entity;

import java.sql.*;
import java.util.Hashtable;

import javax.sql.DataSource;

import com.millionasia.kscloud.servlet.Configurations;

public abstract class BaseTableObject {
	
	public BaseTableObject(DataSource ds) throws Exception{
		mDs = ds;

	}
	
	public BaseTableObject() throws Exception{
		mDs = Configurations.getDataSource();

	}
	
	public Connection getConnection() {
		return mConn;
	}

	public void setConnection(Connection mConn) {
		this.mConn = mConn;
	}
	
	protected Connection mConn;
	protected Statement  mStatement;
	protected PreparedStatement  mPreStatement;
	protected DataSource  mDs;
	
/*
	protected void openConnection() throws Exception{
	    try {
		    	Context inictx = new InitialContext();
			    Context m_envctx = null;
			    DataSource ds = null;
		
			    m_envctx = (Context) inictx.lookup("java:comp/env");
	
		    	if(null!=m_envctx){
				    ds = (DataSource)m_envctx.lookup("maDBPool"); 
				   if(mConn==null)
					   mConn = ds.getConnection();

		    	}
	       }catch( com.microsoft.sqlserver.jdbc.SQLServerException sqlex){
			    throw sqlex;
	       } catch (Exception e) {
	    	   e.printStackTrace();
	    	   throw e;
	       }
	}
	*/
	
	public void openConnection(DataSource ds) throws Exception{
	    try {
	    		mDs = ds;
	    		openConnection();
	       }catch( com.microsoft.sqlserver.jdbc.SQLServerException sqlex){
			    throw sqlex;
	       } catch (Exception e) {
	    	   e.printStackTrace();
	    	   throw e;
	       }
	}
	
	public void openConnection() throws Exception{
	    try {
	    		if(mConn == null){
	    			mConn = mDs.getConnection();
	    		}
	       }catch( com.microsoft.sqlserver.jdbc.SQLServerException sqlex){
			    throw sqlex;
	       } catch (Exception e) {
	    	   e.printStackTrace();
	    	   throw e;
	       }
	}
	
	
	protected void releaseConnection() throws Exception{
	    try {
	    	if(mStatement != null){
	    		mStatement.close();
	    		mStatement=null;
	    	}
	    	
	    	
	    	if(mPreStatement != null){
	    		mPreStatement.close();
	    		mPreStatement=null;
	    	}
	    	if(mConn != null){
	    		mConn.close();
	    		mConn=null;
	    	}
	    	
		
	       }catch( com.microsoft.sqlserver.jdbc.SQLServerException sqlex){
	    	   
			    throw sqlex;
	       } catch (Exception e) {
	    	   e.printStackTrace();
	    	   throw e;
	       }
	}
	
	public boolean isClosed() throws Exception{
		boolean rtnValue = false;
	    try {
	    	if(mConn == null){
	    		rtnValue = true;
	    	}
	    	if(mConn != null){
		    	if(mConn.isClosed())
		    		rtnValue = true;
	    	}
		
	       }catch( com.microsoft.sqlserver.jdbc.SQLServerException sqlex){
	    	   sqlex.printStackTrace();
			    throw sqlex;
	       } catch (Exception e) {
	    	   e.printStackTrace();
	    	   throw e;
	       }
	    return rtnValue;
	}
	
	abstract public void insert() throws SQLException, Exception;
	abstract public void update() throws SQLException, Exception;
	abstract public void delete() throws SQLException, Exception;
	abstract public Hashtable<?, ?> selectAll() throws SQLException, Exception;
	abstract public <T>boolean isExist(T ID) throws SQLException, Exception;
	abstract public void release() throws Exception;
	
	
}
