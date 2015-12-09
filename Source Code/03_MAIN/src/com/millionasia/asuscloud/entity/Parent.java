package com.millionasia.asuscloud.entity;

public class Parent
{
	/* all fields */
	private String _name = null;
	private String _id = null;

	/* default constructor */
	public Parent()
	{
	}

	/* all fields constructor */
	public Parent(String name, String id)
	{
		this._name = name;
		this._id = id;
	}	

	/* setters */
	public void setName(String name)
	{
		this._name = name;
	}

	public void setId(String id)
	{
		this._id = id;
	}

	/* getters */
	public String getName()
	{
		return _name;
	}

	public String getId()
	{
		return _id;
	}

	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Parentfolder=>\n");
		sb.append(" name:").append(_name).append("\n");
		sb.append(" id:").append(_id).append("\n");
		return sb.toString();
	}
}