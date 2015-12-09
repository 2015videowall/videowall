package com.millionasia.asuscloud.entity;

public class PageRsp
{
	/* all fields */
	private int _pageno = 1;
	private int _pagesize = 0;
	private int _totalcount = 0;
	private int _hasnextpage = 0;

	/* default constructor */
	public PageRsp()
	{
	}

	/* all fields constructor */
	public PageRsp(int pageno, int pagesize, int totalcount, int hasnextpage)
	{
		this._pageno = pageno;
		this._pagesize = pagesize;
		this._totalcount = totalcount;
		this._hasnextpage = hasnextpage;
	}

	/* setters */
	public void setPageno(int pageno)
	{
		this._pageno = pageno;
	}

	public void setPagesize(int pagesize)
	{
		this._pagesize = pagesize;
	}

	public void setTotalcount(int totalcount)
	{
		this._totalcount = totalcount;
	}

	public void setHasnextpage(int hasnextpage)
	{
		this._hasnextpage = hasnextpage;
	}

	/* getters */
	public int getPageno()
	{
		return _pageno;
	}

	public int getPagesize()
	{
		return _pagesize;
	}

	public int getTotalcount()
	{
		return _totalcount;
	}

	public int getHasnextpage()
	{
		return _hasnextpage;
	}	

	/* toString */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("PageRsp=>\n");
		sb.append(" pageno:").append(_pageno).append("\n");
		sb.append(" pagesize:").append(_pagesize).append("\n");
		sb.append(" totalcount:").append(_totalcount).append("\n");
		sb.append(" hasnextpage:").append(_hasnextpage).append("\n");
		return sb.toString();
	}
}