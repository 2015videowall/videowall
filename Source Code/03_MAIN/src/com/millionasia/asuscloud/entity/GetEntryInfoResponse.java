package com.millionasia.asuscloud.entity;

public class GetEntryInfoResponse 
{
	/* all fields */
	private String _status = null;
	private String _isfolder = null;
	private String _display = null;
	private String _parent = null;
	private String _isbackup = null;
	private String _attribute = null;
	private String _mimetype = null;
	private String _isinfected = null;
	private String _filesize = null;
	private String _createdtime = null;
	private String _headversion = null;
	private String _contributor = null;
	private String _owner = null;
	
	/* default constructor */
	public GetEntryInfoResponse()
	{		
	}

	/* all fields constructor */
	public GetEntryInfoResponse(String _status, String _isfolder, String _display, String _parent, String _isbackup, String _attribute, String _mimetype, String _isinfected, String _filesize, String _createdtime, String _headversion, String _contributor, String _owner)
	{
		this._status = _status;
		this._isfolder = _isfolder;
		this._display = _display;
		this._parent = _parent;
		this._isbackup = _isbackup;
		this._attribute = _attribute;
		this._mimetype = _mimetype;
		this._isinfected = _isinfected;
		this._filesize = _filesize;
		this._createdtime = _createdtime;
		this._headversion = _headversion;
		this._contributor = _contributor;
		this._owner = _owner;
	}
	
	/* toString */
	public String toString() 
	{
		StringBuilder msg = new StringBuilder("GetEntryInfoResponse=>\n");
		msg.append(" _status:").append(_status).append("\n");
		msg.append(" _isfolder:").append(_isfolder).append("\n");
		msg.append(" _display:").append(_display).append("\n");
		msg.append(" _parent:").append(_parent).append("\n");
		msg.append(" _isbackup:").append(_isbackup).append("\n");
		msg.append(" _attribute:").append(_attribute).append("\n");
		msg.append(" _mimetype:").append(_mimetype).append("\n");
		msg.append(" _isinfected:").append(_isinfected).append("\n");
		msg.append(" _filesize:").append(_filesize).append("\n");
		msg.append(" _createdtime:").append(_createdtime).append("\n");
		msg.append(" _headversion:").append(_headversion).append("\n");
		msg.append(" _contributor:").append(_contributor).append("\n");
		msg.append(" _owner:").append(_owner).append("\n");
		return msg.toString();
	}

	/* setters  and getters*/
	public String getStatus() {
		return _status;
	}

	public void setStatus(String _status) {
		this._status = _status;
	}

	public String getIsfolder() {
		return _isfolder;
	}

	public void setIsfolder(String _isfolder) {
		this._isfolder = _isfolder;
	}

	public String getDisplay() {
		return _display;
	}

	public void setDisplay(String _display) {
		this._display = _display;
	}

	public String getParent() {
		return _parent;
	}

	public void setParent(String _parent) {
		this._parent = _parent;
	}

	public String getIsbackup() {
		return _isbackup;
	}

	public void setIsbackup(String _isbackup) {
		this._isbackup = _isbackup;
	}

	public String getAttribute() {
		return _attribute;
	}

	public void setAttribute(String _attribute) {
		this._attribute = _attribute;
	}

	public String getMimetype() {
		return _mimetype;
	}

	public void setMimetype(String _mimetype) {
		this._mimetype = _mimetype;
	}

	public String getIsinfected() {
		return _isinfected;
	}

	public void setIsinfected(String _isinfected) {
		this._isinfected = _isinfected;
	}

	public String getFilesize() {
		return _filesize;
	}

	public void setFilesize(String _filesize) {
		this._filesize = _filesize;
	}

	public String getCreatedtime() {
		return _createdtime;
	}

	public void setCreatedtime(String _createdtime) {
		this._createdtime = _createdtime;
	}

	public String getHeadversion() {
		return _headversion;
	}

	public void setHeadversion(String _headversion) {
		this._headversion = _headversion;
	}
	
	public String getContributor() {
		return _contributor;
	}

	public void setContributor(String contributor) {
		this._contributor = contributor;
	}
	
	public String getOwner() {
		return _owner;
	}

	public void setOwner(String _owner) {
		this._owner = _owner;
	}
}
