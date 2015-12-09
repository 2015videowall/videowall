package com.millionasia.asuscloud.entity;

public class SqlQueryResponse
{
	/* all fields */
	private String _status = null;
	private String _total = null;
	private java.util.LinkedList<SearchResultEntry> _entries = null;

	/* default constructor */
	public SqlQueryResponse()
	{
	}

	/* all fields constructor */
	public SqlQueryResponse(String status, String total, java.util.LinkedList<SearchResultEntry> entries)
	{
		this._status = status;
		this._total = total;
		this._entries = entries;
	}
	
	/* setters */
	public void setStatus(String status)
	{
		this._status = status;
	}
	
	public void setTotal(String total)
	{
		this._total = total;
	}
	
	public void setEntries(java.util.LinkedList<SearchResultEntry> entries)
	{
		this._entries = entries;
	}

	/* getters */
	public String getStatus()
	{
		return _status;
	}
	
	public String getTotal()
	{
		return _total;
	}
	
	public java.util.LinkedList<SearchResultEntry> getEntries()
	{
		return _entries;
	}
		
	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("FolderBrowsePublicResponse=>\n");
		sb.append(" status:").append(_status).append("\n");
		sb.append(" total:").append(_total).append("\n");
		sb.append(" entries:").append(_entries).append("\n");
		return sb.toString();
	}
}
