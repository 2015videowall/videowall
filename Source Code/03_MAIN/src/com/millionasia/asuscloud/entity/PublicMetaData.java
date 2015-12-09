package com.millionasia.asuscloud.entity;

public class PublicMetaData
{
	/* all fields */
	private String _tag = null;
	private String _name = null;
	private String _version = null;
	private String _disp = null;
	private String _desc = null;
	private String _sharelink = null;
	private String _domain = null;
	private String _author = null;

	/* default constructor */
	public PublicMetaData()
	{
	}

	/* all fields constructor */
	public PublicMetaData(String tag, String name, String version, String disp, String desc, String sharelink, String domain, String author)
	{
		this._tag = tag;
		this._name = name;
		this._version = version;
		this._disp = disp;
		this._desc = desc;
		this._sharelink = sharelink;
		this._domain = domain;
		this._author = author;
	}
	
	/* setters */
	public void setTag(String tag)
	{
		this._tag = tag;
	}

	public void setName(String name)
	{
		this._name = name;
	}

	public void setVersion(String version)
	{
		this._version = version;
	}

	public void setDisp(String disp)
	{
		this._disp = disp;
	}

	public void setDesc(String desc)
	{
		this._desc = desc;
	}

	public void setSharelink(String sharelink)
	{
		this._sharelink = sharelink;
	}

	public void setDomain(String domain)
	{
		this._domain = domain;
	}

	public void setAuthor(String author)
	{
		this._author = author;
	}

	/* getters */
	public String getTag()
	{
		return _tag;
	}

	public String getName()
	{
		return _name;
	}

	public String getVersion()
	{
		return _version;
	}

	public String getDisp()
	{
		return _disp;
	}

	public String getDesc()
	{
		return _desc;
	}

	public String getSharelink()
	{
		return _sharelink;
	}

	public String getDomain()
	{
		return _domain;
	}

	public String getAuthor()
	{
		return _author;
	}
	
	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("PublicMetaData=>\n");
		sb.append(" tag:").append(_tag).append("\n");
		sb.append(" name:").append(_name).append("\n");
		sb.append(" version:").append(_version).append("\n");
		sb.append(" disp:").append(_disp).append("\n");
		sb.append(" desc:").append(_desc).append("\n");
		sb.append(" sharelink:").append(_sharelink).append("\n");
		sb.append(" domain:").append(_domain).append("\n");
		sb.append(" author:").append(_author).append("\n");
		return sb.toString();
	}
}