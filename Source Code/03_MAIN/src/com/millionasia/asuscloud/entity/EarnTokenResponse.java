package com.millionasia.asuscloud.entity;

import java.util.LinkedList;

public class EarnTokenResponse 
{
	/* all fields */
	private String _status = null;
	private String _token = null;
	private String _infoRelay = null;
	private String _filerelay = null;
	private String _rssrelay = null;
	private String _contentrelay = null;
	private String _webRelay = null;
	private String _chameleondb = null;
	private String _navigate = null;
	private String _mediarelay= null;
	private String _searchserver= null;
	private String _jobrelay = null;
	private String _managerstudio = null;
	private String _sps = null;
	private LinkedList<PackageInfo> _packageInfo = null;
	private String _auxpasswordurl = null;
	private String _time = null;
	//private LinkedList<TeamInfo> _teamInfo = null;
	

	private static final String HTTPS = "https://";

	/* default constructor */
	public EarnTokenResponse()
	{		
	}

	/* all fields constructor */
	public EarnTokenResponse(String token, String ir, String fr, String rr, String cr, String wr, String ch, String navi, String mr, String ss, String jr, String ms, String sps,LinkedList<PackageInfo> packageInfo, String auxpasswordurl, String time)
	{
		this._token = token;
		this._infoRelay = ir;
		this._filerelay = fr;
		this._rssrelay = rr;
		this._contentrelay = cr;
		this. _webRelay = wr;
		this._chameleondb = ch;
		this._navigate = navi;
		this. _mediarelay= mr;
		this. _searchserver= ss;
		this._jobrelay = jr;
		this. _managerstudio = ms;
		this. _sps = sps;
		this._packageInfo = packageInfo;
		this._auxpasswordurl = auxpasswordurl;
		this._time = time;
	}

	/* setters */
	public void setStatus(String _status) {
		this._status = _status;
	}

	public void setToken(String _token) {
		this._token = _token;
	}

	public void setInfoRelay(String _infoRelay) {
		this._infoRelay = HTTPS + _infoRelay;
	}
	
	public void setFileRelay (String _fileRelay) {
		this._filerelay = HTTPS + _fileRelay;
	}
	
	public void setRssRelay (String _rssRelay) {
		this._rssrelay = HTTPS + _rssRelay;
	}

	public void setContentRelay (String _ContentRelay) {
		this._contentrelay = HTTPS + _ContentRelay;
	}
	
	public void setWebRelay(String _webRelay) {
		this._webRelay = HTTPS + _webRelay;
	}	

	public void setChameleondb(String _chameleondb) {
		this._chameleondb = _chameleondb;
	}
	
	public void setNavigate(String _navigate) {
		this._navigate = _navigate;
	}

	public void setMediaRelay(String _mediarelay) {
		this._mediarelay = _mediarelay;
	}
	
	public void setSearchServer(String _searchserver) {
		this._searchserver = _searchserver;
	}
	
	public void setJobRelay(String _jobrelay) {
		this._jobrelay = _jobrelay;
	}
	
	public void setManagerStudio(String _managerstudio) {
		this._managerstudio = _managerstudio;
	}
	
	public void setSharePointServer(String _sps) {
		this._sps = _sps;
	}

	public void setPackageInfo(LinkedList<PackageInfo> pinfo) {
		this._packageInfo = pinfo;
	}

	public void setAuxpasswordurl(String _auxpasswordurl) {
		this._auxpasswordurl = _auxpasswordurl;
	}

	public void setTime(String _time) {
		this._time = _time;
	}

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getToken() {
		return _token;
	}

	public String getInfoRelay() {
		return _infoRelay;
	}

	public String getFileRelay () {
		return _filerelay;
	}
	
	public String getRssRelay () {
		return _rssrelay;
	}

	public String getContentRelay () {
		return _contentrelay;
	}
	
	public String getWebRelay() {
		return _webRelay;
	}

	public String setChameleondb( ) {
		return _chameleondb;
	}
	
	public String setNavigate( ) {
		return _navigate;
	}

	public String setMediaRelay( ) {
		return _mediarelay;
	}
	
	public String setSearchServer( ) {
		return _searchserver;
	}
	
	public String setJobRelay( ) {
		return _jobrelay;
	}
	
	public String setManagerStudio( ) {
		return _managerstudio;
	}
	
	public String setSharePointServer( ) {
		return _sps;
	}

	public LinkedList<PackageInfo> getPackageInfo() {
		return _packageInfo;
	}

	public String getAuxpasswordurl() {
		return _auxpasswordurl;
	}

	public String getTime() {
		return _time;
	}

	/* toString */
	public String toString() 
	{
		StringBuilder msg = new StringBuilder("EarnTokenResponse=>\n");
		msg.append(" status:").append(_status ).append("\n");
		msg.append(" token:").append(_token).append("\n");
		msg.append(" inforelay:").append(_infoRelay).append("\n");
		msg.append(" filerelay :").append(_filerelay ).append("\n");
		msg.append(" rssrelay :").append(_rssrelay ).append("\n");
		msg.append(" contentrelay :").append(_contentrelay ).append("\n");
		msg.append(" webrelay:").append(_webRelay).append("\n");
		msg.append(" chameleondb :").append(_chameleondb ).append("\n");
		msg.append(" mediarelay :").append(_mediarelay ).append("\n");
		msg.append(" searchserver :").append(_searchserver ).append("\n");
		msg.append(" jobrelay  :").append(_jobrelay  ).append("\n");
		msg.append(" managerstudio  :").append(_managerstudio  ).append("\n");
		msg.append(" sps  :").append(_sps  ).append("\n");
		msg.append(" navigate :").append(_navigate ).append("\n");
		msg.append(" packageinfo:").append(_packageInfo).append("\n");
		msg.append(" auxpasswordurl:").append(_auxpasswordurl).append("\n");
		msg.append(" time:").append(_time).append("\n");		
		return msg.toString();
	}
}
