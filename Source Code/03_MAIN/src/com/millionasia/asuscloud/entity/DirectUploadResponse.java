package com.millionasia.asuscloud.entity;

public class DirectUploadResponse 
{
	/* all fields */
	private String _status = null;	
	private String _fileid = null;
	private String _rawfilename = null;

	/* default constructor */
	public DirectUploadResponse()
	{		
	}

	/* all fields constructor */
	public DirectUploadResponse(String status, String fileid, String rawfilename)
	{
		this._status = status;
		this._fileid = fileid;
		this._rawfilename = rawfilename;
	}

	/* setters */
	public void setStatus(String _status) {
		this._status = _status;
	}

	public void setFileid(String _fileid) {
		this._fileid = _fileid;
	}

	public void setRawfilename(String _rawfilename) {
		this._rawfilename = _rawfilename;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getFileid() {
		return _fileid;
	}

	public String getRawfilename() {
		return _rawfilename;
	}

	/* toString */
	public String toString() 
	{	
		StringBuilder msg = new StringBuilder("DirectUploadResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" fileid:").append(_fileid).append("\n");
		return msg.toString();	
	}
}
