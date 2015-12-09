package com.millionasia.kscloud.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.millionasia.kscloud.servlet.Configurations;

public class DatabaseHelper extends BaseTableObject {

	final String mTableName = "";


	public DatabaseHelper() throws Exception{
		super(Configurations.getDataSource());
	}

	@Override
	public	void insert() throws SQLException, Exception {
	    throw new Exception("The class do not support this function");

	}

	@Override
	public	void update()  throws SQLException, Exception {
		throw new Exception("The class do not support this function");
	}

	@Override
	public	void delete()  throws SQLException, Exception {
		throw new Exception("The class do not support this function");
	}
	
	@Override
	public	 void release() throws Exception {
			this.releaseConnection();		
	}

	@Override
	public Hashtable<?, ?> selectAll() throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> boolean isExist(T ID) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return false;
	}	
	
	public List<String> getUserList(String schoolname) throws SQLException, Exception {
		if(this.mConn == null)
			this.openConnection(Configurations.getDataSource());
		
		List<String> list = new ArrayList<String>();
	    
	    String strSQL ="select ig.Userid from SchoolInfo si, IDGroup ig " +
                                   "where si.GroupID = ig.GroupID and (si.SchoolName like '%?%')";
	    
		mPreStatement = mConn.prepareStatement(strSQL);
		mPreStatement.setString(1, schoolname);
		ResultSet rs = mPreStatement.executeQuery();
	    

		while(rs.next()){
			list.add(rs.getString("Userid"));
		}
		rs.close();
		mPreStatement.close();
		
		return list;
	}	
	
	public boolean vote(String userID, String fileID) throws Exception{
		if(this.mConn == null)
			this.openConnection(Configurations.getDataSource());

		boolean rtnvalue = false;
	    String strSQL ="insert into Vote(Userid, Fileid) values(?,?)";
	    if(!isVoted(userID)){
			mPreStatement = mConn.prepareStatement(strSQL);
			mPreStatement.setString(1, userID);
			mPreStatement.setString(2, fileID);
			mPreStatement.executeUpdate();
			mPreStatement.close();
			rtnvalue = true;
	    }
	    return rtnvalue;

	}
	
	public boolean isVoted(String userID) throws Exception{
		if(this.mConn == null)
			this.openConnection(Configurations.getDataSource());

		boolean rtnValue = false;
	    String strSQL ="select * from Vote where Userid = ?";
	    
		mPreStatement = mConn.prepareStatement(strSQL);
		mPreStatement.setString(1, userID);
		ResultSet rs = mPreStatement.executeQuery();

		if(rs.next()){
			rtnValue = true;
		}
		rs.close();
		mPreStatement.close();
		
		return rtnValue;

	}
	
	public int getVotes(String fileID) throws Exception{
		if(this.mConn == null)
			this.openConnection(Configurations.getDataSource());

		int count = 0;
	    String strSQL ="select count(*) num from Vote where Fileid = ?";
	    
		mPreStatement = mConn.prepareStatement(strSQL);
		mPreStatement.setString(1, fileID);
		ResultSet rs = mPreStatement.executeQuery();

		if(rs.next()){
			count = rs.getInt(1);
		}
		rs.close();
		mPreStatement.close();
		
		return count;

	}
	
	public String getMyVote(String userID) throws Exception{
		if(this.mConn == null)
			this.openConnection(Configurations.getDataSource());

		String rtnValue = "";
	    String strSQL ="select Fileid from Vote where Userid = ?";
	    
		mPreStatement = mConn.prepareStatement(strSQL);
		mPreStatement.setString(1, userID);
		ResultSet rs = mPreStatement.executeQuery();

		if(rs.next()){
			rtnValue = rs.getString(1);
		}
		rs.close();
		mPreStatement.close();
		
		return rtnValue;

	}

}
