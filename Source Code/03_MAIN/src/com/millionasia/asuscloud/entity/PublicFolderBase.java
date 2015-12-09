package com.millionasia.asuscloud.entity;

//import sun.misc.BASE64Decoder;
import org.apache.commons.codec.binary.Base64;

public class PublicFolderBase
{
	/* all fields */
	private String _id = null;	
	private String _base64display = null;
	private String _attribute = null;
	private boolean _issharing = false;
	private boolean _isencrypted = false;
	private boolean _isowner = false;
	private boolean _isbackup = false;
	private boolean _isorigdeleted = false;
	private boolean _ispublic = false;
	private String _createdtime = null;
	private String _markid = null;
	private PublicMetaData _metadata = null;

	/* default constructor */
	public PublicFolderBase()
	{
	}

	/* all fields constructor */
	public PublicFolderBase(String id, String base64display, String attribute, boolean issharing, boolean isencrypted, boolean isowner, boolean isbackup, boolean isorigdeleted, boolean ispublic, String createdtime, String markid, PublicMetaData metadata)
	{
		this._id = id;
		this._base64display = base64display;
		this._attribute = attribute;
		this._issharing = issharing;
		this._isencrypted = isencrypted;
		this._isowner = isowner;
		this._isbackup = isbackup;
		this._isorigdeleted = isorigdeleted;
		this._ispublic = ispublic;
		this._createdtime = createdtime;
		this._markid = markid;
		this._metadata = metadata;
	}

	/* setters */
	public void setId(String id)
	{
		this._id = id;
	}

	public void setBase64display(String base64display)
	{
		
		
		//BASE64Decoder decoder = new BASE64Decoder(); 
		try { 
		//byte[] b = decoder.decodeBuffer(base64display); 
			byte[] b = Base64.decodeBase64(base64display); 
		this._base64display= new String(b, "UTF-8"); 
		} catch (Exception e) { 
			this._base64display= null; 
		} 
		
		//this._base64display = Base64.decodeFastToString(base64display);
	}

	public void setAttribute(String attribute)
	{
		this._attribute = attribute;
	}

	public void setIssharing(boolean issharing)
	{
		this._issharing = issharing;
	}

	public void setIsencrypted(boolean isencrypted)
	{
		this._isencrypted = isencrypted;
	}

	public void setIsowner(boolean isowner)
	{
		this._isowner = isowner;
	}

	public void setIsbackup(boolean isbackup)
	{
		this._isbackup = isbackup;
	}

	public void setIsorigdeleted(boolean isorigdeleted)
	{
		this._isorigdeleted = isorigdeleted;
	}

	public void setIspublic(boolean ispublic)
	{
		this._ispublic = ispublic;
	}

	public void setCreatedtime(String createdtime)
	{
		this._createdtime = createdtime;
	}

	public void setMarkid(String markid)
	{
		this._markid = markid;
	}

	public void setMetadata(PublicMetaData metadata)
	{
		this._metadata = metadata;
	}

	/* getters */
	public String getId()
	{
		return _id;
	}

	public String getBase64display()
	{
		return _base64display;
	}

	public String getAttribute()
	{
		return _attribute;
	}

	public boolean getIssharing()
	{
		return _issharing;
	}

	public boolean getIsencrypted()
	{
		return _isencrypted;
	}

	public boolean getIsowner()
	{
		return _isowner;
	}

	public boolean getIsbackup()
	{
		return _isbackup;
	}

	public boolean getIsorigdeleted()
	{
		return _isorigdeleted;
	}

	public boolean getIspublic()
	{
		return _ispublic;
	}

	public String getCreatedtime()
	{
		return _createdtime;
	}

	public String getMarkid()
	{
		return _markid;
	}

	public PublicMetaData getMetadata()
	{
		return _metadata;
	}

	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("PublicFolderBase=>\n");
		sb.append(" id:").append(_id).append("\n");
		sb.append(" base64display:").append(_base64display).append("\n");
		sb.append(" attribute:").append(_attribute).append("\n");
		sb.append(" issharing:").append(_issharing).append("\n");
		sb.append(" isencrypted:").append(_isencrypted).append("\n");
		sb.append(" isowner:").append(_isowner).append("\n");
		sb.append(" isbackup:").append(_isbackup).append("\n");
		sb.append(" isorigdeleted:").append(_isorigdeleted).append("\n");
		sb.append(" ispublic:").append(_ispublic).append("\n");
		sb.append(" createdtime:").append(_createdtime).append("\n");
		sb.append(" markid:").append(_markid).append("\n");
		sb.append(" metadata:").append(_metadata).append("\n");
		return sb.toString();
	}
}