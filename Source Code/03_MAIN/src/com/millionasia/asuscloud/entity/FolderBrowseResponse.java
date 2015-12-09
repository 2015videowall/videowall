package com.millionasia.asuscloud.entity;

public class FolderBrowseResponse
{
	/* all fields */
	private String _status = null;
	private String _scrip = null;
	private Parent _parentfolder = null;
	private PageRsp _page = null;
	private java.util.LinkedList<PublicFolderBase> _folders = null;
	private java.util.LinkedList<PublicFileBase> _files = null;

	/* default constructor */
	public FolderBrowseResponse()
	{
	}

	/* all fields constructor */
	public FolderBrowseResponse(String status, String scrip, Parent parentfolder, PageRsp page, java.util.LinkedList<PublicFolderBase> folders, java.util.LinkedList<PublicFileBase> files)
	{
		this._status = status;
		this._scrip = scrip;
		this._parentfolder = parentfolder;
		this._page = page;
		this._folders = folders;
		this._files = files;
	}
	
	/* setters */
	public void setStatus(String status)
	{
		this._status = status;
	}
	
	public void setScrip(String scrip)
	{
		this._scrip = scrip;
	}
	
	public void setParentfolder(Parent parentfolder)
	{
		this._parentfolder = parentfolder;
	}
	
	public void setPage(PageRsp page)
	{
		this._page = page;
	}
	
	public void setFolders(java.util.LinkedList<PublicFolderBase> folders)
	{
		this._folders = folders;
	}
	
	public void setFiles(java.util.LinkedList<PublicFileBase> files)
	{
		this._files = files;
	}	

	/* getters */
	public String getStatus()
	{
		return _status;
	}
	
	public String getScrip()
	{
		return _scrip;
	}
	
	public Parent getParentfolder()
	{
		return _parentfolder;
	}
	
	public PageRsp getPage()
	{
		return _page;
	}
	
	public java.util.LinkedList<PublicFolderBase> getFolders()
	{
		return _folders;
	}
	
	public java.util.LinkedList<PublicFileBase> getFiles()
	{
		return _files;
	}
		
	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("FolderBrowsePublicResponse=>\n");
		sb.append(" status:").append(_status).append("\n");
		sb.append(" scrip:").append(_scrip).append("\n");
		sb.append(" parentfolder:").append(_parentfolder).append("\n");
		sb.append(" page:").append(_page).append("\n");
		sb.append(" folders:").append(_folders).append("\n");
		sb.append(" files:").append(_files).append("\n");
		return sb.toString();
	}
}
