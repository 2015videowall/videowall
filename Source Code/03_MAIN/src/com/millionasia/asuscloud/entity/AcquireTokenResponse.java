package com.millionasia.asuscloud.entity;

import java.util.LinkedList;

public class AcquireTokenResponse 
{
	/* all fields */
	private String _status = null;
	private String _token = null;
	private String _infoRelay = null;
	private String _webRelay = null;
	private String _searchServer = null;
	private String _credential = null;
	private String _credentialState = null;
	private LinkedList<PackageInfo> _packageInfo = null;
	private String _auxpasswordurl = null;
	private String _time = null;
	private String _serviceGateway = null;

	private static final String HTTPS = "https://";

	/* default constructor */
	public AcquireTokenResponse()
	{		
	}

	/* all fields constructor */
	public AcquireTokenResponse(String token, String ir, String wr, String ss, String credential, String credentialState, LinkedList<PackageInfo> packageInfo, String auxpasswordurl, String time, String ServiceGateway)
	{
		this._token = token;
		this._infoRelay = ir;
		this._webRelay = wr;
		this._searchServer = ss;
		this._credential = credential;
		this._credentialState = credentialState;
		this._packageInfo = packageInfo;
		this._auxpasswordurl = auxpasswordurl;
		this._time = time;
		this._serviceGateway = time;
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

	public void setWebRelay(String _webRelay) {
		this._webRelay = HTTPS + _webRelay;
	}	
	
	public void setSearchServer(String _searchServer) {
		this._searchServer = HTTPS + _searchServer;
	}	

	public void setCredential(String _credential) {
		this._credential = _credential;
	}

	public void setCredentialState(String _credentialState) {
		this._credentialState = _credentialState;
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
	
	public void setServiceGateway(String _serviceGateway) {
		this._serviceGateway = _serviceGateway;
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

	public String getWebRelay() {
		return _webRelay;
	}
	
	public String getSearchServer() {
		return _searchServer;
	}

	public String getCredential() {
		return _credential;
	}

	public String getCredentialState() {
		return _credentialState;
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
	public String getServiceGateway() {
		return _serviceGateway;
	}

	/* toString */
	public String toString() 
	{
		StringBuilder msg = new StringBuilder("AcquiretokenResponse=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" token:").append(_token).append("\n");
		msg.append(" inforelay:").append(_infoRelay).append("\n");
		msg.append(" webrelay:").append(_webRelay).append("\n");
		msg.append(" searchserver:").append(_searchServer).append("\n");
		msg.append(" credential:").append(_credential).append("\n");
		msg.append(" credentialstate:").append(_credentialState).append("\n");
		msg.append(" packageinfo:").append(_packageInfo).append("\n");
		msg.append(" auxpasswordurl:").append(_auxpasswordurl).append("\n");
		msg.append(" time:").append(_time).append("\n");		
		msg.append(" serviceGateway:").append(_serviceGateway).append("\n");		
		return msg.toString();
	}
}
