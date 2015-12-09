package com.millionasia.asuscloud.entity;

public class SearchResultEntry implements Comparable<SearchResultEntry>
{
	/* all fields */
	private String _id = null;
	private String _parent = null;
	private String _rawentryname = null;
	private String _kind = null;
	private String _time = null;
	private boolean _ispublic = false;
	private boolean _isorigdeleted = false;
	private String _marks = null;
	private String _size = null;
	private String _lastchangetime = null;
	private String _userid = null;
	private int _vote = 0;
	
	public String getUserId() {
	
		return _userid;
	}

	public void setUserId(String _userid) {
		
		this._userid = _userid;
	}
	
	
	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
	}

	public String getParent() {
		return _parent;
	}

	public void setParent(String _parent) {
		this._parent = _parent;
	}

	public String getRawEntryName() {
		return _rawentryname;
	}

	public void setRawEntryName(String _rawentryname) {
		this._rawentryname = _rawentryname;
	}

	public String getKind() {
		return _kind;
	}

	public void setKind(String _kind) {
		this._kind = _kind;
	}

	public String getTime() {
		return _time;
	}

	public void setTime(String _time) {
		this._time = _time;
	}

	public boolean IsPublic() {
		return _ispublic;
	}

	public void setPublic(boolean _ispublic) {
		this._ispublic = _ispublic;
	}

	public boolean getIsOrigDeleted() {
		return _isorigdeleted;
	}

	public void setOrigDeleted(boolean _isorigdeleted) {
		this._isorigdeleted = _isorigdeleted;
	}

	public String getMarks() {
		return _marks;
	}

	public void setMarks(String _marks) {
		this._marks = _marks;
	}

	public String getSize() {
		return _size;
	}

	public void setSize(String _size) {
		this._size = _size;
	}

	public String getLastChangeTime() {
		return _lastchangetime;
	}

	public void setLastChangeTime(String _lastchangetime) {
		this._lastchangetime = _lastchangetime;
	}
	
	public int getVotes() {
		return _vote;
	}

	public void setVotes(int _vote) {
		this._vote = _vote;
	}

	/* default constructor */
	public SearchResultEntry()
	{
	}

	/* all fields constructor */
	public SearchResultEntry(String id, String parent, String  rawentryname, String kind, String time, boolean ispublic, boolean isorigdeleted, String marks, String size, String lastchangetime, String userid, int vote)
	{
		this._id = id;
		this._parent = parent;
		this._rawentryname = rawentryname;
		this._kind = kind;
		this._time = time;
		this._ispublic = ispublic;
		this._isorigdeleted = isorigdeleted;
		this._marks = marks;
		this._size = size;
		this._lastchangetime = lastchangetime;
		this._userid= userid;
		this._vote= vote;
	}	

	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Parentfolder=>\n");
		sb.append(" _userid:").append(_userid).append("\n");
		sb.append(" _id:").append(_id).append("\n");
		sb.append(" _parent:").append(_parent).append("\n");
		sb.append(" _rawentryname:").append(_rawentryname).append("\n");
		sb.append(" _kind:").append(_kind).append("\n");
		sb.append(" _time:").append(_time).append("\n");
		sb.append(" _ispublic:").append(_ispublic).append("\n");
		sb.append(" _isorigdeleted:").append(_isorigdeleted).append("\n");
		sb.append(" _marks:").append(_marks).append("\n");
		sb.append(" _size:").append(_size).append("\n");
		sb.append(" _lastchangetime:").append(_lastchangetime).append("\n");
		sb.append(" _vote:").append(_vote).append("\n");
		return sb.toString();
	}

	@Override
	public int compareTo(SearchResultEntry entry) {
		int comparevote=entry.getVotes();
		return comparevote-this._vote;
	}
}