package com.millionasia.asuscloud.entity;

public class PackageInfo
{
	/* all fields */
	private int _id = 0;
	private String _display = null;
	private long _capacity = 0;
	private long _uploadbandwidth = 0;
	private long _downloadbandwidth = 0;
	private long _upload = 0;
	private long _download = 0;
	private int _concurrentsession = 0;
	private long _maxfilesize = 0;
	private boolean _hasencryption = false;
	private String _expire = null;
	private int _maxbackuppc = 3;
	private String _featurelist = null;

	/* default constructor */
	public PackageInfo()
	{
	}

	/* all fields constructor */
	public PackageInfo(int id, String display, long capacity, long uploadbandwidth, long downloadbandwidth, long upload, long download, int concurrentsession, long maxfilesize, boolean hasencryption, String expire, int maxbackuppc, String featurelist)
	{
		this._id = id;
		this._display = display;
		this._capacity = capacity;
		this._uploadbandwidth = uploadbandwidth;
		this._downloadbandwidth = downloadbandwidth;
		this._upload = upload;
		this._download = download;
		this._concurrentsession = concurrentsession;
		this._maxfilesize = maxfilesize;
		this._hasencryption = hasencryption;
		this._expire = expire;
		this._maxbackuppc = maxbackuppc;
		this._featurelist = featurelist;
	}

	/* setters */
	public void setId(int id)
	{
		this._id = id;
	}

	public void setDisplay(String display)
	{
		this._display = display;
	}

	public void setCapacity(long capacity)
	{
		this._capacity = capacity;
	}

	public void setUploadbandwidth(long uploadbandwidth)
	{
		this._uploadbandwidth = uploadbandwidth;
	}

	public void setDownloadbandwidth(long downloadbandwidth)
	{
		this._downloadbandwidth = downloadbandwidth;
	}

	public void setUpload(long upload)
	{
		this._upload = upload;
	}

	public void setDownload(long download)
	{
		this._download = download;
	}

	public void setConcurrentsession(int concurrentsession)
	{
		this._concurrentsession = concurrentsession;
	}

	public void setMaxfilesize(long maxfilesize)
	{
		this._maxfilesize = maxfilesize;
	}

	public void setHasencryption(boolean hasencryption)
	{
		this._hasencryption = hasencryption;
	}

	public void setExpire(String expire)
	{
		this._expire = expire;
	}

	public void setMaxbackuppc(int maxbackuppc)
	{
		this._maxbackuppc = maxbackuppc;
	}

	public void setFeaturelist(String featurelist)
	{
		this._featurelist = featurelist;
	}

	/* getters */
	public int getId()
	{
		return _id;
	}

	public String getDisplay()
	{
		return _display;
	}

	public long getCapacity()
	{
		return _capacity;
	}

	public long getUploadbandwidth()
	{
		return _uploadbandwidth;
	}

	public long getDownloadbandwidth()
	{
		return _downloadbandwidth;
	}

	public long getUpload()
	{
		return _upload;
	}

	public long getDownload()
	{
		return _download;
	}

	public int getConcurrentsession()
	{
		return _concurrentsession;
	}

	public long getMaxfilesize()
	{
		return _maxfilesize;
	}

	public boolean getHasencryption()
	{
		return _hasencryption;
	}

	public String getExpire()
	{
		return _expire;
	}

	public int getMaxbackuppc()
	{
		return _maxbackuppc;
	}

	public String getFeaturelist()
	{
		return _featurelist;
	}

	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("PackageInfo=>\n");
		sb.append(" id:").append(_id).append("\n");
		sb.append(" display:").append(_display).append("\n");
		sb.append(" capacity:").append(_capacity).append("\n");
		sb.append(" uploadbandwidth:").append(_uploadbandwidth).append("\n");
		sb.append(" downloadbandwidth:").append(_downloadbandwidth).append("\n");
		sb.append(" upload:").append(_upload).append("\n");
		sb.append(" download:").append(_download).append("\n");
		sb.append(" concurrentsession:").append(_concurrentsession).append("\n");
		sb.append(" maxfilesize:").append(_maxfilesize).append("\n");
		sb.append(" hasencryption:").append(_hasencryption).append("\n");
		sb.append(" expire:").append(_expire).append("\n");
		sb.append(" maxbackuppc:").append(_maxbackuppc).append("\n");
		sb.append(" featurelist:").append(_featurelist).append("\n");
		return sb.toString();
	}
}